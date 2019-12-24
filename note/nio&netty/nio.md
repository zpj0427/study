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

### 3.3.1，服务端

```java
package com.gupao.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*** AIO 服务端 */
public class AIOServer {
    private final int port;

    public static void main(String args[]) {
        int port = 8000;
        new AIOServer(port);
    }

    public AIOServer(int port) {
        this.port = port;
        listen();
    }

    private void listen() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            // 工作线程, 用来进行回调, 事件响应时候进行回调
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup);
            server.bind(new InetSocketAddress(port));
            System.out.println("服务已启动，监听端口" + port);
            // 准备接收数据
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                // 实现 Completed 方法, 进行回调
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("IO 操作成功，开始获取数据");
                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                    System.out.println("操作完成");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("IO 操作是失败: " + exc);
                }
            });
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### 3.3.2，客户端

```java
package com.gupao.io.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author pj_zhang
 * @create 2019-11-12 22:43
 **/
public class AIOClient {

    private final AsynchronousSocketChannel client;

    public AIOClient() throws Exception {
        client = AsynchronousSocketChannel.open();
    }

    public void connect(String host, int port) throws Exception {
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                try {
                    client.write(ByteBuffer.wrap("这是一条测试数据".getBytes())).get();
                    System.out.println("已发送至服务器");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
            }
        });
        final ByteBuffer bb = ByteBuffer.allocate(1024);
        client.read(bb, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("IO 操作完成" + result);
                System.out.println("获取反馈结果" + new String(bb.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        new AIOClient().connect("localhost", 8000);
    }

}
```

# 4，[Netty](https://netty.io/)
## 4.1，Netty概述

### 4.1.1，原生NIO存在的问题

* NIO的类库和API繁杂，使用麻烦。需要熟练的掌握`Selector`，`Channel`，`Buffer`等
* 需要熟悉Java多线程，因为NIO编程涉及到Reactor模型，必须对多线程和网络编程非常熟悉，才能写出高质量的NIO代码
* 开发工作量和难度非常大：比如客户端断连重连，网络闪断，半包读写，失败缓存，网络拥堵和异常流的处理等
* NIO固有的BUG，比如Epoll Bug。会造成`Selector`空轮询，最终导致CPU爆表
### 4.1.2，Netty官网说明

* Netty是有JBOSS提供的一个Java开源框架。Netty提供异步的、基于事件驱动的网络引用程序框架，用以快速开发高性能、高可靠性的网络IO程序
* Netty可以帮助快速、简单的开发一个网络应用，简化并流程化了NIO的开发过程
* Netty是目前最流行的NIO框架，在互联网，大数据，游戏，通信等领域已经得到广泛应用。比如常用框架ES，Dubbo内部都采用Netty

## 4.2，Netty高性能架构设计

### 4.2.1，Reactor线程模型

#### 4.2.1.1，传统IO设计模型

##### 4.2.1.1.1，原理图

![1576657284789](E:\gitrepository\study\note\image\nio\1576657284789.png)

##### 4.2.1.1.2，方案说明

* 客户端发起请求
* 服务端对接收到的每一个请求开启线程，并通过阻塞方式进行业务处理
* 服务端处理完成后，返回数据到客户端，流程完毕

##### 4.2.1.1.3，优缺点分析

* **缺点**：当并发数很大时，会创建大量的线程，占用很大系统资源
* **缺点**：线程创建后，如果暂时没有数据可读，则线程会阻塞在`read()`操作，造成系统资源浪费

#### 4.2.1.2，Reactor模型简介

##### 4.2.1.2.1，对BIO设计模型的解决方案

* **基于IO复用模型**：多个连接共用一个阻塞对象，应用程序只需要在一个阻塞对象中等待，无需等待阻塞所有连接。当某个连接有新的数据可以处理时，操作系统通知应用程序，线程从阻塞状态状态，开始进行处理。因为Reactor模型也被称为***反应器模式***、***分发者模式***、***通知者模式***
* **基于线程池复用线程资源**：不必再为每一个链接创建一个线程，将连接完成后的业务处理分配给线程进行处理，通过线程池模式进行管理

##### 4.2.1.2.2，Reactor模型基本设计思想

![1576658943895](E:\gitrepository\study\note\image\nio\1576658943895.png)

* 多个客户端访问同时传递给服务器端进行处理
* 服务端基于**事件驱动**处理传入的多个请求，分发到对应的线程进行处理

##### 4.2.1.2.3，Reactor模型核心组件

* `Reactor`：相当于NIO的`Selector`，在一个独立的线程中运行，负责监听和分发事件，分发给合适的IO程序对IO事件做出反应。
* `Handlers`：对请求进行业务处理的实际工作线程

##### 4.2.1.2.4，Reactor模型分类

* 单Reactor单线程模型
* 单Reactor多线程模型
* 多Reactor多线程模型

#### 4.2.1.3，单Reactor单线程模型

##### 4.2.1.3.1，原理图

![1576657346264](E:\gitrepository\study\note\image\nio\1576657346264.png)

![1576659605169](E:\gitrepository\study\note\image\nio\1576659605169.png)

##### 4.2.1.3.2，方案说明

* `Selector`是前面IO复用模型介绍的标准网络编程API，可以实现一个阻塞对象监测多路连接请求
* `Reactor`对象通过`Selector`监控客户端请求事件，收到事件后通过`Dispatcher`进行分发
* 如果客户端连接为建立连接事件，则由`Acceptor`进行事件处理，处理完成后继续提交`Handler`进行业务逻辑处理
* 如果客户端连接不是建立连接事件，则由`Reactor`直接分发到`Handler`进行处理
* `Handler`会完成`read` -> `process` -> `send`等操作

##### 4.2.1.3.3，优缺点分析

* **优点**：模型简单，没有多线程，进程通信竞争的问题，全部在一个线程中进行处理
* **缺点**：性能问题，只有一个线程，无法完全发挥CPU的多核特性。`Handler`在处理某个连接上的业务时，整个进行无法处理其他连接事件，很容易导致性能瓶颈
* **缺点**：可靠性问题，线程意外终止，或者进行死循环。会导致整个系统通信模块不可用，节点故障
* Redis基于内存处理，使用的该模式

#### 4.2.1.4，单Reactor多线程模型

##### 4.2.1.4.1，原理图

![1576657421318](E:\gitrepository\study\note\image\nio\1576657421318.png)

![1576659920111](E:\gitrepository\study\note\image\nio\1576659920111.png)

##### 4.2.1.4.2，方案说明

* 接收客户端建立连接，并创建`Handler`对象进行处理与[单Reactor单线程模式](#4.2.1.3，单Reactor单线程模型)基本一致
* `Handler`内部只负责接收数据和响应数据，真正业务执行部分继续分发给`Worker`线程池的某个线程进行业务处理
* `Worker`线程池执行业务完成后，注册写事件到`Selector`，通过`send`发送数据到客户端

##### 4.2.1.4.3，优缺点分析

* **优点**：可以充分利用多核CPU的处理能力
* **缺点**：多线程数据共享和访问比较复杂，`Reactor`处理所有事件的监听和响应，在单线程运行下，高并发场景容易出现性能瓶颈

#### 4.2.1.5，多Reactor多线程模型

##### 4.2.1.5.1，原理图

![1576657447286](E:\gitrepository\study\note\image\nio\1576657447286.png)

![1576660599165](E:\gitrepository\study\note\image\nio\1576660599165.png)

##### 4.2.1.5.2，方案说明

* `Reactor`分为主线程`MainReactor`和分支线程`SubReactor`，`MainReactor`处理连接事件，`SubReactor`处理其他事件
* 当`MainReacotr`处理完成连接事件后，会随机分发给某一`SubReactor`。*一个`MainRector`可以管理多个`SubReactor`*
* `SubReacotr`将连接加入到队列中进行监听，并创建`Handler`进行对应事件处理
* 后续部分与[单Reactor多线程](#4.2.1.4，单Reactor多线程模型)一致

##### 4.2.1.5.3，优缺点分析

* **优点**：`MainReactor`与`SubReactor`的数据交互职责明确，`MainReactor`处理连接请求，`SubReactor`处理业务逻辑
* **优点**：数据交互简单，`MainReactor`只需将数据传递给`SubReactor`即可
* **缺点**：编程复杂度较高

#### 4.2.1.6，Reactor模型小结

##### 4.2.1.6.1，三种案例的生活化解析

* **单Reactor单线程**：前台接待员(`Reactor`)和服务员(线程)是一个人，全程为顾客服务
* **单Reactor多线程**：一个前台接待员(`Reactor`)负责接待，接待到的每一个顾客传递给服务员(线程)进行服务
* **多Reactor多线程**：由一个门迎(`MainReactor`)欢迎每一个顾客，并从多个接待员(`SubReactor`)中指定一个人进行接待，接待到的每一个顾客随后传递给服务员(线程)进行服务

##### 4.2.1.6.2，Reactor模式优点总结

* 响应快，不必为单个同步事件所阻塞，虽然`Reactor`本身依旧是同步的
* 可以最大程度的避免复杂的多线程同步问题，避免线程的切换开销
* 扩展性好，可以方便的通过增加`Reactor`实例个数充分利用CPU资源
* 复用性好，`Reactor`模型本身与具体事件处理逻辑无关，具有很高的复用性

### 4.2.2，Netty模型

#### 4.2.2.1，原理图

![1576680578769](E:\gitrepository\study\note\image\nio\1576680578769.png)

#### 4.2.2.2，方案说明

1. Netty抽象出了两组线程池，`BossGroup`专门负责接收客户端连接，`WorkerGroup`专门负责网络的读写
2. `BossGroup`和`WorkerGroup`类型都是`NioEventLoopGroup`
3. `NioEventLoopGroup`相当于一个事件循环线程组，组中包含多个事件循环线程，每一个事件循环线程是`NioEventLoop`
4. `NioEventLoop`表示一个不断循环的任务执行线程，每一个`NioEventLoop`都有一个`Selector`，用于监听绑定在其上的`Socket`网络通，可以同时监听多个`NioChannel`；同时包含一个`taskQueue`，用于进行异步队列发起
5. `NioEventLoop`内部采用串行化执行，从消息的读取->解码->处理->编码->发送，都由`NioEventLoop`线程负责。**包括异步任务后，多个异步任务间同样串行执行，后续有代码演示**
6. 每个`NioChannel`只会绑定在唯一的`NioEventLoop`上，同时绑定一个自己专属的`ChannelPipeline`
7. `ChannelPipeline`中包含多个`Handler`，通过`AbstractChannelHandlerContext`双向链表包装存储
8. 每一个Boss的`NioEventLoop`循环执行步骤有如下三步：

* 轮询`accept`事件
* 处理`accept`事件，与Client建立连接并生成`NioSocketChannel`，并将其注册到Worker某一`NioEventLoop`的`selector`上
* 继续循环处理任务队列的任务，即`runAllTasks`

9. 每一个Worker的`NioEventLoop`循环执行步骤有如下三步：

* 轮询`read`，`write`事件
* 处理IO事件，即`read`，`write`事件，在对应的`NioSocketChannel`处理
* 继续处理任务队列的任务，即`runAllTasks`

10. 每个Worker的`NioEventLoop`在处理任务时，会使用`Pipeline`(管道)，`Pipeline`中包含了`Channel`，即使用管道可以获取到对应的通道，管道中维护了很多的处理器

### 4.2.3，NIO快速入门_TCP服务

#### 4.2.3.1，服务端代码

* `NettyServer`

```java
package com.self.netty.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * NETTY_服务端代码
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午9:36:18
 */
public class NettyServer {

	public static void main(String[] args) throws Exception {
		// Group子线程数不填默认为 (CPU核数 * 2)
		// 初始化 Boss Group
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		// 初始化 Worker Group
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 初始化并配置 Netty 服务
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 设置线程组, 包括Boss线程组和Worker线程组
			serverBootstrap.group(bossGroup, workerGroup)
				// 设置Boss线程处理通道
				.channel(NioServerSocketChannel.class)
				// 设置Boss线程处理参数
				.option(ChannelOption.SO_BACKLOG, 128)
				// 设置Worker线程处理参数
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				// 设置Worker线程处理器
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						// 获取pipeline进行业务处理
						// 管道主要进行数据处理
						socketChannel.pipeline().addLast(new NettyServerHandler());
					}
				});
			// 启动Netty服务, 并绑定端口
			ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
			System.out.println("NETTY SERVER START SUCCESS...");
			// 对关闭通道进行监听
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
```

* `NettyServerHandler`

```java
package com.self.netty.netty.demo;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * NETTY_服务器对应Handler代码 定义Handler, 需要继承Netty定义好的适配器
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		System.out.println("client address: " + ctx.channel().remoteAddress());
		// 将msg转换为ByteBuf
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("msg: " + buf.toString(Charset.forName("UTF-8")));
	}

	/**
	 * 数据读取处理完成后, 返回响应结果到客户端
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将数据写入缓冲区
		ctx.writeAndFlush(Unpooled.copiedBuffer("has received message...", Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.channel().close();
		// 打印异常
		cause.printStackTrace();
	}

}
```

#### 4.2.3.2，客户端代码

* `NettyClient`

```java
package com.self.netty.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty客户端
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:58:42
 */
