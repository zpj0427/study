package com.hadoop.hdfs.quickstart;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @author PJ_ZHANG
 * @create 2021-04-06 15:15
 **/
public class HDFSTestAPI {

    /**
     * HDFS配置文件优先级判断
     * * 在resources目录下加hdgs-site.xml文件, 并配置dfs.replication=4
     * * 在代码中修改 dfs.replication 属性, 修改为2
     * * 对上面内容修改, 分别上传文件, 查看副本数量
     */
    @Test
    public void testConfig() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        // configuration.set("dfs.replication", "2");
        // 参数说明:
        // * URI uri: HDFS连接信息, 注意端口为内部通信端口
        // * Configuration conf: 配置信息, 代码配置, 优先级最高, 会覆盖掉所有配置文件配置
        // * String user: 操作的用户
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        fileSystem.copyFromLocalFile(false, true, new Path("F:\\a.txt"), new Path("/sanguo/"));
        fileSystem.copyFromLocalFile(false, true, new Path("F:\\b.txt"), new Path("/sanguo/"));
    }

    /**
     * 创建文件夹
     */
    @Test
    public void createDir() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        fileSystem.mkdirs(new Path("/xiyou/huaguoshan"));
        fileSystem.close();
    }

    /**
     * 文件上传
     *
     * @throws Exception
     */
    @Test
    public void upload() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // 复制本地文件到远端HDFS
        // boolean delSrc: 是否复制完成后删除源文件
        // boolean overwrite: 在远端文件存在时, 是否覆盖, 存在时不覆盖会报 PathExistsException
        // Path src: 源路径, 即本地路径
        // Path dst: 目标路径, 即远端路径
        fileSystem.copyFromLocalFile(false, false, new Path("F:\\sunwukong.txt"), new Path("/xiyou/"));
        fileSystem.close();
    }

    /**
     * 文件下载
     */
    @Test
    public void download() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // 从远端拷贝文件到本地
        // boolean delSrc: 复制完成后是否完成源文件
        // Path src: 源文件, 即远端文件
        // Path dst: 目标文件, 即本地文件
        // boolean useRawLocalFileSystem: 是否开启文件验证, 验证后, 会在本地生成*.crc验证文件,用于文件校验
        fileSystem.copyToLocalFile(false, new Path("/xiyou/"), new Path("E:\\"), false);
        fileSystem.close();
    }

    /**
     * 文件改名或者文件移动
     */
    @Test
    public void renameAndMove() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // 文件改名
        // 第一个Path参数: 文件源文件路径及文件名
        // 第二个Path参数: 文件新文件路径及文件名
        // fileSystem.rename(new Path("/xiyou/sunwukong.txt"), new Path("/xiyou/newsunwukong.txt"));

        // 文件移动, 移动文件到根路径下, 并修改名称
        // fileSystem.rename(new Path("/xiyou/newsunwukong.txt"), new Path("/cut.txt"));

        // 文件移动, 移动文件到根路径下, 不修改名称
        // fileSystem.rename(new Path("/sanguo/a.txt"), new Path("/"));

        // 文件夹移动, 移动文件夹到根路径下, 不修改名称
        // fileSystem.rename(new Path("/xiyou/huaguoshan"), new Path("/"));

        // 文件夹移动, 移动文件夹到根路径下, 修改名称
        fileSystem.rename(new Path("/xiyou/huaguoshan"), new Path("/newhuaguoshan"));
        fileSystem.close();
    }

    /**
     * 文件删除
     */
    @Test
    public void delete() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // Path f: 要删除的远端路径
        // boolean recursive: 是否递归删除, 删除文件和空文件夹时, 无所谓true/false都可以删除
        // 对于非空文件夹, 设置为false会报 PathIsNotEmptyDirectoryException 异常
        // 设置为true后, 会递归删除该文件夹
        fileSystem.delete(new Path("/wcinput"), false);
        fileSystem.close();
    }

    /**
     * 展示文件详情信息
     */
    @Test
    public void showDetails() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // Path f: 远端文件路径, 即取该路径下的文件列表
        // boolean recursive: 是否递归获取, 如果路径是文件, 无所谓该参数;
        // 如果路径为文件夹时
        // * false: 直接获取该路径下文件列表
        // * true: 获取递归路径下文件列表
        // 注意: 该方法不会取到文件夹, 可通过 path 信息进行路径截取
        RemoteIterator<LocatedFileStatus> lstFileData = fileSystem.listFiles(new Path("/"), true);
        for (;lstFileData.hasNext();) {
            LocatedFileStatus fileData = lstFileData.next();
            System.out.println("文件路径: " + fileData.getPath());
            System.out.println("文件名称: " + fileData.getPath().getName());
            System.out.println("文件权限: " + fileData.getPermission());
            System.out.println("文件所有者: " + fileData.getOwner());
            System.out.println("文件群组: " + fileData.getGroup());
            System.out.println("文件大小: " + fileData.getLen());
            System.out.println("文件修改时间: " + fileData.getModificationTime());
            System.out.println("文件副本数量: " + fileData.getReplication());
            System.out.println("文件所属块大小: " + fileData.getBlockSize());
            // 文件分块信息
            // 以一个352.8M文件的分块信息为例
            // [0,134217728,Hadoop102,Hadoop103,Hadoop104,
            // 134217728,134217728,Hadoop102,Hadoop103,Hadoop104,
            // 268435456,101505031,Hadoop103,Hadoop102,Hadoop104]

            // 因为 HDFS 默认分块信息为128M, 所以该文件会存储在3个块中
            // 又因为副本会存储三份, 所以每一个分块会存储在三个节点上
            // 上面三组数组分别表示三个分块的存储信息及副本信息
            // 以第一组数据进行解析: 0,134217728,Hadoop102,Hadoop103,Hadoop104
            // 0: 指该分块存储文件的开始字节数
            // 134217728: 该分块存储文件的字节长度, 即大小
            // Hadoop102,Hadoop103,Hadoop104: 表示该块副本所存储的节点信息
            BlockLocation[] blockArr = fileData.getBlockLocations();
            System.out.println(Arrays.toString(blockArr));
            System.out.println("=================================");
        }
    }

    /**
     * 校验文件类型: 文件/文件夹
     */
    @Test
    public void checkFile() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // 不同于文件列表查看, 该方法不会递归查询
        FileStatus[] lstFileStatus = fileSystem.listStatus(new Path("/"));
        for (FileStatus fileStatus : lstFileStatus) {
            // 是文件
            if (fileStatus.isFile()) {
                System.out.println(fileStatus.getPath().getName());
            // 是文件夹
            } else if (fileStatus.isDirectory()) {
                System.out.println(fileStatus.getPath().getName());
            }
         }
    }

    /**
     * 以流的方式复制文件
     */
    @Test
    public void copyFile() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://Hadoop102:8020"), configuration, "root");
        // 以流的方式读文件
        FSDataInputStream inputStream = fileSystem.open(new Path("/ant_1.3.4(1).zip"));
        // 以流的方式写文件
        FSDataOutputStream outputStream = fileSystem.create(new Path("/ant_1.3.4(2).zip"));
        byte[] bytes = new byte[1024];
        int len = 0;
        for (;(len = inputStream.read(bytes)) != -1;) {
            outputStream.write(bytes, 0, len);
        }
        outputStream.flush();
        System.out.println("文件复制完成");
        fileSystem.close();
    }

}
