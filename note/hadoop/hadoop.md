版本：Hadoop3.1.3

# 1，基础介绍

## 1.1，大数据概念

* 大数据（`Big Data`）：指<font color=red>无法在一定时间范围内</font>用常规软件工具进行捕捉、管理和处理的数据集合，是需要新处理模式才能<font color=red>具有更强的决策力、洞察发现力和流程优化能力的海量、高增长率及多样化</font>的信息资产。
* 大数据主要解决**海量**数据的**存储，计算和分析问题**

## 1.2，大数据特点（4V）

* <font color=red>Volumn（大量）</font>：截至目前，人类生产的所有**印刷材料的数据量是200PB**，而历史上**全人类总共说过的话的数据量大约是5EB**。当前，典型**个人计算机硬盘的容量为TB量级**，而一些**大企业的数据量已经接近EB量级**。

* <font color=red>Velocity（高速）</font>：**这是大数据区分于传统数据挖掘的最显著特征**。根据IDC的“数字宇宙”的报告，预计到2025年，全球数据使用量将达到163ZB。在如此海量的数据面前，**处理数据的效率就是企业的生命。**
* <font color=red>Variety（多样）</font>：类型的多样性也让数据被分为结构化数据和非结构化数据。相对于以往便于存储的以数据库/文本为主的结构化数据，非结构化数据越来越多，包括**网络日志、音频、视频、图片、地理位置信息等**，这些多类型的数据对数据的处理能力提出了更高要求。

* <font color=red>Value（低价值密度）</font>：价值密度的高低与数据总量的大小成反比。**如何快速对有价值数据“提纯”成为目前大数据背景下待解决的难题**。

## 1.3，应用场景

## 1.4，发展前景

## 1.5，工作内容

* 平台组：
  * `Hadoop`、`Flume`、`Kafka`、`HBase`、`Spark`等框架平台搭建
  * 集群性能监控
  * 集群性能调优
* 数据仓库组：
  * ETL工程师（数据清洗）
  * 数据分析、数据仓库建模
* 实时组：
  * 实时指标分析、性能调优
* 数据挖掘组：
  * 算法工程师
  * 推荐系统工程师
  * 用户画像工程师
* 报表开发组：
  * JavaEE工程师
  * 前端工程师

# 2，Hadoop入门

## 2.1，Hadoop是什么

* `Hadoop`是一个由 `Apache` 基金会所开发的分布式系统基础架构

* 主要解决海量数据的**存储**和海量数据的**分析计算**问题

* 广义上来说，`Hadoop` 通常是指一个更广泛的概念——**Hadoop生态圈**

  ![1615435569312](E:\gitrepository\study\note\image\hadoop\1615435569312.png)

## 2.2，Hadoop发展历史

* Hadoop创始人<font color=red>`Doug Cutting`</font>，为了实现与Google类似的全文搜索功能，他在 `Lucene` 框架基础上进行优化升级，查询引擎和索引引擎。

* 2001年年底 `Lucene` 成为 `Apache` 基金会的一个子项目。

* 对于海量数据的场景，`Lucene` 框架面对与 `Google` 同样的困难，存储海量数据困难，检索海量速度慢。

* 学习和模仿 `Google` 解决这些问题的办法：**微型版 `Nutch` **。

* 可以说 `Google` 是 `Hadoop` 的思想之源（Google在大数据方面的三篇论文）

  * `GFS` -> `HDFS`
  * `Map-Reduce` -> `MapReduce`
  * `BigTable` -> `HBase`

* 2003-2004年，`Google` 公开了部分 `GFS` 和 `MapReduce` 思想的细节，以此为基础 `Doug Cutting` 等人用了**2年业余时间**实现了 `DFS` 和 `MapReduce` 机制，使 `Nutch` 性能飙升。

* 2005 年 `Hadoop` 作为 `Lucene` 的子项目 `Nutch` 的一部分正式引入 `Apache` 基金会。

* 2006年3月份，`Map-Reduce` 和 `Nutch Distributed File System（NDFS）` 分别被纳入到 `Hadoop` 项目中，`Hadoop` 就此正式诞生，标志着大数据时代来临。

* LOGO来源于 `Doug Cutting` 儿子的玩具大象

  ![1615445357960](E:\gitrepository\study\note\image\hadoop\1615445357960.png)

## 2.3，Hadoop优势（4高）

* **高可靠性**：`Hadoop` 底层维护多个数据副本，所以即使存在某个计算元素或存储出现故障，也不会造成数据丢失。如下图，两套数据在三个 `Hadoop` 节点中分别备份，即使 `Hadoop102` 节点宕机，也不会影响集群运行。

  ![1615456723161](E:\gitrepository\study\note\image\hadoop\1615456723161.png)

* **高扩展性**：在集群建分配任务数据，可方便的扩展数以千计的节点。并且支持集群在线热扩展

  ![1615456903588](E:\gitrepository\study\note\image\hadoop\1615456903588.png)

* **高效性**：在 `MapReduce` 的思想下，`Hadoop` 是并行工作的，以加快任务处理速度。由单台节点进行部分计算，并最终进行计算任务汇总

  ![1615456977050](E:\gitrepository\study\note\image\hadoop\1615456977050.png)

* **高容错性**：能够自动将失败的任务重新分配

  ![1615457956663](E:\gitrepository\study\note\image\hadoop\1615457956663.png)

## 2.4，Hadoop组成

![1615796773792](E:\gitrepository\study\note\image\hadoop\1615796773792.png)

### 2.4.1，Hadoop 1.X组成

* `HDFS（Hadoop Distributed File System）`：数据存储
* `MapReduce`：计算 + 资源调度
* `Common`：辅助工具

### 2.4.2，Hadoop 2.X组成

* `HDFS`：数据存储
* `Yarn`：资源调度
* `MapReduce`：计算
* `Common`：辅助工具

### 2.4.3，Hadoop 3.X组成

* 与 `Hadoop 2.X` 的组成基本一致

## 2.5，HDFS架构概述

* `Hadoop Distributed File System`，简称 `HDFS`，是 `Hadoop` 的分布式文件存储系统

* `NameNode(nn)`：存储文件的**元数据**，如文件名、文件目录结构、文件属性（生成树时间、副本数、文件权限），以及每个文件的**块列表**和**块所在的 `DataNode` **等
* `DataNode(dn)`：在本地文件系统**存储文件块数据**，以及**块数据的校验和**
* `Secondary NameNode(2nn)`：每隔一段时间对 `NameNode` 元数据进行备，在 `NameNode` 宕机后，会接替 `NameNode` 的部分工作

## 2.5，Yarn架构概述

* `Yet Another Resource Negotiator` 简称 `Yarn`，另一种资源协调者，是 `Hadoop` 的资源管理器
* `ResourceManager(RM)`：整个集群资源（内存，CPU等）的管理者
* `NodeManager(NM)`：单个节点服务器资源管理者

* `ApplicationMaster(AM)`：单个任务运行的管理者
* `Container`：容器，相当一个独立的服务器，里面装了任务运行所需要的资源，**如内存、CPU、磁盘、网络等**

![1615797421399](E:\gitrepository\study\note\image\hadoop\1615797421399.png)

* *说明一：客户端可以有多个*
* *说明二：集群上可以运行多个 `ApplicationMaster`*
* *说明三：每个 `NodeManager` 上可以有多个 `Container`*

## 2.5，MapReduce架构概述

* `MapReduce` 将计算过程分为两个阶段，`Map` 阶段和 `Reduce` 阶段
  * `Map` 阶段：并行处理输入数据
  * `Reduce` 阶段：对 `Map` 阶段处理数据进行汇总

![1615797852533](E:\gitrepository\study\note\image\hadoop\1615797852533.png)

## 2.6，HDFS、Yarn和MapReduce之间关系

![1615798182837](E:\gitrepository\study\note\image\hadoop\1615798182837.png)

* 从一个数据处理流程看 `HDFS`、`Yarn`、`MapReduce` 之间关系及任务协作
* 首先由 `client` 发起一个任务，发送到 `Yarn` 组件的 `ResourceManager` 资源调度管理者
* 由 `ResourceManager` 调度某一台节点机器的 `NodeManager` 进行具体资源调度
* `NodeManager` 会在当前资源服务器对任务进行响应，通过 `Container` 容器创建一个任务管理者 `ApplicationMaster`
* `ApplicationMaster` 根据该任务需要的资源从 `ResourceManager` 集群资源调度处申请数据计算的所需资源
* 资源申请后在对应的 `NodeManager` 节点服务器中，通过 `Container` 容器创建 `MapTask` 数据处理任务；并在集群中某一节点创建 `ReduceTask` 数据汇总任务，
* `MapTask` 进行数据计算，从当前的 `DataNode` 数据节点中取目标数据进行计算
* `MapTask` 计算完成的结果，由 `MapReduce` 进行汇总，并将文件数据存储在某一台 `DataNode` 节点上，将文件信息写入到 `NameNode` 中
* `SecondaryNameNode` 会定期对 `NameNode` 中的部分数据进行备份，在 `NomeNode` 异常宕机时会担负起 `NameNode` 的一部分工作

## 2.7，大数据技术生态体系

![1615801867773](E:\gitrepository\study\note\image\hadoop\1615801867773.png)

* `Sqoop`：`Sqoop` 是一款开源的工具，主要用于在 `Hadoop`、`Hive` 与传统的数据库（`MySQL`）间进行数据的传递，可以将一个关系型数据库（例如 ：`MySQL`，`Oracle` 等）中的数据导进到 `Hadoop` 的 `HDFS` 中，也可以将 `HDFS` 的数据导进到关系型数据库中。
* `Flume`：`Flume` 是一个高可用的，高可靠的，分布式的海量日志采集、聚合和传输的系统，`Flume` 支持在日志系统中定制各类数据发送方，用于收集数据。
* `Kafka`：`Kafka` 是一种高吞吐量的分布式发布订阅消息系统。
* `Spark`：`Spark` 是当前最流行的开源大数据内存计算框架，可以基于 `Hadoop` 上存储的大数据进行计算。 
* `Flink`：`Flink` 是当前最流行的开源大数据内存计算框架，用于实时计算的场景较多。
* `Oozie`：`Oozie` 是一个管理 `Hadoop` 作业（job）的工作流程调度管理系统。
* `Hbase`：`HBase` 是一个分布式的、面向列的开源数据库。`HBase` 不同于一般的关系数据库，它是一个适合于非结构化数据存储的数据库。 
* `Hive`：`Hive` 是基于 `Hadoop` 的一个数据仓库工具，可以将结构化的数据文件映射为一张数据库表，并提供简单的 `SQL` 查询功能，可以将 `SQL` 语句转换为 `MapReduce` 任务进行运行。其优点是学习成本低，可以通过类 `SQL` 语句快速实现简单的 `MapReduce` 统计，不必开发专门的 `MapReduce` 应用，十分适合数据仓库的统计分析。 
* `ZooKeepe`：它是一个针对大型分布式系统的可靠协调系统，提供的功能包括：配置维护、名字服务、分布式同步、组服务等。

## 2.8，推荐系统架构图

![1615801926167](E:\gitrepository\study\note\image\hadoop\1615801926167.png)

# 3，Hadoop运行环境搭建（开发重点）

## 3.1，VMWare安装

* 激活码：`UY758-0RXEQ-M81WP-8ZM7Z-Y3HDA`

## 3.2，CentOS 7 硬件安装

### 3.2.1，安装软件操作系统

* 虚拟机搭建

![1615866963772](E:\gitrepository\study\note\image\hadoop\1615866963772.png)

* 虚拟机手动分区

  ![1615875558199](E:\gitrepository\study\note\image\hadoop\1615875558199.png)

* 虚拟机网络和主机名

  ![1615875677955](E:\gitrepository\study\note\image\hadoop\1615875677955.png)

* 创建用户名密码，完成安装

  ```
  admin  123456
  ```

  ![1615875940665](E:\gitrepository\study\note\image\hadoop\1615875940665.png)

* 图形化界面和命令行界面切换

  ```java
  // 查看现在界面
  systemctl get-default
  // 修改为图形化界面
  systemctl set-default graphical.target
  // 修改为命令行界面
  systemctl set-default multi-user.target
  ```

### 3.2.2，配置IP地址

* `VMWare` IP设置

  ![1615878089717](E:\gitrepository\study\note\image\hadoop\1615878089717.png)

* `Windows` IP设置，设置为同一网段

  ![1615878295939](E:\gitrepository\study\note\image\hadoop\1615878295939.png)

* `CentOS7` 静态IP配置

  ```java
  // 修改文件
  vim /etc/sysconfig/network-scripts/ifcfg-ens33
  ```

  ```json
  TYPE="Ethernet"
  PROXY_METHOD="none"
  BROWSER_ONLY="no"
  // 修改为静态IP，
  // dhcp：动态IP
  // static：静态IP
  BOOTPROTO="static"
  DEFROUTE="yes"
  IPV4_FAILURE_FATAL="no"
  IPV6INIT="yes"
  IPV6_AUTOCONF="yes"
  IPV6_DEFROUTE="yes"
  IPV6_FAILURE_FATAL="no"
  IPV6_ADDR_GEN_MODE="stable-privacy"
  NAME="ens33"
  UUID="4335716f-5f90-4d6d-9767-247e9a225f4b"
  DEVICE="ens33"
  ONBOOT="yes"
  // 静态IP地址
  IPADDR=192.168.10.100
  // 网关
  GATEWAY=192.168.10.2
  // 域名解析器
  DNS1=192.168.10.2
  ```

### 3.2.3，配置主机名称

* 修改主机名称

  ```java
  // 修改文件
  vim /etc/hostname
  ```

  ![1615881117509](E:\gitrepository\study\note\image\hadoop\1615881117509.png)

* 修改IP映射文件

  ```java
  // 修改文件
  vim /etc/hosts
  ```

  ![1615881277520](E:\gitrepository\study\note\image\hadoop\1615881277520.png)

## 3.3，虚拟机配置软件

* 安装 `epel-release`：`Extra Packages for Enterprise Linux` 是为“红帽系”的操作系统提供额外的软件包，适用于 `RHEL`、`CentOS` 和 `Scientific Linux`。相当于一个软件仓库，大多数 `rpm` 包在官方仓库中是找不到的

  ```java
  [root@Hadoop100 ~]# yum install -y epel-release
  ```

* 关闭防火墙，关闭防火墙软件自启

  ```java
  // 关闭防火墙
  systemctl stop firewalld
  // 关闭防火墙开机自启动
  systemctl disable firewalld.service
  ```

* 给指定用户添加最高权限

  ```java
  // 在该文件修改权限
  [root@Hadoop100 ~]# vim /etc/sudoers
  // 在指定用户权限不足时，通过添加 sudo 前缀，提升权限
  [pj_zhang@Hadoop100 opt]$ sudo rm -rf rh/
  ```

  ![1615887716716](E:\gitrepository\study\note\image\hadoop\1615887716716.png)

  ![1615887895765](E:\gitrepository\study\note\image\hadoop\1615887895765.png)

* 卸载自带的JDK

  ```java
  // 卸载命令
  rpm -qa | grep -i java | xargs -n1 rpm -e --nodeps
  ```

  * `rpm -qa`：查询所安装的所有rpm软件包
  * `grep -i java`：忽略大小写，过滤带 `java` 字样的软件包
  * `xargs -n1`：每次只传递一个参数
  * `rpm -e --nodeps`：强制卸载软件

## 3.4，克隆虚拟机

* 直接克隆模板虚拟机 `Hadoop100`
* 克隆三个 `Hadoop` 节点 `Hadoop102`、`Hadoop103`、`Hadoop104`，作为集群节点使用
* 修改各个 `Hadoop` 节点的IP地址、主机名称

## 3.4，安装JDK和Hadoop

* 上传并解压文件包

* 配置环境变量

  ```java
  // 创建环境变量文件
  vim /etc/profile.d/my_env.sh
  // 加载环境变量
  source /etc/profile
  ```

  ```sh
  # JAVA_HOME
  export JAVA_HOME=/opt/software/jdk1.8.0_171
  export PATH=$PATH:$JAVA_HOME/bin
  
  # HADOOP_HOME
  export HADOOP_HOME=/opt/software/hadoop-3.1.3
  export PATH=$PATH:$HADOOP_HOME/bin
  export PATH=$PATH:$HADOOP_HOME/sbin
  ```

* `Hadoop` 目录结构

  ![1615966686739](E:\gitrepository\study\note\image\hadoop\1615966686739.png)

  * `bin`：存放对 `Hadoop` 相关服务（`HDFS`、`MapReduce`、`Yarn`）进行操作的脚本
  * `etc`：`Hadoop` 的配置文件目录，存在 `Hadoop` 的配置文件
  * `lib`：存在 `Hadoop` 的本地库（对数据进行压缩解压功能）
  * `sbin`：存在启动或停止 `Hadoop` 相关服务的脚本
  * `share`：存在 `Hadoop` 的相关jar包、依赖和官方文档

# 4，Hadoop运行模式

* `Hadoop` 运行模式包括：**本地模式**、**伪分布式模式**以及**完全分布式模式**
  * **本地模式**：单机运行，只是用来演示案例，一般在自测时使用
  * **伪分布式模式**：也是单机运行，但是具备 `Hadoop` 集群的所有功能，通过一台服务器模拟一个分布式环境。*小公司可以用来测试*
  * **完全分布式模式**：多台服务器组成分布式环境，在生产环境使用

## 4.1，本地模式（基于官方wordcount案例演示）

* 在 `Hadoop` 工作空间中创建 `wcinput` 文件夹

  ```sh
  [root@Hadoop102 hadoop-3.1.3]# mkdir wcinput
  ```

* 在 `wcinput` 文件夹下创建 `word.txt` 文件，并填充如下内容

  ```sh
  [root@Hadoop102 hadoop-3.1.3]# vim ./wcinput/word.txt
  ```

  ![1615972477144](E:\gitrepository\study\note\image\hadoop\1615972477144.png)

* 执行如下命令：该命令是统计在上面文本文件中各个单词出现的次数，并最终以文件形式输出到指定目录中

  ```sh
  [root@Hadoop102 hadoop-3.1.3]# hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar wordcount wcinput wcoutput
  
  # 命令解析
  # hadoop：表示Hadoop命令，
  # jar：以jar包的形式执行
  # ./share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar：执行jar包路径
  # wordcount：执行程序具体的方法名称
  # wcinput：文件输入路径
  # wcoutput：文件输出路径，该路径必须不存在
  ```

* 查看结果：在输出路径 `wcoutput` 下查看。其中 `_SUCCESS` 说明执行成功，结果文本在 `part-r-00000` 文件中

  ![1615972697198](E:\gitrepository\study\note\image\hadoop\1615972697198.png)

  ![1615972772115](E:\gitrepository\study\note\image\hadoop\1615972772115.png)

## 4.2，完全分布式运行模式（开发重点）

### 4.2.1，编写集群分发脚本

#### 4.2.1.1，scp（secure copy） 安全拷贝

* scp 定义：scp可以实现服务器与服务器之间的数据拷贝

* 基本语法

  ```sh
  # 语法模板
  scp  	-r 		$pdir/$fname 		 $user@$host:$pdir/$fname
  命令 	  递归     要拷贝的文件路径/名称	目的地用户@主机:目的地路径/名称
  
  # Hadoop102推文件给Hadoop103
  scp -r /opt/software/jdk1.8.0_171/ root@Hadoop103:/opt/software/
  
  # Hadoop103从Hadoop102拉文件
  scp -r root@Hadoop102:/opt/software/hadoop-3.1.3 /opt/software/
  
  # Hadoop103拉取Hadoop102的文件推送给Hadoop104
  scp -r root@Hadoop102:/opt/software/ root@Hadoop104:/opt/software/
  ```

#### 4.2.1.2，rsync远程同步工具

* `rsync` 主要用于备份和镜像。具有速度快、避免复制相同内容和支持符号链接的优点

* <font color=red>`rsync` 和 `scp` 区别</font>：用 `rsync` 做文件的复制要比 `scp` 的速度快，`rsync` 只对差异文件做更 新。`scp` 是把所有文件都复制过去

* `rsync` 默认是进行增量同步，添加 `--delete` 属性后，可删除本端没有对端有的文件

* 基本语法

  ```sh
  rsync  -av 		$pdir/$fname 		 $user@$host:$pdir/$fname [--delete]
  命令    选项参数  要备份的文件路径/名称    目的地用户@主机:目的地路径/文件名
  
  # 选项参数
  #   -a：归档拷贝
  #   -v：显示复制过程
  # --delete：删除本端没有，对端有的文件
  
  # 从Hadoop103的Hadoop下删除一个文件
  rm -rf /opt/software/hadoop-3.1.3/wcinput/
  
  # Hadoop102推送备份数据到Hadoop103
  rsync -av /opt/software/hadoop-3.1.3/ root@Hadoop103:/opt/software/hadoop-3.1.3/ --delete
  
  # Hadoop103从Hadoop102拉文件
  rsync -av root@Hadoop102:/opt/software/hadoop-3.1.3/ /opt/software/hadoop-3.1.3/
  
  ### Hadoop103不支持从Hadoop102拉文件推送给Hadoop104的双向远程操作
  [root@Hadoop103 hadoop-3.1.3]# rsync -av root@Hadoop102:/opt/software/hadoop-3.1.3/ root@Hadoop104:/opt/software/hadoop-3.1.3/ --delete
  The source and destination cannot both be remote.
  ```

#### 4.2.1.3，xsync集群分发脚本

* **需求：做一个脚本文件，能循环复制文件到所有节点的相同目录下**

* 将脚本放在全局环境变量的路径下，以方便脚本在任何地方可以使用，通过全局变量命令查看，可发现用户目录的 `bin` 文件夹属于全局变量路径，可创建该路径并将脚本置于其中

  ```sh
  # 查看全局环境变量
  [root@Hadoop103 hadoop-3.1.3]# echo $PATH
  /usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
  
  # 创建 bin 目录
  [root@Hadoop103 ~]# mkdir bin
  ```

* 在 `bin` 目录下编写脚本文件 `xsync`，文件内容如下

  ```sh
  #!/bin/bash
  #1. 判断参数个数
  # 如果此处没有参数, 即只执行了 xsync 命令, 没有带文件, 则不执行脚本
  if [ $# -lt 1 ]
  then
  	echo Not Enough Arguement!
  	exit;
  fi
  
  #2. 遍历集群所有机器
  for host in hadoop102 hadoop103 hadoop104
  do
  	# 输出正在处理的机器
  	echo ==================== $host ====================
  	#3. 遍历所有目录，挨个发送
  	for file in $@
  	do
  		#4. 判断文件是否存在
  		if [ -e $file ]
  			then
  				#5. 获取父目录
  				# dirname $file: 输出当前文件路径, 以指定路径形式输出(相对路径/绝对路径)
  				# cd -P file/dir: 进入真是文件, -P 主要考虑弱引用
  				# 此处是进入到文件真是目录, 通过 pwd 获取文件路径
  				pdir=$(cd -P $(dirname $file); pwd)
  				#6. 获取当前文件的名称
  				fname=$(basename $file)
  				# 通过ssh进入到当前遍历的机器, 强行创建文件
  				ssh $host "mkdir -p $pdir"
  				# 复制文件到遍历机器的相同目录下
  				rsync -av $pdir/$fname $host:$pdir
  			else
  				# 输出文件不存在
  				echo $file does not exists!
  		fi
  	done
  done
  ```

* 给脚本文件 `xsync`  添加执行权限

  ```sh
  [root@Hadoop103 bin]# chmod 777 xsync
  ```

* 执行该脚本，分发文件

  ```sh
  # 分发该文件
  [root@Hadoop103 bin]# xsync /root/bin/
  
  # 分发配置文件
  [root@Hadoop102 bin]# xsync /etc/profile.d/my_env.sh
  
  # 配置文件其他端生效
  [root@Hadoop103 bin]# source /etc/profile
  ```

### 4.2.2，SSH无密码登录设置

* SSH基本语法

  ```sh
  # 从Hadoop104节点通过SSH连接Hadoop103节点，输入密码连接成功
  [root@Hadoop104 ~]# ssh Hadoop103
  
  # 退出Hadoop103的链接
  [root@Hadoop103 ~]# exit
  ```

  ![1616139453619](E:\gitrepository\study\note\image\hadoop\1616139453619.png)

* 免密登录原理：**<font color=red>A服务器授权公钥给B服务器后，通过A服务器连接B服务器不再需要输入B服务器密码，这跟我理解的是反的...</font>**

  ![1616139225275](E:\gitrepository\study\note\image\hadoop\1616139225275.png)

