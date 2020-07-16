# 1，设计模式的七大原则

## 1.1，设计模式的目的

* 在程序编写过程中，程序员面临着**耦合性**，**内聚性**以及**可维护性**，**可扩展性**，**重用性**，**灵活性**等多方面的挑战，设计模式就是为了让软件，可以更好的满足上面的标准
* 代码重用性：相同功能的代码，不用多次编写
* 代码可读性：编程规范性，便于其他程序员对代码的阅读和理解
* 可扩展性：当需求变更，需要增加新的功能时，能最小改动，最快时间实现
* 可靠性：增加新的功能时，对现有功能没有影响
* 使程序呈现出高内聚，低耦合的特性

## 1.2，设计模式的七大原则

* 单一职责原则
* 接口隔离原则
* 依赖倒转（倒置原则）
* 里式替换原则
* 开闭原则
* 迪米特法则
* 合成复用原则

## 1.3，单一职责原则

### 1.3.1，单一职责介绍

* 单一职责是对类来说的，即一个类只负责一项职责。如类A存在两个不同的职责：职责1，职责2。当职责1需要变更而进行修改时，极有可能造成职责2不可用，所以需要将A的粒度分解为A1，A2

### 1.3.2，应用示例

1. 假设存在一个交通的工具类，提供交通工具跑的方式，在最开始的时候只有汽车，所以提供了*汽车在公路上跑*；功能运行起来后，还需要支撑轮船和飞机，此时功能就变成了*汽车，轮船，飞机都在公路上跑*

   ```java
   package com.self.designmode.discipline;
   
   /**
    * 设计模式七大原则_单一职责原则
    * 即一个类应该只负责一项职责
    * @author pj_zhang
    * @create 2020-07-15 22:03
    **/
   public class SingleResponsibility1 {
   
       public static void main(String[] args) {
           Vehicle vehicle = new Vehicle();
           // 初始需求
           vehicle.run("汽车");
   
           // 后续添加需求
           vehicle.run("轮船");
           vehicle.run("飞机");
       }
   
       static class Vehicle {
   
           public void run(String vehicleName) {
               System.out.println(vehicleName + " 在公路上跑...");
           }
   
       }
   
   }
   ```

2. 从1中可以看出来，让`Vehicle.run(..)`一个类处理交通工具跑的方式是不够的，此时需要对功能进行分解，可以先从类角度进行分解

   * 此时可以看到，一个交通类`Vehicle`被分解成为三个交通类`CarVehicle`，`SteamerVehicle`，`AirVehicle`，三个交通类各司其职，如果如果还有其他交通需求，可以再加对应的交通类进行处理
   * 该处理方式，将功能的职责可以完全区分开，但是从一定程度上无疑会造成类爆炸

   ```java
   package com.self.designmode.discipline;
   
   /**
    * 设计模式七大原则_单一职责原则
    * 即一个类应该只负责一项职责
    * @author pj_zhang
    * @create 2020-07-15 22:03
    **/
   public class SingleResponsibility2 {
   
       public static void main(String[] args) {
           CarVehicle carVehicle = new CarVehicle();
           // 初始需求
           carVehicle.run("汽车");
   
           // 后续添加需求, 进行类职责单一维度的变更
           SteamerVehicle steamerVehicle = new SteamerVehicle();
           steamerVehicle.run("轮船");
           AirVehicle airVehicle = new AirVehicle();
           airVehicle.run("飞机");
       }
   
       static class CarVehicle {
   
           public void run(String vehicleName) {
               System.out.println(vehicleName + " 在公路上跑...");
           }
   
       }
   
       static class SteamerVehicle {
   
           public void run(String vehicleName) {
               System.out.println(vehicleName + " 在大海上航行...");
           }
   
       }
   
       static class AirVehicle {
   
           public void run(String vehicleName) {
               System.out.println(vehicleName + " 在天空中飞...");
           }
   
       }
   
   }
   
   ```

