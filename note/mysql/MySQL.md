# 1，MySQL高级

## 1.1，视图

* MySQL5.1版本后的新特性，是通过表动态生成数据

* 视图是一种虚拟的表，行和列的数据来自于定义视图的查询中使用的表，并且是在**使用视图时候动态生成的**，<font color=red>视图只保存SQL逻辑，不保存查询结构。也就是说每一次基于视图的数据查询，是实时的SQL语句查询</font>
* 应用场景：多个地方用到同样的查询语句；SQL语句较复杂，用视图进行封装

### 1.1.1，视图定义语句

#### 1.1.1.1，视图创建

```sql
CREATE VIEW view_name
AS 
QUERY SQL;
```

* `view_name`：表示视图名称
* `QUERY_SQL`：表示标准的查询语句
* 通过这种方式，视图即可创建完成，视图的列就是 `QUERY SQL` 的 `SELECT` 中定义的列

#### 1.1.1.2，视图修改

##### 1.1.1.2.1，`Merge` 方式修改

```sql
CREATE OR REPLACE VIEW view_name
AS
QUERY SQL；
```

* 这种方式类似于Merge语句，与视图创建语句基本一致，只是增加了 `REPLACE` 部分
* 当视图不存在时候，按指定语句创建该视图；当视图存在时，按指定语句修改该视图的执行语句

##### 1.1.1.2.2，`ALTER` 方式

```sql
ALTER VIEW view_name
AS 
QUERY SQL;
```

* Alter的视图修改方式与表修改方式完全一致，直接修改视图执行语句即可

#### 1.1.1.3，视图删除

```sql
DROP VIEW view_name1 (view_name2, view_name3);
```

* 视图删除与表删除语句基本一致，并且支持多删除

#### 1.1.1.5，视图查看

##### 1.1.1.5.1，查看视图结构

```sql
DESC view_name
```

![1608363366008](E:\gitrepository\study\note\image\MySQL\1608363366008.png)

* 按表结构形式展示视图的列结构，展示字段详细信息

##### 1.1.1.5.2，查看视图创建语句

```sql
SHOW CREATE VIEW view_name;
```

![1608363472607](E:\gitrepository\study\note\image\MySQL\1608363472607.png)

* 在 `CREATE VIEW` 字段中展示视图创建语句

### 1.1.2，定义操作语句

#### 1.1.2.1，视图使用_通过视图进行查询

```sql
SELECT * FORM view_name WHERE XXX
```

- 按表查询方式，直接查询视图即可，视图的列是视图创建时候，`SELECT` 语句指定的列

#### 1.1.2.2，视图数据操作

* <font color=red>视图理论上是可以进行数据更新和数据删除的，直接按照表数据操作语句（INSERT，UPDATE，DELETE）操作视图即可，视图操作成功后，会同步更新表数据</font>

* 但是如果构建视图的 `QUERY SQL` 语句中存在下列情况，则该视图不支持进行操作

  * 包含以下关键字的语句：分组函数、distinct、group by、having、union或者union all

    ```sql
    CREATE VIEW view_name
    AS
    SELECT DISTINCT(COLUMN) FROM TABLE;
    ```

  * 常量视图

    ```sql
    CREATE VIEW view_name
    AS
    SELECT '张三' AS NAME;
    ```

  * SELECT 中包含子查询

    ```sql
    CREATE VIEW view_name
    AS
    SELECT (SELECT COLUMN FROM TABLE) AS NAME
    ```

  * JOIN 语句

    ```sql
    CREATE VIEW view_name
    AS
    SELECT * FROM TABLE_A INNER JOIN TABLE_B
    ```

    * <font color=red>这种形式创建的视图，可以修改不能新增</font>

  * FROM 一个不能更新的视图（视图权限控制）

    ```sql
    -- 从上一步中取一个不能更新的视图，作为QUERY SQL，进行新视图创建
    CREATE VIEW view_name
    AS
    SELECT * FROM exists_view_name
    ```

  * WHERE 子句中的子查询应用了 FROM 子句中的表

    ```sql
    CREATE VIEW view_name
    AS
    SELECT * FROM table_name_a
    WHERE column IN (SELECT column FROM table_name_a WHERE xxx)
    ```

## 1.2，变量

### 1.2.1，变量分类

* 系统变量
  * 全局变量
  * 会话变量
* 自定义变量
  * 用户变量
  * 局部变量

### 1.2.2，系统变量

* 变量由系统提供，不是用户定义的，属于服务器层面的变量
* <font color=red>系统变量查询/操作，不加限制，默认为 `SESSION` 级别</font>

#### 1.2.2.1，查看系统变量

```sql
-- 查看系统变量
SHOW VARIABLES LIKE '%commit%';
-- 查看全局系统变量
SHOW GLOBAL VARIABLES LIKE '%commit%';
-- 查看会话系统变量
SHOW SESSION VARIABLES LIKE '%commit%';
-- 查询指定变量
SELECT @@[GLOBAL|SESSION].VARIABLES_NAME;
```

#### 1.2.2.2，系统变量赋值

```sql
-- 设置系统变量值
SET SYSTEM_VARIABLE_NAME = '';
-- 设置全局系统变量值
SET GLOBAL SYSTEM_VARIABLE_NAME = '';
-- 设置会话系统变量值
SET SESSION SYSTEM_VARIABLE_NAME = '';
-- 更新指定变量值
SET @@[GLOBAL|SESSION].VARIABLES_NAME = '';
```

### 1.2.3，自定义变量

#### 1.2.3.1，用户变量

* <font color=red>用户变量，顾名思义，只对操作用户有效，其他用户不可见</font>

##### 1.2.3.1.1，声明并初始化

```sql
-- MERGE方式，有则改，没则增
SET @user_variable_name = '';
SET @user_variable_name := '';
SELECT @user_variable_name := '';
```

##### 1.2.3.1.2，赋值

```sql
-- 直接修改值
SET @user_variable_name = '';
SET @user_variable_name := '';
SELECT @user_variable_name := '';
-- 通过表数据动态修改值
SELECT COLUMN INTO @user_variable_name FROM TABLE;
```

##### 1.2.3.1.4，使用

```sql
SELECT @user_variable_name;
```

##### 1.2.3.1.5，示例

```sql
-- 定义成员变量a和b
SET @a := 1;
SET @b = 2;
-- 定义成员变量sum是a和b的和
SELECT @sum := @a + @b;
-- 查看成员变量
SELECT @sum;
```

#### 1.2.3.2，局部变量

* <font color=red>局部变量，只在局部有效，即定义它的 BEGION ... END 中有效，必须定义在 BEGIN ... END 的第一句话</font>

##### 1.2.3.2.1，声明

```sql
-- 声明
DECLARE variable_name type;
DECLARE variable_name type DEFAULT value;
```

##### 1.2.3.2.2，赋值

```sql
-- 直接修改值
-- 注意set方式不加@，SELECT方式需要加@
SET variable_name = '';
SET variable_name := '';
SELECT @variable_name := '';
-- 通过表数据动态修改值
-- 注意不用加@
SELECT COLUMN INTO variable_name FROM TABLE;
```

##### 1.2.3.2.3，使用

```sql
SELECT variable_name,variable_name2,variable_name3;
```

#### 1.2.3.3，用户变量和局部变量比较