public class NettyClient {

	public static void main(String[] args) {
		// 创建线程组
		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			// 创建客户端核心处理类
			Bootstrap bootstrap = new Bootstrap();
			// 绑定线程组
			bootstrap.group(eventLoopGroup)
					// 绑定客户端通道实现类
					.channel(NioSocketChannel.class)
					// 绑定业务处理器
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyClientHandler());
						}
					});
			// 启动服务端
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
			System.out.println("CLIENT START SUCCESS...");
			// 监听关闭通道
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}
```

* `NettyClientHandler`

```java
package com.self.netty.netty.demo;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty客户端处理类
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午11:13:50
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 通道就绪触发
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		ctx.writeAndFlush(Unpooled.copiedBuffer("HELLO SERVER", Charset.forName("UTF-8")));
	}

	/**
	 * 读取服务端返回的数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		System.out.println("Remote Address: " + ctx.channel().remoteAddress());
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("reveive msg: " + buf.toString(Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}

}
```

### 4.2.4，任务队列

#### 4.2.4.1，Task的典型使用场景

* 用户自定义普通任务
* 用户自定义定时任务
* 非当前`Reactor`线程调用`Channel`的各种方法：其他连接变更通知，*类似于上线通知*

#### 4.2.4.2，用户程序自定义的普通任务：**注意发起的任务串行执行**

```java
@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    // 对于业务执行较长的任务, 可以自定义任务队列进行处理
    // 接收任务后直接发起任务队列, 即异步执行
    // 之后channelRead()执行完成, 会继续执行channelReadComplete()
    // 等业务代码真正执行完成后, 再次提示客户端

    // 发起执行任务, 实际是将任务添加到 NioEventLoop 的 taskQueue 属性中,
    // taskQueue 中的线程对象会顺序执行, 也就是说当前定义的两个异步任务, 会依次执行, 共6S执行完成
    // 而不是并行执行3S完成
    ctx.channel().eventLoop().execute(() -> {
        try {
            Thread.sleep(3 * 1000);
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer("channelRead_1...", Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    ctx.channel().eventLoop().execute(() -> {
        try {
            Thread.sleep(3 * 1000);
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer("channelRead_2...", Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    // 发起异步后, 主线程不会阻塞, 会直接执行
    System.out.println("channelRead() 执行完成");
}
```

#### 4.2.4.3，用户自定义定时任务

```java
package com.self.netty.netty.demo;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * NETTY_定时任务队列处理
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyScheduleTaskHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.channel().eventLoop().execute(() -> {
			try {
				Thread.sleep(3 * 1000);
				ctx.channel().writeAndFlush(Unpooled.copiedBuffer("channelRead_1...", Charset.forName("UTF-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// 倒计时执行, 与execute()任务串行执行,
		ctx.channel().eventLoop().schedule(() -> {
			try {
				Thread.sleep(3 * 1000);
				ctx.channel()
						.writeAndFlush(Unpooled.copiedBuffer("channelRead_SCHEDULED...", Charset.forName("UTF-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 3, TimeUnit.SECONDS);
		// 发起异步后, 主线程不会阻塞, 会直接执行
		System.out.println("channelRead() 执行完成");
	}

	/**
	 * 数据读取处理完成后, 返回响应结果到客户端
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 打印接收完成数据
		ctx.writeAndFlush(Unpooled.copiedBuffer("channelReadComplete...", Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.channel().close();
		// 打印异常
		cause.printStackTrace();
	}

}
```

#### 4.2.4.4，非当前`Reactor`线程调用`Channel`的各种方法

* `NettyPublishServer`

```java
package com.self.netty.netty.demo;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

/**
 * NETTY_服务器对应Handler代码 定义Handler, 需要继承Netty定义好的适配器
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		System.out.println("client address: " + ctx.channel().remoteAddress());
		// 将msg转换为ByteBuf
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("msg: " + buf.toString(Charset.forName("UTF-8")));
	}

	/**
	 * 数据读取处理完成后, 返回响应结果到客户端
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将数据写入缓冲区
		ctx.writeAndFlush(Unpooled.copiedBuffer("has received message...", Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.channel().close();
		// 打印异常
		cause.printStackTrace();
	}

}
```

* `NettyPublishServerHandler`

```java
package com.self.netty.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.nio.charset.Charset;

/**
 * NETTY_服务器对应Handler代码 定义Handler, 需要继承Netty定义好的适配器
 * 
 * @author pj_zhang
 * @date 2019年12月19日 上午10:05:56
 */