3. 第2点从类拆分角度给出方案，此外在简单的相似功能拆分下，也可以使用方法拆分的原则，各个方法处理不同的场景，对于类来讲，处理的也算是一项职责，没有未被单一职责原则

   ```java
   package com.self.designmode.discipline;
   
   /**
    * 设计模式七大原则_单一职责原则
    * 即一个类应该只负责一项职责
    * @author pj_zhang
    * @create 2020-07-15 22:03
    **/
   public class SingleResponsibility3 {
   
       public static void main(String[] args) {
           CarVehicle carVehicle = new CarVehicle();
           // 初始需求
           carVehicle.runCar("汽车");
   
           // 后续添加需求, 进行方法职责单一维度的变更
           carVehicle.runStreamer("轮船");
           carVehicle.runAir("飞机");
       }
   
       static class CarVehicle {
   
           public void runCar(String vehicleName) {
               System.out.println(vehicleName + " 在公路上跑...");
           }
   
           public void runStreamer(String vehicleName) {
               System.out.println(vehicleName + " 在大海上航行...");
           }
   
           public void runAir(String vehicleName) {
               System.out.println(vehicleName + " 在天空中飞...");
           }
   
       }
   
   }
   
   ```

4. 到此处基本单一职责原则已经说清楚，当然该部分可以通过Java的多态机制，通过单例+工厂实现，也可以通过策略实现，但都是后话了...

### 1.3.3，单一职责的注意事项和细节

* 降低类的复杂度，一个类只负责一项职责
* 提高类的可读性和可维护性
* 降低需求变更引发的风险
* 通常情况下，我们需要保持类的职责单一
  * 只有逻辑足够简单，才可以在代码级别违反单一职责原则
  * 只有类中方法数量足够少，才可以在方法维度保持单一职责原则

## 1.4，接口隔离原则

### 1.4.1，接口隔离原则基本介绍

* 客户端不一定依赖它不需要的接口，即一个类对另一个类的依赖应该建立在最小接口之上

  ![1594893273606](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1594893273606.png)

* 在上图中，可以看到类A通过接口`Interface`依赖类B，类C通过接口`Interface`依赖类D，如果接口`Interface`对应类B和类D来说不是最小接口，那么类B和类D必须实现他们不需要的方法

* 按照隔离原则：需要将接口`Interface`拆分为独立的几个接口，类B和类D分别实现对应的接口并只需要实现各自需要的方法，而类A和类C也分别于他们需要的接口建立依赖关系，也就是采用接口隔离原则

  ![1594893493754](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1594893493754.png)

### 1.4.2，应用示例

1. 在第一张图中，没有采用接口隔离原则时，代码如下

   ```java
   package com.self.designmode.discipline.segregation;
   
   /**
    * 设计模式七大基础原则_接口隔离原则
    * @author LiYanBin
    * @create 2020-07-16 17:42
    **/
   public class InterfaceSegregation1 {
   
       public static void main(String[] args) {
           A a = new A();
           a.depend1();
           a.depend2();
           a.depend3();
   
           C c = new C();
           c.depend1();
           c.depend4();
           c.depend5();
       }
   
       /**
        * 外部调用类_C
        * C依赖D
        * C通过接口调用D的 1 4 5 接口
        */
       static class C {
   
           private Interface myInterface = new D();
   
           public void depend1() {
               myInterface.method_1();
           }
   
           public void depend4() {
               myInterface.method_4();
           }
   
           public void depend5() {
               myInterface.method_5();
           }
   
       }
   
       /**
        * 外部调用类_A
        * A依赖B
        * A通过接口调用B的1 2 3接口
        */
       static class A {
   
           private Interface myInterface = new B();
   
           public void depend1() {
               myInterface.method_1();
           }
   
           public void depend2() {
               myInterface.method_2();
           }
   
           public void depend3() {
               myInterface.method_3();
           }
   
       }
   
       /**
        * 对外接口
        */
       interface Interface {
           void method_1();
           void method_2();
           void method_3();
           void method_4();
           void method_5();
       }
   
       /**
        * 接口实现类_B
        */
       static class B implements Interface {
   
           @Override
           public void method_1() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_1");
           }
   
           @Override
           public void method_2() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_2");
           }
   
           @Override
           public void method_3() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_3");
           }
   
           @Override
           public void method_4() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_4");
           }
   
           @Override
           public void method_5() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_5");
           }
       }
       /**
        * 接口实现类_D
        */
       static class D implements Interface {
   
           @Override
           public void method_1() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_1");
           }
   
           @Override
           public void method_2() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_2");
           }
   
           @Override
           public void method_3() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_3");
           }
   
           @Override
           public void method_4() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_4");
           }
   
           @Override
           public void method_5() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_5");
           }
       }
   
   }
   
   ```

