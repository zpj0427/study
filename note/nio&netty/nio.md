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
* `MappedByteBuffer`：内存映射数据，可让文件直接在内存中修改，操作系统不需要拷贝一次，即零拷贝

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

#### 3.1.5.3，API及属性

```java
// 将缓冲区数据写出去
public abstract int write(ByteBuffer src) throws IOException;
// 读取数据到缓冲区中
public abstract int read(ByteBuffer dst) throws IOException;

/************FileChannel****************/
// 初始化文件通道
public static FileChannel open(Path path, OpenOption... options);
// 获取内存映射缓冲区
public abstract MappedByteBuffer map(MapMode mode, long position, long size) throws IOException;
// 从源通道中读取数据
public abstract long transferFrom(ReadableByteChannel src, long position, long count) throws IOException;
// 写数据到目标通道去，windows系统下一次最多传输8M，再多需要分段传输
public abstract long transferTo(long position, long count, WritableByteChannel target) throws IOException;
// 文件操作_只读类型
public static final MapMode READ_ONLY = new MapMode("READ_ONLY");
// 文件操作_读写类型
public static final MapMode READ_WRITE = new MapMode("READ_WRITE");
/************ServerSocketChannel****************/
// 初始化通道,根据操作系统类型初始化
public static ServerSocketChannel open() throws IOException;
// 绑定地址信息
public final ServerSocketChannel bind(SocketAddress local) throws IOException;
// 设置是否异步
public final SelectableChannel configureBlocking(boolean block);
// 获取连接的客户端信息
public abstract SocketChannel accept() throws IOException;
// 获取服务端ServerSocket
public abstract ServerSocket socket();
// 注册选择器
public final SelectionKey register(Selector sel, int ops) throws ClosedChannelException;
/************SocketChannel****************/
// 初始化
public static SocketChannel open() throws IOException;
public static SocketChannel open(SocketAddress remote) throws IOException;
// 绑定地址
public abstract SocketChannel bind(SocketAddress local) throws IOException;
// 设置异步
public final SelectableChannel configureBlocking(boolean block) throws IOException;
// 终止输入,不关闭连接
public abstract SocketChannel shutdownInput() throws IOException;
// 终止输出,不关闭连接
public abstract SocketChannel shutdownOutput() throws IOException;
// 获取客户端Socket
public abstract Socket socket();
// 注册选择器
public final SelectionKey register(Selector sel, int ops) throws ClosedChannelException;
```

#### 3.1.5.4，FileChannel进行文件读写

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

* 直接利用通道进行文件读写

```java
/**
 * 利用通道直接进行数据传输
 */
@Test
public void channelFileCopy() throws Exception {
    // 获取读通道
    FileChannel inChannel = FileChannel.open(Paths.get("F:\\1.jpg"), StandardOpenOption.READ);
    // 获取写通道
    FileChannel outChannel = FileChannel.open(Paths.get("F:\\2.jpg"), StandardOpenOption.WRITE,
StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
    // 直接进行通道传输
    //		outChannel.transferFrom(inChannel, 0, inChannel.size());
    inChannel.transferTo(0, inChannel.size(), outChannel);
    inChannel.close();
    outChannel.close();
}
```

* 内存映射缓冲区进行文件编辑

```java
public void txtFileOperate() throws Exception {
    // 创建文件并授权
    RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\test.txt", "rw");
    // 打开通道
    FileChannel fileChannel = randomAccessFile.getChannel();
    // 获取内存映射缓冲区
    // 参数1：MapMode.READ_WRITE，文件操作类型，此处为读写
    // 参数2：0，可以直接修改的起始位置，此处表示从文件头开始修改
    // 参数3: 1024，可以修改的文件长度，此处表示可以修改1024个字节，超过限定长度修改，会报异常 IndexOutOfBoundException
    MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 1024);
    mappedByteBuffer.clear();
    // 对缓冲区操作, 会直接同步到文件
    mappedByteBuffer.put(0, (byte) 97);
    mappedByteBuffer.put(1023, (byte) 122);
    randomAccessFile.close();
    fileChannel.close();
}
```

* 内存映射缓冲区进行文件读写