public class NettyPublishServerHandler extends ChannelInboundHandlerAdapter {

	private NettyPublishServer nettyPublishServer;

    public NettyPublishServerHandler(NettyPublishServer nettyPublishServer) {
    	this.nettyPublishServer = nettyPublishServer;
    }

    /**
	 * 读取客户端发送的数据 ChannelHandlerContext: 上下文对象, 含有管道,通道,地址 msg: 客户端发送的消息, 默认为Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ChannelHandlerContext: " + ctx);
		System.out.println("client address: " + ctx.channel().remoteAddress());
		// 将msg转换为ByteBuf
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("msg: " + buf.toString(Charset.forName("UTF-8")));
		System.out.println("curr client connect: " + nettyPublishServer.getLstSocketChannel().size());
		// 给每一个连接发送变更消息
		for (SocketChannel socketChannel : nettyPublishServer.getLstSocketChannel()) {
			if (socketChannel == ctx.channel()) {
				continue;
			}
			System.out.println("server send message to : " + socketChannel.remoteAddress());
			socketChannel.writeAndFlush(Unpooled.copiedBuffer((" server receive message from " + ctx.channel().remoteAddress() + " and publish it").getBytes("UTF-8")));
		}
	}

	/**
	 * 数据读取处理完成后, 返回响应结果到客户端
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将数据写入缓冲区
		ctx.writeAndFlush(Unpooled.copiedBuffer("has received message...", Charset.forName("UTF-8")));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.channel().close();
		// 打印异常
		cause.printStackTrace();
	}

}
```

### 4.2.5，Netty异步模型

#### 4.2.5.1，基本介绍

* 异步调用发出后，不会立即返回结果。当实际处理这个调用的组件在处理到一定阶段后，通过状态，通知和回调来通知调用者
* Netty中的IO为异步的，包括`bind`，`connect`，`write`等操作都会返回一个`ChannelFuture`
* 调用者不能立即获得执行结果，而是通过Future-Listener机制，主动获取或者通过通知获取消息
* Netty的异步模型就是建立的`future`和`callback`之上的

#### 4.2.5.1，Future-Listener机制

* Netty的异步返回接口`ChannelFuture`继承自顶层接口`java.util.concurrent.Futrue`，异步结果获取核心理念基本一致

* 调用者可以通过对`ChannelFuture`设置监听来进行后续操作，常用操作方法如下：

```java
// 监听核心方法，添加监听器，参数需要传递一个实现类
ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> var1);
// 是否操作完成
boolean isDone();
// 是否操作成功
boolean isSuccess();
// 操作是否取消
boolean isCancellable();
// 获取操作异常信息
Throwable cause();
```

* **监听举例说明**

```java
// 启动Netty服务, 并绑定端口
ChannelFuture cf = serverBootstrap.bind(8080).sync();
System.out.println("NETTY SERVER START SUCCESS...");
// 添加监听
cf.addListener((ChannelFutureListener) channelFuture -> {
    // 状态为成功
    if (channelFuture.isSuccess()) {
        System.out.println("启动成功...");
    } else {
        System.out.println("启动失败...");
    }
});
```

### 4.2.6，Netty快速入门_HTTP服务

* `Server`

 ```java
package com.self.netty.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Netty快速入门_HTTP服务_服务端
 * @author pj_zhang
 * @create 2019-12-21 18:32
 **/
public class NettyHttpServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // Netty 提供的处理HTTP的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new NettyHttpServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            System.out.println("SERVER START COMPLETE...");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
 ```

* `Handler`

```java
package com.self.netty.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

/**
 * @author pj_zhang
 * @create 2019-12-21 21:46
 **/
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     * @param channelHandlerContext 客户端连接上下文
     * @param msg 客户端传递信息, 与类定义泛型想对应
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        // 判断 HttpObject 是否是 HttpRequest请求
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("当前类: " + this.getClass());
            // 每一次请求, 哈希不一致, 说明Handler不共享
            System.out.println("当前对象哈希: " + this.hashCode());
            System.out.println("请求路径: " + httpRequest.uri());
            // 路径过滤
            if ("/favicon.ico".equalsIgnoreCase(httpRequest.uri())) {
                return;
            }
            System.out.println("MSG 类型: " + msg.getClass());
            System.out.println("客户端远程路径: " + channelHandlerContext.channel().remoteAddress());