2. 从代码中可以看出，类B和类D都分别实现的自己不需要的方法，无效的方法容易给客户端调用产生调用混乱，此时通过接口隔离原则对整个结构进行重构，参考第二张图

   * 在重构过程中，首先将接口`Interface`拆分为三部分，第一部分是公共部分，第二部分是B类需要实现的部分，第三部分是D类需要实现的部分；
   * 接口拆分完成后，对应类B和类D来说，只需要实现公共部分和各自的接口部分即可，不会多实现没必要的接口

   ```java
   package com.self.designmode.discipline.segregation;
   
   /**
    * 设计模式七大基础原则_接口隔离原则
    * @author LiYanBin
    * @create 2020-07-16 17:42
    **/
   public class InterfaceSegregation2 {
   
       public static void main(String[] args) {
           A a = new A();
           a.depend1();
           a.depend2();
           a.depend3();
   
           C c = new C();
           c.depend1();
           c.depend4();
           c.depend5();
       }
       
       /**
        * 外部调用类_C
        * C依赖D
        * C通过接口调用D的 1 4 5 接口
        */
       static class C {
   
           private InterfaceD myInterface = new D();
   
           private InterfaceCommon interfaceCommon = new B();
   
           public void depend1() {
               interfaceCommon.method_1();
           }
   
           public void depend4() {
               myInterface.method_4();
           }
   
           public void depend5() {
               myInterface.method_5();
           }
   
       }
   
       /**
        * 外部调用类_A
        * A依赖B
        * A通过接口调用B的1 2 3接口
        */
       static class A {
   
           private InterfaceB myInterface = new B();
   
           private InterfaceCommon interfaceCommon = new B();
   
           public void depend1() {
               interfaceCommon.method_1();
           }
   
           public void depend2() {
               myInterface.method_2();
           }
   
           public void depend3() {
               myInterface.method_3();
           }
   
       }
   
       /**
        * 对外接口_公共部分
        */
       interface InterfaceCommon {
           void method_1();
       }
   
       /**
        * 对外接口_B实现部分
        */
       interface InterfaceB {
           void method_2();
           void method_3();
       }
   
       /**
        * 对外接口_D实现部分
        */
       interface InterfaceD {
           void method_4();
           void method_5();
       }
   
       /**
        * 接口实现类_B
        */
       static class B implements InterfaceCommon, InterfaceB {
   
           @Override
           public void method_1() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_1");
           }
   
           @Override
           public void method_2() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_2");
           }
   
           @Override
           public void method_3() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_3");
           }
   
       }
       /**
        * 接口实现类_D
        */
       static class D implements InterfaceCommon, InterfaceD {
   
           @Override
           public void method_1() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_1");
           }
   
           @Override
           public void method_4() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_4");
           }
   
           @Override
           public void method_5() {
               System.out.println(this.getClass().getSimpleName() + " 实现了 method_5");
           }
       }
   
   }
   ```

## 1.5，依赖倒转原则