```java
/**
 * 利用通道完成文件复制_直接缓冲区
 * 通过内存映射缓冲区完成
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

*  分散`Scattering`和聚集`Gatering`：`FileChannel`演示

```java
@Test
public void scatterAndAggregated() throws Exception {
    /* 分散读取 */
    // 创建文件并授权
    RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\test.txt", "rw");
    // 获取通道
    FileChannel inChannel = randomAccessFile.getChannel();
    // 构造缓冲区, 构造分散缓冲区
    ByteBuffer bufferFirst = ByteBuffer.allocate(128);
    ByteBuffer bufferSecond = ByteBuffer.allocate(1024);
    ByteBuffer[] lstBuffers = { bufferFirst, bufferSecond };
    // 进行分散读取
    inChannel.read(lstBuffers);
    // 解析数据
    for (ByteBuffer buffer : lstBuffers) {
        // 从读状态转为写状态, 并输出
        buffer.flip();
        System.out.println(
            "初始化长度: " + buffer.capacity() + ", 结果数据: " + new String(buffer.array(), 0, buffer.limit()));
    }
    /*******************************************************************/
    /* 聚集写入 */
    RandomAccessFile accessFile = new RandomAccessFile("F://2.txt", "rw");
    FileChannel outChannel = accessFile.getChannel();
    outChannel.write(lstBuffers);
    // 关闭资源
    inChannel.close();
    outChannel.close();
    randomAccessFile.close();
    accessFile.close();
}
```

* 分散`Scattering`和聚集`Gatering`

```java
@Test
public void scatterAndAggregated() throws Exception {
    /* 分散读取 */
    // 创建文件并授权
    RandomAccessFile randomAccessFile = new RandomAccessFile("F:\\test.txt", "rw");
    // 获取通道
    FileChannel inChannel = randomAccessFile.getChannel();
    // 构造缓冲区, 构造分散缓冲区
    ByteBuffer bufferFirst = ByteBuffer.allocate(128);
    ByteBuffer bufferSecond = ByteBuffer.allocate(1024);
    ByteBuffer[] lstBuffers = { bufferFirst, bufferSecond };
    // 进行分散读取
    inChannel.read(lstBuffers);
    // 解析数据
    for (ByteBuffer buffer : lstBuffers) {
        // 从读状态转为写状态, 并输出
        buffer.flip();
        System.out.println(
            "初始化长度: " + buffer.capacity() + ", 结果数据: " + new String(buffer.array(), 0, buffer.limit()));
    }
    /*******************************************************************/
    /* 聚集写入 */
    RandomAccessFile accessFile = new RandomAccessFile("F://2.txt", "rw");
    FileChannel outChannel = accessFile.getChannel();
    outChannel.write(lstBuffers);
    // 关闭资源
    inChannel.close();
    outChannel.close();
    randomAccessFile.close();
    accessFile.close();
}
```

### 3.1.6，Buffer和Channel的注意事项和细节梳理

* `ByteBuffer`支持类型化的`put()`和`get()`，`put()`放入的是什么数据，`get()`就应该使用相应的数据类型接收，否则可能会有`BufferUnderFlowException`；**`short`，`int`，`long`**在内存中长度分配不一致，如果存储多个`short`后，用`long`接收，则注定长度越界

```java
@Test
public void cast() {
    // 初始化缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(5);
    // 存储一个 short 数据
    buffer.putShort((short) 1);
    buffer.flip();
    // 通过 long 类型获取, 会报BufferUnderflowException异常
    System.out.println(buffer.getLong());
}
```

* 可以将一个普通的`Buffer`转换为只读`Buffer`，比如`ByteBuffer -> HeapByteBufferR`，只读`Buffer`的写操作会抛出`ReadOnlyBufferException`异常

```java
@Test
public void readOnly() {
    // 初始化缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(5);
    // 存储数据到缓冲区
    buffer.put("a".getBytes());
    // 设置缓冲区为只读
    buffer = buffer.asReadOnlyBuffer();
    // 进行读写转换
    buffer.flip();
    // 读取数据, 读取数据正常
    System.out.println(new String(new byte[] {buffer.get()}));
    // 写数据, 因为已经设置只读, 写数据报ReadOnlyBufferException异常
    buffer.put("123".getBytes());
}
```

* NIO提供了`MappedByteBuffer`内存映射缓冲区，可以让文件直接在内存中进行修改，并同步到磁盘文件中

* NIO支持`Buffer`缓冲区的分散`Scattering`和聚集`Gatering`操作，通过多个`Buffer`完成一个操作

### 3.1.7，Selector选择器

#### 3.1.7.1，Selector基本介绍

* NIO是非阻塞式IO，可以用一个线程，处理多个客户端连接，就是使用到`Selector`选择器
* `Selector`能够检测多个注册的通道上是否有时间发生（多个`Channel`可以以事件的方式注册到同一个`Selector`上），如果有时间发生，可以获取事件后针对每一个事件进行相应的处理。这就是使用一个单线程管理多个通道，处理多个连接和请求
* 只有在连接或者通道真正有读写发生时，才进行读写，这就大大减少了系统开销，并且不必要为每一个连接都创建一个线程，不用去维护多个线程
* 避免了多线程之前的上下文切换导致的开销

#### 3.1.7.2，Selector API介绍

```java
/**********Selector API**********/
// 初始化
public abstract boolean isOpen();
// 获取新建的事件数量，并添加到内部 SelectionKey 集合
// 阻塞获取
public abstract int select() throws IOException;
// 阻塞一定时间获取
public abstract int select(long timeout) throws IOException;
// 非阻塞获取
public abstract int selectNow() throws IOException;
// 获取所有注册事件
public abstract Set<SelectionKey> selectedKeys();
/*************SelectionKey API********************/
// 读事件状态码,即1
public static final int OP_READ = 1 << 0;
// 写事件状态码，即4
public static final int OP_WRITE = 1 << 2;
// 连接建立状态码，即8
public static final int OP_CONNECT = 1 << 3;
// 有新连接状态码，即16
public static final int OP_ACCEPT = 1 << 4;
// 获取注册通道
public abstract SelectableChannel channel();
// 获取注册的Selector对象
public abstract Selector selector();
// 获取通道绑定数据
public final Object attachment();
// 获取事件状态码
public abstract int interestOps();
// 修改事件状态码
public abstract SelectionKey interestOps(int ops);
// 是否新连接事件
public final boolean isAcceptable();
// 是否可读事件
public final boolean isReadable();
// 是否可写事件
public final boolean isWritable();
// 是否保持连接事件
public final boolean isConnectable();
```

### 3.1.8，NIO执行流程分析

#### 3.1.8.1，NIO执行流程概述

* 初始化服务端通道和选择器，绑定启动端口，并注册通道（`ServerSocketChannel`）到选择器（`Selector`）上，等待客户端连接
* 客户端连接时，会通过`ServerSocketChannel`获取到一个`SocketChannel`
* `Selector`选择器会通过`select()`方法阻塞监听新建连接， 添加到内部`SelectionKey`集合中后，返回监听到的数量
* 每一次通道注册到选择器后，会包装成一个`SelectionKey`返回，并会添加到内部`SelectionKey`集合中，与`Selector`关联
* `Selector`选择器通过`selectionKeys()`方法获取所有注册的事件，可遍历进行处理
* 通过每一个`SelectionKey`反向获取`channel()`注册的通道`Channel`，并进行后续业务处理

#### 3.1.8.2，NIO执行代码块

* 服务端

```java
package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 完整NIO服务端
 * 
 * @author Administrator
 *
 */
