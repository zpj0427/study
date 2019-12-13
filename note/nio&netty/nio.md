# 1，Java支持的网络IO模型

* **BIO**：同步阻塞。服务器实现为一个连接对应一个线程，即客户端有请求时，服务端就会启动一个线程进行处理，如果这个连接不做任何事情就会造成不必要的开销
* **NIO**：同步非阻塞。服务器模式为一个服务处理多个请求，即客户端发送的请求会被注册到多路复用器上，多路复用器轮询连接到的IO请求进行处理
* **AIO**： 异步非阻塞。操作系统完成后通知服务端线程处理，一般使用与连接数多且连接时间较长的场景。*应用比较有限*

# 2，Java BIO

## 2.1，Java BIO工作流程

* 服务器启动一个`ServerSocket`
* 客户端启动`Socket`对服务器进行通信，默认情况下服务器需要对每一个客户端连接创建线程通讯
* 客户端发出请求后，咨询服务器是否有线程响应，如果没有则等待或者被拒绝
* 如果有响应，客户端阻塞直到服务端执行完成后响应数据

## 2.2，Java BIO代码演示

* 服务端代码演示

```java
package com.self.netty.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO服务端代码
 * 
 * @author pj_zhang
 * @date 2019年12月13日 上午11:39:17
 */
public class BIOServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// 创建线程池, 进行连接处理
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		// 创建 ServerSocket, 并启动8080端口
		ServerSocket serverSocket = new ServerSocket(8080);
		System.out.println("服务端启动成功, 准备接收消息...");
		for (;;) {
			// 此处阻塞接收客户端链接, 接收到后形成一个Socket套接字
			Socket socket = serverSocket.accept();
			// 直接通过线程池分配线程进行处理
			executorService.execute(() -> {
				try {
					StringBuilder sb = new StringBuilder();
					// 通过输入流读取客户端传递数据
					byte[] bytes = new byte[1024];
					InputStream inputStream = socket.getInputStream();
					int len = 0;
					// 此处如果用循环, 会阻塞一直获取, 需要通过明显的结束字符来控制
					// 方便起见, if玩吧
					if ((len = inputStream.read(bytes)) != -1) {
						sb.append(new String(bytes, 0, len));
					}
					String reveiveMsg = "reveive message: " + sb.toString();
					System.out.println(reveiveMsg);
					// 接收到数据后, 组装返回结果进行返回
					OutputStream outputStream = socket.getOutputStream();
					outputStream.write(reveiveMsg.getBytes());
					outputStream.flush();
					System.out.println("REVEIVE MESSAGE SUCCESS");
					// 关闭资源
					outputStream.close();
					inputStream.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

}
```

* 客户端代码演示

```java
package com.self.netty.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * BIO客户端
 * 
 * @author pj_zhang
 * @date 2019年12月13日 下午12:21:21
 */
public class BIOClient {

	public static void main(String[] args) throws Exception {
		// 创建客户端连接, 连接服务端
		Socket socket = new Socket("127.0.0.1", 8080);
		// 获取输出流, 输出客户端数据到服务端
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(LocalDateTime.now().toString().getBytes());
		outputStream.flush();
		// 获取输入流, 读取服务端传递数据
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		if ((len = inputStream.read(bytes)) != -1) {
			System.out.println(new String(bytes, 0, len));
		}
		// 关闭资源
		socket.close();
		outputStream.close();
		inputStream.close();
	}

}
```

# 3，Java NIO

## 3.1，Java NIO基础概念

### 3.1.1，Java NIO三大核心概念

* 缓冲区（Buffer）
* 通道（Channel）
* 选择器（Selector）

### 3.1.2，Java NIO三大核心关系

* 每个`Channel`对应一个`Buffer`
* `Selector`对应一个县城，一个线程对应多个`Channel`（即连接）
* `Selector`进行轮询切换`Channel`，并由事件决定处理逻辑
* `Buffer`就是一个内存块，底层由数组构成
* 数据的读取和写入通过`Buffer`，并通过`Channel`进行`Buffer`数据传递
* `Channel`是双向处理的

### 3.1.3，NIO和BIO的比较

* BIO以流的方法处理数据，而NIO以块的方式处理数据；效率提升很多
* BIO是阻塞的，NIO是非阻塞的
* BIO基于字节流和字符流进行操作，NIO基于`Buffer`缓冲区和`Channel`通道进行操作，基于`Selector`选择器监听通道中的时间，可以使用单线程监听多个通道数据