            // 构造客户端响应
            ByteBuf byteBuf = Unpooled.copiedBuffer("THIS IS SERVER...", Charset.forName("UTF-8"));
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, byteBuf);
            // 返回类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            // 返回长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            // 返回response
            channelHandlerContext.writeAndFlush(response);
        }
    }

}
```

## 4.3，Netty核心组件

### 4.3.1，BootStrap & ServerBootStrap

#### 4.3.1.1，基本概念

* `Bootstrap`意思是引导，一个Netty程序通常由一个`Bootstrap`开始，用于配置整个Netty程序，串联各个组件，其中`Bootstrap`是客户端的启动引导类，`ServerBootstrap`是服务端的启动引导类

#### 4.3.1.2，常用API

```java
/********公共部分**********/
// 绑定 Channel 通道
public B channel(Class<? extends C> channelClass);
// 绑定 Handler 处理器
public B handler(ChannelHandler handler);
// 添加主线程组配置
public <T> B option(ChannelOption<T> option, T value);
/*********ServerBootStrap部分***********/
// 设置主线程组件和工作线程组件
public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup);
// 添加工作线程组配置
public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value);
// 添加工作线程组处理器
public ServerBootstrap childHandler(ChannelHandler childHandler);
// 绑定端口并启动
public ChannelFuture bind(int inetPort);
/*********BootStrap部分***********/
// 连接服务端
public ChannelFuture connect(String inetHost, int inetPort);
```

### 4.3.2，Future & ChannelFutrue

#### 4.3.2.1，基本概念

* Netty中的IO操作都是异步的，不能立刻知道消息处理情况。但是可以通过`Future`来注册监听，在操作执行到一定阶段后自动触发注册的监听事件进行回调。

#### 4.3.2.2，常用API

```java
// 添加监听事件
Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);
// 获取所属通道
Channel channel();
// 等待异步操作执行完毕
ChannelFuture sync() throws InterruptedException;
// 是否操作完成
boolean isDone();
// 是否操作成功
boolean isSuccess();
// 操作是否取消
boolean isCancellable();
// 获取操作异常信息
Throwable cause();
```

### 4.3.3，Channel

#### 4.3.3.1，基本概念

* `Channel`是Netty网络通信的组件，用于执行网络IO操作
* 通过`Channel`可以获得当前网络连接状态及配置参数
* `Channel`提供了异步的网络IO操作，可以配置`Future`进行事件回调监听
* 不同协议，不同阻塞类型的连接都有不同的`Channel`与之对应，如下：

#### 4.3.3.2，基本类型

* `NioSocketChannel`：基于TCP协议的客户端连接
* `NioServerSocketChannel`：基于TCO协议的服务端连接
* `NioDatagramChannel`：基于UDP协议的连接
* `NioSctpChannel`：基于SCTP协议的客户端连接
* `NioSctpServerChannel`：基于SCTP协议的服务端连接

#### 4.3.3.3，常用API

```java
// 获取当前 Channel 所属的 EventLoop
EventLoop eventLoop();
// 获取当前 Channel 下的 Pipeline
ChannelPipeline pipeline();
```

### 4.3.4，ChannelHandler

#### 4.3.4.1，基本概念

* `ChannelHandler`是一个顶层接口，定义IO处理或者拦截事件，并将其添加到`ChannelPipeline`中进行顺序处理
* `ChannelHandler`本身并没有提供太多方法，在子接口和实现类中定义了一系列操作方法，用户继承实现

#### 4.3.4.2，主体类图

![1577072063081](E:\gitrepository\study\note\image\nio\1577072063081.png)

#### 4.3.4.3，常用API：基于ChannelInboundHandler

```java
// 连接建立
public void handlerAdded(ChannelHandlerContext ctx) throws Exception;
// 连接移除
void handlerRemoved(ChannelHandlerContext ctx) throws Exception;
// 通道注册事件
void channelRegistered(ChannelHandlerContext ctx) throws Exception;
// 通道登出事件
void channelUnregistered(ChannelHandlerContext ctx) throws Exception;
// 通道就绪事件
void channelActive(ChannelHandlerContext ctx) throws Exception;
// 通道断开事件
void channelInactive(ChannelHandlerContext ctx) throws Exception;
// 通道读事件
void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;
// 通道读取完成事件
void channelReadComplete(ChannelHandlerContext ctx) throws Exception;
// 异常回调事件
void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
// 事件通知回调(类似心跳检测)
void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;
```

### 4.3.4，Pipeline & ChannelPipeline

#### 4.3.4.1，基本概念

* `ChannelPipeline`可以理解为`ChannelHandler`的一个集合，负责处理和拦截`ChannelInboundHandler`和`ChannelOutboundHandler`的事件和操作，`ChannelHandler`在`ChannelPipeline`被包装成为`DefaultChannelHandlerContext`并以双向链表的形式存在
* 用户在添加处理器时，可以根据添加顺序自定义处理器执行顺序
* 在Netty中，`Channel`通道和`ChannelPipeline`是一一对应的关系

#### 4.3.4.2，常用API

```java
// 添加到链表头
ChannelPipeline addFirst(String name, ChannelHandler handler);
ChannelPipeline addFirst(ChannelHandler... handlers);
// 添加到链表尾
ChannelPipeline addLast(String name, ChannelHandler handler);
ChannelPipeline addLast(ChannelHandler... handlers);
// 获取对应的通道
Channel channel();
// 获取指定的处理器
ChannelHandlerContext context(String name);
ChannelHandlerContext context(Class<? extends ChannelHandler> handlerType);
```

### 4.3.5，ChannelHandlerContext

#### 4.3.5.1，基本概念

* 保存`Channel`相关的所有上下文信息，同时关联一个`ChannelHandler`对象

#### 4.3.5.2，常用方法

````java
// 获取处理器
ChannelHandler handler();
// 获取通道
Channel channel();
// 获取管道
ChannelPipeline pipeline();
// 写数据,包装为ByteBuf写
ChannelFuture writeAndFlush(Object msg);
````

### 4.3.6，ChannelOption

* `Channel`相关参数，Netty在创建`Channel`实例后，一般都需要设置对应参数，即`ChannelOption`参数
* `ChannelOption.SO_BACKLOG`：初始化服务器可连接队列大小
* `ChannelOption.SO_KEEPALIVE`：是否一直保持连接活动状态

### 4.3.7，EventLoopGroup & NioEventLoopGroup

#### 4.3.7.1，基本概念

* `EventLoopGroup`是一组`EventLoop`的抽象，Netty为了更好的利用多核CPU的资源，一般会有多个`EventLoop`同时工作，每个`EventLoop`内部都维护一个`Selector`对象进行轮询
* `EventLoopGroup`提供`next()`方法，可以从组中根据一定的规则获取`EventLoop`执行任务，在Netty服务端编程中，一般指定Boss和Worker两个组进行客户端处理。Boss组负责处理客户端连接，Worker组负责客户端数据交互

#### 4.3.7.2，常用API

```java
// 初始化；不指定长度默认为 CPU合数 * 2
public NioEventLoopGroup(int nThreads);
// 资源释放
public Future<?> shutdownGracefully();
```

### 4.3.8，Unpooled

#### 4.3.8.1，基本概念

* `Unpooled`是Netty提供的一个专门用来操作缓冲区`ByteBuf`的工具类
* **Netty的缓冲区`ByteBuf`与NIO的`ByteBuffer`实现方式完全不同**

#### 4.3.8.2，ByteBuf状态变更

* `ByteBuf`底层数据结构为数组，在初始化时需要指定数组长度
* `ByteBuf`内部指定`writeIndex`和`readIndex`两个参数，用户缓冲区读写处理
  * `0 ~ readIndex`：表示已经读过数据索引区间
  * `readIndex ~ writeIndex`：表示可读数据索引区间
  * `writeIndex ~ capacity`：表示可写数据索引区间
* `ByteBuf`支持自动扩容，初始化长度指定，最大长度默认为`Integer.MAX_VALUE`，当写长度超过可写长度后，触发扩容
  * 第一次扩容，如果`capacity`长度小于64，且写数据长度小于64，会默认扩容到64
  * 如果写数据长度小于4M(一页)，直接扩容到原数据长度一倍，即左移一位
  * 如果写数据长度大于4M，扩容长度为 `capacity + length`

#### 4.3.8.2，常用API

```java
/***********Unpooled*************/
// 初始化 ByteBuf
public static ByteBuf buffer(int initialCapacity);
// 包装数据为 ByteBuf
public static ByteBuf copiedBuffer(byte[] array);
public static ByteBuf wrappedBuffer(byte[] array);
/***********ByteBuf*************/
// 获取缓冲区长度
public abstract int capacity();
// 获取写索引
public abstract int writerIndex();
// 获取读索引
public abstract int readerIndex();
// 获取缓冲区可读长度
public abstract int readableBytes();
// 获取缓冲区可写长度
public abstract int writableBytes();
// 写数据
public abstract ByteBuf writeByte(int value);
// 读数据,后移读索引位置
public abstract byte  readByte();
// 读数据,不后移读索引位置
public abstract byte  getByte(int index);
// 清空缓冲区
public abstract ByteBuf clear();
```

### 4.3.9，Netty应用实例_群聊系统

#### 4.3.9.1，实例要求

* 编写Netty群聊系统，实现多人群聊
* 服务器端：可以实现用户上线，离线，并实现消息转发功能
* 客户端：通过`Channel`可以发送消息给其他用户，并接受来自其他用户的消息

#### 4.3.9.2，代码演示

* 服务端主代码

```java
package com.self.netty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty群聊系统_服务端
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午4:44:23
 */