public class NIOServer {

	private Selector selector;

	public void init() throws Exception {
		// 初始化服务端通道, 并绑定端口
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 设置非阻塞
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress(8080));
		// 初始化选择器
		selector = Selector.open();
		// 绑定通道到选择器上, 并初始化为可接收链接
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("初始化服务端成功, 监听端口: " + 8080);
		// 开始进行处理
		start();
	}

	private void start() throws Exception {
		// 存在已经注册就绪的事件
		while (selector.select() > 0) {
			// 获取就绪的所有事件
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			// 遍历事件进行处理
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				// 处理数据
				process(selectionKey);
				iterator.remove();
			}
		}
	}

	private void process(SelectionKey selectionKey) throws IOException {
		if (selectionKey.isAcceptable()) {
			// 从服务链接中获取到客户端连接通道, 并注册为可读
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			System.out.println("服务端接收到一个客户端链接请求, 并注册为读事件, 准备读取客户端数据数据...");
		} else if (selectionKey.isReadable()) {
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			// 初始化缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocate(128);
			int read = socketChannel.read(byteBuffer);
			byteBuffer.flip();
			String content = new String(byteBuffer.array(), 0, read);
			System.out.println("服务端接收到客户端消息, 消息内容为: " + content);
			// 携带一个attach, 准备进行返回
			SelectionKey key = socketChannel.register(selector, SelectionKey.OP_WRITE);
			key.attach("服务端返回数据: " + content);
		} else if (selectionKey.isWritable()) {
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			String content = (String) selectionKey.attachment();
			System.out.println("服务端返回数据为: " + content);
			selectionKey.attach(null);
			if (null == content) {
				socketChannel.register(selector, SelectionKey.OP_READ);
				return;
			}
			// 初始化缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocate(128);
			// 写数据到客户端
			byteBuffer.put(content.getBytes());
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			byteBuffer.clear();
			System.out.println("服务端响应客户端数据完成...");
            // 从新注册为读，准备重新读取数据
			socketChannel.register(selector, SelectionKey.OP_READ);
		}
	}

	public static void main(String[] args) throws Exception {
		new NIOServer().init();
	}

}