### 3.1.4，Buffer缓冲区

**概念**：缓冲区本质上是一个可以读写数据的内存块，底层数据结构为数组

#### 3.1.4.1，常用Buffer类型

* `ByteBuffer`：字节数据
* `ShortBuffer`：短整型数据
* `IntBuffer`：整型数据
* `LongBuffer`：长整型数据
* `CharBuffer`：字符数据
* `FloatBuffer`：短小数数据
* `DoubleBuffer`：长小数数据
* `MappedByteBuffer`：内存映射数据

#### 3.1.4.2，API及属性

* 基本属性

```java
// 0 <= mark <= position <= limit <= capacity 
// 位置标记
private int mark = -1;
// 缓冲区当前操作位置，包括读写位置
private int position = 0;
// 缓冲区当前操作最大索引
private int limit;
// 容量；初始化时候设定，并不能改变
private int capacity;
```

* 常用API

```java
/************************** Buffer **************************/
// 获取缓冲区容量
public final int capacity();
// 获取缓冲区操作位置
public final int position();
// 重置缓冲区操作位置
public final Buffer position(int newPosition);
// 获取缓冲区操作上限
public final int limit();
// 重置缓冲区操作上限
public final Buffer limit(int newLimit);
// 标记缓冲区操作位置
public final Buffer mark();
// 重置缓冲区操作位置到标记位置
public final Buffer reset();
// 清除缓冲区; 各个标记位恢复到初始状态,但是数据并没有真正擦除
public final Buffer clear();
// 反转缓冲区, 缓冲区状态从写到读变更
public final Buffer flip();
// 重置缓冲区操作位
public final Buffer rewind();
// 返回可读/可写元素个数
public final int remaining();
// 返回是否存在可读/可写元素判断
public final boolean hasRemaining();
// 判断缓冲区是否为只读缓冲区
public abstract boolean isReadOnly();
// 判断缓冲区是否为直接缓冲区
public abstract boolean isDirect();
// 转换缓冲区为数组
public abstract Object array();
/******************* ByteBuffer 其他类似 *******************/
// 初始化缓冲
public static ByteBuffer allocate(int capacity);
// 初始化为直接缓冲区
public static ByteBuffer allocateDirect(int capacity);
// 包装数组为缓冲区
public static ByteBuffer wrap(byte[] array);
// 从缓冲区读数据
public abstract byte get();
public abstract byte get(int index);
// 往缓冲区写数据
public abstract ByteBuffer put(byte b);
public abstract ByteBuffer put(int index, byte b);
```

#### 3.1.4.2，Buffer属性值变更，通过一段流程

**Buffer缓冲区支持读和写操作，通过`capacity` `limit` `position` `mark`字段的数值转换进行读写操作切换，涉及的数值状态变更如下：**

* 初始化：`capacity = 5`, `limit = 5`, `position = 0`, `mark = -1`

  *`capacity`和`limit`初始化为缓冲区长度*

  *`position`初始化为0值*

  *`mark`初始化为-1，并且如果不存在`mark`操作，会一直是-1*

```java
// 初始化容量为5，该长度后续稳定
ByteBuffer buffer = ByteBuffer.allocate(5);
ByteBuffer buffer = ByteBuffer.allocateDirect(5);
```

![1576243536858](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576243536858.png)

* 写数据：`capacity = 5`, `limit = 5`, `position = 2`, `mark = -1`

  *写数据后，`mark`, `limit`, `mark`不变，`position`推进长度位*

```java
// 写入两个长度位数据
buffer.put("ab".getBytes());
```

![1576244011830](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576244011830.png)

* 写读转换：`capacity = 5`, `limit = position = 2`, `position = 0`, `mark = -1`

  *写读转换后，将数组中的有效数据返回通过`limit`和`position`包起来，并通过`position`前移进行读取，直到读到`limit`位置，标识整个数组读取完成*

```java
// 缓冲区从写到读转换时，需要调用该方法进行读写位重置
// 将 limit 设置为 position 值，表示最大可读索引
// 将 position 置为0值，表示从0索引开始读
buffer.flip();
```

![1576244295873](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576244295873.png)

* 取数据：`capacity = 5`, `limit = 2`, `position = 1`, `mark = -1`

  *取数据就是对`position`位置进行后移，并不断取数据直到`limit`*