public class GroupChatServer {

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new GroupChatServerHandler());
						}
					});
			ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
			System.out.println("Netty群聊系统, 服务端启动成功");
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
```

* 服务端处理器

```java
package com.self.netty.netty.groupchat;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Netty群聊系统_服务端处理器
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午4:49:18
 */
public class GroupChatServerHandler extends ChannelInboundHandlerAdapter {

	// 定义Channel组, 管理所有的Channel
	private final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 表示连接建立, 一旦连接, 第一个被执行
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// 获取客户端通道
		Channel channel = ctx.channel();
		// 通知其他客户端用户上线
		CHANNEL_GROUP
				.writeAndFlush(Unpooled.copiedBuffer((channel.remoteAddress() + ": 加入群聊").getBytes(CharsetUtil.UTF_8)));
		// 添加到群组中
		CHANNEL_GROUP.add(ctx.channel());
	}

	/**
	 * 断开连接
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// 获取客户端通道
		Channel channel = ctx.channel();
		// 通知其他客户端用户上线
		CHANNEL_GROUP.writeAndFlush(
				Unpooled.copiedBuffer((channel.remoteAddress() + ": 已经不在了...").getBytes(CharsetUtil.UTF_8)));
	}

	/**
	 * 表示Channel出于活动状态
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + ": 上线");
	}

	/**
	 * 表示Channel出于非活动状态
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + ": 已经下线了~~~~");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 获取当前Channel
		Channel channel = ctx.channel();
		ByteBuf buf = (ByteBuf) msg;
		String inputMessage = getMessage(buf);
		// 遍历Channel组, 根据不同的情况, 传递不同的消息
		CHANNEL_GROUP.forEach(ch -> {
			// 通知到其他客户端
			if (ch != channel) {
				ch.writeAndFlush(Unpooled.copiedBuffer(
						(channel.remoteAddress() + "说: " + inputMessage).getBytes(Charset.forName("UTF-8"))));
			}
		});
		System.out.println(channel.remoteAddress() + "说: " + inputMessage);
	}

	/**
	 * 获取数据成功
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("读取数据成功...");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public String getMessage(ByteBuf byteBuf) {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		return new String(bytes, CharsetUtil.UTF_8);
	}

}
```

* 客户端主代码

```java
package com.self.netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * Netty群聊系统_客户端
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午5:14:00
 */