```

* 客户端

```java
package com.self.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NIOClientSync {

	private Selector selector;

	// 初始化客户端
	private void init() throws Exception {
		// 初始化客户端通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
		// 并设置为非阻塞
		socketChannel.configureBlocking(false);
		// 初始化选择器
		selector = Selector.open();
		// 注册通道到选择器上, 并初始化状态为可读和可写
		socketChannel.register(selector, SelectionKey.OP_READ);
		System.out.println("客户端初始化完成...");
		// 异步读数据
		read();
		write(socketChannel);
	}

	private void write(SocketChannel socketChannel) throws IOException {
		Scanner scanner = new Scanner(System.in);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		while (scanner.hasNextLine()) {
			String msg = scanner.nextLine();
			if (null == msg) {
				continue;
			}
			if ("EXIT".equalsIgnoreCase(msg)) {
				break;
			}
			byteBuffer.clear();
			byteBuffer.put(msg.getBytes());
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
		}
		scanner.close();
	}

	public void read() {
		new Thread(() -> {
			try {
				while (selector.select() > 0) {
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						SelectionKey selectionKey = iterator.next();
						process(selectionKey);
						iterator.remove();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void process(SelectionKey selectionKey) throws Exception {
		if (selectionKey.isReadable()) {
			System.out.println("客户端接收服务端响应数据");
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			ByteBuffer buffer = ByteBuffer.allocate(256);
			int read = socketChannel.read(buffer);
			buffer.flip();
			System.out.println("服务端响应数据: " + new String(buffer.array(), 0, read));
		}
	}

	public static void main(String[] args) throws Exception {
		new NIOClientSync().init();
	}

}
```

## 3.2，NIO实现群聊系统

### 3.2.1，系统要求

* 编写一个NIO群聊系统，实现服务端和客户端之间的数据简单通讯（非阻塞）
* 实现多人群聊
* 服务端：可以监测用户上线，离线并实现消息转发功能
* 客户端：通过Channel可以无阻塞发送消息给其他所有用户，同时可以接受其他用户发送的消息（由服务器转发得到）
* 目的：进一步理解NIO非阻塞网络编程机制

### 3.2.2，代码实现

* **服务端代码**

```java
package com.self.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊服务端
 * 1, 接收客户端连接
 * 2, 对客户端上线和下线进行检测
 * 3, 客户端发送消息后, 转发显示到其他客户端
 *
 * @author pj_zhang
 * @create 2019-12-15 21:59
 **/
public class GroupChatServer {

    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 服务端通道
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * 服务端监听端口
     */
    private final int PORT = 8080;

    public GroupChatServer() throws Exception {
        // 初始化非阻塞服务端, 并绑定端口
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        // 初始化选择器
        selector = Selector.open();
        // 注册通道到选择器上, 并初始化为监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功, 监听端口: " + PORT);
    }

    public void start() throws Exception {
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 处理事件
                process(selectionKey);
                iterator.remove();
            }
        }
    }

    private void process(SelectionKey selectionKey) throws Exception {
        // 初始化链接
        if (selectionKey.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            // 获取到客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            // 注册到选择器, 并注册为可读
            socketChannel.register(selector, SelectionKey.OP_READ);
            // 服务端提示上线
            String message = socketChannel.getRemoteAddress() + " 上线了...";
            System.out.println(message);
            publishMessage(message, socketChannel);
        } else if (selectionKey.isReadable()) {
            SocketChannel socketChannel = null;
            try {
                // 读取当前客户端发送消息
                socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readCount = socketChannel.read(buffer);
                String showMessage = socketChannel.getRemoteAddress() + " 说: " + new String(buffer.array(), 0, readCount);
                System.out.println(showMessage);
                // 向其他客户端广播消息
                publishMessage(showMessage, socketChannel);
            } catch (IOException e) {
                if (null != socketChannel) {
                    // 读取消息失败, 说明客户端已经下线, 做下线处理
                    System.out.println(socketChannel.getRemoteAddress() + " 下线了...");
                    // 取消注册
                    selectionKey.cancel();
                    // 关闭通道
                    socketChannel.close();
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    private void publishMessage(String showMessage, SocketChannel socketChannel) throws IOException {
        System.out.println("服务端接收到消息, 现在进行转发...");
        // 初始化需要发送的消息为ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(showMessage.getBytes());
        // 遍历每一个注册的客户端进行消息发送
        for (SelectionKey selectionKey : selector.keys()) {
            Channel channel = selectionKey.channel();
            // 跳过自身
            if (channel instanceof SocketChannel && channel != socketChannel) {
                SocketChannel targetChannel = (SocketChannel) channel;
                // 消息发送前进行读写转换, 保证每一次都能发出有效数据
                // 如果出现多个客户端只有一个接收到, 其他没有接受到, 但是服务单正常广播了
                // 优先查看ByteBuffer问题
                byteBuffer.flip();
                targetChannel.write(byteBuffer);
                System.out.println("发送消息成功, Address: " + targetChannel.getRemoteAddress());
            }
        }
        System.out.println("服务端转发消息成功...");
    }

    public static void main(String[] args) throws Exception {
        new GroupChatServer().start();
    }

}
```

* **客户端代码**

```java
package com.self.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * NIO群聊系统_客户端
 * 1, 连接服务端
 * 2, 发送消息到服务端
 * 3, 接口服务端转发的消息
 *
 * @author pj_zhang
 * @create 2019-12-15 22:28
 **/
public class GroupChatClient {

    // 服务端IP
    private final String HOST = "127.0.0.1";

    // 服务端端口
    private final int PORT = 8080;

    private SocketChannel socketChannel;

    private Selector selector;

    public GroupChatClient() throws IOException {
        // 初始化客户端SocketChannel
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        // 初始化选择器
        selector = Selector.open();
        // 绑定事件, 绑定为读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("客户端已经准备完成, NickName: " + socketChannel.getLocalAddress());
    }

    public void start() throws IOException {
        // 接收服务端消息, 此处开线程接收, 保证读写不冲突,不会造成互相影响
        // 先开读, 再去写, 防止写造成的读执行不到
        receiveMessage();
        // 发送消息到服务端
        sendMessage();
    }

    private void receiveMessage() throws IOException {
        // 启动一个线程进行服务端数据接收
        new Thread(() -> {
            try {
                // selector.select() 会阻塞, 直到有连接进入
                while (selector.select() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    for (;iterator.hasNext();) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isReadable()) {
                            // 接收服务端消息并处理
                            SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                            readChannel.configureBlocking(false);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int readCount = readChannel.read(buffer);
                            buffer.flip();
                            System.out.println("接收服务端消息: " + new String(buffer.array(), 0, readCount));
                        }
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("客户端接收消息完成");
        }).start();
    }

    private void sendMessage() throws IOException {
        // 从控制台输入消息
        Scanner scanner = new Scanner(System.in);
        System.out.println("等待客户端输入消息: ");
        // 发送到服务端
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        }
    }

    public static void main(String[] args) throws IOException {
        new GroupChatClient().start();
    }

}
```

## 3.2，零拷贝



## 3.3，Java AIO



# 4，Netty