|          | 作用域          | 定义和使用位置                  | 语法                                    |
| -------- | --------------- | ------------------------------- | --------------------------------------- |
| 用户变量 | 当前会话        | 会话任何地方                    | [1.2.3.1，用户变量](#1.2.3.1，用户变量) |
| 局部变量 | BEGIN ... END中 | 只在BEGIN...END中，且为第一句话 | [1.2.3.2，局部变量](#1.2.3.2，局部变量) |

## 1.3，存储过程

### 1.3.1，基本介绍

* 存储过程和函数，类似于Java中的方法
* 存储过程是一组预先编译好的SQL语句集合，可以理解为批处理语句
* 存储过程进行逻辑处理，具有以下优点：
  * 提高代码的重用性
  * 简化业务操作
  * 减少编译次数并且减少与数据库的连接次数，提高效率

### 1.3.2，基本语法

```sql
-- 定义存储过程结束标志
DELIMITER $
-- 定义创建存储过程语句
CREATE PROCEDURE name(参数列表)
BEGIN
	执行语句部分：一系列SQL语句
END $

-- 存储过程定义完成后，执行存储过程，NAVICAT不能执行，需要到DOS窗口执行
-- 注意结束符已经被重新定义
CALL name()$
```

* 结束标志：SQL的结束标志为 `;`，但是`BEGIN ... END` 之间是一系列SQL语句，系列SQL语句之间用 `;` 隔开，所以 `;` 已经被占用，此时需要定义一个结束符号，由 `DELIMITER` 进行定义，并标注在 `END` 之后，表示存储过程语句结束，<font color=red>注意：定义之后，表示SQL语句的结束符为定义的语句，不是原来的 `;`，如果需要变回来，需要重定义一次</font>
* 参数列表由三部分组成：参数模式、参数名称、参数类型。其实参数模式分为三种：`IN`，`OUT`，`INOUT`；参数名称是形参；参数类型是要求具体类型：
  * `IN`：表示该参数是入参，已经被赋值，不需要通过该参数返回值
  * `OUT`：表示该参数是出参，没有被赋值，需要在存储过程中被赋值
  * `INOUT`：表示该参数是出入参，已经被赋值，且需要在存储过程中需要重新复制返回
* `BEGIN ... END` 之间是具体实现存储过程逻辑的部分，由一系列SQL语句组成

### 1.3.3，存储过程示例

#### 1.3.3.1，数据准备

* 随便创建两张表

  ```sql
  -- 男子表，带女子外键
  CREATE TABLE `boy` (
    `ID` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(20) DEFAULT NULL,
    `girl_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`ID`)
  ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
  -- 女子表
  CREATE TABLE `girl` (
    `ID` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`ID`)
  ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8
  ```

* 通过存储过程随便入库两组数据

  ```sql
  -- 入库女数据
  DELIMITER $
  CREATE PROCEDURE INSERT_GIRL4()
  BEGIN
  INSERT INTO GIRL(ID, NAME) VALUES('3', '女1'), ('4', '女2');
  END $
  -- 入库男数据
  CREATE PROCEDURE INSERT_BOY1()
  BEGIN
  INSERT INTO BOY(NAME,GIRL_ID) VALUES('男1',3), ('男2',4);
  END $
  
  -- 执行存储过程
  CALL INSERT_GIRL4();
  CALL INSERT_BOY1();
  ```

#### 1.3.3.2，空参存储过程_查询数量

```sql
CREATE PROCEDURE QUERY_COUNT()
BEGIN
	-- 定义局部变量，男子数量
	DECLARE boyCount INT;
	-- 定义局部变量，女子数量
	DECLARE girlCount INT;
	-- 查询男子数量并进行赋值
	SELECT COUNT(1) INTO boyCount FROM BOY;
	-- 查询女子数量进行赋值
	SELECT COUNT(1) INTO girlCount FROM GIRL;
	-- 通过局部变量展示男子和女子数量
	SELECT boyCount, girlCount;
END $

-- 执行存储过程
CALL QUERY_COUNT()$
```

#### 1.3.3.3，入参存储过程_查询3号女子对应的男子姓名

```sql
-- 入参，女子ID，类型是int
CREATE PROCEDURE QUERY_BOY_NAME(IN girlId INT)
BEGIN
	-- 局部变量，进行名称存储
	DECLARE boyName VARCHAR(32);
	-- 查询女子对应的男子名称
	SELECT NAME INTO boyName FROM BOY WHERE GIRL_ID = girlId;
	-- 展示名称
	SELECT boyName;
END $

-- 执行存储过程
-- 3 表示入参
CALL QUERY_BOY_NAME(3)$
```

#### 1.3.3.4，出参存储过程_返回3号女子对应的男子姓名

```sql
-- girlId：入参，女子ID
-- boyName：出参，男子姓名
CREATE PROCEDURE RETURN_BOY_NAME(IN girlId INT, OUT boyName VARCHAR(32))
BEGIN
	-- 查询男子姓名并进行赋值
	-- 返回参数到此为止，不需要再进行return之类操作
	SELECT NAME INTO boyName FROM BOY WHERE GIRL_ID = girlId;
END $

-- 执行存储过程
-- 3：表示入参的女子ID
-- @boyName：直接定义一个用户变量进行传参，该变量不用提前定义，直接如此传递即可生成
CALL RETURN_BOY_NAME(3, @boyName)$

-- 查看返回值
-- 直接查看传递的用户变量即可
SELECT @boyName$
```

#### 1.3.3.4，出入参存储过程_传递女子ID，并用该参数返回对应的男子ID

```sql
-- girlId：出入参，出参入参都通过这一个参数表示，用INOUT修饰
CREATE PROCEDURE QUERY_BOY_ID(INOUT girlId INT)
BEGIN
	-- 将返回结果直接赋值到该参数
	SELECT ID INTO girlId FROM BOY WHERE GIRL_ID = girlId;
END $

-- 执行存储过程
-- 因为入参是变量需要复制，且返回参需要通过该变量返回，所以需要提前定义用户变量作为入参
SET @girlId=3$
CALL QUERY_BOY_ID(@girlId)$
-- 执行完成后，查询结果
SELECT @girlId$
```

### 1.3.4，存储过程删除

```sql
-- 存储过程一次只能删除一个
DROP PROCEDURE name;
```

### 1.3.5，存储过程语句查看

```sql
-- 存储过程列表
-- 修改dbName进行查询即可
SELECT * FROM MYSQL.PROC WHERE DB = '{dbName}' and TYPE = 'PROCEDURE'
-- 指定存储过程语句
SHOW CREATE PROCEDURE name;
```

## 1.4，函数

### 1.4.1，基本介绍

* 函数与存储过程基本相同
  * 是一组预编译好的SQL语句的集合，可以理解成批处理语句
  * 能够提高代码重用性
  * 能够减少操作
  * 能够减少编译次数并能够减少和服务器的连接次数，提高效率
* 同时函数和存储过程也有一些区别：
  * 存储过程可以有0个返回，也可以有多个返回，适用于数据批量插入、批量更新
  * 函数有且仅有一个返回，适合于数据处理并返回一个数据

### 1.4.2，基本语法

```sql
-- 创建语法
CREATE FUNCTION function_name(参数列表) RETURNS 返回值类型
BEGIN
	函数体（一系列SQL语句）
END

-- 调用语法
SELECT 函数名(参数列表)
```

* 参数列表：包含两部分：参数名，参数类型
* 函数体：肯定存在 `return` 语句，如果没有会报错
* 函数体中如果只有一句话，则 `BEGIN ... END` 语句可以省略
* 需要使用 `DELIMITER` 语句设置结束标记

### 1.4.3，函数示例

#### 1.4.3.1，无参函数_返回男子表数量

```sql
-- 创建函数
-- 无参函数，参数列表为空
-- 定义返回类型为INT，返回数量
CREATE FUNCTION QUERY_COUNT() RETURNS INT
BEGIN
	-- 需要返回数据，定义一个局部变量
	-- 注意：此处可以定义用户变量
	DECLARE count INT;
	-- 查询数据并直接对局部变量赋值
	SELECT COUNT(1) INTO count FROM BOY;
	-- 返回该局部变量
	-- 注意：函数一定有return语句
	return count;
END $

-- 执行函数
SELECT QUERY_COUNT()$
```

#### 1.4.3.2，有参函数_返回指定男子ID的名称

```sql
-- 创建函数
-- 带参函数，存在一个入参
-- 返回类型为VARCHAR，返回名称
CREATE FUNCTION QUERY_BOY_NAME(id INT) RETURNS VARCHAR(32)
BEGIN
	-- 通过用户变量定义返回名称
	SET @name = '';
	-- 查询名称并直接赋值
	SELECT NAME INTO @name FROM BOY WHERE BOY.ID = id;
	-- 返回名称
	return @name;
END $

-- 执行函数
-- 1 表示入参的ID
SELECT QUERY_BOY_NAME(1)$
```

### 1.4.4，函数删除

```sql
DROP FUNCTION function_name;
```

### 1.4.5，函数语句查看

```sql
-- 查询函数的创建语句
SHOW CEREATE FUNCTION function_name;
```

## 1.5，流程控制结构

### 1.5.1，分支结构

#### 1.5.1.1，`if` 函数

```sql
-- 与Java的三元运算符基本一致
-- 表达式1成立，返回表达式2，不成立，返回表达式3
IF(表达式1, 表达式2, 表达式3)
```

#### 1.5.1.2，`case` 结构

* 基本语法

```sql
-- SELECT 中写法
-- 可以在任何地方使用，即 `BEGIN ... END` 内部或者外部都可以
-- 写法1
CASE 参数
	WHEN 数值匹配 THEN 返回值
	ELSE 返回值
END
-- 写法2
CASE
	WHEN 条件语句 THEN 返回值
	ELSE 返回值
END

-- 存储过程或者函数中写法
-- 只能在 `BEGIN ... END` 内部使用
-- 存储过程或者函数中, 如果在SELECT子句中使用，可依旧使用上面的写法
-- 存储过程和函数中，CASE语句和作为分支语句单独执行，THEN后可跟随执行语句，跟随执行语句时，对应写法如下：
-- 写法1
CASE 参数
	WHEN 数值匹配 THEN 语句;
	ELSE 语句;
END CASE;
-- 写法2
CASE
	WHEN 条件语句 THEN 语句;
	ELSE 语句;
END CASE;
```
* 语法示例

```sql
-- CASE语句在存储过程中的写法
-- 写法1
CREATE PROCEDURE TEST_CASE(in num INT)
BEGIN
	CASE num
	WHEN 0 THEN SELECT '男子' FROM BOY;
	WHEN 1 THEN SELECT '女子' FROM GIRL;
	ELSE SELECT '无';
	END CASE;
END $
-- 写法2
CREATE PROCEDURE TEST_CASE_1(in num INT)
BEGIN
	CASE
	WHEN num=0 THEN SELECT '男子' FROM BOY;
	WHEN num=1 THEN SELECT '女子' FROM GIRL;
	ELSE SELECT '无';
	END CASE;
END $

-- CASE语句在函数中的写法
-- 写法1
CREATE FUNCTION TEST_CASE(num INT) RETURNS VARCHAR(20)
BEGIN
	DECLARE name VARCHAR(20);
	CASE num
	WHEN 0 THEN SELECT '男子' INTO name FROM BOY LIMIT 1;
	WHEN 1 THEN SELECT '女子' INTO name FROM GIRL LIMIT 1;
	ELSE SELECT '无' INTO name;
	END CASE;
	RETURN name;
END $

-- 写法2
CREATE FUNCTION TEST_CASE_1(num INT) RETURNS VARCHAR(20)
BEGIN
	DECLARE name VARCHAR(20);
	CASE
	WHEN num=0 THEN SELECT '男子' INTO name FROM BOY LIMIT 1;
	WHEN num=1 THEN SELECT '女子' INTO name FROM GIRL LIMIT 1;
	ELSE SELECT '无' INTO name;
	END CASE;
	RETURN name;
END $
```

#### 1.5.2.3，`if` 结构

* 基本语法

```sql
-- 只能使用在 `BEGIN ... END` 语法中
IF 条件 THEN 语句;
ELSEIF 条件 THEN 语句;
ELSE 语句;
END IF;
```

* 语法示例

```sql
-- IF 结构在存储过程中使用
CREATE PROCEDURE TEST_IF(in num INT)
BEGIN 
	IF num = 1 THEN SELECT '男子' FROM BOY LIMIT 1;
	ELSEIF num = 2 THEN SELECT '女子' FROM GIRL LIMIT 1;
	END IF;
END $

-- IF 结构在函数中的使用
CREATE FUNCTION TEST_IF(num INT) RETURNS VARCHAR(20)
BEGIN
	DECLARE name VARCHAR(20);
	IF num=0 THEN SELECT '男子' INTO name FROM BOY LIMIT 1;
	ELSEIF num=1 THEN SELECT '女子' INTO name FROM GIRL LIMIT 1;
	ELSE SELECT '无' INTO name;
	END IF;
	RETURN name;
END $
```

### 1.5.2，循环结构

#### 1.5.2.1，基本介绍

* 循环关键字：
  * `while`：类似Java中的 `while` 循环，至少执行0次
  * `loop`：类似Java中的 `while` 循环，但是没有循环条件控制，是一个默认的死循环，需要搭配 `leave`使用
  * `repeat`：类似Java中的 `do ... while` 循环，至少执行1次，`repeat` 的 `until` 条件为true是退出循环
* 循环控制语句，<font color=red>使用这两个关键字时，循环一定要加标签</font>：
  * `iterate`：类似于Java中的 `continue`
  * `leave`：类似于Java中的 `break`
* <font color=red>循环结构必须依托于存储过程或者函数，应用在 `BEGIN ... END` 语句中</font>

#### 1.5.2.2，`while` 循环

* 基本语法

  ```sql
  -- 标签相当于Java的命名循环, 可不写
  -- 至少循环0次
  [标签:] WHILE 循环条件 DO
  	循环体;
  END WHILE [标签];
  ```

* 语法示例

  ```sql
  -- 新增数据，直接新增
  CREATE PROCEDURE WHILE_INSERT_GIRL(IN count INT)
  BEGIN
  	DECLARE currCount INT DEFAULT 0;
  	WHILE currCount < count DO
  		INSERT INTO GIRL(NAME) VALUES(CONCAT('新增',currCount));
  		SET currCount = currCount + 1;
  	END WHILE;
  END $
  
  -- 新增数据，入库两条后，其他扔出
  CREATE PROCEDURE WHILE_INSERT_GIRL1(IN count INT)
  BEGIN
  	DECLARE currCount INT DEFAULT 0;
  	DECLARE dataCount INT DEFAULT 0;
  	a: WHILE currCount < count DO
  	    IF dataCount >= 2 THEN LEAVE a;
  		END IF;
  		INSERT INTO GIRL(NAME) VALUES(CONCAT('新增',currCount));
  		SET currCount = currCount + 1;
  		SET dataCount = dataCount + 1;
  	END WHILE a;
  END $
  
  -- 新增数据，基数跳开， 只入偶数
  CREATE PROCEDURE WHILE_INSERT_GIRL2(IN count INT)
  BEGIN
  	DECLARE currCount INT DEFAULT 0;
  	a: WHILE currCount < count DO
  	    IF currCount % 2 != 0 THEN SET currCount = currCount + 1; ITERATE a;
  		END IF;
  		INSERT INTO GIRL(NAME) VALUES(CONCAT('新增',currCount));
  		SET currCount = currCount + 1;
  	END WHILE a;
  END $
  
  -- 统一执行语句
  CALL LOOP_INSERT_GIRL(10)$
  ```

#### 1.5.2.3，`loop` 循环

* 基本语法

  ```sql
  -- 该循环没有条件控制, 可能用来处理死循环
  -- 如果需要达到一定条件退出, 需要配合Leave使用
  [标签:] LOOP
  	循环体;
  END LOOP [标签];
  ```

* 语法示例

  ```sql
  -- 创建存储过程
  CREATE PROCEDURE LOOP_INSERT_GIRL(IN count INT)
  BEGIN
  	DECLARE currCount INT DEFAULT 100;
  	a: LOOP
  		IF currCount >= count THEN LEAVE a;
  		END IF;
  		INSERT INTO GIRL(NAME) VALUES(CONCAT('新增',currCount));
  		SET currCount = currCount + 1;
  	END LOOP a;
  END $
  
  -- 执行
  CALL LOOP_INSERT_GIRL(103)$
  ```

#### 1.5.2.4，`repeat` 循环语法

* 基本语法

  ```sql
  -- 类似Java中的 `do ... while` 循环
  -- 至少循环1次
  [标签:] REPEAT
  	循环体;
  UNTIL 结束循环的条件(为true是结束循环)
  END REPEAT [标签];
  ```

* 语法示例：`repeat` 语句最少执行一次，直接用一个条件不符合的试试不满足情况

  ```sql
  -- 创建存储过程
  CREATE PROCEDURE REPEAT_INSERT_GIRL(IN count INT)
  BEGIN
  	DECLARE currCount INT DEFAULT 1000;
  	a: REPEAT
  		INSERT INTO GIRL(NAME) VALUES(CONCAT('新增',currCount));
  		SET currCount = currCount + 1;
  	-- 入参为1,1 < 10为true，结束循环条件成立，直接跳出循环
  	UNTIL count < 10
  	END REPEAT a;
  END $
  
  -- 执行
  CALL REPEAT_INSERT_GIRL(1)$
  ```



# 2，MySQL架构介绍

## 2.1，MySQL逻辑架构

![2019110142053365.png](https://img.jbzj.com/file_images/article/201901/2019110142053365.png?201901014217)

1. 连接层

   > 最上层是一些客户端和连接服务，包含本地socket通信和大多数基于客户端/服务端工具实现的类似于tcp/ip的通信。主要完成一些类似于连接处理、授权认证及相关的安全方案。在该层上引入了线程池的概念，为通过认证安全接入的客户端提供线程，同时在该层上可以提供基于SSL的安全链接。服务器也会为安全接入的每个客户端验证它所具备的操作权限。

2. 服务层

   > 主要完成大多数的核心服务功能，如SQL接口；并完成缓存的查询，SQL的分析和优化及部分内置函数的执行。所有跨存储引擎的功能也在这一层实现，如过程、函数等。在该层，服务器会解析查询并创建相应的内部解析树，对其完成相应的优化。如确定查询索引，是否利用索引等，最终生成响应的执行操作。如果是SELECT语句，服务器会查询内部缓存，如果缓存空间足够大，在解读大量读操作的环境中能够很好的提升系统性能。

3. 引擎层

   > 存储引擎层，真正的负责了MySQL中数据的存储和提取，服务器通过API与存储引擎进行通信。不同的存储引擎具备的功能不同，这样我们可以根据自己的实际需要进行选取。

4. 存储层

   > 将数据存储在运行与裸设备的文件系统之上，并完成与存储引擎的交互。

## 2.2，MySQL存储引擎

### 2.2.1，存储引擎查询语句

```sql
-- 查看当前数据库支持的存储引擎
SHOW ENGINES;
-- 查看当前数据库的默认存储引擎和使用的存储引擎
SHOW VARIABLES LIKE '%storage_engine%';
```

### 2.2.2，`Myisam` 和 `InnoDB` 的区别

|   对比项   |                         Myisam                         |                            InnoDB                            |
| :---------------: | :---------------------------------------------: | :---------------------------------------------------: |
| 主键和外键 |                           N                            |                              Y                               |
|    事务    |                           N                            |                              Y                               |
| 行锁和表锁 | 表锁，即使操作一条数据也会锁住整个表，不适合高并发操作 |       行锁，操作时只锁某一行，不影响其他行，适合高并发       |
|    缓存    |               只缓存索引，不缓存真是数据               | 不仅缓存索引也缓存真实数据，对内存要求较高，内存大小对性能有决定性影响 |
|   表空间   |                           小                           |                              大                              |
|   关注点   |                          性能                          |                             事务                             |
|  默认安装  |                           Y                            |                              Y                               |

## 2.3，查询语句

### 2.3.1，SQL查询执行顺序

- 一条完成的SQL语句

  ```sql
  SELECT DISTINCT
  	<select-list>
  FROM TABLE
  JOIN TABLE ON <join-condition>
  WHERE <where-condition>
  GROUP BY COLUMN
  HAVING <having-condition>
  ORDER BY COLUMN
  LIMIT num
  ```

- SQL执行顺序

  ```sql
  1. FROM TABLE
  2. ON <join-condition>
  3. JOIN TABLE
  4. WHERE <where-condition>
  5. GROUP BY COLUMN
  6. HAVING <having-condition>
  7. SELECT
  8. DISTINCT
  9. ORDER BY COLUMN
  10. LIMIT num
  ```

![减少](E:\gitrepository\study\note\image\MySQL\1608604222907.png)

### 2.3.2，SQL执行流程



# 3，索引优化

## 3.1，SQL性能下降原因

* 查询语句写的差
* 索引失效：单值索引和复合索引
* 关联查询太多 `JOIN`（设计缺陷或者需求妥协）
* 服务器调优及各个参数设置（缓存，线程数等）

## 3.3，索引基础概念

### 3.3.1，索引是什么

* 索引（Index）是帮助MySQL高效获得数据的<font color=red>***数据结构***</font>
* 索引是一种<font color=red>***排好序的快速查找数据结构：B+树***</font>
* <font color=red>数据本身之外，数据库还维护着一个满足特定查找算法的数据结构，这些数据结构以某种形式指向数据，这样就可以在这些数据结构的基础上实现高级查找算法，这种数据结构就是索引，实现算法结构就是B树</font>
* 索引本身很大，不可能全部存储在内存中，索引往往以索引文件的形式存储在磁盘上

### 3.3.2，索引优劣势

* 优势
  * 提供数据检索效率，减少数据库的IO次数
  * 通过索引对数据进行排序，减低数据排序的成本，降低CPU的消耗
* 劣势
  * 实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，索引也是要占用空间的
  * 索引虽然大大提高了查询速度，但同时索引也降低了操作速度。需要同步更新索引结构
  * 索引只是提高效率的一个因素

### 3.3.3，索引分类

1. 单值索引：即一个索引只包含一个列，一个表可以有多个单值索引
2. 唯一索引：索引列的值必须唯一，但是可以为空值
3. 复合索引：即一个索引包含多个列

### 3.3.4，索引语法

```sql
-- 创建
CREATE [UNIQUE] INDEX indexName ON tableName(column1, column2);
ALTER TABLE tableName ADD [UNIQUE] INDEX indexName ON (column1, column2);

-- 删除索引
DROP INDEX indexName ON tableName;

-- 查看索引列表
SHOW INDEX FROM tableName;
```

### 3.3.5，索引数据结构（待后续原理补充）

* MySQL的索引数据结构包括：BTree索引、Hash索引、Full-Text索引、R-Tree索引
* 其中常规索引都是通过BTree实现
* <font color=red>原理方面后续补充</font>

### 3.3.4，索引创建时机

* 列索引可创建公式：DISTINCT值数量 / 数据数量 = 无限接近1 = 必要性

#### 3.3.4.1，适合创建索引的场景

* 主键自动建立唯一索引
* 频繁作为查询条件的字段应该创建索引
* 查询中与其他表建立关系的字段，外键关系建立索引
* 单值/组合索引的选择问题，在高并发下建议创建组合索引
* 查询中排序的字段，排序字段建所以讲大大提高排序速度
* 查询中统计和分组的字段

#### 3.3.4.2，不适合创建索引的场景

* 表记录太少
* 经常增删改的表，会一直伴随索引重拍
* 数据重复且分部平均的表字段
* 频繁更新的字段不适合创建索引

* `where` 条件中用不到的字段不创建索引

## 3.4，性能分析

### 3.4.1，常见瓶颈

* CPU：CPU在饱和的时候一般发生在数据装入内存或从磁盘读取数据的时候
* IO：磁盘I/0瓶颈发生在装入数据远大于硬盘容量的时候
* 服务器硬件性能瓶颈：top、free、iostat、vmstat等查看系统性能状态

### 3.4.2，Explain

* 使用 `explain` 关键字可以模拟优化器执行MySQL语句，从而知道MySQL是如何处理SQL语句的。分析查询语句或者表结构的性能瓶颈

#### 3.4.2.1，Explain关键字作用

* 表的读取顺序
* 数据读取操作的操作类型
* 哪些索引可以使用
* 哪些索引实际被使用
* 表之间的引用
* 每张表有多少行被优化器处理

#### 3.4.2.1，使用方式及字段解析

##### 3.4.2.1.1，使用方式

```sql
-- 在查询语句前加上 EXPLAIN 关键字即可
EXPLAIN [QUERY SQL];
-- 具体示例
EXPLAIN SELECT DISTINCT A. NAME FROM GIRL A LEFT JOIN BOY B ON A.ID = B.GIRL_ID WHERE A.ID = 14618;
```

##### 3.4.2.1.2，字段解析

![1608622176990](E:\gitrepository\study\note\image\MySQL\1608622176990.png)
* `id`：`SELECT` 查询的序列号，包含一组数字，表示查询中执行 `SELECT` 子句或操作表的顺序；可分为三种情况：
  
  * `id` 相同：执行顺序由上而下，如上面的例子
  * `id` 不相同：如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
  * `id` 相同不同混杂：同时存在id相同的和不同数据，先执行优先级高的，优先级相同的顺序执行
  
* `select_type`：查询类型，主要分为六种：
  * `SIMPLE`：简单的 `SELECT` 查询，查询中不包含子查询或者 `UNION`
  * `PRIMARY`：查询中若包含任何的子查询，最外层查询的查询类型
  * `SUBQUERY`：在 `SELECT` 和 `WHERE` 列表中包含的子查询
  * `DERIVED`：在 `FROM` 列表中包含的子查询被标记为 `DERIVED`（衍生），MySQL会递归执行这些子查询，把结果放到临时表中
  * `UNION`：若第二个 `SELECT` 出现在 `UNION` 之后，则标记为 `UNION`；若 `UNION` 包含在 `FROM` 子句的子查询中，外层 `SELECT` 将被标记为 `DERIVED` 
  * `UNION RESULT`：从 `UNION` 中获取结果的 `SELECT`
  
* `table`：显示这一行的数据是关于哪张表的

* `partitions`：这群

* `type`：访问类型排列，包括八种类型：
  * `ALL`：`FULL TABLE SCAN`，全表扫描，尽量避免
  * `index`：`FULL INDEX SCAN`，`index` 与 `ALL` 区别为 `index` 类型只遍历索引树。这通常比 `ALL` 快，因为索引文件通常比数据文件小
  * `range`：只检索给定范围的行，使用一个索引来选择行。一般在 `where` 子句中出现 `between`、`>`、`<` 时出现
  * `ref`：非唯一性索引扫描，返回匹配某个单独值得素有行。本质上也是一种索引访问，它返回所有匹配某个单独值得行；<font color=red>然而，它可能会找到多个符合条件的行，所以应该属于查找和扫描的混合体</font>
  * `eq_ref`：唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或者唯一索引扫描
  * `const`：表示通过索引一次就找到，`const` 用于比较 `primary key`  或者 `unique index`；因为只匹配一行数据，所以执行速度很快。如将主键置于 `where` 列表中，`MySQL` 就能将该查询转换为一个常量
  * `system`：表里面只有一行记录（等于系统表），是 `const` 类型的特例，平时不会出现，可以忽略不计；<font color=red>这个没有试出来</font>
  * `NULL`：
  * 性能排列依次是：`system > const > eq_ref > ref > range > index > ALL`
  * 一般来讲，`type` 至少要达到 `range` 级别，最好能达到 `ref` 级别
  
* `possible_keys`：显示可能应用在这张表中的索引，一个或者多个。查询涉及到的字段上若存在索引，<font color=red>则该索引将被列出，但不一定被实际查询使用</font>

* `key`：实际使用到的索引，如果为 `NULL`，说明没有使用到索引。查询中若使用了覆盖索引，则该索引仅出现在 `key` 列表中

* `key_len`：表示索引中使用的字节数，可通过该列计算得出查询中使用的索引的长度；在不损失精度的情况下，长度越短越好。`key_len` 显示的值为索引字段的最大可能长度，<font color=red>而不是实际使用长度</font>，即 `key_len` 是根据表计算得出的，不是通过表示检索得出的；

  * `key_len` 计算公式：`where` 后匹配到索引的列长度  + 可空的1 + 变长字符2

  * 其中定长字符包括 `char`、`int`、`datetime`；变长字符包括 `varchar`

  * int = 4 + 1（可空）

  * varchar(32) = 32 * 3（UTF-8）+ 1（可空） + 2（变长字符）

  * 在上面的 `BOY` 表中，对 `name = varchar(20)` 和 `girl_id(int)` 建立联合索引，并通过 `explain` 进行条件查询

    ```sql
    explain select * from boy where girl_id = 1 and name = '男1';
    -- key_len计算
    name(varchar(20)) = 20 * 30 + 1 + 2 = 63
    girl_id(int) = 4 + 1 = 5
    key_len = name + girl_id = 68
    ```

* `ref`：显示索引的哪一列被使用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值；<font color=red>这个没搞懂</font>

* `rows`：根据表统计信息及索引选用结果，大致估算出找到所需的记录所需要读取的行数

* `Extra`：包括不适合在上述列，但十分重要的额外信息：

  * `Using FileSort`：MySQL会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。即MySQL无法利用索引完成的排序操作成为 ***文件排序***
  * `Using Temporary`：使用了临时表保存中间数据，MySQL对查询结果排序时使用了临时表。常见于 `order by` 和 `group by`
  * `Using Index`：表示相应的 `SELECT` 操作中使用了***覆盖索引***，避免访问表的数据行，效率较高
    * 如果同时出现 `Using Where`：表示索引被用来执行索引键值得查找
    * 如果没有出现 `Using Where`：表示索引用来读取数据而非执行查找操作
  * `Using Where`：表名使用了 `WHERE` 过滤条件
  * `Using Join Buffer`：使用了连接缓存
  * `impossible where`：`where` 子句的值总是 `false`，不能用来取任何数据
  * `select tables optimized away`：在没有 `GROUP BY` 子句的情况下，基于索引优化 `MIN/MAX` 或者对应 MyISAM 引擎优化 `count(*)` 操作，不必等到执行阶段进行计算，查询执行计划生成的阶段即完成优化
  * `distinct`：优化 `DSITINCT` 操作，在找到第一个匹配后元素后终止找同样值得动作

### 3.4.3，索引优化

#### 3.4.3.1，执行过程分析

* <font color=red>视频的MySQL版本是Linux 5.5，我在Windows 5.7上演示，可能是版本原因导致的，执行计划部分结构不一致</font>

![1608707412595](E:\gitrepository\study\note\image\MySQL\1608707412595.png)

* 在之前的执行计划字段解析中，`id` 值越大，说明执行顺序越靠前，所以具体的执行顺序按 `id` 排应该是 `4 -> 3 -> 2 -> 1 -> NULL`
* `id = 4`：查询类型为 `UNION`，执行表为 `t2`，所以执行的是 `UNION语句`，即 `(select name, id from t2)`
* `id = 3`：查询类型为 `DERIVED`，执行表为 `t1`，通过类型可看是虚表查询，即 `(select id, name from t1 where other_column = '')`
* `id = 2`：查询类型是 `SUBQUERY`，执行表为 `t3`，是一个基于 `select` 子句的子查询， 即 `(select id from t3)`
* `id = 1`：查询类型是 `PRIMARY`，执行表为 `<derived3>`，也就是 `id = 3` 时候查出来的虚表，通过 `select_type = PRIMARY` 可知，是包含 `select` 子查询的最外层查询，即 `select d1.name, (select id from t3) d2 from (select id, name from t1 where other_column = '') d1`
* `id = null`：查询类型是 `union result`，执行表示 `<union1, 4`，是 `UNION` 结果获取部分，从 `id = 1` 和 `id = 4` 中取数据

#### 3.4.3.2，索引优化简单案例

##### 3.4.3.2.1，单表优化

* 表创建及数据初始化_文章表

  ```sql
  -- 表创建
  DROP TABLE IF EXISTS ARTICLE;
  CREATE TABLE ARTICLE (
  	ID INT PRIMARY KEY AUTO_INCREMENT,
  	AUTHOR_ID INT NOT NULL,
  	CATEGORY_ID INT NOT NULL,
  	VIEWS INT NOT NULL,
  	COMMENTS INT NOT NULL,
  	TITLE VARCHAR(256) NOT NULL,
  	CONTENT TEXT NOT NULL
  )
  -- 数据初始化
  INSERT INTO ARTICLE(AUTHOR_ID, CATEGORY_ID, VIEWS, COMMENTS, TITLE, CONTENT) VALUES(1,1,1,1,'1','1'),(2,2,2,2,'2','2'),(1,1,3,3,'3','3');
  ```

* 查询 `CATEGORY_ID = 1` 并且 `COMMENTS > 1` 的情况下，`VIEWS` 最多的 `ARTICLE_ID`

  ```sql
  -- 查询语句
  SELECT * FROM ARTICLE WHERE CATEGORY_ID = 1 AND COMMENTS > 1 ORDER BY VIEWS DESC LIMIT 1;
  ```

* 执行计划分析

  ```sql
  EXPLAIN SELECT * FROM ARTICLE WHERE CATEGORY_ID = 1 AND COMMENTS > 1 ORDER BY VIEWS DESC LIMIT 1;
  ```

  ![1608709877875](E:\gitrepository\study\note\image\MySQL\1608709877875.png)

  * 从执行计划中可以看到两点问题：首先是 `type = ALL`  说明是全表扫描，其次是 `Extra` 中存在 `Using Filesort` 字样，说明排序没有走索引
  * 当然，因为此时还没有给表中加索引，可以通过添加索引解决

* 第一步优化_按照 `where` 字段添加索引

  * 给 `where` 子句有关系的 `CATEGORY_ID`，`COMMENTS`，`VIEWS` 字段添加组合索引

    ```sql
    CREATE INDEX  ARTICLE_CCV ON ARTICLE(CATEGORY_ID, COMMENTS, VIEWS);
    ```

    ![1608710059706](E:\gitrepository\study\note\image\MySQL\1608710059706.png)

  * 继续执行执行计划，可以发现 `type = range`，即从全表扫描编成了范围扫描，第一步索引生效

  * 但是在 `Extra` 中，依然存在 `Using Filesort`，说明排序依旧没有生效，<font color=red>这是因为索引的匹配原则，当存在范围查找时，索引中断，索引 `VIEWS` 索引字段并没有走到</font>

  * <font color=red>此时可以对组合索引字段进行调整，将影响索引执行的字段 `COMMENTS` 移除即可</font>

  * <font color=red>特别注意：将一个索引拆成两个，并没有生效</font>

* 第二部优化_创建指定字段索引

  ```sql
  CREATE INDEX  ARTICLE_CV ON ARTICLE(CATEGORY_ID, VIEWS);
  ```

  ![1608710519229](E:\gitrepository\study\note\image\MySQL\1608710519229.png)

* 经过第二步优化后，`type = ref` 表示查询利用了索引，同时 `Extra` 中没有 `Using Filesort` 字样

##### 3.4.3.2.2，两表优化

* <font color=red>依旧版本不一致，以演示为例说明问题</font>

* 对于一个两表非主键、非索引列关联SQL，执行计划如下，可以看到执行计划的两条都是 `type = ALL` 的全表扫描

  ![1608713209852](E:\gitrepository\study\note\image\MySQL\1608713209852.png)

* 首先给右表的 `card` 列建立索引，执行计划如下：可以看到 `type = ref`，使用了索引且级别为 `ref`

  ![1608713404052](E:\gitrepository\study\note\image\MySQL\1608713404052.png)

* 然后给左表的 `card` 列建立索引，删掉右表的索引，执行计划如下：可以看到 `type = index`，使用了索引且级别为 `index`

  ![1608713710026](E:\gitrepository\study\note\image\MySQL\1608713710026.png)

* <font color=red>结论：理论上来讲，`ref` 级别优于 `index` 级别，在存在 `JOIN` 连接的场景下，左连接给右表加索引，右连接给左表加索引</font>

##### 3.4.3.2.3，三表优化

* 依旧版本不一致，以演示为例说明问题

* 对于一个三表非主键、非索引列关联SQL，执行计划如下，可以看到执行计划的两条都是 `type = ALL` 的全表扫描

  ![1608715146329](E:\gitrepository\study\note\image\MySQL\1608715146329.png)

* 给右表两个表全部加索引，执行计划如下：可以看到 `type = ref`，都使用了索引

  ![1608715284620](E:\gitrepository\study\note\image\MySQL\1608715284620.png)

* <font color=red>结论：减少 `JOIN` 语句中 `NestedLoop` 的循环总次数，永远使用小结果集驱动大结果及，即小表驱动大表；</font>
* <font color=red>保证 `JOIN` 语句中被驱动表上 `JOIN` 条件字段已经被索引</font>

#### 3.4.3.3，索引优化_索引失效

* 全值匹配：尽量使用组合索引的所有字段进行条件查找。<font color=red>全值匹配我最爱</font>

* 最左匹配：如果索引是组合索引，要遵守最左匹配法则。指的是从索引列的最左侧开始匹配，并且不能跳过中间列。<font color=red>带头大哥不能死，中间兄弟不能断</font>

* 不在索引上做任何操作（计算、函数、（自动或者手动）类型转换），或导致索引失效而进行全表扫描：<font color=red>索引列上无计算</font>
* 存储引擎中不能使用索引中范围条件右边的列：如果组合索引有三列，`where` 子句在匹配第二列时使用了范围语句，如 `>`、`<`、`IN` 等，则第三列会失去索引匹配。<font color=red>范围之后全失效</font>

* 尽量使用覆盖索引，减少 `SELECT *`：索引列和查询列一致，会减少一次回表

* MySQL在使用不等于时会无法使用索引

* `IS NULL`，`IS NOT NULL` 也无法使用索引

* `LIKE` 以通配符开头 `%|_xxxx`，MySQL索引会失效：<font color=red>如果 `where` 条件的字段与 `select` 查询的字段被某个覆盖索引 + 主键索引完全覆盖，此时无论通配符在什么位置，都可以匹配到索引，此处使用索引更多是从索引表中直接取数据</font>

* 字符串不加单引号索引失效：底层会自动进行 `int` 类型到 `varchar` 类型的转换，引起自动类型转换；<font color = red>字符串里有引号</font>

* 少用 `OR`，用来连接时索引会失效：

### 3.4.4，索引优化面试题分析

* 创建表并初始化数据

  ```sql
  -- 表创建
  create table test (
  	id int primary key auto_increment,
  	c1 varchar(32),
  	c2 varchar(32),
  	c3 varchar(32),
  	c4 varchar(32)
  )
  
  -- 初始化数据
  insert into test VALUES(null, 'c1', 'c2', 'c3', 'c4');
  insert into test VALUES(null, 'c1', 'c2', 'c3', 'c4');
  insert into test VALUES(null, 'c1', 'c2', 'c3', 'c4');
  insert into test VALUES(null, 'c1', 'c2', 'c3', 'c4');
  ```

* 创建索引

  ```sql
  create index test_c1234 on test (c1, c2, c3, c4);
  ```

#### 3.4.4.1，SQL查询语句索引匹配情况分析

![1608887515315](E:\gitrepository\study\note\image\MySQL\1608887515315.png)

##### 3.4.4.1.1，全值顺序匹配

* 可以看到使用了全索引，一个 `varchar(32)` 对应的 `key_len = 32 * 3 + 1 + 2 = 99`，匹配到索引的 `key_len = 396`，表示四个字段全部匹配到索引，即组合索引完全生效

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C2 = 'c2' AND C3 = 'c3' AND C4 = 'c4';
  ```

  ![1608885465403](E:\gitrepository\study\note\image\MySQL\1608885465403.png)

##### 3.4.4.1.2，全职乱序匹配

* `and` 是并列关系，在执行SQL时，SQL优化器会对SQL语句进行重组，按最终执行顺序组合后提交执行，所以写的顺序对执行没影响

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C2 = 'c2' AND C4 = 'c4' AND C3 = 'c3';
  ```

  ![1608885642798](E:\gitrepository\study\note\image\MySQL\1608885642798.png)

##### 3.4.4.1.3，全职范围匹配

* 在组合索引的 `C3` 列，也就是第三列位置断了，按照索引最左匹配原则，第四列不会走索引，索引只匹配到三个索引，`key_len = 297`；乱序结果一致，由优化器先进行优化

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C2 = 'c2' AND C3 > 'c3'AND C4 = 'c4' ;
  ```

  ![1608885754073](E:\gitrepository\study\note\image\MySQL\1608885754073.png)

##### 3.4.4.1.4，排序，组合索引不中断

* 首先看索引长度引用 `key_len = 198`，引用了两列，也就是左侧两列；其次看 `Extra` 中的额外属性，并没有 `Using Filesort` 等字样，说明排序是走了索引；通过 `C1`，`C2` 列常量确定之后，基本 `ORDER BY` 后面的就可以理解为常量的 `C1`，常量的 `C2` 和变量的 `C3` 进行排序，是可以匹配到索引的；

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C2 = 'c2' AND C4 = 'c4' ORDER BY C3;
  ```

  ![1608886023743](E:\gitrepository\study\note\image\MySQL\1608886023743.png)

##### 3.4.4.1.5，排序，组合索引中断

* 与上一个不同，这里最左匹配只匹配到了 `C1`，所以 `key_len = 99`；另外在排序时，因为索引中断了 `C2`，此时 `C2` 依旧是变量，无法利用索引进行排序，所以 `Extra` 中有 `Using Filesort` 字样，说明排序没有使用到索引

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C4 = 'c4' ORDER BY C3;
  ```

  ![1608886098077](E:\gitrepository\study\note\image\MySQL\1608886098077.png)

##### 3.4.4.1.6，排序，范围查找并排序

* 范围查找后再排序，要考虑排序的字段是已经在 `where` 子句中的字段还是其他的索引字段；

  * 如果是已经在 `where` 子句中索引字段，是可以匹配到索引进行排序的
  * 如果是其他索引中的字段，此时不会匹配到索引排序

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C2 > 'c2' ORDER BY C3;
  ```

  ![1608887074553](E:\gitrepository\study\note\image\MySQL\1608887074553.png)

##### 3.4.4.1.7，多字段排序，顺序排序

* 与索引不断的排序基本一致，使用到了常量的 `C1` 和变量的 `C2`，`C3` 进行排序，是可以匹配到索引的

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C4 = 'c4' ORDER BY C2, C3;
  ```

  ![1608886386871](E:\gitrepository\study\note\image\MySQL\1608886386871.png)

##### 3.4.4.1.8，多字段排序，乱序排序

* 查询可以通过SQL优化器进行优化，因为 `and` 连接的是并行的，但是 `order by` 不是，`order by` 后如果随意调整顺序，是要影响输出结果的，所以乱序是不能匹配到索引的

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C4 = 'c4' ORDER BY C3, C2;
  ```

  ![1608886524300](E:\gitrepository\study\note\image\MySQL\1608886524300.png)

##### 3.4.4.1.9，字段常量查询并顺序排序

* 在前一步的分析中，已经在单字段排序加上了基于常量的多字段的分析，对字段查询并排序，基本一致

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' AND C2 = 'c2' ORDER BY C2, C3;
  ```

  ![1608886814739](E:\gitrepository\study\note\image\MySQL\1608886814739.png)

##### 3.4.4.1.10，字段常量查询并逆序排序

* 与排序基本一致，逆序索引不生效

  ```sql
  EXPLAIN SELECT * FROM TEST WHERE C1 = 'c1' ORDER BY C3, C2;
  ```

  ![1608886909247](E:\gitrepository\study\note\image\MySQL\1608886909247.png)

##### 3.4.4.1.11，数组分组，顺序索引

* 分组与排序基本一致，顺序会匹配到索引进行分组处理

  ```sql
  EXPLAIN SELECT C2, C3 FROM TEST WHERE C1 = 'c1' GROUP BY C2, C3;
  ```

  ![1608887340647](E:\gitrepository\study\note\image\MySQL\1608887340647.png)

##### 3.4.4.1.12，数组分组，乱序索引

* 乱序不会匹配到索引，而且更可怕的是，排序只是出现了 `Using Filesort`，而分组出现了 `Using temporary; Using filesort`；<font color=red>`group by` 如果没有匹配到索引进行处理，会有临时表的产生</font>

  ```sql
  EXPLAIN SELECT C2, C3 FROM TEST WHERE C1 = 'c1' GROUP BY C3, C2;
  ```

  ![1608887370191](E:\gitrepository\study\note\image\MySQL\1608887370191.png)

#### 3.4.4.2，索引优化的一般性建议

* 对于单列索引，尽量选择针对当前查询过滤性最好的索引
* 在选择组合索引的时候，当前查询中过滤性最好的字段在索引列顺序中，越靠前越好
* 在选择组合索引的时候，尽量选择在当前查询的 `where` 子句中匹配字段最多的索引
* 尽可能通过分析统计信息和调整查询的写法来达到选择合适索引的目的



# 4，查询截取分析

## 4.1，查询优化

### 4.1.1，小表驱动大表

* <font color = red>小表驱动大表</font>：对于表A和表B，如果A表数据小于B表，使用A表关联B表时，`EXISTS` 比 `IN` 的性能更好

  ```sql
  SELECT * FROM A WHERE EXISTS (SELECT 1 FROM B WHERE B.ID = A.ID)
  ```

* <font color=red>大表驱动小表</font>：对于表A和表B，如果A表数据大于B表，使用A表关联B表时，`IN` 比 `EXISTS` 的性能更好

  ```sql
  SELECT * FROM A WHERE A.ID IN (SELECT ID FROM B);
  ```

### 4.1.2，`Order By` 关键字优化

#### 4.1.2.1，`Index` 方式排序

- `Order By` 子句，尽量使用 `Using Index` 方式排序，减少使用 `Using Filesort` 方式排序
- MySQL支持两种方式的排序，`Filesort` 和 `Index`：`Index` 效率较高，是使用MySQL的索引完成排序；`Filesort` 效率低
- `Order By` 满足两种情况，会使用 `Index` 排序：
  - `Order By` 语句使用索引最左前列
  - 使用 `where` 子句和 `Order By` 子句条件组合满足索引最左前列

![1609147731900](E:\gitrepository\study\note\image\MySQL\1609147731900.png)

##### 4.1.2.1.1，`Using Index` 方式示例

* 创建表

  ```sql
  -- 表创建
  CREATE TABLE `phone` (
    `ID` int(11) NOT NULL AUTO_INCREMENT,
    `AUTHOR_ID` int(11) NOT NULL,
    `NUMBER` varchar(32) NOT NULL,
    `NAME` varchar(32) NOT NULL,
    PRIMARY KEY (`ID`),
    KEY `phone_author` (`AUTHOR_ID`,`NUMBER`)
  ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
  
  -- 索引创建
  create index phone_author on phone(author_id, number);
  
  -- 入库数据
  insert into phone VALUES(null, 1, '1234', '1234'),(null, 2, '1234', '1234'),(null, 3, '1234', '1234'),(null, 4, '1234', '1234'),(null, 5, '1234', '1234'),(null, 6, '1234', '1234'),(null, 7, '1234', '1234'),(null, 8, '1234', '1234');
  ```

* 满足组合索引最左列的范围查找

  * 此处注意一个问题：如果范围查找的结果值趋近与全表值，此处已经会使用 `Using Filesort`

  ```sql
  explain select * from phone where author_id > 7 order by author_id;
  ```

  ![1609145458231](E:\gitrepository\study\note\image\MySQL\1609145458231.png)

* 满足组合索引的范围查找

  ```sql
  explain select * from phone where author_id > 7 order by author_id, number;
  ```

  ![1609145515261](E:\gitrepository\study\note\image\MySQL\1609145515261.png)

* 不满足组合索引最左列范围查找

  ```sql
  explain select * from phone where author_id > 7 order by number;
  ```

  ![1609145555051](E:\gitrepository\study\note\image\MySQL\1609145555051.png)

* 不满足组合索引顺序的范围查找

  ```sql
  explain select * from phone where author_id > 7 order by number, author_id;
  ```

  ![1609145583838](E:\gitrepository\study\note\image\MySQL\1609145583838.png)

* 排序顺序不一致的满足索引的排序

  * MySQL的排序默认走<font color=red>全顺序或者全逆序</font>，如果在组合索引中排序方式不一致，则会触发 `Using Filesort`

  ```sql
  explain select * from phone where author_id > 7 order by author_id, number desc;
  ```

  ![1609145654866](E:\gitrepository\study\note\image\MySQL\1609145654866.png)

#### 4.1.2.2，`Filesort` 排序算法

* 双路排序：在MySQL4.1之前使用双路排序，即先从磁盘中读取行指针和 `Order By` 列（第一路磁盘访问），在内存中进行排序后，按照排序后的行指针，重新从磁盘读取完整数据（第二路磁盘访问）；<font color=red>磁盘读数据是比较耗时的，考虑从双路排序到单路排序优化</font>
* 单路排序：一次性从磁盘中取出需要查询和排序的所有的列，按照 `Order By` 条件在 `Buffer` 中进行排序，然后再扫描排序后的列表进行输出，避免了第二次的读取。但是在 `Buffer` 中排序，是典型的用空间换时间，需要消耗大量内存。
* <font color=red>单路排序存在的问题：单路排序相比双路排序会消耗更多的 `sort_buffer` 空间，如果 `sort_buffer` 空间不足时，每一次从磁盘取数据最多只能取 `sort_buffer` 最大容量数据，并创建 `tmp` 文件进行合并排序。这样就可能会导致单路排序实际上可能需要更多次的IO交互，得不偿失</font>

#### 4.1.2.3，优化策略

* `Order By` 是只查询需要的字段
  * 当查询的字段总和小于 `max_length_for_sort_data` 并且字段类型不是 `BLOB|TEXT` 类型时，会用改进后的算法：单路算法，否则依旧使用原始算法：双路算法
  * 两种算法都有可能超过 `sort_buffer` 的限制，超出之后，会创建 `tmp` 临时文件进行合并排序，导致多次IO。相对而言，单路排序算法的风险更大一点，需要提高 `sort_buffer_size` 参数值得设置

* 增大 `sort_buffer_size` 参数的设置，增大排序缓冲区：不管使用哪种算法，这个参数的值都必须按照实际情况设置；<font color=red>注意：该参数是针对每一个进程的，需要按照系统的实际承压能力进行调整</font>
* 增大 `max_length_for_sort_data` 参数：提高该参数值，会增加用改进算法的概率。<font color=red>注意：该参数值应该与 `sort_buffer_size` 相匹配，如果设的过高，`sort_buffer_size` 溢出的概率就越大，造成多次IO</font>

### 4.1.3，`group by` 关键字优化

* `group by` 实质是先排序后进行分组，遵循索引建的最佳左前缀
* 当无法使用索引列的时候，增大 `max_length_sort_for_data` 和 `sort_buffer_size` 参数的设置
* `where` 高于 `having` 的执行顺序，能在 `where` 中限定的条件就不要在 `having` 中限定

## 4.2，慢查询日志

### 4.2.1，慢查询日志开启和基本分析

* MySQL的慢查询日志是MySQL提供的一种日志记录，用来记录SQL执行时间超过阈值的语句；具体是指运行时间超过 `long_query_time = 10` 的SQL语句，会记录在慢查询日志中

* 一般情况下，MySQL数据库没有开启慢查询日志，需要进行手动设置；如果不是调优需要的话，一般不开启该参数，会造成一定的性能影响；<font color=red>开启后只对当前进程生效，如果数据库重启后会失效</font>

  ```sql
  -- 查看开启状态
  -- ON表示打开， OFF表示关闭
  SHOW VARIABLES LIKE 'slow_query_log';
  
  -- 慢日志文件路径
  SHOW VARIABLES LIKE 'slow_query_log_file';
  ```

* 如果需要永久修改该参数，需要修改 `my.ini` 配置文件，在 `mysqld` 模块添加语句

  ```sql
  slow_query_log = 1
  slow_query_log_file = PATH
  ```

* 设置完成后，时间大于 `long_query_time = 10` 的SQL语句，会记录在慢查询日志中，日志路径参考 `slow_query_log_file`

  ```sql
  SHOW VARIABLES LIKE 'long_query_time'
  ```

* 模拟慢查询SQL语句，并且到文件夹进行分析

  ```sql
  -- 模拟慢查询日志，阈值提前从10秒修改为3秒
  SET long_query_time = 3
  -- 查询语句
  select sleep(4);
  ```

  * 慢查询日志

    ![1609149020535](E:\gitrepository\study\note\image\MySQL\1609149020535.png)

* 查询触发了慢日志的SQL数量

  ```sql
  show global status like '%Slow_queries%'
  ```

* 配置文件配置：在 `my.ini` 的 `mysqld` 模块进行配置

  ```sql
  -- 开启慢日志
  slow_query_log = 1
  -- 配置慢日志路径
  slow_query_log_file = PATH
  -- 设置慢日志阈值
  long_query_time = ?秒
  log_output = FILE
  ```

### 4.2.2，慢日志分析工具：mysqldumpslow

* LINUX系统下可以直接使用，Windows系统下是 `mysqldumpslow.pl` 文件，需要安装 `perl` 相关程序，没试

* 相关命令

  * `-s`：表示按照何种方式排序
    * `c`：访问次数
    * `l`：锁定时间
    * `r`：返回记录
    * `t`：查询时间
    * `al`：平均锁定时间
    * `ar`：平均返回记录数
    * `at`：平均查询时间
  * `-t`：返回前面多少条数据
  * `-g`：搭配正则匹配模式，大小写不敏感

* 大体使用方式

  ```sql
  -- 得到返回记录集最多的10个SQL
  -- `-s r`：表示排序方式是按返回记录排序
  -- `-t 10`：表示返回前面10条数据
  -- `FILE_PAHT`：是慢日志路径
  mysqldumpslow -s r -t 10 FILE_PATH;
  
  -- 返回访问次数最多的10条数据
  mysqldumpslow -s c -t 10 FILE_PATH;
  
  -- 返回按照时间排序的前十条里面包含左连接的查询语句
  mysqldumpslow -s t -t 10 -g "left join" FILE_PATH;
  
  -- 建议使用命令时搭配 `|` 和 `more` 使用，否则可能爆屏
  mysqldumpslow -s r -t 10 FILE_PATH | more;
  ```

## 4.3，批量数据脚本_插入1000W条随机数据

### 4.3.1，建表

```sql
-- 创建部门表
CREATE TABLE DEPATMENT(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	D_NUMBER INT NOT NULL,
	D_NAME VARCHAR(256) NOT NULL
)

-- 创建员工表
CREATE TABLE EMPLOYEE (
	ID INT PRIMARY KEY AUTO_INCREMENT,
	D_NUMBER INT NOT NULL,
	E_NAME VARCHAR(256) NOT NULL
)
```

### 4.3.2，创建自定义方法

```sql
-- 生成随机指定位数字符串
DELIMITER $$
CREATE FUNCTION random_str(count INT) RETURNS VARCHAR(256)
BEGIN
	DECLARE random_str_all VARCHAR(64) DEFAULT 'qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM';
	DECLARE return_str VARCHAR(256) DEFAULT '';
	DECLARE curr_index INT DEFAULT '0';
	WHILE curr_index < count DO
		SET return_str = CONCAT(return_str,SUBSTR(random_str_all,FLOOR(1 + RAND() * 52), 1));
		SET curr_index = curr_index + 1;
	END WHILE;
	return return_str;
END $$
DELIMITER ;

-- 生成随机数字
DELIMITER $$
CREATE FUNCTION random_num() RETURNS INT
BEGIN
	DECLARE randomNum INT DEFAULT 0;
	SET randomNum = FLOOR(100 + RAND() * 10);
	return randomNum;
END $$
DELIMITER ;
```

### 4.3.3，批量插入存储过程

```sql
-- 插入员工表的存储过程
DELIMITER $$
CREATE PROCEDURE insert_employee(IN START INT, IN TOTAL_COUNT INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	REPEAT
		SET i = i + 1;
		INSERT INTO EMPLOYEE VALUES(START + i, random_num(), random_str(6));
	UNTIL i = TOTAL_COUNT
	END REPEAT;
END $$
DELIMITER ;

-- 插入部门表的存储过程
DELIMITER $$
CREATE PROCEDURE insert_department(IN START INT, IN TOTAL_COUNT INT)
BEGIN
	DECLARE i INT DEFAULT 0;
	REPEAT
		SET i = i + 1;
		INSERT INTO DEPATMENT VALUES(START + i, START + i, random_str(6));
	UNTIL i = TOTAL_COUNT
	END REPEAT;
END $$
DELIMITER ;
```

### 4.3.4，执行

* 通过存储过程，插入1000万数据到员工表中，一次性插入过多可能会有影响，考虑分阶段插入，比如一次插入50万条，多插入几次

  ```sql
  -- 插入部门表，插入十条数据
  CALL insert_department(100, 10);
  -- 插入员工表，一次50万
  CALL insert_employee(10000, 500000);
  ```

## 4.4，Show Profile_性能分析脚本

* 是MySQL提供用来分析当前会话中语句执行的资源消耗情况，可以用于SQL调优的测量。该参数默认情况下处理管理状态，开启后可以保存最多15次的运行结果

### 4.4.1，打开并查看执行记录

```sql
-- 查看属性状态
SHOW VARIABLES LIKE 'profiling';
-- 打开该属性
SET profiling = 1;
-- 如果需要持久化, 在 `my.ini` 的 [mysqld] 标签下加属性
profiling = 1;
-- 查看SQL执行记录
SHOW profiles;
```

![1609216276410](E:\gitrepository\study\note\image\MySQL\1609216276410.png)

### 4.4.2，诊断SQL_Profile分析

```sql
-- Query_ID 表示上一步查询的序列号, 下面截图是15的序列号
-- CPU: 显示CPU消耗时间
-- block io: 显示IP阻塞时间
show profile cpu, block io for query Query_ID;
```

![1609216289509](E:\gitrepository\study\note\image\MySQL\1609216289509.png)

### 4.4.3，Status 列异常情况

* `Converting HEAP to MyISAM`：查询结果太大，内存不够用，动用了磁盘进行处理
* `Creating Tmp Table`：创建临时表；拷贝数据到临时表，用完删除临时表 `removing tmp table`
* `Copying to tmp table on disk`：把内存中临时表复制到磁盘，<font color=red>危险</font>
* `locked`：锁表
* <font color=red>在上面的分析中，可以看到出现了 `Creating tmp table`，有创建中间表。</font>

## 4.5，全局查询日志

* 开启之后，会对数据库的所有命令操作进行日志存储
* <font color=red>永远不要在生产环境启用该功能</font>

### 4.5.1，配置启用

```sql
-- windows 在 my.ini 的 [mysqld] 块里面加配置信息
-- 开启
general_log = 1
-- 记录日志文件路径
general_log_file = PATH
-- 输入格式
log_output = FILE
```

### 4.5.2，编码启用

```sql
-- 启用全局日志
SET GLOBAL general_log = 1;
-- 设置输出形式为表
SET GLOBAL log_output = 'TABLE';
-- 通过表形式查看日志
SELECT * FROM mysq.general_log;
```

![1609224019004](E:\gitrepository\study\note\image\MySQL\1609224019004.png)



# 5，锁

## 5.1，事务

- 偏向InnoDB存储引擎，开销大，加锁慢；会出现死锁；锁定粒度最小，发生索冲突的概率最低，并发度也最高
- 相对于MyISAM，InnoDB<font color=red>支持事务，支持行锁</font>

### 5.1.1，事务ACID属性

- 原子性（Atomicity）：事务是一个原子操作单位，其对数据的修改，要么全都执行，要么全都不执行
- 一致性（Consistent）：在事务开始和结束的时候，必须保持数据的一致性
- 隔离性（Isolation）：数据库提供的隔离机制，保证事务在不受外部并发操作影响的独立环境执行
- 持久性（Durable）：事务完成之后，对数据的修改是永久性的

### 5.1.2，事务带来的问题

- 更新丢失（Lost Update）：多个事务选择同一行数据进行处理，会由最后更新覆盖之前其他事务的更新
- 脏读（Dirty Reads）：事务A读到了事务B已经修改但是尚未提交的数据
- 幻读（Non-Repeatable Reads）：事务A读取到了事务B提交的新增数据，不符合隔离性
- 不可重复读（Phantom Reads）：事务A读取一条数据后，再读取该数据，结果两次数据不一致，即读到了事务B提交的数据，不符合隔离性

### 5.1.3，事务隔离级别

| 隔离级别                                               | 读一致性                             | 脏读 | 不可重复读 | 幻读 |
| ------------------------------------------------------ | ------------------------------------ | ---- | ---------- | ---- |
| 未提交读（Read Uncommitted）                           | 最低级别，保证不读取物理上损坏的数据 | N    | N          | N    |
| 已提交读（Read Committed）                             | 语句级                               | Y    | N          | N    |
| <font color=red>可重复读（Repeatable Read）默认</font> | 事务级                               | Y    | Y          | N    |
| 串行化（Serializable）                                 | 最高级别，事务级                     | Y    | Y          | Y    |

## 5.2，表锁

* <font color=red>表锁是MyISAM存储引擎默认的锁，MyISAM的读写锁调度是写锁优先，这也是MyISAM不适合做主表的引擎</font>

### 5.2.1，表加锁及解锁

```sql
-- 加锁
lock table TABLE_NAME [READ/WRITE], (...支持多个表操作);

-- 解锁，一次性解锁全部表
unlock tables;

-- 查看加锁表
-- In_Use为0表示未加锁，1表示被锁
show open tables;
```

### 5.2.2，表锁_共享读锁

| 操作         | 当前 Session | 其他 Session |
| :----------- | ------------ | ------------ |
| 加读锁表读取 | Y            | Y            |
| 加读锁表操作 | N            | 阻塞         |
| 未加锁表读取 | N            | Y            |
| 未加锁表操作 | N            | Y            |

### 5.2.3，表锁_独占写锁

| 操作         | 当前 Session | 其他 Session |
| :----------- | ------------ | ------------ |
| 加写锁表读取 | Y            | 阻塞         |
| 未加锁表读取 | N            | Y            |
| 未加锁表操作 | N            | Y            |

### 5.2.4，表锁性能分析

```sql
-- 查看表锁的两个关键状态
show status like 'table%';
```

* `Table_locks_immediate`：产生表级锁定的次数，表示可以立即获取锁的查询次数，每立即获取一次锁值+1
* `Table_locks_waited`：出现表级锁定争用而发生等待的次数（不能立即获取锁的次数，每出现一次+1），此值高则说明存在严重的表级锁争用情况

## 5.3，行锁