* SSH免密登录配置

  ```sh
  # 在Hadoop102机器上生成公钥和私钥
  # 生成的公钥和私钥文件在 家文件夹下的 .ssh 隐藏文件夹下
  # id_rsa：表示私钥
  # id_ras.pub：表示公钥
  [root@Hadoop102 .ssh]# ssh-keygen -t rsa
  
  # 将公钥分发给指定机器，则访问指定机器不需要密码
  # 在对端机器下，会在 家文件夹下的 .ssh 隐藏文件夹下，生成对应授权文件
  [root@Hadoop102 .ssh]# ssh-copy-id Hadoop103
  ```

  * 生成秘钥对文件

    ![1616139849527](E:\gitrepository\study\note\image\hadoop\1616139849527.png)

  * 将公密文件发送到指定节点，则再次通过 `ssh` 访问该节点时，不在需要输入密码

    ![1616139944143](E:\gitrepository\study\note\image\hadoop\1616139944143.png)

  * 在指定节点，即 `Hadoop103` 下，会生成相关授权文件

    ![1616139986740](E:\gitrepository\study\note\image\hadoop\1616139986740.png)

  * 如果需要双向免密，则需要进行双向配置！

### 4.2.3，集群配置

#### 4.2.3.1，集群部署规划

* <font color=red>`HDFS` 的 `NameNode` 和 `SecondaryNameNode` 不要处于一个节点，节省性能</font>

* <font color=red>`ResourceManager` 消耗内存也挺大，不要和 `NameNode` 和 `SecondaryNameNode` 处于同一个节点</font>

  ![1616142101427](E:\gitrepository\study\note\image\hadoop\1616142101427.png)

#### 4.2.3.2，配置文件说明

* `Hadoop` 配置文件分为两类：默认配置文件和自定义配置文件，只有用户想要修改某一默认配置值时，才需要对自定义配置文件进行修改，更改响应属性值。<font color=red>可以理解为自定义配置文件是对默认配置文件重叠属性的覆盖</font>

* 默认配置文件

  ![1616142288196](E:\gitrepository\study\note\image\hadoop\1616142288196.png)

* 自定义配置文件：**`core-site.xml`**、**`hdfs-site.xml`**、**`yarn-site.xml`**、**`mapred-site.xml`** 四个配置文件存放在 `$HADOOP_HOME/etc/hadoop` 这个路径上，用户可以根据项目需求重新进行修改配置。

#### 4.2.3.3，配置集群

* 集群配置完全按照集群部署规划进行配置，分别对自定义文件进行配置

* 核心配置：`core-site.xml`

  ```xml
  <configuration>
      <!-- 指定 NameNode 的地址 -->
      <property>
          <name>fs.defaultFS</name>
          <value>hdfs://Hadoop102:8020</value>
      </property>
  
      <!-- 指定 hadoop 数据的存储目录 -->
      <property>
          <name>hadoop.tmp.dir</name>
          <value>/opt/software/hadoop-3.1.3/data</value>
      </property>
      <!-- 配置 HDFS 网页登录使用的静态用户为 root -->
      <!-- 配置该用户可直接在 HDFS 页面上对文件目录进行操作 -->
      <property>
          <name>hadoop.http.staticuser.user</name>
          <value>root</value>
      </property>
  </configuration>
  ```

* HDFS配置：`hdfs-site.xml`

  ```xml
  <configuration>
  	<!-- nn web 端访问地址-->
  	<property>
  		<name>dfs.namenode.http-address</name>
  		<value>Hadoop102:9870</value>
  	</property>
  	
  	<!-- 2nn web 端访问地址-->
  	<property>
  		<name>dfs.namenode.secondary.http-address</name>
  		<value>Hadoop104:9868</value>
  	</property>
  </configuration>
  ```

* YARN配置：`yarn-site.xml`

  ```xml
  <configuration>
  	<!-- 指定 MR 走 shuffle -->
  	<property>
  		<name>yarn.nodemanager.aux-services</name>
  		<value>mapreduce_shuffle</value>
  	</property>
  	
  	<!-- 指定 ResourceManager 的地址-->
  	<property>
  		<name>yarn.resourcemanager.hostname</name>
  		<value>Hadoop103</value>
  	</property>
  	
  	<!-- 环境变量的继承 -->
  	<property>
  		<name>yarn.nodemanager.env-whitelist</name>
  		<value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
  	</property>
  </configuration>
  ```

* MapReduce配置：`mapred-site.xml`

  ```xml
  <configuration>
  	<!-- 指定 MapReduce 程序运行在 Yarn 上 -->
  	<property>
  		<name>mapreduce.framework.name</name>
  		<value>yarn</value>
  	</property>
  </configuration>
  ```

* 分发配置文件到其他 `Hadoop` 节点

  ```sh
  [root@Hadoop102 hadoop]# xsync core-site.xml
  [root@Hadoop102 hadoop]# xsync hdfs-site.xml
  [root@Hadoop102 hadoop]# xsync yarn-site.xml
  [root@Hadoop102 hadoop]# xsync mapred-site.xml
  ```

#### 4.2.3.4，群起集群

##### 4.2.3.4.1，配置 `workers` 文件，并分发到其他所有文件

```sh
# 编辑Workers文件
[root@Hadoop102 hadoop]# cd /opt/software/hadoop-3.1.3/etc/hadoop/
[root@Hadoop102 hadoop]# vim workers

# 分发到其他节点
[root@Hadoop102 hadoop]# xsync workers
```

```sh
# workers内容
Hadoop102
Hadoop103
Hadoop104
```

##### 4.2.3.4.2，启动集群

* <font color=red>如果集群是第一次启动：</font>需要在 `NameNode` 节点上先格式化 `NameNode`，注意：格式化 `NameNode`，会产生新的集群 id，导致 `NameNode` 和 `DataNode` 的集群 id 不一致，集群找不到历史数据。如果集群在运行过程中报错，需要重新格式化 `NameNode` 的话，一定要先停 止 `Namenode` 和 `Datanode` 进程，并且要删除所有机器的 `data` 和 `logs` 目录，然后再进行格式化。初始化成功字样如下截图

  ```sh
  [root@Hadoop102 hadoop]# hdfs namenode -format
  ```

  ![1616149276121](E:\gitrepository\study\note\image\hadoop\1616149276121.png)

* `NameNode` 初始化成功后，会在 `data` 数据文件夹下，生成 `VERSION` 文件，标识集群年代

  ```sh
  # 文件路径
  /opt/software/hadoop-3.1.3/data/dfs/name/current/VERSION
  # 文件内容
  #Fri Mar 19 18:02:56 CST 2021
  namespaceID=1740009071
  clusterID=CID-6a79a63c-e310-422e-803b-acc932c35072
  cTime=1616148176325
  storageType=NAME_NODE
  blockpoolID=BP-668303545-192.168.10.102-1616148176325
  layoutVersion=-64
  ```

* 启动 `HDFS`：启动成功后，`Hadoop102` 节点上会启动 `NameNode` 和 `DataNode` 两个 `Java` 进程，`Hadoop103` 上会启动 `DataNode` 进程，`Hadoop104` 上会启动 `DataNode` 和 `SecondaryNameNode` 两个进程

  ```sh
  # 这一步如果报错，直接参考4.2.3.4.3
  [root@Hadoop102 hadoop-3.1.3]# sbin/start-dfs.sh
  ```

  ![1616467090772](E:\gitrepository\study\note\image\hadoop\1616467090772.png)

  ![1616467158510](E:\gitrepository\study\note\image\hadoop\1616467158510.png)

  ![1616467216972](E:\gitrepository\study\note\image\hadoop\1616467216972.png)

  

* <font color=red>在配置了 `ResourceManager` 的节点 `Hadoop103` 上</font>启动 `Yarn`：启动成功后，在 `Hadoop103` 上会启动 `ResourceManager` 和 `NodeManager`  两个 `Java` 进程，在 `Hadoop102` 和 `Hadoop104` 上会启动 `NodeManager` 进程

  ```sh
  [root@Hadoop102 hadoop-3.1.3]# sbin/start-yarn.sh
  ```

  ![1616467277902](E:\gitrepository\study\note\image\hadoop\1616467277902.png)

  ![1616467395583](E:\gitrepository\study\note\image\hadoop\1616467395583.png)

  ![1616467408393](E:\gitrepository\study\note\image\hadoop\1616467408393.png)

* 防火墙开放端口

  ```sh
  # 查看已经开放的端口
  firewall-cmd --list-ports
  # 开放端口
  # 9870:HDFS页面
  # 8088:Yarn页面
  # 19888:历史服务器和日志聚集
  firewall-cmd --zone=public --add-port=80/tcp --permanent
  # 开放完成后重新加载防火墙
  systemctl reload firewalld
  ```

* 进入 `HDFS` 系统管理界面：http://192.168.10.102:9870

  ![1616467504686](E:\gitrepository\study\note\image\hadoop\1616467504686.png)

* 进行 `Yarn` 系统管理界面：http://192.168.10.103:8088

  ![1616467679446](E:\gitrepository\study\note\image\hadoop\1616467679446.png)

##### 4.2.3.4.3，<font color=red>启动 `HDFS` 直接排错处理</font>

* 如果直接用 `root` 用户运行，不出意外会报下面错误。这是因为 `Hadoop` 默认执行不考虑 `root` 用户，需要修改 `.sh` 启动文件

  ![1616149099199](E:\gitrepository\study\note\image\hadoop\1616149099199.png)

* 修改 `start-dfs.sh` 和 `stop-dfs.sh` 文件：在文件头加下面内容

  ```sh
  #!/usr/bin/env bash     -> # 这表示第一行
  HDFS_DATANODE_USER=root
  HADOOP_SECURE_DN_USER=hdfs
  HDFS_NAMENODE_USER=root
  HDFS_SECONDARYNAMENODE_USER=root
  ```

* 修改 `start-yarn.sh` 和 `stop-dfs.sh` 文件：在文件头加下面内容

  ```sh
  #!/usr/bin/env bash
  YARN_RESOURCEMANAGER_USER=root
  HADOOP_SECURE_DN_USER=yarn
  YARN_NODEMANAGER_USER=root
  ```

* 修改完成后重新启动 `HDFS`，启动成功。成功字样如截图

  ![1616149252471](E:\gitrepository\study\note\image\hadoop\1616149252471.png)

##### 4.2.3.4.3，`Hadoop` 文件存储测试

* 创建文件夹

  ```sh
  [root@Hadoop102 sbin]# hadoop fs -mkdir /mydir
  ```

  ![1616467842984](E:\gitrepository\study\note\image\hadoop\1616467842984.png)

* 上传小文件

  ```sh
  [root@Hadoop102 wcoutput]# hadoop fs -put ./part-r-00000 /mydir
  ```

  ![1616468435471](E:\gitrepository\study\note\image\hadoop\1616468435471.png)

* 上传大文件

  ```sh
  # ./jdk-8u171-linux-x64.tar.gz：当前机器文件路径
  # /mydir：远程机器文件路径
  [root@Hadoop102 softapp]# hadoop fs -put ./jdk-8u171-linux-x64.tar.gz /mydir
  ```

  ![1616468574633](E:\gitrepository\study\note\image\hadoop\1616468574633.png)

* 上传文件后，文件在 `Hadoop` 数据文件的存储路径在定义的 `data` 目录下，一路到最终的文件夹下，可看到如下文件列表。其中 `blk_1073741825` 是第一次上传的小文件，`blk_1073741826` 和 `blk_1073741827` 是大文件的分片存储

  ![1616468682216](E:\gitrepository\study\note\image\hadoop\1616468682216.png)

  * 查看小文件

    ![1616468834509](E:\gitrepository\study\note\image\hadoop\1616468834509.png)

  * 查看大文件

    ```sh
    # 将文件写到压缩包中，（因为传的压缩包，直接还原）
    [root@Hadoop102 subdir0]# cat blk_1073741826 >> tmp.tar.gz
    [root@Hadoop102 subdir0]# cat blk_1073741827 >> tmp.tar.gz
    # 解压后，解压出完整的jdk包
    [root@Hadoop102 subdir0]# tar -zxvf tmp.tar.gz 
    ```

    ![1616469890534](E:\gitrepository\study\note\image\hadoop\1616469890534.png)

##### 4.2.3.4.4，`YARN` 集群调度测试

* 按本机演示按时继续执行一次 `Hadoop` 计算任务

  ```sh
  # /wcinput：输入路径，需在Hadoop存储系统中存在
  # /wcoutput：输出路径，需在Hadoop存储系统存在
  [root@Hadoop102 hadoop-3.1.3]# hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar wordcount /wcinput /wcoutput
  ```

* 执行任务后，在 `Yarn` 调度系统管理页面可以查看调度任务

  ![1616474996103](E:\gitrepository\study\note\image\hadoop\1616474996103.png)

* 点击 `History` 可以查看历史任务调度信息，此时不可查询，<font color=red>该历史任务查看需要配置</font>

* 执行完成后，可在 `HDFS` 存储系统的 `/wcoutput` 路径下，查看计算结果

  ![1616475111539](E:\gitrepository\study\note\image\hadoop\1616475111539.png)

##### 4.2.3.4.5，集群崩溃处理

* 先停掉集群，从 `Yarn` 到 `HDFS`

* 然后删除集群节点中的 `$HADOOP_HOME/data` 和 `$HADOOP_HOME/logs` 文件夹
* 再执行 `NameNode` 的格式化命令
* 最后重启集群，即可恢复集群功能

#### 4.2.3.5，历史服务器配置

> 为了查询程序的历史运行情况，需要配置历史服务器，具体配置步骤如下：

* 配置 `mapred-site.xml` 

  ```xml
  <!-- 历史服务器端地址 -->
  <property>
  	<name>mapreduce.jobhistory.address</name>
  	<value>hadoop102:10020</value>
  </property>
  <!-- 历史服务器 web 端地址 -->
  <property>
  	<name>mapreduce.jobhistory.webapp.address</name>
  	<value>hadoop102:19888</value>
  </property>
  ```

* 分发配置，分发到其他集群节点

  ```sh
  [root@Hadoop102 bin]# xsync ../etc/hadoop/mapred-site.xml 
  ```

* 在 `Hadoop102` 中启动历史服务器，在启动之前需要先停止 `Yarn` 服务

  ```sh
  # 停止 Yarn 服务
  [root@Hadoop102 bin]# ../sbin/stop-yarn.sh 
  # 启动 historyserver 服务
  [root@Hadoop102 bin]# mapred --daemon start historyserver
  ```

  ![1616481739716](E:\gitrepository\study\note\image\hadoop\1616481739716.png)

* 进入链接 `http://hadoop102:19888/jobhistory`，查看历史服务是否启动正常

  ![1616481748872](E:\gitrepository\study\note\image\hadoop\1616481748872.png)

* 再次出发一次调用任务，在 `Yarn` 页面中查看调度详情

  ![1616481796312](E:\gitrepository\study\note\image\hadoop\1616481796312.png)

* 点击 `History` 查看任务详情：通过点击左侧页签切换，可具体查看配置信息、计算信息、汇总信息等；<font color=red>`logs` 相关功能，参考下一部分：日志聚集功能</font>

  ![1616482045830](E:\gitrepository\study\note\image\hadoop\1616482045830.png)

#### 4.2.3.6，配置日志聚集功能

> 日志聚集概念：应用运行完成后，将程序运行日志信息上传到 `HDFS` 系统上。通过对聚集后的日志查看，可以方便的看到程序运行详情，方便系统调试。<font color=red>配置日志聚集，需要重启 `ResourceManager` 和 `HistoryServer` 服务</font>

* 配置 `yarn-site.xml` 文件

  ```xml
  <!-- 开启日志聚集功能 -->
  <property>
  	<name>yarn.log-aggregation-enable</name>
  	<value>true</value>
  </property>
  <!-- 设置日志聚集服务器地址 -->
  <property> 
  	<name>yarn.log.server.url</name> 
  	<value>http://Hadoop102:19888/jobhistory/logs</value>
  </property>
  <!-- 设置日志保留时间为 7 天 -->
  <property>
  	<name>yarn.log-aggregation.retain-seconds</name>
  	<value>604800</value>
  </property>
  ```

* 分发配置文件信息

  ```sh
  [root@Hadoop102 bin]# xsync ../etc/hadoop/yarn-site.xml
  ```

* 停掉并重启 `ResourceManager` 和 `HistoryServer`

  ```sh
  # 在Hadoop103 停掉ResourceManager
  [root@Hadoop103 sbin]# ./stop-yarn.sh
  # 在Hadoop102 停掉HistoryServer
  [root@Hadoop102 bin]# mapred --daemon stop historyserver
  
  # 在Hadoop103 启动Yarn
  [root@Hadoop103 sbin]# ./start-yarn.sh
  # 在Hadoop102 启动HistoryServer
  [root@Hadoop102 bin]# mapred --daemon start historyserver
  ```

* 重新启动一个 `Hadoop` 任务，并在 `Yarn` 查询任务信息，并通过 `History` 进入到 `Logs` 中

  ![1616483080369](E:\gitrepository\study\note\image\hadoop\1616483080369.png)

### 4.2.4，集群启动/停止方式总结

#### 4.2.4.1，各个模块整体启动方式

* 整体启动 `HDFS`

  ```sh
  # 整体启动 HDFS
  [root@Hadoop102 bin]# /opt/software/hadoop-3.1.3/sbin/start-dfs.sh 
  # 整体停止 HDFS
  [root@Hadoop102 bin]# /opt/software/hadoop-3.1.3/sbin/stop-dfs.sh 
  ```

* 整体启动 `Yarn`

  ```sh
  # 整体启动 Yarn
  [root@Hadoop102 bin]# /opt/software/hadoop-3.1.3/sbin/start-yarn.sh 
  # 整体停止 Yarn
  [root@Hadoop102 bin]# /opt/software/hadoop-3.1.3/sbin/stop-yarn.sh 
  ```

#### 4.2.4.2，各个模块单独启动方式

* 分别启动 `HDFS` 组件

  ```sh
  # 分别启动 HDFS 组件
  [root@Hadoop102 bin]# mapred --daemon start namenode|datenode|secondarynamenode
  # 分别停止 HDFS 组件
  [root@Hadoop102 bin]# mapred --daemon stop namenode|datenode|secondarynamenode
  ```

* 分别启动 `Yarn` 组件

  ```sh
  # 分别启动 Yarn 组件
  [root@Hadoop102 bin]# mapred --daemon start resourcemanager|nodemanager
  # 分别停止 Yarn 组件
  [root@Hadoop102 bin]# mapred --daemon stop resourcemanager|nodemanager
  ```

* 启动 `HistoryServer` 历史服务器

  ```sh
  # 启动 HistoryServer
  [root@Hadoop102 bin]# mapred --daemon start historyserver
  # 停止 HistoryServer
  [root@Hadoop102 bin]# mapred --daemon stop historyserver
  ```

#### 4.2.4.3，`Hadoop` 服务器启停脚本

```sh
# 编辑文件
[root@Hadoop102 bin]# vim /root/bin/myhadoop
# 文件授权
[root@Hadoop102 bin]# chmod 777 myhadoop
```

```sh
#!/bin/bash

# 输入参数少于一个, 错误退出
if [ $# -lt 1 ]
	then
		echo "No Args Input..."
		exit ;
fi

# 对入参字符进行分支处理
case $1 in
"start")
	# 启动脚本分支处理, 依次启动各个服务组件
	echo " =================== 启动 hadoop 集群 ==================="
	echo " --------------- 启动 hdfs ---------------"
	# 在 102 启动 HDFS
	ssh hadoop102 "/opt/software/hadoop-3.1.3/sbin/start-dfs.sh"
	echo " --------------- 启动 yarn ---------------"
	# 在 103 启动 Yarn
	ssh hadoop103 "/opt/software/hadoop-3.1.3/sbin/start-yarn.sh"
	echo " --------------- 启动 historyserver ---------------"
	# 在 102 启动 historyServer
	ssh hadoop102 "/opt/software/hadoop-3.1.3/bin/mapred --daemon start historyserver"
;;
"stop")
	# 停止脚本分支处理, 依次停止各个服务组件
	echo " =================== 关闭 hadoop 集群 ==================="
	echo " --------------- 关闭 historyserver ---------------"
	# 在 102 关闭 HistoryServer
	ssh hadoop102 "/opt/software/hadoop-3.1.3/bin/mapred --daemon stop historyserver"
	echo " --------------- 关闭 yarn ---------------"
	# 在 103 关闭 Yarn
	ssh hadoop103 "/opt/software/hadoop-3.1.3/sbin/stop-yarn.sh"
	echo " --------------- 关闭 hdfs ---------------"
	# 在 102 关闭 HDFS
	ssh hadoop102 "/opt/software/hadoop-3.1.3/sbin/stop-dfs.sh"
;;
*)
	# 其他输入均为错误处理
	echo "Input Args Error..."
;;
esac
```

#### 4.2.4.4，查看三台服务器 `Java` 进程脚本

```sh
# 编辑脚本
[root@Hadoop102 bin]# vim /root/bin/jpsall
# 脚本执行权限
[root@Hadoop102 bin]# chmod 777 jpsall
```

```sh
#!/bin/bash

# 遍历三台机器
for host in Hadoop102 Hadoop103 Hadoop104
do
	# 输出当前遍历机器
	echo =============== $host ===============
	# 打印 JPS 命令
	ssh $host jps 
done
```

### 4.2.5，常用端口号及2.x、3.x对比

| 端口名称           | Hadoop2.x | Hadoop3.x      |
| ------------------ | --------- | -------------- |
| NameNode内部通信   | 8020/9000 | 8020/9000/9820 |
| NameNode外部通信   | 9870      | 9870           |
| Yarn执行任务端口   | 8088      | 8088           |
| 历史服务器通信端口 | 19888     | 19888          |

### 4.2.6，常用配置文件及2.x，3.x对比

| 文件名称            | Hadoop2.x       | Hadoop3.x       |
| ------------------- | --------------- | --------------- |
| 核心自定义配置      | core-site.xml   | core-site.xml   |
| HDFS自定义配置      | hdfs-site.xml   | hdfs-site.xml   |
| Yarn自定义配置      | yarn-site.xml   | yarn-site.xml   |
| MapReduce自定义配置 | mapred-site.xml | mapred-site.xml |
| 集群启动配置        | slaves          | workers         |

# 5，HDFS

## 5.1，`HDFS` 概述

### 5.1.1，产生背景和定义

1. `HDFS` 产生背景

   > 随着数据量越来越大，在一个操作系统存不下所有的数据，那么就分配到更多的操作系统管理的磁盘中，但是不方便管理和维护，迫切需要一种系统来管理多台机器上的文件，这就是分布式文件管理系统。<font color=red>`HDFS` 只是分布式文件管理系统中的一种。</font>

2. `HDFS` 定义

   > <font color=red>`HDFS（Hadoop Distributed File System）`</font>，它是一个文件系统，用于存储文件，通过目录树来定位文件；其次，它是分布式的，由很多服务器联合起来实现其功能，集群中的服务器有各自的角色。
   >
   > <font color=red>`HDFS`  的使用场景：适合一次写入，多次读出的场景。</font>一个文件经过创建、写入和关闭之后就不需要改变。

### 5.1.2，`HDFS` 优缺点

#### 5.1.2.1，`HDFS` 优点

1. **高容错性**
   
   * 数据自动保存到多个副本中，通过增加副本的形式，提高容错性
   
     ![1616557435969](E:\gitrepository\study\note\image\hadoop\1616557435969.png)
   
   * 某一个副本丢失后，可以自定进行数据备份恢复

![1616557459242](E:\gitrepository\study\note\image\hadoop\1616557459242.png)

2. **适合处理大数据**
   * 数据规模：能够处理数据规模达到 GB、TB 甚至 PB 级别的数据
   * 文件规模：能够处理百万规模以上的文件数量

#### 5.1.2.2，`HDFS` 缺点

* <font color=red>不适合低延时数据访问</font>，比如毫秒级的存储数据，是做不到的
* <font color=red>无法高效的对大量小文件进行存储</font>
  * 存储大量小文件的话，需要占用 `NameNode` 大量空间来存储文件目录和块信息。<font color=red>但 `NameNode` 的内存总是有限的</font>
  * 小文件存储的寻址时间会超过读时间，这违背了 `HDFS` 的设计目标
* 不支持并发写入和文件随机修改
  * 一个文件只能有一个写，不允许多个线程同时写
  * <font color=red>仅支持数据 `append`（追加），</font>不支持文件随机修改

### 5.1.3，`HDFS` 组成

![1617241588463](E:\gitrepository\study\note\image\hadoop\1617241588463.png)

#### 5.1.3.1，NameNode（NN）：就是 `master`，是一个主管，管理者：

* 管理 `HDFS` 的名称空间
* 配置副本策略
* 管理数据块（Block）映射信息
* 处理客户端读写请求

#### 5.1.3.2，DataNode：就是 `slave`，`NameNode` 下达命令，`DataNode` 执行实际操作

* 存储实际的数据块
* 执行数据块的读/写操作

#### 5.1.3.3，SecondaryNameNode（2NN）：并非 `NameNode` 的热备，不能马上替换 `NameNode` 并提供服务

* 辅助 `NameNode`，分担其工作，比如定期合并 `Fsimage` 和 `Edits`，并推送给 `NameNode`
* 在紧急情况下，可辅助恢复 `NameNode`

#### 5.1.3.4，Client：客户端

