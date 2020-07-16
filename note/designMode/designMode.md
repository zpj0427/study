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

### 1.5.1，依赖倒转原则基本介绍

* 高层模块不应该依赖底层模块，二者都应该依赖其抽象
* **抽象不应该依赖细节，细节应该依赖抽象**
* 依赖倒转的核心思想是面向接口编程，即面向对象的多态特性
* 依赖倒转原则基于的设计理念是：**相对于细节的多边形，抽象的东西相对稳定很多**。所以以抽象为基础搭建的框架会比以细节为基础搭建的框架稳定很多。在java中，抽象指的是抽象类或者接口，细节就是对应的子类和实现类
* 使用**接口和抽象类**的目的是指定好规范，而把具体的细节交给它们的实现类和子类去完成，在调用方不涉及任何具体的操作

### 1.5.2，应用示例

1. 从一个邮件接收看依赖倒转原则的使用

   * 此时存在一个用户类和一个邮件类，用户需要接收邮件信息，用最直观的代码书写方式如下
   * 此时如果用户增加了一种消息收发途径，如微信，则此时需要对消息收发方式对应的方法进行重写
   * 继续扩展，如果用户一直增加消息收发途径，则该方法会一直重载下去

   ```java
   package com.self.designmode.discipline.dependreverse;
   
   /**
    * 顺序方式实现功能
    * @author pj_zhang
    * @create 2020-07-16 21:32
    **/
   public class DependSequence {
   
       public static void main(String[] args) {
           Person person = new Person();
           person.receive(new Email());
           // 增加微信方式
           person.receive(new WeChat());
       }
   
       static class Email {
           public void readMessage() {
               System.out.println("邮件方式发送消息...");
           }
       }
   
       static class WeChat {
           public void readMessage() {
               System.out.println("微信方式发送消息...");
           }
       }
   
       static class Person {
   
           // 通过通过邮件方式接收消息
           public void receive(Email email) {
               email.readMessage();
           }
   
           // 此时如果用户增加了接收方式, 如微信, 则需要进行重载
           public void receive(WeChat wechat) {
               wechat.readMessage();
           }
       }
   
   }
   ```

2. 从上一个直接方式写的代码可以看出，核心内容基本一致，这是传递的细节不同，如果能对细节进行抽象，通过抽象方式传递细节，实现细节依赖抽象，则细节部分的代码就会很简单

   ```java
   package com.self.designmode.discipline.dependreverse;
   
   /**
    * 设计模式七大原则_依赖倒转原则
    * @author pj_zhang
    * @create 2020-07-16 21:40
    **/
   public class DependReverse {
   
       public static void main(String[] args) {
           // 客户端的调用方法完全一致
           // 但是对于实现层来讲, 已经进行了代码的整合
           Person person = new Person();
           person.receive(new WeChat());
           person.receive(new Email());
       }
   
       // 此次我们先创建一个通讯的顶层接口
       interface ICommunication {
           // 在该接口中提供一个抽象的统一方法, 读消息
           // 所有通讯方式都实现该接口并实现该方法
           // 在方法中实现各自的通讯方式
           void readMessage();
       }
   
       static class Email implements ICommunication {
           @Override
           public void readMessage() {
               System.out.println("邮件收发消息...");
           }
       }
   
       static class WeChat implements ICommunication {
           @Override
           public void readMessage() {
               System.out.println("微信收发消息...");
           }
       }
   
       static class Person {
           // 在用户接受消息的时候, 让细节依赖抽象
           // 通过面向对象的多态传递, 我们不用知道该通讯方式到底是什么方式
           // 我们只需要知道, 它一定重写了通讯方法, 直接获取内容即可
           public void receive(ICommunication communication) {
               communication.readMessage();
           }
       }
   
   }
   ```

### 1.5.3，依赖关系传递的三种方式和应用示例

1. 接口方式传递：通过将顶层抽象接口作为方法的入参进行传递，实现在方法内通过抽象调用

   ```java
   package com.self.designmode.discipline.dependreverse;
   
   /**
    * 依赖倒转原则三种传递方式
    * @author pj_zhang
    * @create 2020-07-16 21:48
    **/
   public class TransmitMode {
   
       public static void main(String[] args) {
           Person_1 person = new Person_1();
           person.receive(new Alipay());
       }
   
       // 方式一: 接口传递, 通过形参传递为抽象引用进行调用
       interface ICommunication {
           void readMessage();
       }
   
       static class Alipay implements ICommunication {
           @Override
           public void readMessage() {
               System.out.println("支付宝...");
           }
       }
   
       static class Person_1 {
           public void receive(ICommunication communication) {
               communication.readMessage();
           }
       }
   
   }
   ```

2. 构造方法传递：通过在构建细节类时，直接作为构造参数将抽象接口传递为类成员变量，在后续调用中进行调用

   ```java
   package com.self.designmode.discipline.dependreverse;
   
   /**
    * 依赖倒转原则三种传递方式
    * @author pj_zhang
    * @create 2020-07-16 21:48
    **/
   public class TransmitMode {
   
       public static void main(String[] args) {
           Person_2 person = new Person_2(new Alipay());
           person.receive();
       }
   
       interface ICommunication {
           void readMessage();
       }
   
       static class Alipay implements ICommunication {
           @Override
           public void readMessage() {
               System.out.println("支付宝...");
           }
       }
   
       // 方式二: 构造方法传递, 通过构造细节类时, 传递调用类为抽象引用
       static class Person_2 {
           private ICommunication communication;
           public Person_2(ICommunication communication) {
               this.communication = communication;
           }
           public void receive() {
               communication.readMessage();
           }
       }
   
   }
   ```

