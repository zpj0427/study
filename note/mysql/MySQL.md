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

##### 1.1.1.2.1，Merge方式修改

```sql
CREATE OR REPLACE VIEW view_name
AS
QUERY SQL；
```

* 这种方式类似于Merge语句，与视图创建语句基本一致，只是增加了 `REPLACE` 部分
* 当视图不存在时候，按指定语句创建该视图；当视图存在时，按指定语句修改该视图的执行语句

##### 1.1.1.2.2，Alter方式

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
    -- 从上一步中取一个不能额更新的视图，作为QUERY SQL，进行新视图创建
    CREATE VIEW view_name
    AS
    SELECT * FROM exists_view_name
    ```

  * WHERE 子句中的子查询应用了 FROM 字句中的表

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
* 系统变量查询/操作，不加限制，默认为 `SESSION` 级别

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

### 1.5.1，顺序结构

1. `if` 函数

   ```sql
   -- 与Java的三元运算符基本一致
   -- 表达式1成立，返回表达式2，不成立，返回表达式3
   IF(表达式1, 表达式2, 表达式3)
   ```

2. `case` 结构

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

3. `if` 结构

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

### 1.5.2，分支结构

### 1.5.3，循环结构