public class GroupChatClient {

	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new GroupChatClientHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
			// 获取Channel
			Channel channel = channelFuture.channel();
			// 通过 Channel 写数据
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String inputMessage = scanner.nextLine();
				channel.writeAndFlush(Unpooled.copiedBuffer(inputMessage.getBytes(CharsetUtil.UTF_8)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}
```

* 客户端处理器

```java
package com.self.netty.netty.groupchat;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty群聊系统_客户端处理器
 * 
 * @author pj_zhang
 * @date 2019年12月23日 下午5:16:52
 */
public class GroupChatClientHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 初始化
	 * 客户端如果把发送消息的循环写到此处，会产生占用，不会再接收到服务端消息，具体原因不解
	 */
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		System.out.println("当前客户连接: " + ctx.channel().remoteAddress());
//		String inputMessage = ctx.channel().localAddress().toString();
//		ctx.channel().writeAndFlush(Unpooled.copiedBuffer(inputMessage.getBytes(Charset.forName("UTF-8"))));
//	}

	/**
	 * 读取数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("接收到读取的数据");
		System.out.println("接收读取数据: " + getMessage((ByteBuf) msg));
	}

	public String getMessage(ByteBuf byteBuf) {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		return new String(bytes, CharsetUtil.UTF_8);
	}

}
```

### 4.3.10，Netty应用实例_心跳检测

#### 4.3.10.1，实例要求

* 服务端三秒没有读时，提示读空闲
* 服务端五秒没有写时，提示写空闲
* 服务端七秒没有读写时，提示读写空闲

#### 4.3.10.2，核心内容

* 服务端添加心跳检测处理器`IdleStateHandler`，初始化数据时需要传递四个参数，分别如下：
  * readerIdleTime：读限定时间未操作时触发
  * writerIdleTime：写限定时间未操作时触发
  * allIdleTime：读写限定时间未操作时触发
  * TimeUnit：限定时间单位
* 心跳检测处理器下一个处理器，需要定义心跳检测结果处理器，并重写`userEventTriggered()`方法，方法第二个参数为事件参数，如：`IdleStateEvent`，可以根据不同的事件类型进行处理

#### 4.3.10.3，代码演示

* 服务端主代码

```java
package com.self.netty.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 心跳检测服务端
 * @author pj_zhang
 * @create 2019-12-23 21:21
 **/
public class HeartBeatServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // IdleStateHandler: 心跳检测处理器
                            // readerIdleTime: 读限定时间未操作时触发
                            // writerIdleTime: 写限定时间未操作时触发
                            // allIdleTime: 读写限定时间未操作时触发
                            // IdleStateHandler触发后, 会顺序执行下一个处理器, 进行回调方法处理
                            socketChannel.pipeline().addLast(new IdleStateHandler(3,
                                    5, 7, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new HeartBeatServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
```

* 服务端处理类

```java
package com.self.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳检测机制
 * @author pj_zhang
 * @create 2019-12-23 21:28
 **/
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 重写该方法, 进行心跳检测回调处理
     * @param ctx 上下文内容
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = null;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "读空闲...";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲...";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲...";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + ": " + eventType);
//            ctx.channel().close();
        }
    }
}
```

### 4.3.11，Netty应用实例_WebSocket

#### 4.3.11.1，实例要求

* HTTP协议是无状态的，浏览器和服务端之前请求一次后，下一次需要重新建立连接
* 通过WebSocket可以改变HTTP协议多次请求的约束，实现长连接
* 浏览器和服务端可以相互感知关闭（浏览器端感知未演示）

#### 4.3.11.2，核心内容

* Netty对WebSocket长连接的支撑同样也是添加一系列处理器实现
* `HttpServerCodec`：WebSocket内部也是对HTTP请求的包装处理，所以需要HTTP的编码解码处理器
* `ChunkedWriteHandler`：WebSocket是以块的形式进行写，添加响应处理器
* `HttpObjectAggregator(4096)`：HTTP在传输过程中，如果数据量过大，会分段处理，该处理器根据一定的长度对HTTP请求聚合
* `WebSocketServerProtocolHandler("/path")`：WebSocket服务端接收主体类，参数传递的路径是对前台路径映射，相当于路径白名单。**有测试过正则，没有调通**

#### 4.3.11.3，代码演示

* 服务端主代码

```java
package com.self.netty.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocket服务端
 * @author LiYanBin
 * @create 2019-12-24 10:34
 **/
