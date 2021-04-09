# 版本：Hadoop3.1.3

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
Hadoop102
Hadoop103
Hadoop104
```

##### 4.2.3.4.2，启动集群

* <font color=red>如果集群是第一次启动：</font>需要在 `NameNode` 节点上先格式化 `NameNode`，注意：格式化 `NameNode`，会产生新的集群 id，导致 `NameNode` 和 `DataNode` 的集群 id 不一致，集群找不到已往数据。如果集群在运行过程中报错，需要重新格式化 `NameNode` 的话，一定要先停 止 `Namenode` 和 `Datanode` 进程，并且要删除所有机器的 `data` 和 `logs` 目录，然后再进行格式化。初始化成功字样如下截图

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
  [root@Hadoop103 sbin]# ./start-yarn.sh
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