```java
/* 这一部分获取数据后 position 后移 */
// 取下一条数据
buffer.get();
// 取范围数据，演示取一条
byte[] bytes = new byte[1];
buffer.get(bytes, 0, 1);
buffer.get(bytes);
/* 这一部分获取数据后 position 不变 */
// 取指定索引数据
buffer.get(0);

```

![1576244737312](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576244737312.png)

* 设置标记位：`capacity = 5`, `limit = 2`, `position = 1`, `mark = position = 1`

  *设置标记位就是对`position`位置进行标记，值存储在`mark`属性中，后续读取`position`前移，但`mark`值维持不变*

```java
buffer.mark();
```

![1576245021937](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576245021937.png)

* 继续取数据：`capacity = 5`, `limit = 2`, `position = 2`, `mark = 1`

  *如上所说，`position`继续前移，像演示这样，取了后`limit`值与`position`值已经相等，说明已经读取完成，如果再次强行读取，会报`BufferUnderflowException`异常*

![1576245171287](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576245171287.png)

* 标记位重置：`capacity = 5`, `limit = 2`, `position = mark = 1`, `mark = -1`

  *重置标记位与`mark()`方法配合使用，将设置的标记位重置为初始状态。配合使用可以实现对`Buffer`数组中部分区间的重复读取*

```java
buffer.reset();
```

![1576245362562](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576245362562.png)

* 操作位重置：`capacity = 5`, `limit = 2`, `position = 0`, `mark = -1`

  *操作位重置，就是对`position`置0值，`limit`位置不变，且数据不清楚*

```java
buffer.rewind();
```

![1576245513860](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576245513860.png)

* 数据清空：`capacity = 5`, `limit = 5`, `position = 0`, `mark = -1`

  *四个基本属性回到初始化状态，数据清空也只是对基本属性值初始化，并不会对数据进行清空*

```java
buffer.clear();
```

![1576245608145](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1576245608145.png)

### 3.1.5，Channel通道

#### 3.1.5.1，通道与流的区别

* 通道可以同时进行读写，而流只能进行读`inputStream`或者写`outputStream`
* 通道可以进行异步读写数据
* 通道可以从缓存读数据，也可以写数据到缓存中

#### 3.1.5.2，常用Channel类型

* `FileChannel`：本地文件读取通道
* `ServerSocketChannel`：TCP网络服务端通道
* `SocketChannel`：TCP网络通道
* `DatagramChannel`：UDP网络通道

#### 3.1.5.3，FileChannel进行文件读写

* 非直接缓冲区进行文件读写

```java
/**
 * 利用通道完成文件复制_非直接缓冲区
 */
@Test
public void fileCopy() throws Exception {
    // 初始化流
    FileInputStream inputStream = new FileInputStream("F:\\1.jpg");
    FileOutputStream outputStream = new FileOutputStream("F:\\2.jpg");
    // 从流中获取通道
    FileChannel inChannel = inputStream.getChannel();
    FileChannel outChannel = outputStream.getChannel();
    // 初始化化缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    // 通过通道, 从流中读数据到缓冲区
    while (inChannel.read(buffer) != -1) {
        // 切换为写状态
        buffer.flip();
        // 将缓冲区中的数据写出去
        outChannel.write(buffer);
        // 初始化状态, 进行重新读取
        buffer.clear();
    }
    // 关资源
    outputStream.flush();
    inChannel.close();
    outChannel.close();
    outputStream.close();
    inputStream.close();
    System.out.println("执行完成...");
}
```

* 直接缓冲区进行文件读写

```java
/**
 * 利用通道完成文件复制_直接缓冲区
 */
@Test
public void directFileCopy() throws Exception {
    // 获取读通道
    FileChannel inChannel = FileChannel.open(Paths.get("F:\\1.jpg"), StandardOpenOption.READ);
    // 获取写通道
    FileChannel outChannel = FileChannel.open(Paths.get("F:\\2.jpg"), StandardOpenOption.WRITE,
                                              StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
    // 获取内存映射对应的缓冲区
    // MappedByteBuffer 存储在物理内存中
    MappedByteBuffer inMappedByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
    MappedByteBuffer outMappedByteBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
    // 直接通过缓冲区进行读写
    byte[] bytes = new byte[inMappedByteBuffer.limit()];
    inMappedByteBuffer.get(bytes);
    outMappedByteBuffer.put(bytes);
    inChannel.close();
    outChannel.close();
}
```