public class WebSocketServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // WebSocket基于HTTP, 首先提供HTTP编解码
                            pipeline.addLast(new HttpServerCodec());
                            // WebSocket分块处理
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 定义每一批量传递数据长度
                            pipeline.addLast(new HttpObjectAggregator(4096));
                            // 添加WebSocket核心处理, 并定义拦截路径
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 自定义处理器
                            pipeline.addLast(new WebSocketServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            System.out.println("SERVER START COMPLETE...");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
```

* 服务端处理器

```java
package com.self.netty.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * WebSocket自定义处理器
 * @author LiYanBin
 * @create 2019-12-24 10:40
 **/
// TextWebSocketFrame：WebSocket包装的消息传递
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 打印消息
        System.out.println("接收到客户端消息: " + msg.text());
        // 输出消息到客户端
        ctx.writeAndFlush(new TextWebSocketFrame("服务端响应: " + msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接, 长ID: " + ctx.channel().id().asLongText());
        System.out.println("客户端连接, 端ID: " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端退出, 长ID: " + ctx.channel().id().asLongText());
        System.out.println("客户端退出, 端ID: " + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常: " + cause.getMessage());
    }
}
```

#### 4.3.11.4，客户端连接

* 应用在线模拟Socket工具模拟请求：http://www.bejson.com/httputil/websocket/

![1577156532494](E:\gitrepository\study\note\image\nio\1577156532494.png)

## 4.4，Netty编解码机制

### 4.4.1，编解码的基本介绍

### 4.4.2，Netty自身的编解码器

### 4.4.3，Google Protobuf