* 文件切分：客户端在上传大文件时，会根据 `Block` 大小文件切割成一个一个的 `Block`，然后进行文件上传
* 与 `NameNode` 交互：获取文件的位置信息
* 与 `DataNode` 交互：读取或写入数据
* `Client` 提供了一组命令来管理 `NameNode`：比如 `NameNode` 格式化
* `Client` 提供了一组命令来操作 `HDFS`，如对 `HDFS` 进行增删改查

### 5.1.4，`HDFS` 文件块大小

> `HDFS` 中的文件在物理上是分块存储 `Block`，块的大小可以通过配置 `HDFS` 配置文件的参数 `dfs.blocksize` 来规定，<font color=red>默认大小在 `Hadoop2.x/Hadoop3.x` 版本中是128MB，在 `Hadoop1.x` 中是64MB</font>

* `HDFS` 块默认大小定义
  * 在 `Hadoop` 集群中，数据文件以 `Block` 块为单位存储在硬件设备上
  * 如果文件寻址时间为 `10ms`，即查找到目标 `Block` 所需要的时间为 `10ms`
  * <font color=red>寻址时间为传输时间的1%时，为集群最佳状态</font>，则以 `10ms` 的寻址时间为标准，传输时间大概为 `1s`
  * 目前的机械硬盘的传输速率在 80 ~ 100M左右，所以一个 `Block` 的默认大小为 128MB；<font color=red>如果是固态硬盘，则可将默认大小调整为 256MB</font>
* `HDFS` 默认大小不能设置过大，也不能设置过小
  * `HDFS` 的块如果设置太小，则会在集群中存在大量的 `Block` 块，增加寻址时间，程序一直在寻找数据所存储的块
  * `HDFS` 的块如果设置的过大，从磁盘传输数据时间会明显定义这个块所需要的时间比，减少数据处理并发量，导致程序在处理数据时特别慢
  * <font color=red>`HDFS` 的块大小取决于磁盘传输速率</font>

## 5.2，`HDFS` 的 `Shell` 操作

```sh
# 基本语法
hadoop fs|dfs|hdfs 具体命令
```

### 5.2.1，命令大全

```sh
# 通过该命令呈现 Hadoop 的所有可执行命令
[root@Hadoop102 bin]# hadoop fs 
Usage: hadoop fs [generic options]
	[-appendToFile <localsrc> ... <dst>]
	[-cat [-ignoreCrc] <src> ...]
	[-checksum <src> ...]
	[-chgrp [-R] GROUP PATH...]
	[-chmod [-R] <MODE[,MODE]... | OCTALMODE> PATH...]
	[-chown [-R] [OWNER][:[GROUP]] PATH...]
	[-copyFromLocal [-f] [-p] [-l] [-d] [-t <thread count>] <localsrc> ... <dst>]
	[-copyToLocal [-f] [-p] [-ignoreCrc] [-crc] <src> ... <localdst>]
	[-count [-q] [-h] [-v] [-t [<storage type>]] [-u] [-x] [-e] <path> ...]
	[-cp [-f] [-p | -p[topax]] [-d] <src> ... <dst>]
	[-createSnapshot <snapshotDir> [<snapshotName>]]
	[-deleteSnapshot <snapshotDir> <snapshotName>]
	[-df [-h] [<path> ...]]
	[-du [-s] [-h] [-v] [-x] <path> ...]
	[-expunge]
	[-find <path> ... <expression> ...]
	[-get [-f] [-p] [-ignoreCrc] [-crc] <src> ... <localdst>]
	[-getfacl [-R] <path>]
	[-getfattr [-R] {-n name | -d} [-e en] <path>]
	[-getmerge [-nl] [-skip-empty-file] <src> <localdst>]
	[-head <file>]
	[-help [cmd ...]]
	[-ls [-C] [-d] [-h] [-q] [-R] [-t] [-S] [-r] [-u] [-e] [<path> ...]]
	[-mkdir [-p] <path> ...]
	[-moveFromLocal <localsrc> ... <dst>]
	[-moveToLocal <src> <localdst>]
	[-mv <src> ... <dst>]
	[-put [-f] [-p] [-l] [-d] <localsrc> ... <dst>]
	[-renameSnapshot <snapshotDir> <oldName> <newName>]
	[-rm [-f] [-r|-R] [-skipTrash] [-safely] <src> ...]
	[-rmdir [--ignore-fail-on-non-empty] <dir> ...]
	[-setfacl [-R] [{-b|-k} {-m|-x <acl_spec>} <path>]|[--set <acl_spec> <path>]]
	[-setfattr {-n name [-v value] | -x name} <path>]
	[-setrep [-R] [-w] <rep> <path> ...]
	[-stat [format] <path> ...]
	[-tail [-f] [-s <sleep interval>] <file>]
	[-test -[defsz] <path>]
	[-text [-ignoreCrc] <src> ...]
	[-touch [-a] [-m] [-t TIMESTAMP ] [-c] <path> ...]
	[-touchz <path> ...]
	[-truncate [-w] <length> <path> ...]
	[-usage [cmd ...]]
```

```sh
# 具体查看某命令使用情况
[root@Hadoop102 bin]# hadoop fs -help tail
-tail [-f] [-s <sleep interval>] <file> :
  Show the last 1KB of the file.
                                                                               
  -f  Shows appended data as the file grows.                                   
  -s  With -f , defines the sleep interval between iterations in milliseconds.
```

### 5.2.2，`HDFS` 文件上传

* `-mkdir`：创建一个文件夹

  ```sh
  [root@Hadoop102 bin]# hadoop fs -mkdir /sanguo
  ```

* `-moveFromLocal`：从本地剪切文件粘贴到 `HDFS`

  ```sh
  # ./shuguo.txt：本地路径
  # /sanguo：HDFS远程路径
  [root@Hadoop102 sanguo]# hadoop fs -moveFromLocal ./shuguo.txt /sanguo
  ```

* `-copyFromLocal`：从本地复制文件粘贴到 `HDFS`

  ```sh
  # ./wuguo.txt：本地路径
  # /sanguo：HDFS远程路径
  [root@Hadoop102 sanguo]# hadoop fs -copyFromLocal ./wuguo.txt /sanguo
  ```

* `-put`：等同于 `-copyFromLocal`，正式使用更习惯用 `-put`

  ```sh
  # ./weiguo.txt：本地路径
  # /sanguo：HDFS远程路径
  [root@Hadoop102 sanguo]# hadoop fs -put ./weiguo.txt /sanguo
  ```

* `-appendToFile`：追加文件内容到现有文件

  ```sh
  # ./shuguo.txt：本地需要追加的文件
  # /sanguo/shuguo.txt：远程文件，此处一定是文件路径
  [root@Hadoop102 sanguo]# hadoop fs -appendToFile ./shuguo.txt /sanguo/shuguo.txt
  ```

### 5.2.3，`HDFS` 文件下载

* `-copyToLocal`：从 `HDFS` 拷贝到本地

  ```sh
  # /sanguo/shuguo.txt：HDFS远程文件，此处一定是文件路径
  # ../：本地路径
  [root@Hadoop102 sanguo]# hadoop fs -copyToLocal /sanguo/shuguo.txt ../
  ```

* `-get`：等同于 `-copyToFile`，正式使用更习惯用 `-get`

  ```sh
  # /sanguo/wuguo.txt：HDFS远程文件，此处一定是完整路径
  # ../：本地路径
  [root@Hadoop102 opt]# hadoop fs -get /sanguo/wuguo.txt ../
  ```

### 5.2.4，`HDFS` 文件直接操作

* `-ls`：显示远程文件目录信息

  ```sh
  # /：远程文件路径
  [root@Hadoop102 /]# hadoop fs -ls /
  ```

* `-cat`：显示远程文件内容

  ```sh
  # /sanguo/shuguo.txt：远程文件完整路径
  [root@Hadoop102 /]# hadoop fs -cat /sanguo/shuguo.txt
  ```

* `-chgrp`：修改文件所属群组

  ```sh
  # pj_zhang：要修改为的群组名称
  # /sanguo/shuguo.txt：要修改的文件路径
  [root@Hadoop102 /]# hadoop fs -chgrp pj_zhang /sanguo/shuguo.txt
  ```

* `-chown`：修改文件所属用户和所属群组

  ```sh
  # pj_zhang:pj_zhang：要修改的用户：要修改的群组
  # /sanguo/shuguo.txt：要修改权限的文件
  [root@Hadoop102 /]# hadoop fs -chown pj_zhang:pj_zhang /sanguo/shuguo.txt
  ```

* `-chmod`：修改文件执行权限

  ```sh
  # 777：需要增加的权限
  # /sanguo/shuguo.txt：要修改的文件
  [root@Hadoop102 /]# hadoop fs -chmod 777 /sanguo/shuguo.txt
  ```

* `-cp`：在 `HDFS` 的文件路径中复制文件

  ```sh
  # /sanguo/shuguo.txt：源文件路径
  # /copy：目标路径
  [root@Hadoop102 /]# hadoop fs -cp /sanguo/shuguo.txt /copy
  ```

* `-mv`：在 `HDFS` 的文件路径中移动文件

  ```sh
  # /sanguo/wuguo.txt：源文件路径
  # /copy：目标路径
  [root@Hadoop102 /]# hadoop fs -mv /sanguo/wuguo.txt /copy
  ```

* `-tail`：显示文件末尾1KB的数据

  ```sh
  # -f：实时读取模式
  # /sanguo/weiguo.txt：要查看的HDFS文件
  [root@Hadoop102 /]# hadoop fs -tail -f /sanguo/weiguo.txt
  ```

* `-rm [-r]`：删除文件或者文件夹，`-r` 表示递归删除

  ```sh
  # /copy/wuguo.txt：要删除的文件，此处不能是文件夹
  [root@Hadoop102 /]# hadoop fs -rm /copy/wuguo.txt
  # /copy：要删除的文件或文件夹
  [root@Hadoop102 /]# hadoop fs -rm -r /copy
  ```

* `-du`：统计文件夹下文件大小信息

  ```sh
  # -h：以文件大小格式化的方式呈现，如10MB
  # -s：以汇总的形式统计大小
  # -v：展示表头信息
  # /：要统计的文件路径
  [root@Hadoop102 /]# hadoop fs -du -h -s -v /
  ```

  ![1617271930454](E:\gitrepository\study\note\image\hadoop\1617271930454.png)

* `-setrep`：设置 `HDFS` 中文件的副本数量；<font color=red>这里设置的副本数只是记录在 `NameNode` 的元数据中，是否真的有这么多副本，还需要看集群的 `DataNode` 节点是否足够。如果节点不够，则最多到该数量副本，等节点够10个后，此时副本数量满足需求</font>

  ```sh
  # 10：要设置的副本数量
  # /sanguo/weiguo.txt：要设置的目标文件
  [root@Hadoop102 /]# hadoop fs -setrep 10 /sanguo/weiguo.txt
  ```


## 5.3，`HDFS` 的API操作

### 5.3.1，环境搭建

1. 取 `HDFS` 的 `windows` 包，放在指定路径，并配置环境变量，运行 `winutils.exe` 文件，文件不报错则说明配置成功：

   * 环境变量

     ![1617700976588](E:\gitrepository\study\note\image\hadoop\1617700976588.png)

   * 运行文件：

   ![1617700917212](E:\gitrepository\study\note\image\hadoop\1617700917212.png)

2. IDE 中引入 Maven 依赖

   ```xml
   <dependencies>
   	<dependency>
   		<groupId>junit</groupId>
   		<artifactId>junit</artifactId>
   		<version>4.11</version>
   	</dependency>
   	<dependency>
   		<groupId>org.apache.hadoop</groupId>
   		<artifactId>hadoop-client</artifactId>
   		<version>3.1.3</version>
   	</dependency>
   	<dependency>
   		<groupId>org.slf4j</groupId>
   		<artifactId>slf4j-log4j12</artifactId>
   		<version>1.7.30</version>
   	</dependency>
   </dependencies>
   ```

3. 在 `resources` 目录下，添加 `log4j.properties` 日志文件

   ```properties
   log4j.rootLogger=INFO, stdout 
   log4j.appender.stdout=org.apache.log4j.ConsoleAppender 
   log4j.appender.stdout.layout=org.apache.log4j.PatternLayout 
   log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n 
   log4j.appender.logfile=org.apache.log4j.FileAppender 
   log4j.appender.logfile.File=target/spring.log 
   log4j.appender.logfile.layout=org.apache.log4j.PatternLayout 
   log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
   ```

### 5.3.2，配置参数优先级测试

* 优先级顺序为：代码配置 > `resources` 路径配置 > `hdfs-site.xml` 配置 > `hdfs-default.xml` 配置

```java
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
```

### 5.3.3，文件夹创建

```java
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
```

### 5.3.4，文件上传

```java
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
```

### 5.3.5，文件下载

```java
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
```

### 5.3.6，文件改名及移动

```java
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
```

### 5.3.7，文件删除

```java
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
```

### 5.3.8，文件详情信息查看

```java
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
```

### 5.3.9，文件/文件夹判断

```java
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
```

### 5.3.10，基于流的文件读写

```java
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
```

## 5.4，`HDFS` 读写流程

### 5.4.1，`HDFS` 写数据流程

#### 5.4.1.1，剖析文件写入

![1617781896091](E:\gitrepository\study\note\image\hadoop\1617781896091.png)

1. 客户端通过 `DistributedFileSystem` 模块向 `NameNode` 请求上传文件，`NameNode` 检验目标文件是否存在，父目录是否存在
2. `NameNode` 返回是否可以上传
3. 客户端请求第一个 `Block` 上传到哪几个 `DataNode` 服务节点上
4. `NameNode` 返回三个服务节点，分别为 `dn1`、`dn2`、`dn3`，表示采用这三个节点存储该块数据
5. 客户端通过 `FSOutPutStrean` 模块请求 `dn1` 上传数据，`dn1` 收到请求会继续调用 `dn2`，然后 `dn2` 调用 `dn3`，将这个通信管道建立完成
6. `dn1`、`dn2`、`dn3` 会逐级应答到客户端
7. 客户端会从 `dn1` 开始，上传第一个 `Block` 数据（先从磁盘读取数据放在第一个本地内存缓存），以 `Packet` 为单位，`dn1` 收到一个 `Packet` 就会传给 `dn2`，`dn2` 传给 `dn3`；<font color=red>`dn1` 每传一个 `Packet` 会放入一个应答队列等待应答，全部应答完成后，删除该 `Pakcet`，否则一定时间后重新发送</font>
8. 当一个 `Block` 传输完成后，客户端再次请求 `NameNode` 上传第二个 `Block` 到服务器，重复执行3-7步

#### 5.4.1.2，节点距离计算

> 在 `HDFS` 写数据过程中，`NameNode` 会选择距离待上传数据距离最近的 `DataNode` 接收数据；节点距离 = 两个节点到达最近的共同祖先的距离之和

![1617782829826](E:\gitrepository\study\note\image\hadoop\1617782829826.png)

#### 5.4.1.3，机架感知（副本存储节点选择）

1. 官方说明

   ```
   For the common case, when the replication factor is three, HDFS’s 
   placement policy is to put one replica on the local machine if the writer 
   is on a datanode, otherwise on a random datanode, another replica on a 
   node in a different (remote) rack, and the last on a different node in 
   the same remote rack. This policy cuts the inter-rack write traffic which 
   generally improves write performance. The chance of rack failure is far 
   less than that of node failure; this policy does not impact data 
   reliability and availability guarantees. However, it does reduce the 
   aggregate network bandwidth used when reading data since a block is 
   placed in only two unique racks rather than three. With this policy, the 
   replicas of a file do not evenly distribute across the racks. One third 
   of replicas are on one node, two thirds of replicas are on one rack, and 
   the other third are evenly distributed across the remaining racks. This 
   policy improves write performance without compromising data reliability 
   or read performance.
   ```

2. 副本节点选择

   * 第一个副本在 `Client` 所处的节点上，如果客户端在集群外，则随机选择一个
   * 第二个副本在另一个机架的随机一个节点
   * 第三个副本在第二个副本所在机架的随机另一个节点

   ![1617783689846](E:\gitrepository\study\note\image\hadoop\1617783689846.png)

### 5.4.2，`HDFS` 读数据流程

![1617784535151](E:\gitrepository\study\note\image\hadoop\1617784535151.png)

1. 客户端通过 `DistributedFileSystem` 向 `NameNode` 请求下载文件，`NameNode` 通过查询元数据，找到文件所在的 `DataNode` 地址
2. 挑选一台 `DataNode` （就近原则，负载过大时进行随机）服务，请求读取数据
3. `DataNode` 从磁盘中读取数据输入流，以 `Packet` 为单位进行校验，最终传输给客户端
4. 客户端以 `Packet` 为单位接收，先在本地缓存，然后写入目标文件

## 5.5，`NameNode` 和 `SecondaryNameNode`

### 5.5.1，问题引入及思考

> 思考：`NameNode` 的元数据存储在什么地方

* 如果 `NameNode` 的元数据只存储在磁盘中，虽然数据安全性和可靠性有保证，但是需要经常进行随机访问，并相应请求到用户，效率过低；
* 如果 `NameNode` 的元数据只存储在内存中，虽然数据处理效率较高，但是只要断电，必然存在数据丢失；
* 在内存存储的基础上，进行数据持久化处理，<font color=red>因此产生了在磁盘中备份元数据的 `FSImage` 文件</font>，在读的时候，会提升系统的整体性能；
* 此时又会引入新问题，当进行元数据操作时，如果同时更新 `FSImage` 文件，就会导致效率过低；如果不更新，又会造成数据不一致；
* 因此，<font color=red>继续引入 `Edits` 文件（只进行数据追加，效率很高），</font>每当元数据存在操作时，修改内存中的元数据并添加操作状态到 `Edits` 文件中。这样，即时 `NameNode` 断电，也可以通过 `FSImage` 和 `Edits` 的合并，合成元数据
* <font color=red>长时间添加数据到 `Edits` 文件中，必然导致文件过大，效率降低，而且一旦断电，数据恢复时间较长。因此，需要通过一定机制对 `FSImage` 和 `Edits` 文件进行合并，但是如果这个操作由 `NameNode` 来完成，又会影响 `NameNode` 效率。因此，引入一个新的节点 `SecondaryNameNode` 专门进行 `FSImage` 和 `Edits` 文件的合并。（具体合并机制下面再分析）</font>

### 5.5.2，`NameNode` 和 `SecondaryNameNode` 工作机制

* 黑色步骤为 `NameNode` 步骤，紫色步骤为 `SecondaryNameNode` 和 `NameNode` 交互步骤

![1618989065673](E:\gitrepository\study\note\image\hadoop\1618989065673.png)

#### 5.5.2.1，`NameNode` 启动阶段

1. 第一次启动 `NameNode` 格式化后，创建 `FSImage` 和 `Edits` 文件。如果不是第一次启动，直接加载编辑日志和镜像文件到内存。
2. 客户端对元数据进行增删改的请求
3. `NameNode` 记录操作日志，更新滚动日志
4. `NameNode` 对内存中的数据进行对应操作；<font color=red>此处一定注意是先操作日志，再修改内存；如果反过来，容易造成数据丢失</font>

#### 5.5.2.2，`SecondaryNameNode` 工作机制

1. `SecondaryNameNode` 询问`NameNode` 是否需要进行 `CheckPoint`，并直接带回 `NameNode` 的是否检查结果
2. 在接收到需要时，`SecondaryNameNode` 再次访问 `NameNode` 请求执行 `CheckPoint`
3. `NameNode` 创建新的 `Edits` 文件滚动正在写的操作日志
4. 将之前的 `Edits` 编辑日志和 `FSImage` 镜像文件拷贝到 `SecondaryNameNode` 进行数据合并
5. `SecondaryNameNode` 加载编辑日志和镜像文件到内存，进行合并
6. `SecondaryNameNode` 合并数据文件完成后，生成新的文件 `FSImage.chkpoint`
7. `SecondaryNameNode` 拷贝 `FSImage.chkpoint` 文件到 `NameNode`
8. `NameNode` 将 `FSImage.chkpoint` 文件重新命名为 `fsimage`；<font color=red>此处注意，会有多个`fsimage` 文件，在文件名称上通过事务号进行区分，每一个 `fsimage` 文件表示该事务及之前事务的所有元数据</font>

### 5.5.3，`CheckPoint` 时间设置及触发机制

> `CheckPoint` 的触发机制在 `hdfs-default.xml` 文件中定义

1. 通常情况下，`SecondaryNameNode` 每隔一小时执行一次 `CheckPoint`

   ```xml
   <property>
     <name>dfs.namenode.checkpoint.period</name>
     <value>3600s</value>
     <description>
       The number of seconds between two periodic checkpoints.
       Support multiple time unit suffix(case insensitive), as described
       in dfs.heartbeat.interval.
     </description>
   </property>
   ```

2. 再这一小时中，如果 `NameNode` 的 `Edits` 文件记录超过100W条，则会提前触发 `CheckPoint`

   ```xml
   <property>
     <name>dfs.namenode.checkpoint.txns</name>
     <value>1000000</value>
     <description>The Secondary NameNode or CheckpointNode will create a checkpoint
     of the namespace every 'dfs.namenode.checkpoint.txns' transactions, regardless
     of whether 'dfs.namenode.checkpoint.period' has expired.
     </description>
   </property>
   ```

3. 超过100W条数据，`SecondaryNameNode` 的感知方式是在一定时间内询问一次 `NameNode`

   ```xml
   <property>
     <name>dfs.namenode.checkpoint.check.period</name>
     <value>60s</value>
     <description>The SecondaryNameNode and CheckpointNode will poll the NameNode
     every 'dfs.namenode.checkpoint.check.period' seconds to query the number
     of uncheckpointed transactions. Support multiple time unit suffix(case insensitive),
     as described in dfs.heartbeat.interval.
     </description>
   </property>
   ```

### 5.5.4，`FSImage` 和 `Edits` 文件解析