3. setter方式传递：细节类中提供对外的setter方法，在需要使用抽象调用时，直接setter该抽象

   ```java
   package com.self.designmode.discipline.dependreverse;
   
   /**
    * 依赖倒转原则三种传递方式
    * @author pj_zhang
    * @create 2020-07-16 21:48
    **/
   public class TransmitMode {
   
       public static void main(String[] args) {
           Person_2 person = new Person_2(new Alipay());
           person.receive();
       }
   
       interface ICommunication {
           void readMessage();
       }
   
       static class Alipay implements ICommunication {
           @Override
           public void readMessage() {
               System.out.println("支付宝...");
           }
       }
       
       // 方式三: setter传递, 在需要调用时, 通过setter方法进行传递
       static class Person_3 {
           private ICommunication communication;
           
           public void receive() {
               communication.readMessage();
           }
           
           public void setCommunication(ICommunication communication) {
               this.communication = communication;
           }
       }
   
   }
   ```

### 1.5.4，依赖倒转原则的注意事项和细节

* 底层模块尽量有抽象类或者接口，能更好的维持程序稳定性
* 变量的声明类型尽量使用抽象类和接口，这样在变量引用和实际对象间，就存在一个缓冲区，利于程序扩展和优化
* 继承时遵循里式替换原则

## 1.6，里式替换原则

### 1.6.1，面向对象中继承的思考和说明

* 继承中有这样一层含义：父类中已经定义好的方法，其实是在定义一种规范和契约，虽然他不强制要求所有的子类都必须遵循这种契约，但是如果子类对这些已经实现的方法任意重写修改，则就会对整个继承体系造成影响
* **继承在带来便利的同时，也带来了弊端**。比如使用继承会给程序带来侵入性，程序的可移植性降低，增加对象之间的耦合性。在继承体系中，如果需要对父类进行修改，则必须考虑其子类实现，所有涉及到的子类都可能产生故障
* 那在编程中，应该如何更好的使用继承 -> **里式替换原则**

### 1.6.2，里式替换原则基本介绍

* 里式替换原则强调，如果对于每个类型为T1的对象o1，都有类型为T2的对象o2，使得以T1定义的所有程序P在其对象o1替换成o2之后，程序P的行为没有发生变化，那么类型T2就是T1的子类。换句话说，所有引用基类的地方都必须透明的可使用其子类对象
* 在使用继承时，应该遵循里式替换原则，在子类中尽量不要重写父类的方法
* 里式替换原则强调，继承其实是在增强两个类的耦合性，在适当的情况下，可以使用聚合，组合，依赖来代替继承

### 1.6.3，一个简单程序引发的问题

* 从这个程序我们可以看出，一次无意间的方法覆盖，可能就会完全改变方法的含义，给外部调用造成困扰

```java
package com.self.designmode.discipline.liskov;

/**
 * 由一个简单的继承问题引起里式替换原则
 * @author pj_zhang
 * @create 2020-07-16 23:05
 **/
public class SimpleExtend {

    public static void main(String[] args) {
        // 按道理是调的同一个方法, 但是有不同的结果
        new A().func_1(1, 2);
        new B().func_1(1, 2);
    }

    static class A {
        // 在A中, 该方法是求两个数之和
        public void func_1(int num1, int num2) {
            System.out.println("result: " + (num1 + num2));
        }
    }

    static class B extends A {
        // 在B中, 该方法是求两个数之差
        // B中可以理解为对A中的该方法不小心触发重写
        public void func_1(int num1, int num2) {
            System.out.println("result: " + (num1 - num2));
        }
    }
}
```

### 1.6.4，里式替换原则解决

* 从上面代码中，我们可以看到，同样的一个方法，在子类和父类中有不同的含义，造成原有功能出现偏差；在实际开发中，或许真实存在这样的场景，通过直接覆盖的方式可以减少工作量，但是在整个继承体系中不太友好，尤其在多态频繁的时候

* 通用的做法是，可以向上抽取，对于类A和类B继续向上抽取一个更基本的基类，类B如果还需要使用类A的方法，可以对类A进行组合

  ![1594912809203](E:\gitrepository\study\note\image\designMode\1594912809203.png)

  ```java
  package com.self.designmode.discipline.liskov;
  
  /**
   * 设计模式七大基础原则_里式替换原则
   * @author pj_zhang
   * @create 2020-07-16 23:20
   **/
  public class Liskov {
  
      public static void main(String[] args) {
          // 按道理是调的同一个方法, 但是有不同的结果
          new A().func_1(1, 2);
          new B().func_2(1, 2);
      }
  
      static class Base {
          // 向上抽取一个基类, 可能会实现一些公共方法
      }
  
      static class A extends Base {
          // A类自有方法
          public void func_1(int num1, int num2) {
              System.out.println("result: " + (num1 + num2));
          }
      }
  
      static class B extends Base {
  
          private A a = new A();
  
          // B类自有方法
          public void func_2(int num1, int num2) {
              System.out.println("result: " + (num1 - num2));
          }
  
          // B类组合使用A类的方法
          public void func_1(int num1, int num2) {
              a.func_1(num1, num2);
          }
      }
  
  }
  ```

## 1.7，开闭原则