> 先格式化集群并重启集群，在新的集群中进行文件查看
>
> 参考：[启动集群](#4.2.3.4.2，启动集群)

#### 5.5.4.1，基本介绍

```sh
[root@Hadoop102 current]# pwd
/opt/software/hadoop-3.1.3/data/dfs/name/current
[root@Hadoop102 current]# ll
总用量 1052
-rw-r--r--. 1 root root     672 4月  21 15:43 edits_0000000000000000001-0000000000000000009
-rw-r--r--. 1 root root 1048576 4月  21 15:49 edits_inprogress_0000000000000000010
-rw-r--r--. 1 root root     388 4月  21 15:40 fsimage_0000000000000000000
-rw-r--r--. 1 root root      62 4月  21 15:40 fsimage_0000000000000000000.md5
-rw-r--r--. 1 root root     800 4月  21 15:43 fsimage_0000000000000000009
-rw-r--r--. 1 root root      62 4月  21 15:43 fsimage_0000000000000000009.md5
-rw-r--r--. 1 root root       3 4月  21 15:43 seen_txid
-rw-r--r--. 1 root root     218 4月  21 15:40 VERSION
```

* 集群格式化完成后，在上图路径下，会生成不带 `Edits` 文件的文件列表，在集群启动成功后，`Edits` 文件生成

* `FSImage` ：`HDFS` 文件系统的一个永久性检查点，其中包括 `HDFS` 文件系统的所有目录和文件 `inode` 的序列化信息；<font color=red>`FSImage` 文件会有多个，文件名称后面的数字序列表示事务号，一个 `FSImage` 文件所包含的数据，是这个事务号之前的所有数据</font>

* `FSImage_XXXXX.md5`：存在对应的 `FSImage` 文件的 `MD5` 校验码

* `Edits`：存放 `HDFS` 所有文件操作日志的文件，文件系统客户端所执行的所有写操作会首先存放在该文件中；<font color=red>`Edits` 文件分为两种，`edits_0000000000000000001-0000000000000000009` 表示已经经过 `CheckPoint` 的操作文件，该文件表示的操作区间是从事务1到事务9；`edits_inprogress_0000000000000000010` 是正在进行写操作还没被 `CheckPoint` 的操作文件，在下一次 `CheckPoint` 时会对该文件进行合并</font>

* `VERSION`：表示集群年代

* `seen_txid`：表示一个数字，即最后一个 `edits` 文件的数字

  ```sh
  [root@Hadoop102 current]# cat seen_txid 
  10
  [root@Hadoop102 current]# cat VERSION 
  #Wed Apr 21 15:40:11 CST 2021
  namespaceID=112460551
  clusterID=CID-b80780bc-a497-41fb-a341-831e52b15640
  cTime=1618990811261
  storageType=NAME_NODE
  blockpoolID=BP-1519557071-192.168.10.102-1618990811261
  layoutVersion=-64
  ```

* 在 `hdfs` 中定义了对 `FSImage` 和 `Edits` 文件的基本访问方式，具体如下

  ```sh
  [root@Hadoop102 current]# hdfs | grep "apply"
  oev                  apply the offline edits viewer to an edits file
  oiv                  apply the offline fsimage viewer to an fsimage
  oiv_legacy           apply the offline fsimage viewer to a legacy fsimage
  [root@Hadoop102 current]# hdfs oiv -h
  Usage: bin/hdfs oiv [OPTIONS] -i INPUTFILE -o OUTPUTFILE
  [root@Hadoop102 current]# hdfs oev -h
  Usage: bin/hdfs oev [OPTIONS] -i INPUT_FILE -o OUTPUT_FILE
  ```

#### 5.5.4.2，`FSImage` 文件查看

1. 查看命令

   ```sh
   # -p 文件类型：表示通过什么样的文件形式查看，如XML
   # -i INPUTFILE：指定 FSImage 文件
   # -o OUTPUTFILE：转换后的 XML 文件的输出路径
   hdfs oiv -p 文件类型 -i INPUTFILE -o OUTPUTFILE
   ```

2. 文件转换并输出，从下图中大体可以看到文件名称、文件目录结构等信息；<font color=red>`FSImage` 文件中会存储文件的分块信息，但并不会存储文件具体在哪个块，又 `DataNode` 上线后具体上报文件块信息</font>

   ```sh
   [root@Hadoop102 current]# hdfs oiv -p XML -i ./fsimage_0000000000000000009 -o /opt/software/fsimage1.xml
   ```

   ![1618992376838](E:\gitrepository\study\note\image\hadoop\1618992376838.png)

#### 5.5.4.3，`Edits` 文件查看

1. 查看命令

   ```sh
   # -p 文件类型：表示通过什么样的文件形式查看，如XML
   # -i INPUTFILE：指定 Edits 文件
   # -o OUTPUTFILE：转换后的 XML 文件的输出路径 
   hdfs oev -p 文件类型 -i INPUT_FILE -o OUTPUT_FILE
   ```

2. 文件转换并输出，从截图中大致可以看出存在几个文件夹创建，但是在 `FSImage` 中并没有对这些文件夹的记录，说明该 `Edists` 文件还没有被合并

   ```sh
   [root@Hadoop102 current]# hdfs oev -p XML -i ./edits_inprogress_0000000000000000010 -o /opt/software/edits.xml
   ```

   ![1618992560011](E:\gitrepository\study\note\image\hadoop\1618992560011.png)

3. 再输出一个已经被合并过的 `Edits` 文件，可以看到相关操作文件在 `FSImages` 的截图中存在

   ```sh
   [root@Hadoop102 current]# hdfs oev -p XML -i ./edits_0000000000000000001-0000000000000000009 -o /opt/software/edits.xml
   ```

   ![1618992688825](E:\gitrepository\study\note\image\hadoop\1618992688825.png)

## 5.6，`DataNode` 

### 5.6.1，`DataNode` 工作机制

![1618999061462](E:\gitrepository\study\note\image\hadoop\1618999061462.png)

* 一个数据块在 `DataNode` 上以文件形式存储在磁盘上，包括两个文件：一个文件是数据本身；一个文件是元数据包括数据块的长度、块数据的校验和、以及块数据的时间戳

  ![1618999196679](E:\gitrepository\study\note\image\hadoop\1618999196679.png)

1. `DataNode` 启动后先向 `NameNode` 注册，通过后，周期性（6小时）的向 `NameNode` 上报所有的块信息

   * `DataNode` 向 `NameNode` 上报默认六小时

     ```xml
     <property>
       <name>dfs.blockreport.intervalMsec</name>
       <value>21600000</value>
       <description>Determines block reporting interval in milliseconds.</description>
     </property>
     ```

   * `DataNode` 扫描自身块信息，默认六小时

     ```xml
     <property>
       <name>dfs.datanode.directoryscan.interval</name>
       <value>21600s</value>
       <description>Interval in seconds for Datanode to scan data directories and
       reconcile the difference between blocks in memory and on the disk.
       Support multiple time unit suffix(case insensitive), as described
       in dfs.heartbeat.interval.
       </description>
     </property>
     ```

2. `NameNode` 和 `DataNode` 存在3s周期的心跳检测，心跳返回结果带有 `NameNode` 给该 `DataNode` 的命令如复制块数据到另一台机器，或删除某个数据块。如果超过10分钟没有收到某个 `DataNode` 的心跳，则认为该节点不可用
3. 集群运行过程中可以安全的加入和退出一些机器

### 5.6.2，`DataNode` 数据完整性校验

> `DataNode` 上的数据可能存在损坏，损坏后没有发现对数据安全性是比较危险的，`DataNode` 通过对元数据进行 `crc` 计算，来确保数据安全性

![1618999623987](E:\gitrepository\study\note\image\hadoop\1618999623987.png)

* 当 `Client` 读取某台 `DataNode` 上的 `Block` 的数据时，会计算数据的 `CheckSum`
* 如果 `CheckSum` 值与当初存储时不一致，则说明该文件已经损坏
* `Client` 会读取其他 `DataNode` 上的 `Block`
* 常见的校验算法包括 `crc(32)`，`MD5(128)`，`shal(160)`
* `DataNode` 在文件创建后会周期性的校验 `CheckSum`

### 5.6.3，掉线时限参数设置

![1618999789313](E:\gitrepository\study\note\image\hadoop\1618999789313.png)

* 默认配置信息

  ```xml
  <!-- 心跳检测时间 -->
  <property>
    <name>dfs.heartbeat.interval</name>
    <value>3s</value>
    <description>
      Determines datanode heartbeat interval in seconds.
      Can use the following suffix (case insensitive):
      ms(millis), s(sec), m(min), h(hour), d(day)
      to specify the time (such as 2s, 2m, 1h, etc.).
      Or provide complete number in seconds (such as 30 for 30 seconds).
    </description>
  </property>
  
  <!-- DataNode断连超时时间设置 -->
  <property>
    <name>dfs.namenode.heartbeat.recheck-interval</name>
    <value>300000</value>
    <description>
      This time decides the interval to check for expired datanodes.
      With this value and dfs.heartbeat.interval, the interval of
      deciding the datanode is stale or not is also calculated.
      The unit of this configuration is millisecond.
    </description>
  </property>
  ```

  

# 6，MapReduce

## 6.1，`MapReduce` 概述

### 6.1.1，`MapReduce` 定义

* `MapReduce` 是一个<font color=red>分布式运算程序</font>的编程框架，是用户开发“基于`Hadoop`数据分析应用”的核心框架
* `MapReduce` 核心是将 <font color=red>用户编写的业务逻辑代码和自带默认组件</font>整合成一个完成的<font color=red>分布式运算程序</font>，并发运行在一个 `Hadoop` 集群上

### 6.1.2，`MapReduce` 优缺点

1. 优点
   * **MapReduce 易于编程**：<font color=red>它简单的实现一些接口，就可以完成一个分布式程序</font>，这个分布式程序可以分布到大量廉价的 PC 机器上运行。也就是说你写一个分布式程序，跟写一个简单的串行程序是一模一样的。就是因为这个特点使得 `MapReduce` 编程变得非常流行。
   * **良好的扩展性**：当你的计算资源不能得到满足的时候，你可以通过<font color=red>简单的增加机器</font>来扩展它的计算能力。
   * **高容错性**：`MapReduce` 设计的初衷就是使程序能够部署在廉价的 PC 机器上，这就要求它具有很高的容错性。比如其中一台机器挂了，它可以把上面的计算任务转移到另外一个节点上运行，不至于这个任务运行失败，而且这个过程不需要人工参与，而完全是由 `Hadoop` 内部完成的。
   * **适合 PB 级以上海量数据的离线处理**：可以实现上千台服务器集群并发工作，提供数据处理能力。
2. 缺点
   * **不擅长实时计算**：`MapReduce` 无法像 `MySQL` 一样，在毫秒或者秒级内返回结果。
   * **不擅长流式计算**：<font color=red>流式计算的输入数据是动态的，而 `MapReduce` 的输入数据集是静态的，不能动态变化</font>。这是因为 `MapReduce` 自身的设计特点决定了数据源必须是静态的。
   * **不擅长 DAG（有向无环图）计算**：`DAG` 是指多个应用程序存在依赖关系，后一个应用程序的输入为前一个的输出。在这种情况下， `MapReduce` 并不是不能做，而是使用后，<font color=red>每个 `MapReduce` 作业的输出结果都会写入到磁盘， 会造成大量的磁盘 IO，导致性能非常的低下。</font> 

### 6.1.3，`MapReduce` 核心思想

![1622086857246](E:\gitrepository\study\note\image\hadoop\1622086857246.png)

* 分布式的运算程序需要分成至少两个步骤：`Map` 阶段和 `Reduce` 阶段
* `Map` 阶段：`MapTask` 并发实例，完全并行运行，互不相干
* `Reduce` 阶段：`ReduceTask` 并发实例互不相干，但是运算数据依赖于上一个 `MapTask` 阶段生成的数据
* `MapReduce` 模型包含一个 `Map` 阶段和一个 `Reduce` 阶段，如果业务逻辑非常复杂，需要多个 `MapReduce` 程序，则将前一个 `MapReduce` 的计算结果作为后一个 `MapReduce` 的源数据，串行执行。<font color=red>`MapReduce` 可以进行这种操作，但是不擅长，`MapReduce` 数据交互是基于磁盘的，耗时较大</font>

### 6.1.4，`MapReduce` 进程

> 一个完整的 `MapReduce` 程序在分布式运行时有三类实例进程：

* `MrAppMaster`：负责整个程序的过程调度以及状态协调

* `MapTask`：负责 `Map` 阶段整个数据处理流程
* `ReduceTask`：负责 `Reduce` 阶段整个数据处理流程

## 6.2，`QuickStart` - `WordCount`

### 6.2.1，官方 `WordCount` 源码

* 源码路径：`/opt/software/hadoop-3.1.3/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.1.3.jar`

* 通过 `jd-gui` 反编译软件编译jar包，取出 `org.apache.hadoop.examples.WordCount` 类文件：

  * 从文件中可以看到，`WrodCount` 类由三个类构成：
  *  `WordCount` 主类：用于进行全流程控制
  * `TokenizerMapper` 类：继承自 `Mapper` 类，用于进行数据计算
  * `IntSumReducer` 类：继承自 `Reducer` 类，用于进行数据汇总

  ```java
  package org.apache.hadoop.examples;
  
  import java.io.IOException;
  import java.util.Iterator;
  import java.util.StringTokenizer;
  import org.apache.hadoop.conf.Configuration;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.IntWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Job;
  import org.apache.hadoop.mapreduce.Mapper;
  import org.apache.hadoop.mapreduce.Reducer;
  import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
  import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
  import org.apache.hadoop.util.GenericOptionsParser;
  
  public class WordCount {
  
      public static void main(String[] args) throws Exception {
          Configuration conf = new Configuration();
          String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
          if(otherArgs.length < 2) {
              System.err.println("Usage: wordcount <in> [<in>...] <out>");
              System.exit(2);
          }
          Job job = Job.getInstance(conf, "word count");
          job.setJarByClass(WordCount.class);
          job.setMapperClass(WordCount.TokenizerMapper.class);
          job.setCombinerClass(WordCount.IntSumReducer.class);
          job.setReducerClass(WordCount.IntSumReducer.class);
          job.setOutputKeyClass(Text.class);
          job.setOutputValueClass(IntWritable.class);
          for(int i = 0; i < otherArgs.length - 1; ++i) {
              FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
          }
          FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
          System.exit(job.waitForCompletion(true)?0:1);
      }
  
  	// Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
  	// Reducer<Text, IntWritable, Text, IntWritable>
  	// 前两个泛型表示入参的 <K, V>
  	// 后两个泛型表示出参的 <K, V>
  	// Reduce的入参是Map的出参
      public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
          private IntWritable result = new IntWritable();
  
          public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
              int sum = 0;
              IntWritable val;
              for(Iterator var5 = values.iterator(); var5.hasNext(); sum += val.get()) {
                  val = (IntWritable)var5.next();
              }
              this.result.set(sum);
              context.write(key, this.result);
          }
      }
  
  	// Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
  	// Mapper<Object, Text, Text, IntWritable>
  	// 前两个泛型表示入参的 <K, V>
  	// 后两个泛型表示出参的 <K, V>
      public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
          private static final IntWritable one = new IntWritable(1);
          private Text word = new Text();
  
          public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
              StringTokenizer itr = new StringTokenizer(value.toString());
              while(itr.hasMoreTokens()) {
                  this.word.set(itr.nextToken());
                  context.write(this.word, one);
              }
          }
      }
  }
  ```

### 6.2.2，`Hadoop` 常用数据序列化类型

![1622101298174](E:\gitrepository\study\note\image\hadoop\1622101298174.png)

### 6.2.3，`MapReduce` 编程规范

1. `Mapper` 阶段
   * 用户自定义的 `Mapper` 需要继承自父类 `org.apache.hadoop.mapreduce.Mapper`
   * `Mapper` 输入的数据是 <K, V> 对的形式（K V类型可自定义）
   * `Mapper` 中的业务逻辑写在 `map()` 中
   * `Mapper` 输出的数据是 <K, V> 对的形式（K V类型可自定义）
   * <font color=red>`map()` 方法（`MapTask`进程）对每一个 <K, V> 调用一次（WordCount的一个 <K, V> 表示一行数据，并对每一行每一个读到的单词记录为1）</font>

2. `Reduce` 阶段
   * 定义自定义的 `Reduce` 需要继承自父类 `org.apache.hadoop.mapreduce.Reducer`
   * `Reduce` 的输入数据类型对应 `Mapper` 的输出数据类型，也是 <K, V>
   * `Reduce` 的业务逻辑写在 `reduce()` 方法中
   * <font color=red>`ReduceTask` 进程对每一组相同 K 的 <K, V> 组调用一次 `reduce()` 方法</font>

3. `Driver` 方法
   * 相当于 `Yarn` 集群的客户端，用于提交整个程序到 `Yarn` 集群，提交的是封装了 `MapRedcue` 程序相关运行参数的 `org.apache.hadoop.mapreduce.Job` 对象

### 6.2.4，`WordCount` 案例实操

#### 6.2.4.1，需求分析

![1622109635884](E:\gitrepository\study\note\image\hadoop\1622109635884.png)

1. 输入数据：如上图，输入一个文本文件，文本文件由若干个单词组成
2. 输出数据：如上图，最终数据以文件形式输出，输出每一个单词在文件中出现的次数，并最终按字符顺序排序输出
3. 代码编写：参考官方的 `WordCount` 案例，应该包含 `Mappeer` 类，`Reduce` 类，`Driver` 类三个类，其中：
   * `Mapper` 类负责计算，拆分出文件中每一个单词（此处不汇总），对每一个单词进行 <K, V> 记录，K表示该单词，V表示出现了1次，因为不汇总，默认记录为1
   * `Reduce` 类负责计算结果汇总，在 `Mapper` 中只是对每一个单词进行拆分，做了初始记录；在 `Reduce` 中按 K 进行汇总，以预订形式进行结果汇总
   * `Driver` 类进行整体调度，包括上图的八个步骤，并最终输入数据结果到指定路径

#### 6.2.4.2，代码实现

1. 创建 Maven 工程

2. 在 `pom.xml` 文件中引入依赖

   ```xml
   <dependencies>
   	<dependency>
   		<groupId>junit</groupId>
   		<artifactId>junit</artifactId>
   		<version>4.11</version>
   	</dependency>
   	<dependency>
   		<groupId>org.apache.hadoop</groupId>
   		<artifactId>hadoop-client</artifactId>
   		<version>3.1.3</version>
   	</dependency>
   	<dependency>
   		<groupId>org.slf4j</groupId>
   		<artifactId>slf4j-log4j12</artifactId>
   		<version>1.7.30</version>
   	</dependency>
   </dependencies>
   ```

3. 编写 `Mapper` 文件

   ```java
   package com.hadoop.mapreduce.wordcount;
   
   import org.apache.commons.lang3.StringUtils;
   import org.apache.commons.lang3.Validate;
   import org.apache.hadoop.io.IntWritable;
   import org.apache.hadoop.io.LongWritable;
   import org.apache.hadoop.io.Text;
   import org.apache.hadoop.mapreduce.Mapper;
   
   import java.io.IOException;
   
   /**
    * Mapper 计算类
    *
    * 泛型参数解析:
    * KEYIN, VALUEIN, KEYOUT, VALUEOUT
    * LongWritable, Text, Text, IntWritable
    * * 前两个表示入参的 <K, V>类型, 后两个表示出参的<K, V> 类型
    * * 按照需求, 以文本文档的形式输入文件进行计算, 在Mapper中最终输出解析的单词和次数(次数默认为1)
    * * LongWritable: 入参K, 表示文本文件中该行文本的偏移索引
    * * Text: 入参V, 以字符串的形式读取每一行数据
    * * Text: 出参K, 计算完成后, 将单词作为K输出
    * * IntWritable: 出参V, 在 Mapper 阶段, 不做汇总, 每一个单词都会输出, 无论重复, 默认为1
    * 最终, Mapper 出参的<K, V>会作为Reduce入参的<K, V>继续进行汇总计算
    *
    * @author PJ_ZHANG
    * @create 2021-05-27 18:08
    **/
   public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
   
       private Text text = new Text();
   
       private IntWritable one = new IntWritable(1);
   
       /**
        * Mapper阶段, map(..)方法调用的基本单位为行
        * 即文本文件的每一行会调用一次map文件
        * 该行中可能存在多个单词, 需要通过空格拆分处理(简单操作)
        *
        * @param key 当前行在文件中的位置偏移索引
        * @param value 当前行的内容
        * @param context 上下文数据
        * @throws IOException
        * @throws InterruptedException
        */
       @Override
       protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
           if (StringUtils.isEmpty(value.toString())) {
               return;
           }
           String valueStr = value.toString();
           String[] valueArr = valueStr.split(" ");
           for (String str : valueArr) {
               text.set(str);
               // 写到context中, 作为出参
               // 因为每一个单词都会统计, 所以对于每一个单词, 都默认出现了一次
               // 会在后续Mapper中进行汇总
               context.write(text, one);
           }
       }
   }
   ```

4. 编写 `Reduce` 文件

   ```java
   package com.hadoop.mapreduce.wordcount;
   
   import org.apache.hadoop.io.IntWritable;
   import org.apache.hadoop.io.Text;
   import org.apache.hadoop.mapreduce.Reducer;
   
   import java.io.IOException;
   
   /**
    * Reduce汇总节点
    * 参数解析:
    * KEYIN,VALUEIN,KEYOUT,VALUEOUT
    * Text, IntWritable, Text, IntWritable
    * * 首先: Mapper的出参对应Reduce的入参, 则前两个参数确定
    * * 按照需求分析, 最终是以<单词, 出现次数>的形式输出,
    * * 所以输出key为Text, 输出value为IntWritable
    *
    * @author PJ_ZHANG
    * @create 2021-05-27 18:18
    **/
   public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
   
       private IntWritable intWritable = new IntWritable();
   
       /**
        * Reduce调用该方法时, 是以每一组调用一次
        * Mapper中对每一个单词进行记录, 如: Hello出现了三次, 则在Mapper会写三个<Hello, 1>
        * 在Reduce的前面步骤处理中, 会先对重复key进行汇总, 处理为<K, List<V>>的形式
        * 在调用一次reduce(..)方法时, 是对每一组汇总后的key的统一处理
        *
        * @param key Mapper输出的每一组key
        * @param values 该key对应的数据集合
        * @param context 上下文
        * @throws IOException
        * @throws InterruptedException
        */
       @Override
       protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
           int sum = 0;
           // 通过sum进行总数记录
           // 对记录的数据进行叠加
           for (IntWritable intWritable : values) {
               sum += intWritable.get();
           }
           intWritable.set(sum);
           // 最终写出单词出现的次数
           context.write(key, intWritable);
       }
   }
   ```

5. 编写 `Driver` 文件

   ```java
   package com.hadoop.mapreduce.wordcount;
   
   import org.apache.hadoop.conf.Configuration;
   import org.apache.hadoop.fs.Path;
   import org.apache.hadoop.io.IntWritable;
   import org.apache.hadoop.io.Text;
   import org.apache.hadoop.mapreduce.Job;
   import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
   import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
   
   /**
    * Driver类中进行统一调度
    * 分8个步骤
    * @author PJ_ZHANG
    * @create 2021-05-27 18:24
    **/
   public class WordCountDriver {
   
       public static void main(String[] args) throws Exception {
           // 1. 获取配置信息, 获取Job示例
           Configuration configuration = new Configuration();
           Job job = Job.getInstance(configuration);
           // 2. 指定本程序jar包所在的路径
           job.setJarByClass(WordCountDriver.class);
           // 3. 关联Mapper/Reduce业务类
           job.setMapperClass(WordCountMapper.class);
           job.setReducerClass(WordCountReduce.class);
           // 4. 指定Mapper输出数据的KV类型
           job.setMapOutputKeyClass(Text.class);
           job.setMapOutputValueClass(IntWritable.class);
           // 5. 指定Reduce输出数据的KV类型
           job.setOutputKeyClass(Text.class);
           job.setOutputValueClass(IntWritable.class);
           // 6. 指定Job输入原始数据的文件路径
           // FileInputFormat.setInputPaths(job, new Path("E:\\123456.txt"));
           FileInputFormat.setInputPaths(job, new Path(args[0]));
           // 7. 指定Job输出结果数据的文件路径
           // FileOutputFormat.setOutputPath(job, new Path("E:\\wcout"));
           FileOutputFormat.setOutputPath(job, new Path(args[1]));
           // 8. 提交执行
           job.waitForCompletion(true);
       }
   
   }
   ```

6. 输入文件

   ```java
   zhangpanjing   zhangpanjing
   test  test
   qwe
   ertyuio
   rty
   sdrtfgyhj
   ret ret
   zhangpanjing
   poiuytrew
   ```

7. 输出文件

   ```java
   	3
   ertyuio	1
   poiuytrew	1
   qwe	1
   ret	2
   rty	1
   sdrtfgyhj	1
   test	2
   zhangpanjing	3
   ```

#### 6.2.4.3，集群部署

1. 修改 `pom.xml` 文件

   ```xml
   <build>
   	<plugins>
   		<plugin>
   			<artifactId>maven-compiler-plugin</artifactId>
   			<version>3.6.1</version>
   			<configuration>
   				<source>1.8</source>
   				<target>1.8</target>
   			</configuration>
   		</plugin>
   		<plugin>
   			<artifactId>maven-assembly-plugin</artifactId>
   			<configuration>
   				<descriptorRefs>
   					<descriptorRef>jar-with-dependencies</descriptorRef>
   				</descriptorRefs>
   			</configuration>
   			<executions>
   				<execution>
   					<id>make-assembly</id>
   					<phase>package</phase>
   					<goals>
   						<goal>single</goal>
   					</goals>
   				</execution>
   			</executions>
   		</plugin>
   	</plugins>
   </build>
   ```

2. 修改文件输入输出方式，改为参数形式

   ```java
   // 6. 指定Job输入原始数据的文件路径
   // FileInputFormat.setInputPaths(job, new Path("E:\\123456.txt"));
   FileInputFormat.setInputPaths(job, new Path(args[0]));
   // 7. 指定Job输出结果数据的文件路径
   // FileOutputFormat.setOutputPath(job, new Path("E:\\wcout"));
   FileOutputFormat.setOutputPath(job, new Path(args[1]));
   ```

3. 文件打包并上传服务器

   * 文件打包

   ![1622112569702](E:\gitrepository\study\note\image\hadoop\1622112569702.png)

   * 上传服务器

     ![1622112598342](E:\gitrepository\study\note\image\hadoop\1622112598342.png)

4. 集群方式运行并查看

   ```shell
   # ./mybigdata-1.0-SNAPSHOT.jar：jar包路径
   # com.hadoop.mapreduce.wordcount.WordCountDriver：Driver类路径
   # /123456.txt：输入文件在HDFS的路径
   # /output：输出文件在HDFS路径，该路径必须不存在
   [root@Hadoop102 hadoop-3.1.3]# hadoop jar ./mybigdata-1.0-SNAPSHOT.jar com.hadoop.mapreduce.wordcount.WordCountDriver /123456.txt /output
   ```

   ![1622112687922](E:\gitrepository\study\note\image\hadoop\1622112687922.png)

   ![1622112709358](E:\gitrepository\study\note\image\hadoop\1622112709358.png)

## 6.3，`Hadoop` 序列化

### 6.3.1，序列化概述

1. 什么是序列化
   * 序列化就是把内存中的对象，转换成字节序列（或其他数据传输协议）以便于存储到磁盘（持久化）和网络传输。
   * 反序列化就是将收到字节序列（或其他数据传输协议）或者是磁盘的持久化数据，转换成内存中的对象。
2. 为什么要序列化
   * 一般来说，“活的”对象只生存在内存里，关机断电就没有了。而且“活的”对象只能由本地的进程使用，不能被发送到网络上的另外一台计算机。 然而序列化可以存储“活的”对象，可以将“活的”对象发送到远程计算机。
3. 为什么不用Java序列化
   * Java 的序列化是一个重量级序列化框架（Serializable），一个对象被序列化后，会附带很多额外的信息（各种校验信息，Header，继承体系等），不便于在网络中高效传输。所以，Hadoop 自己开发了一套序列化机制（Writable）。
4. `Hadoop` 序列化特点
   * **紧凑**：高效使用存储空间。
   * **快速**：读写数据的额外开销小。
   * **互操作**：支持多语言的交互

### 6.3.2，自定义序列化对象

> `Hadoop` 内部对序列化方式进行重新定义，在 `Hadoop` 框架内部进行实例对象传递，需要实现序列化接口（`Writable`）并重写相关方法，具体可以分为以下7步：

1. 相关实例类必须实现 `org.apache.hadoop.io.Writable` 接口

2. 反序列化时，需要反射调用空参构造，所以实体类中必须声明空参构造方法

3. 重写序列化方法

   ```java
   @Override
   public void write(DataOutput out) throws IOException {
   	out.writeLong(uploadBytes);
   	out.writeLong(downloadBytes);
   	out.writeLong(sumBytes);
   }
   ```

4. 重写反序列化方法

   ```java
   @Override
   public void readFields(DataInput in) throws IOException {
   	uploadBytes = in.readLong();
   	downloadBytes = in.readLong();
   	sumBytes = in.readLong();
   }
   ```

5. 注意<font color=red>序列化顺序和反序列化顺序必须完全一致</font>，不然会导致数据错乱

6. 如果需要将对象写入到文件中，需要重写 `toString()` 方法

7. 如果需要将自定义对象以 `Key` 的形式进行处理，因为在 `MapReduce` 的处理中默认会排序处理，所以需要额外实现 `Comparable` 接口，并重写数据比较方法：

   ```java
   @Override
   public int compareTo(FlowBean o) {
       // 倒序排列，从大到小
       return this.sumFlow > o.getSumFlow() ? -1 : 1;
   }
   ```

### 6.3.3，序列化实现手机流量统计

#### 6.3.3.1，需求描述

* 统计每一个手机耗费的上行流量、下行流量、总流量

* 输入数据为文本文件

* 输入手机格式如下：

  ```java
  ID  手机号       IP地址           域名            上行流量  下行流量  网络状态
  1	18291166067	192.168.10.0	www.baidu.com	1123	112	200
  ```

* 输出数据格式如下

  ```java
  手机号码 上行流量 下行流量 总流量
  13560436666 1116 954 2070
  ```

#### 6.3.3.2，需求分析

![1622191349239](E:\gitrepository\study\note\image\hadoop\1622191349239.png)

#### 6.3.3.3，代码实现

1. `Domain` 类

   ```java
   package com.hadoop.mapreduce.serializable;
   
   import org.apache.hadoop.io.Writable;
   
   import java.io.DataInput;
   import java.io.DataOutput;
   import java.io.IOException;
   
   /**
    * 自定义Hadoop对象, 需要满足Hadoop序列化邀请, 实现 Writable接口
    *
    * @author PJ_ZHANG
    * @create 2021-05-28 14:40
    **/
   public class SelfDomain implements Writable {
   
       /**
        * 上行流量
        */
       private long uploadBytes;
   
       /**
        * 下行流量
        */
       private long downloadBytes;
   
       /**
        * 汇总流量
        */
       private long sumBytes;
   
       public long getUploadBytes() {
           return uploadBytes;
       }
   
       public void setUploadBytes(long uploadBytes) {
           this.uploadBytes = uploadBytes;
       }
   
       public long getDownloadBytes() {
           return downloadBytes;
       }
   
       public void setDownloadBytes(long downloadBytes) {
           this.downloadBytes = downloadBytes;
       }
   
       public long getSumBytes() {
           return sumBytes;
       }
   
       public void setSumBytes(long sumBytes) {
           this.sumBytes = sumBytes;
       }
   
       /**
        * 序列化顺序无所谓, 可以进行自定义
        *
        * @param out
        * @throws IOException
        */
       @Override
       public void write(DataOutput out) throws IOException {
           out.writeLong(uploadBytes);
           out.writeLong(downloadBytes);
           out.writeLong(sumBytes);
       }
   
       /**
        * 反序列化顺序必须严格与序列化顺序一致, 不然取数据可能会有问题
        * @param in
        * @throws IOException
        */
       @Override
       public void readFields(DataInput in) throws IOException {
           uploadBytes = in.readLong();
           downloadBytes = in.readLong();
           sumBytes = in.readLong();
       }
   
       @Override
       public String toString() {
           return uploadBytes + "\t" + downloadBytes + "\t" + sumBytes;
       }
   }
   ```

2. `Mapper` 类

   ```java
   package com.hadoop.mapreduce.serializable;
   
   import org.apache.hadoop.io.LongWritable;
   import org.apache.hadoop.io.Text;
   import org.apache.hadoop.mapreduce.Mapper;
   
   import java.io.IOException;
   
   /**
    * 自定义Mapper计算
    * LongWritable, Text: 以行的形式读取数据
    * Text, SelfDomain: 自定义value输出数据
    * 基本数据格式:
    * ID	手机号	IP地址	IP域名	上行流量	下行流量	网络状态
    * 1	18291166067	192.168.10.0	www.baidu.com	1123	112	200
    * 需求分析:
    * 输入: 以上文本数据, 整体为一个文本列表
    * 输出: 每一个手机号对应的上行流量,下行流量,总流量汇总
    * @author PJ_ZHANG
    * @create 2021-05-28 14:44
    **/
   public class SelfMapper extends Mapper<LongWritable, Text, Text, SelfDomain> {
   
       private SelfDomain domain = new SelfDomain();
   
       private Text text = new Text();
   
       /**
        * 以行的形式进行数据读取
        * 1	18291166067	192.168.10.0	www.baidu.com	1123	112	200
        * @param key 偏移量
        * @param value 当前行数据
        * @param context 上下文数据
        * @throws IOException
        * @throws InterruptedException
        */
       @Override
       protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
           String str = value.toString();
           String[] strArr = str.split("\t");
           String phone = strArr[1];
           long uploadBytes = Long.parseLong(strArr[4]);
           long downloadBytes = Long.parseLong(strArr[5]);
           long sumBytes = uploadBytes + downloadBytes;
           domain.setUploadBytes(uploadBytes);
           domain.setDownloadBytes(downloadBytes);
           domain.setSumBytes(sumBytes);
           text.set(phone);
           context.write(text, domain);
       }
   }
   ```

3. `Reduce` 类

   ```java
   package com.hadoop.mapreduce.serializable;
   
   import org.apache.hadoop.io.Text;
   import org.apache.hadoop.mapreduce.Reducer;
   
   import java.io.IOException;
   
   /**
    * 最终进行数据汇总
    * @author PJ_ZHANG
    * @create 2021-05-28 15:37
    **/
   public class SelfReduce extends Reducer<Text, SelfDomain, Text, SelfDomain> {
   
       private SelfDomain domain = new SelfDomain();
   
       @Override
       protected void reduce(Text key, Iterable<SelfDomain> values, Context context) throws IOException, InterruptedException {
           long uploadBytes = 0L;
           long downloadBytes = 0L;
           long sumBytes = 0L;
           for (SelfDomain currDomain : values) {
               uploadBytes += currDomain.getUploadBytes();
               downloadBytes += currDomain.getDownloadBytes();
               sumBytes += currDomain.getSumBytes();
           }
           domain.setUploadBytes(uploadBytes);
           domain.setDownloadBytes(downloadBytes);
           domain.setSumBytes(sumBytes);
           context.write(key, domain);
       }
   }
   ```

4. `Driver` 类

   ```java
   package com.hadoop.mapreduce.serializable;
   
   import org.apache.hadoop.conf.Configuration;
   import org.apache.hadoop.fs.Path;
   import org.apache.hadoop.io.Text;
   import org.apache.hadoop.mapreduce.Job;
   import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
   import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
   
   /**
    * 调度类
    *
    * @author PJ_ZHANG
    * @create 2021-05-28 15:41
    **/
   public class SelfDriver {
   
       public static void main(String[] args) throws Exception {
           // 获取配置信息, 构建Job示例
           Configuration configuration = new Configuration();
           Job job = Job.getInstance(configuration);
           // 指定本程序的jar包路径
           job.setJarByClass(SelfDriver.class);
           // 关联 Mapper/Reduce 业务类
           job.setMapperClass(SelfMapper.class);
           job.setReducerClass(SelfReduce.class);
           // 指定Mapper输出的KV类型
           job.setMapOutputKeyClass(Text.class);
           job.setMapOutputValueClass(SelfDomain.class);
           // 指定Reduce输出的KV类型
           job.setOutputKeyClass(Text.class);
           job.setOutputValueClass(SelfDomain.class);
           // 指定job输入路径
           FileInputFormat.setInputPaths(job, new Path("E:\\123456.txt"));
           // 指定job输出路径
           FileOutputFormat.setOutputPath(job, new Path("E:\\selfout"));
           // 工作
           job.waitForCompletion(true);
       }
   
   }
   ```

## 6.4，`MapReduce` 框架原理

### 6.4.1，`InputFormat` 数据输入

#### 6.4.1.1，切片与 `MapTask` 并行度决定机制

> `MapTask` 的并行度决定 `Map` 阶段的任务处理和并发度，进而影响整个 `Map` 的处理速度
>
> 思考：1G的数据通过8个 `MapTask` 处理会提高性能，那么1KB或者1B的数据呢

* 数据块：`Block` 是 `HDFS` 物理上的数据分块概念，将数据分为一块一块进行存储；<font color=red>数据块是 `HDFS` 的基础存储单位</font>
* 数据切片：数据切片只是在逻辑上对数据输入进行分片，并不会在磁盘上进行分片存储；<font color=red>数据切片是 `MapReduce` 程序计算输入数据的单位，一个数据分片对应一个 `MapTask`</font>
* 默认情况下，数据切片大小等于数据块大小，即一个数据块由一个 `MapTask` 任务进行执行计算

![1628069092826](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628069092826.png)

#### 6.4.1.2，`Job` 提交流程源码详解

![1628133495123](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628133495123.png)

* `com.hadoop.mapreduce.serializable.SelfDriver`

```java
// 提交工作，并等待完成
job.waitForCompletion(true);
```

* `org.apache.hadoop.mapreduce.Job#waitForCompletion`

```java
public boolean waitForCompletion(boolean verbose
                                ) throws IOException, InterruptedException,
ClassNotFoundException {
    // 如果状态是DEFINE, 直接进行任务提交执行
    if (state == JobState.DEFINE) {
        submit();
    }
    if (verbose) {
        // 监控任务, 清除临时文件等
        monitorAndPrintJob();
    } else {
        ......
    }
    return isSuccessful();
}
```

* `org.apache.hadoop.mapreduce.Job#submit`

```java
public void submit() 
    throws IOException, InterruptedException, ClassNotFoundException {
    // 状态确认
    ensureState(JobState.DEFINE);
    // 新旧API转换
    setUseNewAPI();
    // 客户端连接
    // 集群模式: 连接Hadoop集群
    // 本地模式: 取本地支持
    connect();
    final JobSubmitter submitter = 
        getJobSubmitter(cluster.getFileSystem(), cluster.getClient());
    status = ugi.doAs(new PrivilegedExceptionAction<JobStatus>() {
        public JobStatus run() throws IOException, InterruptedException, 
        ClassNotFoundException {
            // 提交任务
            return submitter.submitJobInternal(Job.this, cluster);
        }
    });
    state = JobState.RUNNING;
    LOG.info("The url to track the job: " + getTrackingURL());
}
```

* `org.apache.hadoop.mapreduce.JobSubmitter#submitJobInternal`

```java
JobStatus submitJobInternal(Job job, Cluster cluster) 
    throws ClassNotFoundException, InterruptedException, IOException {

    // 校验输出的工作空间是否存在
    // 根据输出设置的OutputFormatter进行确定
    // 常见的文件存在错误在内部 org.apache.hadoop.mapreduce.lib.output.FileOutputFormat#checkOutputSpecs 方法中提示
    checkSpecs(job);
    ......
	// 创建给集群提交数据的Stag路径
    // 本地模式在本地文件系统中进行创建
    // 本地路径创建代码: org.apache.hadoop.mapred.LocalJobRunner#getStagingAreaDir
    // 默认位置:/tmp/hadoop/mapred/staging
    Path jobStagingArea = JobSubmissionFiles.getStagingDir(cluster, conf);
    ......
    // 获取执行的JobId
    JobID jobId = submitClient.getNewJobID();
    ......
    // 集群模式下拷贝jar包到集群
    // 具体内部路劲: org.apache.hadoop.mapreduce.JobResourceUploader#uploadResourcesInternal
    copyAndConfigureFiles(job, submitJobDir);
    ......
    // 切片, 并根据切片进行后续处理
    // 切片完成后, 会在Stag路径下生成切片相关的4个文件
	int maps = writeSplits(job, submitJobDir);
    conf.setInt(MRJobConfig.NUM_MAPS, maps);
    ......
    // 向Stag写入XML配置信息
    // 最终由 org.apache.hadoop.conf.Configuration#writeXml(java.lang.String, java.io.Writer) 写出
    // 写完成后, 会像Stag写出job.xml文件, 作为后续执行的配置信息
    writeConf(conf, submitJobFile);
    ......
    // 提交任务并返回任务状态
    // 提交任务: 内部会启动线程进行处理
    status = submitClient.submitJob(
        jobId, submitJobDir.toString(), job.getCredentials());
    ......
}
```

* `org.apache.hadoop.mapred.LocalJobRunner#submitJob`

```java
public org.apache.hadoop.mapreduce.JobStatus submitJob(
    org.apache.hadoop.mapreduce.JobID jobid, String jobSubmitDir,
    Credentials credentials) throws IOException {
    // 初始化Job, 初始化内部会执行线程的start()方法,开启线程进行后续处理
    // 线程方法：org.apache.hadoop.mapred.LocalJobRunner.Job#run
    Job job = new Job(JobID.downgrade(jobid), jobSubmitDir);
    job.job.setCredentials(credentials);
    // 初始化Job时, status状态即为Running
    return job.status;
}
```

#### 6.4.1.3，`FileInputFormat` 切片源码详解

![1628150618441](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628150618441.png)

![1628150628827](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628150628827.png)

* 切片入口：`org.apache.hadoop.mapreduce.JobSubmitter#writeSplits`

  ```java
  // jobSubmitDir：Stag文件路径
  private <T extends InputSplit>
      int writeNewSplits(JobContext job, Path jobSubmitDir) throws IOException,
  InterruptedException, ClassNotFoundException {
      // 计算切片数量
      List<InputSplit> splits = input.getSplits(job);
      T[] array = (T[]) splits.toArray(new InputSplit[splits.size()]);
  	// 在Stag中创建切片相关的四个文件
      Arrays.sort(array, new SplitComparator());
      JobSplitWriter.createSplitFiles(jobSubmitDir, conf, 
                                      jobSubmitDir.getFileSystem(conf), array);
      return array.length;
  }
  ```

* 切片计算：`org.apache.hadoop.mapreduce.lib.input.FileInputFormat#getSplits`

  ```java
  public List<InputSplit> getSplits(JobContext job) throws IOException {
      // 取切片的最大最小值，并根据最大最小值与块大小进行比较最终确认切片大小
      StopWatch sw = new StopWatch().start();
      long minSize = Math.max(getFormatMinSplitSize(), getMinSplitSize(job));
      long maxSize = getMaxSplitSize(job);
  
      // 最终返回的切片数据
      List<InputSplit> splits = new ArrayList<InputSplit>();
      // 拆分输入路径，获取文件下的每一个子文件
      List<FileStatus> files = listStatus(job);
  	......
      for (FileStatus file: files) {
          if (ignoreDirs && file.isDirectory()) {
              continue;
          }
          Path path = file.getPath();
          long length = file.getLen();
          if (length != 0) {
              ......
              // isSplitable：判断文件是否可以进行拆分
              if (isSplitable(job, path)) {
                  // 取文件的存储块大小
                  long blockSize = file.getBlockSize();
                  // blockSize, minSize, maxSize在三个值中经过计算取切片大小
                  long splitSize = computeSplitSize(blockSize, minSize, maxSize);
  				// 构建切片信息，并添加到集合
                  long bytesRemaining = length;
                  // SPLIT_SLOP：默认为1.1
                  // 表示文件大小比切片大小如果大于1.1，就重新开切片处理，如果小于该值，直接在当前切片处理
                  // eg：切片大小为32M，文件大小为33M，33/32 < 1.1，剩余的1M在当前切片处理
                  while (((double) bytesRemaining)/splitSize > SPLIT_SLOP) {
                      int blkIndex = getBlockIndex(blkLocations, length-bytesRemaining);
                      splits.add(makeSplit(path, length-bytesRemaining, splitSize,
                                           blkLocations[blkIndex].getHosts(),
                                           blkLocations[blkIndex].getCachedHosts()));
                      bytesRemaining -= splitSize;
                  }
  				......
              }
          }
          ......
      }
      ......
      return splits;
  }
  ```

  ```java
  protected long computeSplitSize(long blockSize, long minSize,
                                  long maxSize) {
      return Math.max(minSize, Math.min(maxSize, blockSize));
  }
  ```

* 切片文件存储：`org.apache.hadoop.mapreduce.split.JobSplitWriter#createSplitFiles(org.apache.hadoop.fs.Path, org.apache.hadoop.conf.Configuration, org.apache.hadoop.fs.FileSystem, T[])`

  ```java
  public static <T extends InputSplit> void createSplitFiles(Path jobSubmitDir, 
                                                             Configuration conf, FileSystem fs, T[] splits) 
      throws IOException, InterruptedException {
      // 创建切片相关文件
      FSDataOutputStream out = createFile(fs, 
                                          JobSubmissionFiles.getJobSplitFile(jobSubmitDir), conf);
      // 将切片信息写入到文件中
      SplitMetaInfo[] info = writeNewSplits(conf, splits, out);
      out.close();
      // 创建并存储切片元数据信息
      writeJobSplitMetaInfo(fs,JobSubmissionFiles.getJobSplitMetaFile(jobSubmitDir), 
                            new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION), splitVersion,
                            info);
  }
  ```

  ```java
  public static Path getJobSplitFile(Path jobSubmissionDir) {
      return new Path(jobSubmissionDir, "job.split");
  }
  ```

  ```java
  public static Path getJobSplitMetaFile(Path jobSubmissionDir) {
      return new Path(jobSubmissionDir, "job.splitmetainfo");
  }
  ```

#### 6.4.1.4，`TextInputFormat`

> TextInputFormat 是默认的 FileInputFormat 实现类。按行读取每条记录。
>
> <font color=red>Key：LongWritable类型，改行在整个文件中的起始字节偏移量</font>
>
> <font color=red>Value：Text类型，这行的内容，不包含任何行终止符</font>

#### 6.4.1.5，`CombineTextInputFormat`

> 框架默认的 `TextInputFormat` 切片机制是对任务按文件规划切片，不管文件多小，都会是一个单独的切片，都会交给一个 `MapTask`，这样如果有大量小文件，就会产生大量的 `MapTask`，处理效率极其低下。 
>
> <font color=red>`CombineTextInputFormat` 切片过程包括虚拟存储过程和切片过程</font>

![1628156007682](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628156007682.png)

* **虚拟存储过程**：将输入目录下所有文件大小，依次和设置的 `setMaxInputSplitSize` 值比较，如果不大于设置的最大值，逻辑上划分一个块。如果输入文件大于设置的最大值且大于两倍，那么以最大值切割一块；当剩余数据大小超过设置的最大值且不大于最大值 2 倍，此时将文件均分成 2 个虚拟存储块（防止出现太小切片）。例如 `setMaxInputSplitSize` 值为 4M，输入文件大小为 8.02M，则先逻辑上分成一个4M。剩余的大小为 4.02M，如果按照 4M 逻辑划分，就会出现 0.02M 的小的虚拟存储文件，所以将剩余的 4.02M 文件切分成（2.01M 和 2.01M）两个文件。
* **切片过程**：
  * 判断虚拟存储的文件大小是否大于 `setMaxInputSplitSize` 值，大于等于则单独形成一个切片。
  * 如果不大于则跟下一个虚拟存储文件进行合并，共同形成一个切片。

##### 6.4.2.4.1，`CombineTextInputFormat` 使用配置

* 在 `Driver` 类中进行设置，设置如下

  ```java
  public class SelfDriver {
  
      public static void main(String[] args) throws Exception {
          // 获取配置信息, 构建Job示例
          Configuration configuration = new Configuration();
          Job job = Job.getInstance(configuration);
          // 指定本程序的jar包路径
          job.setJarByClass(SelfDriver.class);
          // 关联 Mapper/Reduce 业务类
          job.setMapperClass(SelfMapper.class);
          job.setReducerClass(SelfReduce.class);
          // 指定Mapper输出的KV类型
          job.setMapOutputKeyClass(Text.class);
          job.setMapOutputValueClass(SelfDomain.class);
          
          // 设置未`CombineTextInputFormat`处理方式
          job.setInputFormatClass(CombineTextInputFormat.class);
          // 设置最大处理大小为4M
          CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
          
          // 设置最大处理分片大小
          // 指定Reduce输出的KV类型
          job.setOutputKeyClass(Text.class);
          job.setOutputValueClass(SelfDomain.class);
          // 指定job输入路径
          FileInputFormat.setInputPaths(job, new Path("E:\\123456.txt"));
          // 指定job输出路径
          FileOutputFormat.setOutputPath(job, new Path("E:\\selfout"));
          // 工作
          job.waitForCompletion(true);
      }
  
  }
  ```



### 6.4.2，`MapReduce` 工作流程

![1628237080804](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628237080804.png)

![1628237089343](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628237089343.png)



1. 准备待处理的数据文件
2. 客户端通过配置 `Driver` 类，进行 `Job` 提交，开启一个任务处理
3. `Job` 提交后，会对数据文件需要的分片信息、配置信息和 `jar` 包存储到本地路径（基于远程的Hadoop会存储 `Jar` 包）
4. 根据 `Job` 阶段确定的分片信息计算出 `MapTask` 数量（默认 `Windows` 为32M，`Linux` 为128M）
5. `MapTask` 根据当前分片的偏移量从数据文件中读取数据，默认通过 `TextInputFormat` 调用 `recorderReader` 读取，输入方式可以自定义；<font color=red>`MapTask` 间处理相互独立</font>
6. 读取到数据后，调用自定义的 `Mapper` 实现类的 `map` 方法进行数据基础处理并写出
7. `Mapper` 阶段读取的数据，写出时先写出到内存缓冲区中：缓冲区大小默认100M，可配置。缓冲区内部分为两部分：一部分存储索引数据，一部分存储真是数据；在写入数据时，对按照分区对数据进行区分，分区数自定义配置，默认按照 `key` 的哈希值对分区进行取余确定分区；
8. `Mapper` 数据在缓冲区中顺序写入，在写入空间超过缓冲区空间的 `80%` 时，会按分区溢出数据到文件（分区文件重合，通过索引位置区分）如果写缓冲区速度快与写溢出文件速度，则在缓冲区满后，`Mapper` 操作会阻塞直到缓冲区空间够用；<font color=red>在写溢出文件时，会按照 `key` 对缓冲区中数据进行快速排序，排序完成后写出，排序数据移动时只移动元数据即可</font>
9. 每一次溢出处理都会生成一个溢出文件，`Shuffle` 会通过归并排序算法对溢出文件进行合并处理，保证在后续 `Reduce` 处理时，一个 `MapTask` 中只有一个文件（分区文件重合）
10. `MapTask` 将溢出文件按分区归并后，此时可通过 `Combiner` 对文件进行合并，以 `Key` 分组对分区文件进行初步处理，减少传递到 `Reduce` 阶段的网络数据
11. `Redcue` 阶段理论上在 `MapTask` 任务全部执行完成后触发，并启动相应数量的 `ReduceTask`，并告知 `ReduceTask` 数据处理范围；不过如果 `MapTask` 过程，也可能在处理过程中触发 `Reduce` 对已经处理完成的 `MapTask` 数据进行合并
12. `Reduce` 首先会对每一个分区在不同 `MapTask` 下的文件通过归并算法进行合并
13. 对合并排序后的文件，`ReduceTask` 会从文件中取出一个个键值对 ，对同一个 `key` 的数据重新构造后，调用自定义的 `Reduce` 实现类的 `reduce(..)` 执行
14. 数据处理完成后，通过 `OutPutFormat` 调用 `recordWrite` 写出数据，写出到 `part-r-000000` 文件中，

### 6.4.3，`Shuffle` 机制详解

#### 6.4.3.1，`Shuffle` 机制

![1628240722053](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628240722053.png)

#### 6.4.3.2，`Partition` 分区

1. 问题引出：

   > 要求将统计按照条件输出到不同的文件中，比如不同手机前缀的手机号分到不同的结果文件中

2. 默认 `Partition` 分区
   * `MapReduce` 的默认分区数为0
   * 在进行分区计算时，用 `key` 的哈希值与 `int` 的最大值与计算后，对分区数取余即确定所属分区

3. 自定义 `Partition` 步骤

   * 自定义 `Partition` 类，继承 `org.apache.hadoop.mapreduce.Partitioner` 类，并重写抽象方法，再方法中根据规则返回不同从0开始的连续编号即所属分区编号

     ```java
     package com.hadoop.mapreduce.partition;
     
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Partitioner;
     
     /**
      * 自定义分区, key value 为mapper阶段输出的key value类型
      */
     public class MyPartition extends Partitioner<Text, SelfDomain> {
     
         @Override
         public int getPartition(Text text, SelfDomain selfDomain, int numPartitions) {
             String phone = text.toString();
             String prePhone = phone.substring(0, 3);
             if ("136".equals(prePhone)) {
                 return 0;
             } else if ("137".equals(prePhone)) {
                 return 1;
             } else if ("138".equals(prePhone)) {
                 return 2;
             } else if ("139".equals(prePhone)) {
                 return 3;
             } else {
                 return 4;
             }
         }
     
     }
     ```

   * 在 `Job ` 驱动中，设置自定义分区 `Partition`

   * 自定义 `Partition` 后，根据自定义的 `Partition` 逻辑设置响应数量的 `ReduceTask`

     ```java
     // 分区处理, 设置分区处理类
     job.setPartitionerClass(MyPartition.class);
     // 设置ReduceTask个数, 与分区逻辑个数保持一致
     job.setNumReduceTasks(5);
     ```

   * 最终输入结果

     ![1628246668696](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628246668696.png)

4. 分区总结

   * 如果 `ReduceTask` 的数量 > `getPartition` 的结果数，则会多产生几个空的输出文件part-r-000xx

   * 如果1 < `ReduceTask` 的数量 < `getPartition` 的结果数，则有一部分分区数据无处安放，会Exception；

   * 如果 `ReduceTask` 的数量=1，则不管 `MapTask` 端输出多少个分区文件，最终结果都交给这一个`ReduceTask`，最终也就只会产生一个结果文件 part-r-00000；该部分在源码中有所体现

     ```java
     // org.apache.hadoop.mapred.MapTask.NewOutputCollector#NewOutputCollector
     NewOutputCollector(org.apache.hadoop.mapreduce.JobContext jobContext,
                        JobConf job,
                        TaskUmbilicalProtocol umbilical,
                        TaskReporter reporter
                       ) throws IOException, ClassNotFoundException {
         collector = createSortingCollector(job, reporter);
         partitions = jobContext.getNumReduceTasks();
         // 注意在设置分区执行类时, 如果NumReduce为1, 默认取固定的数据
         // 如果大于1, 则根据自定义的分区类反射获取
         if (partitions > 1) {
             partitioner = (org.apache.hadoop.mapreduce.Partitioner<K,V>)
                 ReflectionUtils.newInstance(jobContext.getPartitionerClass(), job);
         } else {
             partitioner = new org.apache.hadoop.mapreduce.Partitioner<K,V>() {
                 @Override
                 public int getPartition(K key, V value, int numPartitions) {
                     return partitions - 1;
                 }
             };
         }
     }
     ```

   * 分区号必须从零开始，逐一累加

#### 6.4.3.3，`WritableComparable` 排序

> 排序是 `MapReduce` 框架中最重要的操作之一；
>
> `MapTask` 和 `ReduceTask` 均会对数据按照 `Key` 进行排序，该操作属于 `Hadoop` 的默认行为。<font color=red>任务应用程序中的数据都会被排序，而无论是否需要</font>
>
> 默认排序是按照<font color=red>字典顺序</font>，且实现该排序的方式是 <font color=red>快速排序</font>

> 对于 `MapTask`，对将处理的结果暂时放在环形缓冲区中，<font color=red>当环形缓冲区使用率达到一定阈值后(80%)，再对缓冲区中的数据进行一次快速排序</font>，并将有序数据溢写到磁盘上（每一次溢写都会产生一个溢写文件），当数据全部处理完毕后，会对<font color=red>磁盘上的所有溢写文件进行归并排序</font>
>
> 对于 `ReduceTask`，它从每个 `MapTask` 上远程拷贝相应的数据文件，如果文件大于一定阈值，则溢写到磁盘，否则在内存中直接处理。如果磁盘上文件数据达到一定的阈值，则进行一次<font color=red>归并排序</font>以生成一个更大文件；如果内存中文件大小或者数据超过一定阈值，在进行一次合并后将数据溢写到磁盘上。当所有数据拷贝完成后，<font color=red>`ReduceTask` 统一对内存和磁盘上的所有数据进行一次归并排序</font>

##### 6.4.3.3.1，排序分类

* **部分排序**：`MapReduce` 根据输入记录的键对数据集排序，保证输出的每个文件内部有序
* **全排序**：最终输出结果只有一个文件，且文件内部有序。实现方式是只设置一个 `ReduceTask`。但该方法在处理大型文件时效率极低，因为一台机器处理所有文件，完全丧失了 `MapReduce` 所提供的并行架构。
* **辅助排序（GroupingComparator分组）**：在 `Reduce` 端对 `key` 进行分组。应用于：在接收的 `key` 为 `bean` 对象时，想让一个或几个字段相同（全部字段比较不相同）的 `key` 进入到同一个 `reduce` 方法时，可以采用分组排序。
* **自定义排序**：实现 `WriteableComparable` 接口并自定义排序方式；在自定义排序过程中，如果`compareTo` 中的判断条件为两个即为二次排序。

##### 6.4.3.3.2，`WritableComparable` 全排序

1. 原始需求

   > 对 [手机流量统计方式](#6.3.3，序列化实现手机流量统计) 进行二次开发，在原有统计结果基础上，对输出结果按总流量进行倒序排列

2. 需求分析

   ![1628653959591](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628653959591.png)

3. 代码实现

   * `SelfDomain` ：

     ```java
     // WritableComparable<T> extends Writable, Comparable<T>
     // 能同时实现序列化和排序两个需求
     public class SelfDomain implements WritableComparable<SelfDomain> {
         ......
         @Override
         public int compareTo(SelfDomain o) {
             // 按总流量进行倒序排列
             return (int)(this.sumBytes - o.getSumBytes());
         }
     }
     ```

   * `SelfMapper`：因为要按总流量进行排序，总流量是 `SelfDomain` 中的字段，所以需要以 `SelfDomain` 为 `Key`

     ```java
     package com.hadoop.mapreduce.sort;
     
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Mapper;
     
     import java.io.IOException;
     
     /**
      * 自定义Mapper计算
      * 按总流量进行排序, 需要把总流浪所在的对象设置为key, 手机号为value
      * @author PJ_ZHANG
      * @create 2021-05-28 14:44
      **/
     public class SelfMapper extends Mapper<LongWritable, Text, SelfDomain, Text> {
     
         private SelfDomain domain = new SelfDomain();
     
         private Text text = new Text();
     
         @Override
         protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
             String str = value.toString();
             String[] strArr = str.split("\t");
             String phone = strArr[0];
             long uploadBytes = Long.parseLong(strArr[1]);
             long downloadBytes = Long.parseLong(strArr[2]);
             long sumBytes = Long.parseLong(strArr[3]);
             domain.setUploadBytes(uploadBytes);
             domain.setDownloadBytes(downloadBytes);
             domain.setSumBytes(sumBytes);
             text.set(phone);
             context.write(domain, text);
         }
     }
     ```

   * `SelfReduce`：入参与 `Mapper` 保持一致，出参还是按照原来形式进行输出，所以出参类型不变，在出参时对 `Key` 和 `Value` 的位置进行互换

     ```java
     package com.hadoop.mapreduce.sort;
     
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Reducer;
     
     import java.io.IOException;
     
     /**
      * 最终进行数据汇总
      * @author PJ_ZHANG
      * @create 2021-05-28 15:37
      **/
     public class SelfReduce extends Reducer<SelfDomain, Text, Text, SelfDomain> {
     
         private SelfDomain domain = new SelfDomain();
     
         /**
          * reduce对应进行入参的key-value调整, 写出还是以value-key形式写出, 保证写出顺序
          */
         @Override
         protected void reduce(SelfDomain key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
     
             for (Text phone : values) {
                context.write(phone, key);
             }
         }
     
     }
     ```

   * `SelfDriver`：修改 `Mapper` 的出入参类型即可

     ```java
     package com.hadoop.mapreduce.sort;
     
     import org.apache.hadoop.conf.Configuration;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Job;
     import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
     import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
     
     /**
      * 调度类
      *
      * @author PJ_ZHANG
      * @create 2021-05-28 15:41
      **/
     public class SelfDriver {
     
         public static void main(String[] args) throws Exception {
             // 获取配置信息, 构建Job示例
             Configuration configuration = new Configuration();
             Job job = Job.getInstance(configuration);
             // 指定本程序的jar包路径
             job.setJarByClass(SelfDriver.class);
             // 关联 Mapper/Reduce 业务类
             job.setMapperClass(SelfMapper.class);
             job.setReducerClass(SelfReduce.class);
             // 指定Mapper输出的KV类型
             job.setMapOutputKeyClass(SelfDomain.class);
             job.setMapOutputValueClass(Text.class);
             // 指定Reduce输出的KV类型
             job.setOutputKeyClass(Text.class);
             job.setOutputValueClass(SelfDomain.class);
             // 指定job输入路径
             FileInputFormat.setInputPaths(job, new Path("E:\\selfout1628652961768\\part-r-00000"));
             // 指定job输出路径
             FileOutputFormat.setOutputPath(job, new Path("E:\\selfout" + System.currentTimeMillis()));
             // 工作
             job.waitForCompletion(true);
         }
     
     }
     
     ```

   * 排序完成后，可能会存在总流量一致，但是上下行流量有偏差的情况，此时需要进行二次排序。

     ![1628663710098](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628663710098.png)

##### 6.4.3.3.3，`WritableComparable` 二次排序实现

> 接上，在总流量一致时，按下行流量进行倒序；在总流量和下行流量一致时，按上行流量进行倒序；

* `SelfDomain`：二次排序，只需要对排序方式进行调整即可

  ```java
  public int compareTo(SelfDomain o) {
      long result = o.sumBytes - this.sumBytes;
      if (0 == result) {
          result = o.downloadBytes - this.downloadBytes;
          if (0== result) {
              result = o.uploadBytes - this.uploadBytes;
          }
      }
      return (int) result;
  }
  ```

  ![1628663872570](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628663872570.png)

##### 6.4.3.3.4，`WritableComparable` 分区内排序

> 在 [`Partition` 分区](#6.4.3.2，`Partition` 分区) 处理的基础上进行分区排序处理，按照对应的分区方式保证区内数据有序性

* `SelfPartition`：增加分区处理类

  ```java
  /**
   * 自定义分区, key value 为mapper阶段输出的key value类型
   */
  public class MyPartition extends Partitioner<SelfDomain, Text> {
  
      @Override
      public int getPartition(SelfDomain selfDomain, Text text, int numPartitions) {
          // 按 value 值进行分区处理
          String phone = text.toString();
          String prePhone = phone.substring(0, 3);
          if ("136".equals(prePhone)) {
              return 0;
          } else if ("137".equals(prePhone)) {
              return 1;
          } else if ("138".equals(prePhone)) {
              return 2;
          } else if ("139".equals(prePhone)) {
              return 3;
          } else {
              return 4;
          }
      }
  
  }
  ```

* `SelfDriver`：添加分区信息

  ```java
  public class SelfDriver {
  
      public static void main(String[] args) throws Exception {
          // 获取配置信息, 构建Job示例
          Configuration configuration = new Configuration();
          Job job = Job.getInstance(configuration);
          // 指定本程序的jar包路径
          job.setJarByClass(SelfDriver.class);
          // 关联 Mapper/Reduce 业务类
          job.setMapperClass(SelfMapper.class);
          job.setReducerClass(SelfReduce.class);
          // 指定Mapper输出的KV类型
          job.setMapOutputKeyClass(SelfDomain.class);
          job.setMapOutputValueClass(Text.class);
          // 指定Reduce输出的KV类型
          job.setOutputKeyClass(Text.class);
          job.setOutputValueClass(SelfDomain.class);
          // 添加分区信息
          job.setPartitionerClass(MyPartition.class);
          // 设置reducetask数量, 与分区数量保持一致
          job.setNumReduceTasks(5);
          // 指定job输入路径
          FileInputFormat.setInputPaths(job, new Path("E:\\selfout1628652961768\\part-r-00000"));
          // 指定job输出路径
          FileOutputFormat.setOutputPath(job, new Path("E:\\selfout" + System.currentTimeMillis()));
          // 工作
          job.waitForCompletion(true);
      }
  
  }
  ```

* 结果如下：

  ![1628664337771](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628664337771.png) 

  ![1628664370398](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628664370398.png)

#### 6.4.3.4，`Combiner` 合并

> * `Combiner` 是 MR 程序中 `Mapper` 和 `Reducer` 之外的一种组件
> * `Combiner` 组件的父类就是 `Reducer`
> * `Combiner` 和 `Reducer` 的区别在于运行的位置
>   * `Combiner` 是在每一个 `MapTask` 所在的节点运行
>   * `Reducer` 是接收全局所有 `Mapper` 的输出结果
> * `Combiner` 的意义是对每一个 `MapTask` 的输出进行局部汇总，以减少网络传输量
> * <font color=red>`Combiner` 能够应用的前提是不能影响最终的业务逻辑</font>，而且，`Combine` 输出的KV要与 `Reduce` 输入的KV类型对应上；`Combine` 输入的KV要与 `Mapper` 输出的KV对应上
> * 自定义 `Combiner` 步骤
>   * 自定义 `Combiner` 类继承 `Reduce` 类，声明出入KV类型并重写 `reduce(...)` 方法
>   * 在 `Job` 驱动类中配置 `Combiner`

###### 6.4.3.4.1，`Combiner` 合并实操

1. 需求

   > 对 WordCount 实例进行局部汇总，以求减少网络数据传输量

2. 需求分析

   ![1628671935177](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628671935177.png)

3. 案例实现

   * 自定义 `Combiner`：

     ```java
     package com.hadoop.mapreduce.combiner;
     
     import org.apache.hadoop.io.IntWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Reducer;
     
     import java.io.IOException;
     
     /**
      * 自定义 Combiner 类
      * 入参KV为Mapper的出参KV
      * 出参KV为Mapper的入参KV
      */
     public class SelfCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {
     
         IntWritable intWritable = new IntWritable();
     
         @Override
         protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
             int sum = 0;
             for (IntWritable value : values) {
                 sum += value.get();
             }
             intWritable.set(sum);
             context.write(key, intWritable);
         }
     
     }
     ```

   * `Job` 配置中添加：

     ```java
     public class WordCountDriver {
     
         public static void main(String[] args) throws Exception {
             // 1. 获取配置信息, 获取Job示例
             Configuration configuration = new Configuration();
             Job job = Job.getInstance(configuration);
             // 2. 指定本程序jar包所在的路径
             job.setJarByClass(WordCountDriver.class);
             // 3. 关联Mapper/Reduce业务类
             job.setMapperClass(WordCountMapper.class);
             job.setReducerClass(WordCountReduce.class);
             // 4. 指定Mapper输出数据的KV类型
             job.setMapOutputKeyClass(Text.class);
             job.setMapOutputValueClass(IntWritable.class);
             // 5. 指定Reduce输出数据的KV类型
             job.setOutputKeyClass(Text.class);
             job.setOutputValueClass(IntWritable.class);
     
             // 指定合并类
             job.setCombinerClass(SelfCombiner.class);
     
             // 6. 指定Job输入原始数据的文件路径
              FileInputFormat.setInputPaths(job, new Path("E:\\123.txt"));
             // 7. 指定Job输出结果数据的文件路径
              FileOutputFormat.setOutputPath(job, new Path("E:\\wcout"));
             // 8. 提交执行
             job.waitForCompletion(true);
         }
     
     }
     ```

   * 结果呈现：生效后会在日志中看到有合并信息

     ![1628672893490](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628672893490.png)

### 6.4.4，`OutputFormat` 数据输出

> `OutputFormat` 是 `MapReduce` 输出的基类，所有实现 `MapReduce` 输出都继承了 `OutputFormat` 接口；
>
> * `MapReduce` 的默认输出类是 `TextOutputFormat`

#### 6.4.4.1，自定义 `OutputFormat`

1. 需求

   > 过滤 log.txt 中的日志信息，包含 hello 的网站输出到 E:/hello.txt，其他网站输出到 E:/other.txt

2. 需求分析：

   ![1628679468337](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628679468337.png)

3. 案例实操

   * `SelfOutputFormat` 类：构建 `SelfRecordWriter` 对象

     ```java
     package com.hadoop.mapreduce.outputformat;
     
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.RecordWriter;
     import org.apache.hadoop.mapreduce.TaskAttemptContext;
     import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
     
     import java.io.IOException;
     
     public class SelfOutputFormat extends FileOutputFormat<Text, NullWritable> {
     
         @Override
         public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
             return new SelfRecordWriter(job);
         }
     
     }
     ```

   * `SelfRecordWriter` 类：创建输出路径并定义输出方式

     ```java
     package com.hadoop.mapreduce.outputformat;
     
     import org.apache.hadoop.fs.FSDataOutputStream;
     import org.apache.hadoop.fs.FileSystem;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.IOUtils;
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.RecordWriter;
     import org.apache.hadoop.mapreduce.TaskAttemptContext;
     
     import java.io.IOException;
     import java.nio.charset.StandardCharsets;
     
     public class SelfRecordWriter extends RecordWriter<Text, NullWritable> {
     
         private FSDataOutputStream helloOutputFormat;
     
         private FSDataOutputStream otherOutputFormat;
     
         public SelfRecordWriter(TaskAttemptContext job) {
             try {
                 FileSystem fs = FileSystem.get(job.getConfiguration());
                 helloOutputFormat = fs.create(new Path("E:\\hello.txt"));
                 otherOutputFormat = fs.create(new Path("E:\\other.txt"));
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     
         @Override
         public void write(Text key, NullWritable value) throws IOException, InterruptedException {
             String line = key.toString();
             if (line.contains("hello")) {
                 helloOutputFormat.write((line + "\n").getBytes(StandardCharsets.UTF_8));
             } else {
                 otherOutputFormat.write((line + "\n").getBytes(StandardCharsets.UTF_8));
             }
         }
     
         @Override
         public void close(TaskAttemptContext context) throws IOException, InterruptedException {
             IOUtils.closeStreams(helloOutputFormat, otherOutputFormat);
         }
     
     }
     ```

   * `Mapper`

     ```java
     package com.hadoop.mapreduce.outputformat;
     
     import org.apache.hadoop.io.IntWritable;
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Mapper;
     
     import java.io.IOException;
     
     public class WordCountMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
     
         private Text text = new Text();
     
         private IntWritable one = new IntWritable(1);
     
         @Override
         protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
             context.write(value, NullWritable.get());
         }
     }
     ```

   * `Reduce`

     ```java
     package com.hadoop.mapreduce.outputformat;
     
     import org.apache.hadoop.io.IntWritable;
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Reducer;
     
     import java.io.IOException;
     
     public class WordCountReduce extends Reducer<Text, NullWritable, Text, NullWritable> {
     
         private IntWritable intWritable = new IntWritable();
     
         @Override
         protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
             for (NullWritable nullWritable : values) {
                 context.write(key, NullWritable.get());
             }
         }
     }
     ```

   * `Driver`

     ```java
     package com.hadoop.mapreduce.outputformat;
     
     import org.apache.hadoop.conf.Configuration;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.IntWritable;
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Job;
     import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
     import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
     
     /**
      * Driver类中进行统一调度
      * 分8个步骤
      * @author PJ_ZHANG
      * @create 2021-05-27 18:24
      **/
     public class WordCountDriver {
     
         public static void main(String[] args) throws Exception {
             // 1. 获取配置信息, 获取Job示例
             Configuration configuration = new Configuration();
             Job job = Job.getInstance(configuration);
             // 2. 指定本程序jar包所在的路径
             job.setJarByClass(WordCountDriver.class);
             // 3. 关联Mapper/Reduce业务类
             job.setMapperClass(WordCountMapper.class);
             job.setReducerClass(WordCountReduce.class);
             // 4. 指定Mapper输出数据的KV类型
             job.setMapOutputKeyClass(Text.class);
             job.setMapOutputValueClass(NullWritable.class);
             // 5. 指定Reduce输出数据的KV类型
             job.setOutputKeyClass(Text.class);
             job.setOutputValueClass(NullWritable.class);
     
             // *****设置自定义输出方式
             job.setOutputFormatClass(SelfOutputFormat.class);
     
             // 6. 指定Job输入原始数据的文件路径
             FileInputFormat.setInputPaths(job, new Path("E:\\log.txt"));
             // 7. 指定Job输出结果数据的文件路径
             // 这一步需要保留，用于输出_SUCCESS信息
             FileOutputFormat.setOutputPath(job, new Path("E:\\wcout"));
             // 8. 提交执行
             job.waitForCompletion(true);
         }
     
     }
     ```

### 6.4.5，`InputFormat` 自定义数据输入

1. 需求

   > 在自定义  `OutputFormat` 的基础上，自定义 `InputFormat`，对 `log.txt` 文件进行读取

2. 需求分析

   > 参考 `TextInputFormat` 实现，以 {行号 ：行数据} 输出数据（这里行号分片会有问题）

3. 实现流程

   * 自定义 `InputFormat` 类继承 `FileInputFormat`，参数类型需要与 `Mapper` 的入参类型保持一致
   * `InputFormat` 中需要 `RecordReader` 子类，自定义该类并重写抽象方法
   * 在 `RecordReader` 子类中进行文件处理，按分片进行数据读取和输出，输出输出到 `Mapper` 中
   * 在 `Driver` 中设置自定义的 `InputFormat`

4. 代码实现

   * `SelfInputFormat`

     ```java
     package com.hadoop.mapreduce.inputformat;
     
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.InputSplit;
     import org.apache.hadoop.mapreduce.JobContext;
     import org.apache.hadoop.mapreduce.RecordReader;
     import org.apache.hadoop.mapreduce.TaskAttemptContext;
     import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
     
     import java.io.IOException;
     
     /**
      * 模拟TextInputformat
      */
     public class SelfInputFormat extends FileInputFormat<LongWritable, Text> {
     
         @Override
         public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
             // 直接返回自定义的 selfRecordReader
             SelfRecordReader selfRecordReader = new SelfRecordReader();
             return selfRecordReader;
         }
     
         @Override
         protected boolean isSplitable(JobContext context, Path filename) {
             // true: 进行分片处理,
             // false: 不进行分片处理
             return true;
         }
     }
     ```

   * `SelfRecordReader`：

     ```java
     package com.hadoop.mapreduce.inputformat;
     
     import org.apache.hadoop.fs.FSDataInputStream;
     import org.apache.hadoop.fs.FileSystem;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.IOUtils;
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.InputSplit;
     import org.apache.hadoop.mapreduce.RecordReader;
     import org.apache.hadoop.mapreduce.TaskAttemptContext;
     import org.apache.hadoop.mapreduce.lib.input.FileSplit;
     import org.apache.hadoop.util.LineReader;
     
     import java.io.IOException;
     
     public class SelfRecordReader extends RecordReader<LongWritable, Text> {
     
         /**
          * 行数据读取
          */
         private LineReader lineReader;
     
         private LongWritable key = new LongWritable(-1);
     
         private Text value = new Text();
     
         private Long currPos;
     
         private Long start;
     
         private Long end;
     
         private Text currLine = new Text();
     
         @Override
         public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
             FileSplit fileSplit = (FileSplit) split;
             // 取文件路径, 构造文件输入流
             Path filePath = fileSplit.getPath();
             FileSystem fs = filePath.getFileSystem(context.getConfiguration());
             FSDataInputStream is = fs.open(filePath);
             lineReader = new LineReader(is, context.getConfiguration());
             // 获取分片文件的开始位置
             start = fileSplit.getStart();
             // 获取分片文件的结束位置
             end = start + fileSplit.getLength();
             // 读取位置定位到start的位置
             is.seek(start);
             if (start != 0) {
                 // 此处大致意思是跳过一个断行, 非第一个分片可能是从行中间某一个位置开始的, 跳过改行, 改行已在上一个分片处理
                 start += lineReader.readLine(new Text(), 0, (int) Math.min(Integer.MAX_VALUE, end - start));
             }
             // 定义当前偏移量到开始位置
             currPos = start;
         }
     
         @Override
         public boolean nextKeyValue() throws IOException, InterruptedException {
             if (currPos > end) {
                 return false;
             }
             // 读取一行, 并移动偏移量
             currPos += lineReader.readLine(currLine);
             if (0 == currLine.getLength()) {
                 return false;
             }
             // 行内容
             value.set(currLine);
             // 行数, 从0行开始
             key.set(key.get() + 1);
             return true;
         }
     
         @Override
         public LongWritable getCurrentKey() throws IOException, InterruptedException {
             // 取当前key
             return key;
         }
     
         @Override
         public Text getCurrentValue() throws IOException, InterruptedException {
             // 取当前value
             return value;
         }
     
         @Override
         public float getProgress() throws IOException, InterruptedException {
             // 取当前进度, 已经执行的百分比
             if (start == end) {
                 return 0.0f;
             } else {
                 return Math.min(1.0f, (currPos - start) / (float) (end - start));
             }
         }
     
         @Override
         public void close() throws IOException {
             IOUtils.closeStreams(lineReader);
         }
     }
     ```

   * `Driver` 添加自定义输入类：

     ```java
     package com.hadoop.mapreduce.inputformat;
     
     import org.apache.hadoop.conf.Configuration;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Job;
     import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
     import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
     
     /**
      * Driver类中进行统一调度
      * 分8个步骤
      * @author PJ_ZHANG
      * @create 2021-05-27 18:24
      **/
     public class WordCountDriver {
     
         public static void main(String[] args) throws Exception {
             // 1. 获取配置信息, 获取Job示例
             Configuration configuration = new Configuration();
             Job job = Job.getInstance(configuration);
             // 2. 指定本程序jar包所在的路径
             job.setJarByClass(WordCountDriver.class);
             // 3. 关联Mapper/Reduce业务类
             job.setMapperClass(WordCountMapper.class);
             job.setReducerClass(WordCountReduce.class);
             // 4. 指定Mapper输出数据的KV类型
             job.setMapOutputKeyClass(LongWritable.class);
             job.setMapOutputValueClass(Text.class);
             // 5. 指定Reduce输出数据的KV类型
             job.setOutputKeyClass(LongWritable.class);
             job.setOutputValueClass(Text.class);
     
             // 设置自定义输入方式
             job.setInputFormatClass(SelfInputFormat.class);
             // 设置自定义输出方式
             job.setOutputFormatClass(SelfOutputFormat.class);
     
             // 6. 指定Job输入原始数据的文件路径
             FileInputFormat.setInputPaths(job, new Path("E:\\log.txt"));
             // 7. 指定Job输出结果数据的文件路径
             // 这一步需要保留，用于输出_SUCCESS信息
             FileOutputFormat.setOutputPath(job, new Path("E:\\wcout"));
             // 8. 提交执行
             job.waitForCompletion(true);
         }
     
     }
     ```

## 6.5，`MapReduce` 内核源码分析

### 6.5.1，`MapTask` 工作机制

![1628736674039](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628736674039.png)

* `Read` 阶段：`MapTask` 通过 `InputFormat` 获得的 `RecordReader`，从输入 `InputSplit` 中解析出一个个 `key/value`
* ·`Map` 阶段：该节点主要是将解析出的 `key/value` 交给用户编写 `map()` 函数处理，并产生一系列新的 `key/value`
* `Collect` 阶段：在用户编写 `map()` 函数中，当数据处理完成后，一般会调用 `OutputCollector.collect()` 输出结果。在该函数内部，它会将生成的 `key/value` 分区（调用`Partitioner`），并写入一个环形内存缓冲区中
* `Spill` 溢写阶段：当环形缓冲区满后，`MapReduce` 会将数据写到本地磁盘上，生成一个临时文件。需要注意的是，将数据写入本地磁盘之前，先要对数据进行一次本地排序（快速排序），并在必要时对数据进行合并、压缩等操作。
  * 利用快速排序算法对缓存区内的数据进行排序，排序方式是，先按照分区编号 `Partition` 进行排序，然后按照 `key` 进行排序。这样，经过排序后，数据以分区为单位聚集在一起，且同一分区内所有数据按照 `key` 有序。
  * 按照分区编号由小到大依次将每个分区中的数据写入任务工作目录下的临时文件 `output/spillN.out`（N 表示当前溢写次数）中。如果用户设置了 `Combiner`，则写入文件之前，对每个分区中的数据进行一次聚集操作
  * 将分区数据的元信息写到内存索引数据结构 `SpillRecord` 中，其中每个分区的元信息包括在临时文件中的偏移量、压缩前数据大小和压缩后数据大小。如果当前内存索引大小超过 1MB，则将内存索引写到文件 `output/spillN.out.index` 中
* `Merge` 阶段：当所有数据处理完成后，`MapTask` 对所有临时文件进行一次合并，以确保最终只会生成一个数据文件
  * 当所有数据处理完后，`MapTask` 会将所有临时文件合并成一个大文件，并保存到文件 `output/file.out` 中，同时生成相应的索引文件 `output/file.out.index`
  * 在进行文件合并过程中，`MapTask` 以分区为单位进行合并。对于某个分区，它将采用多轮递归合并的方式。每轮合并 `mapreduce.task.io.sort.factor`（默认 10）个文件，并将产生的文件重新加入待合并列表中，对文件排序后，重复以上过程，直到最终得到一个大文件
  * 让每个 `MapTask` 最终只生成一个数据文件，可避免同时打开大量文件和同时读取大量小文件产生的随机读取带来的开销

### 6.5.2，`ReduceTask` 工作机制

![1628737225105](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628737225105.png)

* `Copy` 阶段：`ReduceTask` 从各个 `MapTask` 上远程拷贝一片数据，并针对某一片数据，如果其大小超过一定阈值，则写到磁盘上，否则直接放到内存中
* `Sort` 阶段：在远程拷贝数据的同时，`ReduceTask` 启动了两个后台线程对内存和磁盘上的文件进行合并，以防止内存使用过多或磁盘上文件过多。按照 `MapReduce` 语义，用户编写 `reduce()` 函数输入数据是按 key 进行聚集的一组数据。为了将 `key` 相同的数据聚在一起，`Hadoop` 采用了基于排序的策略。由于各个 `MapTask` 已经实现对自己的处理结果进行了局部排序，因此，`ReduceTask` 只需对所有数据进行一次归并排序即可
* `Reduce` 阶段：`reduce()` 函数将计算结果写到文件系统（本地文件/远程文件服务器）中

### 6.5.3，`ReduceTask` 并行机制

> 回顾：`MapTask` 并行度由切片个数决定，切片个数由输入文件和切片规则决定
>
> 思考：`ReduceTask` 并行度由谁决定

* 设置 `ReduceTask` 并行度个数：`ReduceTask` 的并行度同时影响 `Job` 的执行并发度和执行效率，`ReduceTask` 的并行数量由人为指定：

  ```java
  job.setNumReduceTasks(5);
  ```

* 测试 `ReduceTask` 的合适并发数量：通过对不同体量文件不断压测，观察执行时间而定，eg：

  ![1628737609116](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628737609116.png)

* 注意事项：

  * `ReduceTask` 为0时，没有 `Reduce` 阶段，输出文件和 `MapTask` 个数一致
  * `ReduceTask` 默认未1，所以输出文件为1个
  * 如果数据分布不均匀，就有可能在 `Reduce` 阶段产生数据倾斜
  * `ReduceTask` 并不是任意设置，还要考虑业务实现逻辑，有些情况下需要计算汇总结果，就只能有一个 `ReduceTask`
  * 具体多少个 `ReduceTask`，还需要视集群性能而定

### 6.5.4，`MapTask` 源码分析

> 接 [6.4.1.2，`Job` 提交流程源码详解](#6.4.1.2，`Job` 提交流程源码详解)

* 提交工作任务

  ```java
  // 1，Job 提交任务后，会在 org.apache.hadoop.mapred.LocalJobRunner.Job#Job 中开启线程
  public Job(JobID jobid, String jobSubmitDir) throws IOException {
      ......
      this.start();
  }
  
  // 2，start() 方法启动线程后，会调用 run() 方法，run()方法中做了三件时间
  // * 初始化并启动 MapTask
  // * 初始化并启动 ReduceTask
  // * 清除临时文件
  public void run() {
      ......
      // 初始化并启动 MapTask
      List<RunnableWithThrowable> mapRunnables = getMapTaskRunnables(
          taskSplitMetaInfos, jobId, mapOutputFiles);
      initCounters(mapRunnables.size(), numReduceTasks);
      ExecutorService mapService = createMapExecutor();
      runTasks(mapRunnables, mapService, "map");
      ......
  	// 初始化并启动 ReduceTask
      if (numReduceTasks > 0) {
          List<RunnableWithThrowable> reduceRunnables = getReduceTaskRunnables(
                  jobId, mapOutputFiles);
          ExecutorService reduceService = createReduceExecutor();
          runTasks(reduceRunnables, reduceService, "reduce");
      }
      ......
      // 删除临时文件
      outputCommitter.commitJob(jContext);
      status.setCleanupProgress(1.0f);
  	......
  }
  ```

* 初始化并提交 `MapTask` 详解

  * `org.apache.hadoop.mapred.LocalJobRunner.Job#getMapTaskRunnables`

    ```java
    // taskInfo 分片信息
    protected List<RunnableWithThrowable> getMapTaskRunnables(
        TaskSplitMetaInfo [] taskInfo, JobID jobId,
        Map<TaskAttemptID, MapOutputFile> mapOutputFiles) {
    
        int numTasks = 0;
        ArrayList<RunnableWithThrowable> list =
            new ArrayList<RunnableWithThrowable
        // 根据分片信息构造任务列表，一个分片对应一个线程
        // 线程对象为：MapTaskRunnable
        for (TaskSplitMetaInfo task : taskInfo) {
            list.add(new MapTaskRunnable(task, numTasks++, jobId,
                                         mapOutputFiles));
        }
        return list;
    }
    ```

  * `org.apache.hadoop.mapred.LocalJobRunner.Job#initCounters`：初始化 `MapTask` 和 `ReduceTask` 数量

  * `org.apache.hadoop.mapred.LocalJobRunner.Job#createMapExecutor`：构造 `MapTask` 线程池

    ```java
    protected synchronized ExecutorService createMapExecutor() {
    
        ......
        // 线程池数量为分片数量，并左右取极值
        maxMapThreads = Math.min(maxMapThreads, this.numMapTasks);
        maxMapThreads = Math.max(maxMapThreads, 1); // In case of no tasks.
    	.......
        // 构建线程池，并自定义线程名称
        // 线程池对象为 HadoopThreadPoolExecutor
        ThreadFactory tf = new ThreadFactoryBuilder()
            .setNameFormat("LocalJobRunner Map Task Executor #%d")
            .build();
        ExecutorService executor = HadoopExecutors.newFixedThreadPool(
            maxMapThreads, tf);
        return executor;
    }
    ```

  * `org.apache.hadoop.mapred.LocalJobRunner.Job#runTasks`：通过线程池启动 `MapTask` 线程

    ```java
    private void runTasks(List<RunnableWithThrowable> runnables,
                          ExecutorService service, String taskType) throws Exception {
        // 启动线程池
        for (Runnable r : runnables) {
            service.submit(r);
        }
        try {
            service.shutdown();
            ......
    		// 等待线程执行完成
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {}
    	......
    }
    ```

* `MapTask` 线程方法启动：线程类为 `MapTaskRunnable`，启动线程后会执行该类的 `run()` 方法

  ```java
  // org.apache.hadoop.mapred.LocalJobRunner.Job.MapTaskRunnable#run
  public void run() {
      ......
      // 在 run() 方法中，最终调用改方法具体执行
      // localConf 表示本地配置，默认在：\tmp\hadoop-zhangpanjing\mapred\local\localRunner\zhangpanjing\job_local1855014182_0001\job_local1855014182_0001.xml
      map.run(localConf, Job.this);
      ......
  }
  ```

  ```java
  // org.apache.hadoop.mapred.MapTask#run
  public void run(final JobConf job, final TaskUmbilicalProtocol umbilical)
      ......
      // 最终执行该方法，具体进行 MapTask 处理
      if (useNewApi) {
          runNewMapper(job, splitMetaInfo, umbilical, reporter);
      } else {
          runOldMapper(job, splitMetaInfo, umbilical, reporter);
      }
      ......
  }
  ```

* `MapTask` 线程方法执行：`org.apache.hadoop.mapred.MapTask#runNewMapper`

  ```java
  private <INKEY,INVALUE,OUTKEY,OUTVALUE>
      void runNewMapper(final JobConf job,
                        final TaskSplitIndex splitIndex,
                        final TaskUmbilicalProtocol umbilical,
                        TaskReporter reporter
                       ) throws IOException, ClassNotFoundException,
  InterruptedException {
      // 构建一个上下文对象，持有所有任务信息和任务配置信息
      org.apache.hadoop.mapreduce.TaskAttemptContext taskContext =
          new org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl(job, 
                                                                      getTaskID(),
                                                                      reporter);
      // 构建一个执行Mapper，就是自定义的Maper子类
      org.apache.hadoop.mapreduce.Mapper<INKEY,INVALUE,OUTKEY,OUTVALUE> mapper =
          (org.apache.hadoop.mapreduce.Mapper<INKEY,INVALUE,OUTKEY,OUTVALUE>)
          ReflectionUtils.newInstance(taskContext.getMapperClass(), job);
      // 取输入类，有自定义输入类为自定义，没有取默认 `TextInputFormat`
      org.apache.hadoop.mapreduce.InputFormat<INKEY,INVALUE> inputFormat =
          (org.apache.hadoop.mapreduce.InputFormat<INKEY,INVALUE>)
          ReflectionUtils.newInstance(taskContext.getInputFormatClass(), job);
      // 根据提交任务时写到临时文件的分片信息，进行分区构建
      // 此时是多线程下的其中一个线程，只取对应的分片信息
      // file:/tmp/hadoop/mapred/staging/zhangpanjing1855014182/.staging/job_local1855014182_0001/job.split
      org.apache.hadoop.mapreduce.InputSplit split = null;
      split = getSplitDetails(new Path(splitIndex.getSplitLocation()),
                              splitIndex.getStartOffset());
      LOG.info("Processing split: " + split);
  	// 构建 RecordReader 类的包装类
      // 内部 real 属性为真是的 RecordReader 类对象
      // 通过 inputFormat.createRecordReader(split, taskContext) 获取
      org.apache.hadoop.mapreduce.RecordReader<INKEY,INVALUE> input =
          new NewTrackingRecordReader<INKEY,INVALUE>
          (split, inputFormat, reporter, taskContext);
      job.setBoolean(JobContext.SKIP_RECORDS, isSkipping());
      
      // 通过 ReduceTask 数量,构造 Output, 如果数量为0, 则不会走Reduce阶段
      org.apache.hadoop.mapreduce.RecordWriter output = null;
      if (job.getNumReduceTasks() == 0) {
          output = 
              new NewDirectOutputCollector(taskContext, job, umbilical, reporter);
      } else {
          // 内部根据 ReduceTask 数量设置了分片数量
          // 如果 ReduceTask 为1,则没有分片概念
          // 如果大于1, 则分片数量与 ReduceTask 数量相同
          output = new NewOutputCollector(taskContext, job, umbilical, reporter);
      }
  
      // 参数封装, 等待后续具体执行
      org.apache.hadoop.mapreduce.MapContext<INKEY, INVALUE, OUTKEY, OUTVALUE> 
          mapContext = 
          new MapContextImpl<INKEY, INVALUE, OUTKEY, OUTVALUE>(job, getTaskID(), 
                input, output, committer, reporter, split);
      org.apache.hadoop.mapreduce.Mapper<INKEY,INVALUE,OUTKEY,OUTVALUE>.Context 
          mapperContext = 
          new WrappedMapper<INKEY, INVALUE, OUTKEY, OUTVALUE>().getMapContext(
          mapContext);
      try {
          // 执行 RecordReader 的初始化方法
          // 在自定义 InputFormat 时,会重写该方法
          input.initialize(split, mapperContext);
          // MapTask 核心逻辑部分
          mapper.run(mapperContext);
          ......
          // MapTask 执行完毕, 数据已经全部写到缓冲区
          // 此时写出完成, 需要溢出文件
          output.close(mapperContext);
          output = null;
      } finally {
          closeQuietly(input);
          closeQuietly(output, mapperContext);
      }
  }
  ```

* `MapTask` 核心逻辑：`org.apache.hadoop.mapreduce.Mapper#run`

  ```java
  public void run(Context context) throws IOException, InterruptedException {
      // 可进行执行前数据初始化
      setup(context);
      try {
          // Mapper一次读取一组数据
          // context 最终会调用 RecordReader.nextKeyValue() 方法
          while (context.nextKeyValue()) {
              // 调用自定义 Mapper.map() 方法，写出数据
              map(context.getCurrentKey(), context.getCurrentValue(), context);
          }
      } finally {
          cleanup(context);
      }
  }
  ```

  * `Mapper.map(..)` 方法内部的 `context.write(..)` 方法会最终调用到：`org.apache.hadoop.mapred.MapTask.NewOutputCollector#write`，该方法内部是 `Collect` 阶段的主要逻辑

    ```java
    public void write(K key, V value) throws IOException, InterruptedException {
        // 收集收据， 即收集数据到环形缓冲区中
        // partitioner.getPartition(key, value, partitions)：获取分区号
        // 在自定义分区时，会重写改方法，在此处调用
        collector.collect(key, value,
                          partitioner.getPartition(key, value, partitions));
    }
    ```

* `Collect` 阶段：`org.apache.hadoop.mapred.MapTask.MapOutputBuffer#collect`，写数据到环形缓冲区

  ```java
  public synchronized void collect(K key, V value, final int partition
                                  ) throws IOException {
      // 
      ......
      // 分元数据和索引数据写数据到缓冲区
      kvmeta.put(kvindex + PARTITION, partition);
      kvmeta.put(kvindex + KEYSTART, keystart);
      kvmeta.put(kvindex + VALSTART, valstart);
      kvmeta.put(kvindex + VALLEN, distanceTo(valstart, valend));
      // advance kvindex
      kvindex = (kvindex - NMETA + kvmeta.capacity()) % kvmeta.capacity();
  }
  ```

* `Spill` 溢写阶段：`Mapper` 写出缓冲区完成后，对缓冲区内容溢写到文件，在溢写前需要数据进行快速排序

  `org.apache.hadoop.mapred.MapTask.NewOutputCollector#close`

  ```java
  public void close(TaskAttemptContext context
                   ) throws IOException,InterruptedException {
      try {
          // 刷新缓冲区
          collector.flush();
      } catch (ClassNotFoundException cnf) {
          throw new IOException("can't find class ", cnf);
      }
      collector.close();
  }
  ```

  ```java
  public void flush() throws IOException, ClassNotFoundException,
  InterruptedException {
      ......
      // 排序并且溢写
      sortAndSpill();
      ......
      // 合并溢写文件
      mergeParts();
      ......
  }
  ```

  * 排序并溢写：`org.apache.hadoop.mapred.MapTask.MapOutputBuffer#sortAndSpill`

    ```java
    private void sortAndSpill() throws IOException, ClassNotFoundException,
    InterruptedException {
        ......
        // 根据分区创建一个溢写输出的临时文件
        // /tmp/hadoop-zhangpanjing/mapred/local/localRunner/zhangpanjing/jobcache/job_local984617484_0001/attempt_local984617484_0001_m_000000_0/output/spill0.out
        final SpillRecord spillRec = new SpillRecord(partitions);
        final Path filename =
            mapOutputFile.getSpillFileForWrite(numSpills, size);
        out = rfs.create(filename);
    	......
        // 单文件溢出的快速排序
        sorter.sort(MapOutputBuffer.this, mstart, mend, reporter);
        ......
        // 按分区追加文件到 spill0.out 中
        for (int i = 0; i < partitions; ++i) {
            long segmentStart = out.getPos();
            partitionOut = CryptoUtils.wrapIfNecessary(job, out, false);
            writer = new Writer<K, V>(job, partitionOut, keyClass, valClass, codec, spilledRecordsCounter);
            ......
            // `MapTask` 分区数据是共存在一个文件下的, 即 spill0.out,通过不同的区间隔离
            // 在写数据时, 需要确定数据偏移量然后追加写入
            if (combinerRunner == null) {
                DataInputBuffer key = new DataInputBuffer();
                while (spindex < mend &&
                       kvmeta.get(offsetFor(spindex % maxRec) + PARTITION) == i) {
                    final int kvoff = offsetFor(spindex % maxRec);
                    int keystart = kvmeta.get(kvoff + KEYSTART);
                    int valstart = kvmeta.get(kvoff + VALSTART);
                    key.reset(kvbuffer, keystart, valstart - keystart);
                    getVBytesForOffset(kvoff, value);
                    writer.append(key, value);
                    ++spindex;
                }
            }
        }
    }
    ```

  * 合并溢写文件：`org.apache.hadoop.mapred.MapTask.MapOutputBuffer#mergeParts`；合并溢写文件，通过归并排序对所有溢写文件进行整合，最终输出一个大文件

    ```java
    private void mergeParts() throws IOException, InterruptedException,  ClassNotFoundException {
        ......
        // 取切片文件路径，取所有溢出文件路径和文件大小
        long finalOutFileSize = 0;
        long finalIndexFileSize = 0;
        final Path[] filename = new Path[numSpills];
        final TaskAttemptID mapId = getTaskID();
        for(int i = 0; i < numSpills; i++) {
            filename[i] = mapOutputFile.getSpillFile(i);
            finalOutFileSize += rfs.getFileStatus(filename[i]).getLen();
        }
        // 溢出文件已经全合并，目前文件就是最终文件
        if (numSpills == 1) { 
            // 修改溢出文件名
            // /spill0.out -> file.out
            sameVolRename(filename[0],
                          mapOutputFile.getOutputFileForWriteInVolume(filename[0]));
            // 写出一个索引文件
            // /tmp/hadoop-zhangpanjing/mapred/local/localRunner/zhangpanjing/jobcache/job_local1513979178_0001/attempt_local1513979178_0001_m_000000_0/output/file.out.index
            if (indexCacheList.size() == 0) {
                sameVolRename(mapOutputFile.getSpillIndexFile(0),
                              mapOutputFile.getOutputIndexFileForWriteInVolume(filename[0]));
            } else {
                indexCacheList.get(0).writeToFile(
                    mapOutputFile.getOutputIndexFileForWriteInVolume(filename[0]), job);
            }
            sortPhase.complete();
            return;
        }
        .......
    }
    ```

* 执行完成后，`MapTask` 线程执行完成，在 `org.apache.hadoop.mapred.LocalJobRunner.Job#run` 方法中会继续往下执行，继续执行 `TaskReduce` 

### 6.5.5，`ReduceTask` 源码分析

* 接上一步，`MapTask` 执行完成后，继续执行 `ReduceTask` 功能，`ReduceTask` 具体线程类为 `org.apache.hadoop.mapred.LocalJobRunner.Job.ReduceTaskRunnable`

*  `org.apache.hadoop.mapred.ReduceTask#run`

  ```java
  // 核心方法大概可分为三个部分
  public void run(JobConf job, final TaskUmbilicalProtocol umbilical) throws IOException, InterruptedException, ClassNotFoundException {
      ......
      // 初始化
      shuffleConsumerPlugin.init(shuffleContext);
      // Copy & Merge & Sort 阶段
      rIter = shuffleConsumerPlugin.run();
      ......
      // Reduce阶段，执行 ReducerTask 核心方法
      runNewReducer(job, umbilical, reporter, rIter, comparator, keyClass, valueClass);
  }
  ```

* 初始化方法：`org.apache.hadoop.mapreduce.task.reduce.Shuffle#init`

  ```java
  public void init(ShuffleConsumerPlugin.Context context) {
      ......
  	// 在这个构造方法中，获取到了 MapTask 数量
      scheduler = new ShuffleSchedulerImpl<K, V>(jobConf, taskStatus, reduceId, this, copyPhase, context.getShuffledMapsCounter(), context.getReduceShuffleBytes(), context.getFailedShuffleCounter());
      // 在这个构造方法中，对 ReduceTask 合并时基于内存和磁盘的方式进行初始化
      merger = createMergeManager(context);
  }
  ```

* Copy & Merge & Sort 方法：`org.apache.hadoop.mapreduce.task.reduce.Shuffle#run`

  ```java
  public RawKeyValueIterator run() throws IOException, InterruptedException 
      ......
      // 先开一个线程， 处理 MapTask相关事情，没懂
      // Start the map-completion events fetcher thread
      final EventFetcher<K,V> eventFetcher = new EventFetcher<K,V>(reduceId, umbilical, scheduler, this, maxEventsToFetch);
      eventFetcher.start();
      ......
      // 本地方式，构造本地的拉取数据方式，从本地文件系统拉取 MapTask 阶段生成数据
      // Copy阶段，从 MapTask 拷贝文件到 ReduceTask
      if (isLocal) {
          fetchers[0] = new LocalFetcher<K, V>(jobConf, reduceId, scheduler, merger, reporter, metrics, this, reduceTask.getShuffleSecret(), localMapFiles);
          fetchers[0].start();
      }
  	......
      // Sort阶段：对 MapTask 拷贝的文件进行合并和排序
      // 这里面没看懂
      kvIter = merger.close();
  	......
  }
  ```

  * Copy阶段：`org.apache.hadoop.mapreduce.task.reduce.LocalFetcher#run`

    ```java
    public void run() {
        // MapTask 阶段写出的溢出文件
        // attempt_local1865528496_0001_m_000001_0
        Set<TaskAttemptID> maps = new HashSet<TaskAttemptID>();
        for (TaskAttemptID map : localMapFiles.keySet()) {
            maps.add(map);
        }
        ......
    	// 数据拷贝
        doCopy(maps);
        // 执行完成后，数据拷贝成功，MapTask 阶段的所有数据全部读取
        ......
    }
    ```

    * `org.apache.hadoop.mapreduce.task.reduce.LocalFetcher#copyMapOutput`

    ```java
    private boolean copyMapOutput(TaskAttemptID mapTaskId) throws IOException {
        // 取 MapTask 阶段的数据文件和索引文件
        Path mapOutputFileName = localMapFiles.get(mapTaskId).getOutputFile();
        Path indexFileName = mapOutputFileName.suffix(".index");
        ......
    	// 读文件数据到内存中
        FileSystem localFs = FileSystem.getLocal(job).getRaw();
        FSDataInputStream inStream = localFs.open(mapOutputFileName);
        try {
            inStream = CryptoUtils.wrapIfNecessary(job, inStream);
            inStream.seek(ir.startOffset + CryptoUtils.cryptoPadding(job));
            mapOutput.shuffle(LOCALHOST, inStream, compressedLength,
                              decompressedLength, metrics, reporter);
        } finally {
            IOUtils.cleanupWithLogger(LOG, inStream);
        }
    
        scheduler.copySucceeded(mapTaskId, LOCALHOST, compressedLength, 0, 0,
                                mapOutput);
        return true; // successful fetch.
    }
    ```

  * Merge & Sort 阶段：

    * `org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl#close`

* 执行 ReducerTask 核心方法：`org.apache.hadoop.mapred.ReduceTask#runNewReducer`

  ```java
  private <INKEY,INVALUE,OUTKEY,OUTVALUE> void runNewReducer(JobConf job, final TaskUmbilicalProtocol umbilical, final TaskReporter reporter, RawKeyValueIterator rIter, RawComparator<INKEY> comparator, Class<INKEY> keyClass, Class<INVALUE> valueClass) throws IOException,InterruptedException, 
  ClassNotFoundException {
      // make a task context so we can get the classes
      org.apache.hadoop.mapreduce.TaskAttemptContext taskContext = new org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl(job, getTaskID(), reporter);
      // 这个是自定义的Reducer
      org.apache.hadoop.mapreduce.Reducer<INKEY,INVALUE,OUTKEY,OUTVALUE> reducer =
          (org.apache.hadoop.mapreduce.Reducer<INKEY,INVALUE,OUTKEY,OUTVALUE>)
          ReflectionUtils.newInstance(taskContext.getReducerClass(), job);
      // 构建 ReduceTask 阶段的临时输出文件夹
      org.apache.hadoop.mapreduce.RecordWriter<OUTKEY,OUTVALUE> trackedRW = 
          new NewTrackingRecordWriter<OUTKEY, OUTVALUE>(this, taskContext);
      job.setBoolean("mapred.skip.on", isSkipping());
      job.setBoolean(JobContext.SKIP_RECORDS, isSkipping());
      org.apache.hadoop.mapreduce.Reducer.Context 
          reducerContext = createReduceContext(reducer, job, getTaskID(), rIter, reduceInputKeyCounter, reduceInputValueCounter, trackedRW, committer, reporter, comparator, keyClass, valueClass);
      try {
          // 具体方法
          reducer.run(reducerContext);
      } finally {
          trackedRW.close(reducerContext);
      }
  }
  ```

  * `org.apache.hadoop.mapreduce.Reducer#run`

    ```java
    public void run(Context context) throws IOException, InterruptedException {
        setup(context);
        try {
            // 按 Key 分组取数据
            while (context.nextKey()) {
                // 传递到自定义 Reduce 中执行
                reduce(context.getCurrentKey(), context.getValues(), context);
                // If a back up store is used, reset it
                Iterator<VALUEIN> iter = context.getValues().iterator();
                if(iter instanceof ReduceContext.ValueIterator) {
                    ((ReduceContext.ValueIterator<VALUEIN>)iter).resetBackupStore();        
                }
            }
        } finally {
            cleanup(context);
        }
    }
    ```

* `OutputFormat`：写出阶段

  * `org.apache.hadoop.mapreduce.lib.reduce.WrappedReducer.Context#write`

  * `org.apache.hadoop.mapreduce.task.TaskInputOutputContextImpl#write`

  * `org.apache.hadoop.mapred.ReduceTask.NewTrackingRecordWriter#write`

    ```java
    public void write(K key, V value) throws IOException, InterruptedException {
        long bytesOutPrev = getOutputBytes(fsStats);
        // real 表示实际执行的 OutputFormat 子类，
        // 如果自定义输出类，则此处为自定义类
        real.write(key,value);
        long bytesOutCurr = getOutputBytes(fsStats);
        fileOutputByteCounter.increment(bytesOutCurr - bytesOutPrev);
        outputRecordCounter.increment(1);
    }
    ```

  * `org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.LineRecordWriter#write`

    ```java
    public synchronized void write(K key, V value)
        throws IOException {
    
        boolean nullKey = key == null || key instanceof NullWritable;
        boolean nullValue = value == null || value instanceof NullWritable;
        if (nullKey && nullValue) {
            return;
        }
        if (!nullKey) {
            // 写出Key
            writeObject(key);
        }
        if (!(nullKey || nullValue)) {
            out.write(keyValueSeparator);
        }
        if (!nullValue) {
            // 写出Value
            writeObject(value);
        }
        out.write(NEWLINE);
    }
    ```

* 清除临时数据，提交JOB：`org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter#commitJob`

## 6.6，`Join` 合并

### 6.6.1，`Reduce Join`

> 类似于多表关联，在多组数据处理时，以一个统一的字段做为 `Key` 对数据行简历映射，并通过 `Mapper` 写出，在 `Reduce` 阶段，按 `Key` 进行分数进行统一处理，在这组数据出，可进行最终的目标数据识别

1. 需求

   ![1628845659001](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628845659001.png)

   * 订单表：订单商品和商品数量
   * 商品表：商品名称
   * 订单表和商品表通过商品ID进行关联，最终输出订单号，商品名称，商品数量

2. 需求分析

   ![1628845733988](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628845733988.png)

3. 代码实现

   * `MyBean`：定义实体类，包括订单ID，商品数量，商品名称，即区分表的字段 `tableName`，因为需要作为 `Value` 输出，实现 `Writeable` 接口：

     ```java
     package com.hadoop.mapreduce.reducejoin;
     
     import org.apache.hadoop.io.Writable;
     
     import java.io.DataInput;
     import java.io.DataOutput;
     import java.io.IOException;
     
     /**
      * 自定义输出类
      */
     public class MyBean implements Writable {
     
         private String orderId;
     
         private Integer productCount;
     
         private String productName;
     
         private String tableName;
     
         public String getOrderId() {
             return orderId;
         }
     
         public void setOrderId(String orderId) {
             this.orderId = orderId;
         }
     
         public Integer getProductCount() {
             return productCount;
         }
     
         public void setProductCount(Integer productCount) {
             this.productCount = productCount;
         }
     
         public String getProductName() {
             return productName;
         }
     
         public void setProductName(String productName) {
             this.productName = productName;
         }
     
         public String getTableName() {
             return tableName;
         }
     
         public void setTableName(String tableName) {
             this.tableName = tableName;
         }
     
         @Override
         public void write(DataOutput out) throws IOException {
             out.writeUTF(orderId);
             out.writeInt(productCount);
             out.writeUTF(productName);
             out.writeUTF(tableName);
         }
     
         @Override
         public void readFields(DataInput in) throws IOException {
             orderId = in.readUTF();
             productCount = in.readInt();
             productName = in.readUTF();
             tableName = in.readUTF();
         }
     
         /**
          * 重写 toString() 方法, 只输出重点字段
          * @return
          */
         @Override
         public String toString() {
             return "orderId=" + orderId + ",\t productCount=" + productCount + ",\t productName=" + productName;
         }
     
     }
     ```

   * `JoinMapper`：以 `productId` 作为统一的 `Key`，对数据进行数据，并通过表名称进行数据标记

     ```java
     package com.hadoop.mapreduce.reducejoin;
     
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Mapper;
     import org.apache.hadoop.mapreduce.lib.input.FileSplit;
     
     import java.io.IOException;
     
     /**
      * 自定义Mapper
      * 入参: 行号, 行数据
      * 出参: 商品ID, 自定义列表
      */
     public class JoinMapper extends Mapper<LongWritable, Text, Text, MyBean> {
     
         private String tableName = null;
     
         private Text outKey = new Text();
     
         /**
          * 数据初始化, 取表名称, 对应的表关键字
          * @param context
          * @throws IOException
          * @throws InterruptedException
          */
         @Override
         protected void setup(Context context) throws IOException, InterruptedException {
             super.setup(context);
             FileSplit fileSplit = (FileSplit) context.getInputSplit();
             tableName = fileSplit.getPath().getName();
         }
     
         @Override
         protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
             // 解析数据
             String productId = null;
             String orderId = null;
             String productName = null;
             Integer productCount = null;
             String[] strArr = value.toString().split("\t");
             if (tableName.contains("order")) {
                 // 订单表
                 orderId = strArr[0];
                 productId = strArr[1];
                 productCount = Integer.valueOf(strArr[2]);
             } else {
                 // 商品表
                 productId = strArr[0];
                 productName = strArr[1];
             }
             // 组合并写出数据
             MyBean myBean = new MyBean();
             myBean.setOrderId(null == orderId ? "" : orderId);
             myBean.setProductName(null == productName ? "" : productName);
             myBean.setProductCount(null == productCount ? 0 : productCount);
             myBean.setTableName(tableName);
             outKey.set(productId);
             context.write(outKey, myBean);
         }
     
     }
     ```

   * `JoinReduce`：以 `Key` 对数据进行组合，`Value` 为 `Key` 对应的一组数据，对该数据数据按照标识进行拆分，对拆分后的数据进行字段填充

     ```java
     package com.hadoop.mapreduce.reducejoin;
     
     import org.apache.commons.beanutils.BeanUtils;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Reducer;
     
     import java.io.IOException;
     import java.util.ArrayList;
     import java.util.HashMap;
     import java.util.List;
     import java.util.Map;
     
     /**
      * 自定义Reduce类
      * 入参: 与Mapper出参对应, 商品ID, 自定义Bean
      * 出参: 与上面一致
      */
     public class JoinReduce extends Reducer<Text, MyBean, Text, MyBean> {
     
         /**
          * 注意, 在Mapper中对两张表的数据, 以同一个key输出,
          * 所以, 在reduce中, value是两张表的数据在一起, 以tableName字段识别, 先需要对数据进行分离
          * @param key
          * @param values
          * @param context
          * @throws IOException
          * @throws InterruptedException
          */
         @Override
         protected void reduce(Text key, Iterable<MyBean> values, Context context) throws IOException, InterruptedException {
             // 数据分离
             List<MyBean> lstOrderData = new ArrayList<>();
             Map<String, String> productId2Name = new HashMap<>();
             for (MyBean value : values) {
                 // 因为 value 在进行数据填充时, 是值传递
                 // 如果不对数据进行拷贝, 会造成数据覆盖问题
                 MyBean copyBean = new MyBean();
                 try {
                     BeanUtils.copyProperties(copyBean, value);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 if (copyBean.getTableName().contains("order")) {
                     lstOrderData.add(copyBean);
                 } else {
                     productId2Name.put(key.toString(), copyBean.getProductName());
                 }
             }
             // 分离完毕, 进行数据处理
             for (MyBean currData : lstOrderData) {
                 currData.setProductName(productId2Name.get(key.toString()));
                 // 写数据
                 context.write(key, currData);
             }
         }
     
     }
     ```

   * `JoinDriver`：调度类

     ```java
     package com.hadoop.mapreduce.reducejoin;
     
     import org.apache.hadoop.conf.Configuration;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Job;
     import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
     import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
     
     public class JoinDriver {
     
         public static void main(String[] args) throws Exception {
             // 获取配置信息, 构建Job示例
             Configuration configuration = new Configuration();
             Job job = Job.getInstance(configuration);
             // 指定本程序的jar包路径
             job.setJarByClass(JoinDriver.class);
             // 关联 Mapper/Reduce 业务类
             job.setMapperClass(JoinMapper.class);
             job.setReducerClass(JoinReduce.class);
             // 指定Mapper输出的KV类型
             job.setMapOutputKeyClass(Text.class);
             job.setMapOutputValueClass(MyBean.class);
             // 指定Reduce输出的KV类型
             job.setOutputKeyClass(Text.class);
             job.setOutputValueClass(MyBean.class);
             // 指定job输入路径
             FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\join"));
             // 指定job输出路径
             FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
             // 工作
             job.waitForCompletion(true);
         }
     
     }
     ```

   * 结果：

     ![1628847773150](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1628847773150.png)

### 6.6.2，`Map Join`

1. 使用场景:

   > 适用于一张表很大，而另外一张表很小的情况；
   >
   > 以小表作为缓冲数据直接加载，通过 `MapTask` 读取大表数据进行合并

2. 优点：

   > <font color=red>在 `Reduce` 端处理过多的表，容易产生数据倾斜</font>
   >
   > 针对上面的问题，可以在 `Mapper` 端缓存多张表，提前处理相关业务逻辑，增加 `Mapper` 端业务，减少 `Reduce` 端数据压力，减少数据倾斜

3. 具体办法：采用 `DistributedCache`

   > * 在 `Driver` 类中添加缓存驱动，<font color=red>此处注意路径！！！</font>
   >
   >   ```java
   >   job.addCacheFile(new URI("file:///E:/hadoop/tmp.txt"));
   >   ```
   >
   > * 在 `Mapper` 的 `setUp(...)` 方法中读取缓存，进行缓存数据加载

#### 6.6.2.1，`MapJoin` 案例实操

* `Mapper` 类：

  ```java
  package com.hadoop.mapreduce.mapjoin;
  
  import com.hadoop.mapreduce.reducejoin.MyBean;
  import org.apache.hadoop.fs.FSDataInputStream;
  import org.apache.hadoop.fs.FileSystem;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.LongWritable;
  import org.apache.hadoop.io.NullWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Mapper;
  import org.apache.hadoop.mapreduce.lib.input.FileSplit;
  
  import java.io.BufferedReader;
  import java.io.IOException;
  import java.io.InputStreamReader;
  import java.net.URI;
  import java.util.HashMap;
  import java.util.Map;
  
  /**
   * 自定义Mapper
   * 入参: 行号, 行数据
   * 出参: 商品ID, 自定义列表
   */
  public class JoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
  
      private Text outKey = new Text();
  
      private Map<String, String> key2Name = new HashMap<>();
  
      /**
       * 数据初始化, 取表名称, 对应的表关键字
       * @param context
       * @throws IOException
       * @throws InterruptedException
       */
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
          super.setup(context);
          // 取预加载文件
          URI[] cacheFiles = context.getCacheFiles();
          Path path = new Path(cacheFiles[0]);
          FileSystem fileSystem = FileSystem.get(context.getConfiguration());
          FSDataInputStream inputStream = fileSystem.open(path);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
          String line = null;
          // 解析数据, 并对数据进行缓存
          while (null != (line = bufferedReader.readLine())) {
              String[] arr = line.split(" ", -1);
              key2Name.put(arr[0], arr[1]);
          }
      }
  
      @Override
      protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
          String line = value.toString();
          String[] arr = line.split(" ", -1);
          outKey.set(key2Name.get(arr[0]) + " " + arr[1]);
          context.write(outKey, NullWritable.get());
      }
  
  }
  ```

* `Driver` 类：

  ```java
  package com.hadoop.mapreduce.mapjoin;
  
  import org.apache.hadoop.conf.Configuration;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.NullWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.mapreduce.Job;
  import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
  import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
  
  import java.net.URI;
  
  public class JoinDriver {
  
      public static void main(String[] args) throws Exception {
          // 获取配置信息, 构建Job示例
          Configuration configuration = new Configuration();
          Job job = Job.getInstance(configuration);
          // 指定本程序的jar包路径
          job.setJarByClass(JoinDriver.class);
          // 关联 Mapper/Reduce 业务类
          job.setMapperClass(JoinMapper.class);
          // 指定Mapper输出的KV类型
          job.setMapOutputKeyClass(Text.class);
          job.setMapOutputValueClass(NullWritable.class);
          //设置map缓存路径
          job.addCacheFile(new URI("file:///E:/hadoop/tmp.txt"));
          // 只走map阶段, 不走reduce阶段
          job.setNumReduceTasks(0);
          // 指定job输入路径
          FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\mapjoin.txt"));
          // 指定job输出路径
          FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
          // 工作
          job.waitForCompletion(true);
      }
  
  }
  ```

## 6.7，数据清洗（ETL）

> ETL，是英文 `Extract-Transform-Load` 的缩写，用来描述将数据从来源端经过抽取（`Extract`）、转换（`Transform`）、加载（`Load`）至目的端的过程。ETL 一词较常用在数据仓库，但其对象并不限于数据仓库。
>
> 在运行核心业务 MapReduce 程序之前，往往要先对数据进行清洗，清理掉不符合用户要求的数据。<font color=red>清理的过程往往只需要运行 Mapper 程序，不需要运行 Reduce 程序。</font>

1. 需求

   > 去除输入文本文件中，行格式不符合手机号格式的字段

2. 代码分析

   > 在 `Mapper` 阶段读取每一行数据，并根据手机号进行正则匹配，符合则输出，不符合直接丢弃。

3. 代码实现

   * `Mapper` 代码

     ```java
     package com.hadoop.mapreduce.etl;
     
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.NullWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Mapper;
     
     import java.io.IOException;
     
     /**
      * ETL Mapper类
      */
     public class ETLMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
         
         @Override
         protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
             // 取行数据
             String line = value.toString();
             // 校验行数据
             String phoneRegex = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
             if (line.matches(phoneRegex)) {
                 // 匹配直接输出
                 context.write(value, NullWritable.get());
             }
         }
     
     }
     ```

   * `Driver` 代码

     ```java
     package com.hadoop.mapreduce.etl;
     
     import com.hadoop.mapreduce.inputformat.*;
     import org.apache.hadoop.conf.Configuration;
     import org.apache.hadoop.fs.Path;
     import org.apache.hadoop.io.LongWritable;
     import org.apache.hadoop.io.Text;
     import org.apache.hadoop.mapreduce.Job;
     import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
     import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
     
     import java.io.IOException;
     
     public class ETLDriver {
     
         public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
             // 1. 获取配置信息, 获取Job示例
             Configuration configuration = new Configuration();
             Job job = Job.getInstance(configuration);
             // 2. 指定本程序jar包所在的路径
             job.setJarByClass(ETLDriver.class);
             // 3. 关联Mapper/Reduce业务类
             job.setMapperClass(ETLMapper.class);
             // 4. 指定Mapper输出数据的KV类型
             job.setMapOutputKeyClass(LongWritable.class);
             job.setMapOutputValueClass(Text.class);
             // 5. 不需要Reduce 设置数量
             job.setNumReduceTasks(0);
             // 6. 指定Job输入原始数据的文件路径
             FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\etl.txt"));
             // 7. 指定Job输出结果数据的文件路径
             // 这一步需要保留，用于输出_SUCCESS信息
             FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
             // 8. 提交执行
             job.waitForCompletion(true);
         }
     
     }
     ```

## 6.8，数据压缩

### 6.8.1，概述

* 压缩的好处和坏处：
  * 压缩的优点：减少磁盘IO，减少磁盘存储空间
  * 压缩的缺点：增加CPU的开销
* 压缩原则：
  * 运算密集型的 `Job`，少用压缩
  * `IO` 密集型的 `Job`，多用压缩

### 6.8.2，MR支持的压缩编码

* 压缩算法对比介绍

  ![1632151862656](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1632151862656.png)

* 压缩性能比较

  ![1632151880448](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1632151880448.png)

### 6.8.3，压缩方式选择

* `GZip` 压缩
  * 优点：压缩率比较高
  * 缺点：不支持切片；压缩/解压速度一般
* `BZip2` 压缩
  * 优点：压缩率高；支持切片
  * 缺点：压缩/解压速度慢
* `LZO` 压缩
  * 优点：压缩/解压速度快，支持切片
  * 缺点：压缩率一般；<font color=red>想支持切片必须创建索引</font>
* `Snappy` 压缩
  * 优点：压缩/解压速度快
  * 缺点：不支持分片，压缩率一般；<font color=red>在 `Hadoop3.x` 中，需要搭配 CentOS7以上版本才能默认支持</font>

### 6.8.4，压缩位置选择

> 压缩可以在 `MapReduce` 的任意阶段进行，主要可以分为 `Mapper` 读阶段，`Mapper` 输出 `Reduce` 阶段，`Reduce` 输出阶段

![1632152768089](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1632152768089.png)

### 6.8.5，压缩参数配置

* `Hadoop` 引入多种编解码器，支持多种压缩算法

  ![1632152919912](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1632152919912.png)

* `Hadoop` 压缩配置参数

  ![1632152874112](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1632152874112.png)

### 6.8.6，压缩实操

* `Mapper` 输出端压缩

  > `Mapper` 输出压缩，在结果上没有任何压缩体现，压缩数据输出到 `Reduce`，`Reduce` 会进行数据解压，用户无感知

  ```java
  package com.hadoop.mapreduce.compress;
  
  import org.apache.hadoop.conf.Configuration;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.IntWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.io.compress.BZip2Codec;
  import org.apache.hadoop.io.compress.CompressionCodec;
  import org.apache.hadoop.mapreduce.Job;
  import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
  import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
  
  public class CompressDriver {
  
      public static void main(String[] args) throws Exception {
          // 1. 获取配置信息, 获取Job示例
          Configuration configuration = new Configuration();
          // 启用 mapper 端输出压缩
          configuration.setBoolean("mapreduce.map.output.compress", true);
          // 设置压缩方式
          // 最后一个参数表示接口
          configuration.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);
          Job job = Job.getInstance(configuration);
          // 2. 指定本程序jar包所在的路径
          job.setJarByClass(CompressDriver.class);
          // 3. 关联Mapper/Reduce业务类
          job.setMapperClass(WordCountMapper.class);
          job.setReducerClass(WordCountReduce.class);
          // 4. 指定Mapper输出数据的KV类型
          job.setMapOutputKeyClass(Text.class);
          job.setMapOutputValueClass(IntWritable.class);
          // 5. 指定Reduce输出数据的KV类型
          job.setOutputKeyClass(Text.class);
          job.setOutputValueClass(IntWritable.class);
          // 6. 指定Job输入原始数据的文件路径
           FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\123.txt"));
          // 7. 指定Job输出结果数据的文件路径
          FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
          // 8. 提交执行
          job.waitForCompletion(true);
      }
  
  }
  ```

* `Reduce` 输出端解压

  > `Reduce` 输出是直接输出到用户端，对用户有直观感受，用户会接收到一个压缩后的结果数据包

  ```java
  package com.hadoop.mapreduce.compress;
  
  import org.apache.hadoop.conf.Configuration;
  import org.apache.hadoop.fs.Path;
  import org.apache.hadoop.io.IntWritable;
  import org.apache.hadoop.io.Text;
  import org.apache.hadoop.io.compress.BZip2Codec;
  import org.apache.hadoop.io.compress.CompressionCodec;
  import org.apache.hadoop.io.compress.DefaultCodec;
  import org.apache.hadoop.io.compress.GzipCodec;
  import org.apache.hadoop.mapreduce.Job;
  import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
  import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
  
  public class CompressDriver {
  
      public static void main(String[] args) throws Exception {
          // 1. 获取配置信息, 获取Job示例
          Configuration configuration = new Configuration();
          Job job = Job.getInstance(configuration);
          // 2. 指定本程序jar包所在的路径
          job.setJarByClass(CompressDriver.class);
          // 3. 关联Mapper/Reduce业务类
          job.setMapperClass(WordCountMapper.class);
          job.setReducerClass(WordCountReduce.class);
          // 4. 指定Mapper输出数据的KV类型
          job.setMapOutputKeyClass(Text.class);
          job.setMapOutputValueClass(IntWritable.class);
          // 5. 指定Reduce输出数据的KV类型
          job.setOutputKeyClass(Text.class);
          job.setOutputValueClass(IntWritable.class);
          // 6. 指定Job输入原始数据的文件路径
           FileInputFormat.setInputPaths(job, new Path("E:\\hadoop\\123.txt"));
          // 7. 指定Job输出结果数据的文件路径
          FileOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\selfout" + System.currentTimeMillis()));
  
          // Reduce输出压缩
          // 打开解压开发
          FileOutputFormat.setCompressOutput(job, true);
          // 解压方式
          FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
  //        FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
  //        FileOutputFormat.setOutputCompressorClass(job, DefaultCodec.class);
  
          // 8. 提交执行
          job.waitForCompletion(true);
      }
  
  }
  ```

  ![1632154466470](C:\Users\zhangpanjing\AppData\Roaming\Typora\typora-user-images\1632154466470.png)

# 7，Yarn