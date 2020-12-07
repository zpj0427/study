1，设计模式的七大原则

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

## 1.4，接口隔离原则（Interface Segregation Principle）

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

## 1.5，依赖倒转原则（Dependence Inversion Principle）

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

## 1.6，里式替换原则（Liskov Substitution Principle）

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

## 1.7，开闭原则（Open Closed Principle）

### 1.7.1,开闭原则介绍

* 开闭原则（Open Close Principle）是编程中最基本，最重要的原则
* 一个软件实体类，其模块和功能应该是**对扩展开放（提供方），对修改关闭（使用方）**。用抽象构建框架，用实现构建细节
* 当软件需要变化时，尽量通过**扩展**来实现功能变化，而不是通过**修改**
* 编程中遵循其他原则，以及使用设计模式的目的就是遵循开闭原则

### 1.7.2，应用示例

1. 从一段代码开始

   ```java
   package com.self.designmode.discipline.ocp;
   
   /**
    * 从一个普通的问题看问题所在
    * @author LiYanBin
    * @create 2020-07-17 14:50
    **/
   public class OrdinaryFun {
   
       public static void main(String[] args) {
           Client client = new Client();
           client.draw(new Circle().typeEnum);
           client.draw(new Square().typeEnum);
           client.draw(new Triangle().typeEnum);
       }
   
       /**
        * 客户端调用
        */
       static class Client {
           public void draw(TypeEnum typeEnum) {
               if (TypeEnum.CIRCLE == typeEnum) {
                   System.out.println("绘制圆...");
               } else if (TypeEnum.SQUARE == typeEnum) {
                   System.out.println("绘制方形...");
               } else if (TypeEnum.TRIANGLE == typeEnum) {
                   System.out.println("绘制三角形...");
               }
           }
       }
   
       enum TypeEnum {
           // 圆, 正方形, 三角形
           CIRCLE, SQUARE, TRIANGLE
       }
   
       // 圆
       static class Circle {
           TypeEnum typeEnum;
           public Circle() {
               typeEnum = TypeEnum.CIRCLE;
           }
       }
   
       // 正方形
       static class Square {
           TypeEnum typeEnum;
           public Square() {
               typeEnum = TypeEnum.SQUARE;
           }
       }
   
       // 三角形
       static class Triangle {
           TypeEnum typeEnum;
           public Triangle() {
               typeEnum = TypeEnum.TRIANGLE;
           }
       }
   
   }
   ```

2. 优缺点分析

   * 该方法是一个比较标准的顺序代码，逻辑清晰，容易理解，简单易操作
   * 但事其明显违反了OCP原则，在后期需要添加实现方式，如绘制其他图形时，需要对各个部分进行修改
   * 此时我们可以在客户端做一个统一的抽象（对修改关闭），在提供方进行不同的细节扩展（对扩展开放）

3. 改进代码：将具体绘制方式向上抽取，抽取一个公共的父类，交给客户端进行引用，扩展部分根据引用的实际对象进行多态调用

   ```java
   package com.self.designmode.discipline.ocp;
   
   /**
    * 设计模式七大原则_OCP原则
    * @author PJ_ZHANG
    * @create 2020-07-17 15:31
    **/
   public class OCP {
   
       public static void main(String[] args) {
           Client client = new Client();
           client.draw(new Circle());
           client.draw(new Square());
           client.draw(new Triangle());
       }
   
       /**
        * 客户端调用
        */
       static class Client {
           public void draw(Shape shape) {
               shape.draw();
           }
       }
   
       interface Shape {
           void draw();
       }
   
       // 圆
       static class Circle implements Shape {
           @Override
           public void draw() {
               System.out.println("绘制圆...");
           }
       }
   
       // 正方形
       static class Square implements Shape {
           @Override
           public void draw() {
               System.out.println("绘制正方形...");
           }
       }
   
       // 三角形
       static class Triangle implements Shape {
           @Override
           public void draw() {
               System.out.println("绘制三角形...");
           }
       }
   
   }
   ```

## 1.8，迪米特法则

### 1.8.1，迪米特法则基本介绍

* 一个对象应该对其他对象保持最少的了解

* 类与类之间关系越大，耦合越大

* 迪米特法则（Demeter Principle）又叫最少知道原则，即一个类对自己依赖的类知道的越少越好，也就是说，被依赖的类不管多么复杂，都应该尽量将逻辑封装在类内部，对外只提供公共调用接口

* 迪米特法则还有一个更简单的定义：只与直接朋友沟通

  > 直接朋友：每个对象都可能与其他对象存在耦合关系，只要存在关系，即说明这两个对象之间是朋友关系。耦合的方式很多，依赖、组合、聚合、关联等。其中，我们称出现在成员变量，方法参数，方法返回值中的类为直接朋友，出现在局部变量中的类不是直接朋友。也就是说，陌生的类最好不要以局部变量形式出现在类的内部

### 1.8.2，应用示例

1. 从一段代码开始分析

   ```java
   package com.self.designmode.discipline.demeter;
   
   import com.alibaba.fastjson.JSON;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   
   import java.util.ArrayList;
   import java.util.List;
   
   /**
    * 没有使用迪米特法则的代码示例
    * @author PJ_ZHANG
    * @create 2020-07-17 17:12
    **/
   public class NotDemeterCode {
   
       public static void main(String[] args) {
           TeacherManager teacherManager = new TeacherManager();
           teacherManager.showDetails(new StudentManager());
       }
   
       @Data
       static class Student {
           private String name;
           public Student(String name) {this.name = name;}
       }
   
       @Data
       static class Teacher {
           private String name;
           public Teacher(String name) {this.name = name;}
       }
   
       static class StudentManager {
           public List<Student> allStudent() {
               List<Student> lstData = new ArrayList<>(10);
               for (int i = 0; i < 3; i++) {
                   lstData.add(new Student("张三" + i));
               }
               return lstData;
           }
       }
       static class TeacherManager {
           public List<Teacher> allTeacher() {
               List<Teacher> lstData = new ArrayList<>(10);
               for (int i = 0; i < 3; i++) {
                   lstData.add(new Teacher("李四" + i));
               }
               return lstData;
           }
   
           public void showDetails(StudentManager studentManager) {
               List<Student> lstStudent = studentManager.allStudent();
               System.out.println(JSON.toJSON(lstStudent));
               System.out.println(JSON.toJSON(this.allTeacher()));
           }
       }
   }
   ```

2. 从上一段代码可以看出，在方法`showDetails`中，存在局部对象类`Student`与主类`TeacherManager`并不是直接朋友，但是在局部方法中出现。按照迪米特法则，应该避免这种出现非直接朋友关系的耦合，此时将代码进行改进，由类`StudentManager`提供对外的公共方法以供调用

3. 改进代码如下

   ```java
   package com.self.designmode.discipline.demeter;
   
   import com.alibaba.fastjson.JSON;
   import lombok.Data;
   
   import java.util.ArrayList;
   import java.util.List;
   
   /**
    * 设计模式七大基础原则_迪米特法则
    * @author PJ_ZHANG
    * @create 2020-07-17 17:12
    **/
   public class DemeterCode {
   
       public static void main(String[] args) {
           TeacherManager teacherManager = new TeacherManager();
           teacherManager.showDetails(new StudentManager());
       }
   
       @Data
       static class Student {
           private String name;
           public Student(String name) {this.name = name;}
       }
   
       @Data
       static class Teacher {
           private String name;
           public Teacher(String name) {this.name = name;}
       }
   
       static class StudentManager {
           public List<Student> allStudent() {
               List<Student> lstData = new ArrayList<>(10);
               for (int i = 0; i < 3; i++) {
                   lstData.add(new Student("张三" + i));
               }
               return lstData;
           }
   
           public void showDetails() {
               System.out.println(JSON.toJSON(this.allStudent()));
           }
       }
       static class TeacherManager {
           public List<Teacher> allTeacher() {
               List<Teacher> lstData = new ArrayList<>(10);
               for (int i = 0; i < 3; i++) {
                   lstData.add(new Teacher("李四" + i));
               }
               return lstData;
           }
   
           public void showDetails(StudentManager studentManager) {
               studentManager.showDetails();
               System.out.println(JSON.toJSON(this.allTeacher()));
           }
       }
   }
   ```

### 1.8.3，迪米特法则注意事项和细节

* 迪米特法则旨在降低类之间的耦合关系
* <font color=red>由于每个类存在减少不了的必要依赖，所以迪米特法则只是要求减低类之间的耦合，而不是完全没有依赖关系，这也做不到！</font>

## 1.9，合成复用原则（Composite Reuse Principle）

### 1.9.1，基本介绍

* 原则上是尽量使用合成/集合/依赖等方式，而不是继承的方式

  ![1594979908593](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1594979908593.png)

## 1.10，设计原则核心思想

* 找出应用中可能需要变化之处，把这部分进行独立，不要和不需要变化的代码混在一起
* 针对接口编程，而不是针对实现编程
* 为了交互对象之间的送耦合而努力



# 2，UML类图

## 2.1，类关系（Dependency）

* **依赖，泛华（继承），实现，关联，聚合和组合**

## 2.2，依赖关系

* 依赖关系介绍
  * 成员变量可以作为类依赖关系
  * 返回值可以作为类依赖关系
  * 方法参数传递可以作为类依赖关系
  * 局部变量定义可以作为类依赖关系
  * 凡是在该类中出现的其他类，都可以作为该类的依赖类

* 代码示例

  ```java
  package com.self.designmode.uml.dependency;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-22 11:01
   **/
  public class Dependency {
  
      // 成员变量可以作为类依赖关系
      private MemberParam memberParam;
  
      // 返回值可以作为类依赖关系
      public ReturnData getData() { return null;}
  
      // 传参可以作为类依赖关系
      public void param(ParamData paramData) {}
  
      // 局部变量可以作为类依赖关系
      public void local() { LocalData localData = new LocalData(); }
  
  }
  
  class ReturnData {}
  
  class ParamData {}
  
  class MemberParam {}
  
  class LocalData {}
  
  ```

* 依赖关系类图

  * <font color=red>注意方向是从依赖类指向被依赖类</font>

  ![1595388280752](E:\gitrepository\study\note\image\designMode\1595388280752.png)

## 2.3，泛化关系（继承关系）（Generalization）

* 泛化关系介绍

  * 泛化关系就是继承关系，是依赖关系的一种特例

* 代码示例

  ```java
  package com.self.designmode.uml.generalization;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-22 11:09
   **/
  // 存在类继承, 即为泛华关系
  // 包括普通类继承和抽象类继承
  public abstract class Generalization extends Parent {
  }
  
  class Parent {}
  
  // 包括接口继承
  interface CliendInterface {}
  
  interface ParentInterface {}
  
  ```

* 依赖关系类图

  * <font color=red>注意箭头方向是从子类指向父类</font>

  ![1595388649946](E:\gitrepository\study\note\image\designMode\1595388649946.png)

## 2.4，实现关系（Implementation）

* 实现关系判断条件

  * 如果类A实现了接口B，则称A与B是实现关系，实现关系是依赖关系的一种特例

* 代码示例

  ```java
  package com.self.designmode.uml.implementation;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-22 11:35
   **/
  public class Implementation implements Interface {
  }
  
  interface Interface {}
  
  ```

* 实现关系类图

  * <font color=red>注意箭头方向从实现类指向接口</font>

  ![1595389622700](E:\gitrepository\study\note\image\designMode\1595389622700.png)

## 2.5，关联关系（Association）

* 关联关系介绍

  * 关联关系实际上就是类与类之间的关系，是依赖关系的特例
  * 关联关系具有**导航性**：即双向或者单向关系
  * 关系具有多重性，比如一对一(1V1)，一对多(1Vn)，多对多(nVm)，以下仅以一对一说明问题

* 代码示例

  ```java
  package com.self.designmode.uml.association;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-22 12:37
   **/
  public class OneClass {
      private OtherClass otherClass;
  }
  
  class OtherClass {
      // 单向图示中不存在该部分
      private OneClass oneClass;
  }
  ```

* 关联关系类图

  ![1595392650993](E:\gitrepository\study\note\image\designMode\1595392650993.png)

## 2.6，聚合关系（Aggregation）

* 聚合关系介绍

  * **聚合关系是整体和部分的关系，且整体和部分可以分开，是依赖关系的特例**
  * 在实现层面上，如果类A依赖类B，且类B对象不会随着类B对象的初始化而初始化，则可以称为聚合关系，说明它们没有强相关

* 代码示例

  ```java
  package com.self.designmode.uml.aggregation;
  
  /**
   * 聚合关系
   * @author PJ_ZHANG
   * @create 2020-07-22 12:17
   **/
  public class Computer {
      private Mouse mouse;
      private Moniter moniter;
      public void setMouse(Mouse mouse) { this.mouse = mouse; }
      public void setMoniter(Moniter moniter) { this.moniter = moniter; }
  }
  
  class Mouse {}
  
  class Moniter {}
  
  ```

* 聚合关系类图

  * <font color=red>注意箭头在主类端</font>

  ![1595391437069](E:\gitrepository\study\note\image\designMode\1595391437069.png)

## 2.7，组合关系（Composition）

* 组合关系基本介绍

  * **组合关系是整体和部分的关系，但是强调整体和部分不可分割，也是一种特殊的依赖关系**
  * 在实现层面上，如果类A依赖类B，且类A初始化会直接关联类B初始化，则类A与类B有组合关系
  * 如下示例，此时如果在代码中设置了`Person`和`Computer`的**级联删除**，此时关系就变为了组合

* 代码示例

  ```java
  package com.self.designmode.uml.composition;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-22 12:20
   **/
  public class Person {
      // 人和头部是强相关, 必须存在
      // 组合关系
      private Head head = new Head();
      // 和电脑可以分开, 聚合关系
      private Computer computer;
      public void setComputer(Computer computer) { this.computer = computer; }
  }
  
  class Computer {}
  
  class Head {}
  
  ```

* 组合关系类图

  * <font color=red>注意箭头在主类端</font>

  ![1595391909317](E:\gitrepository\study\note\image\designMode\1595391909317.png)

# 3，设计模式概述

## 3.1，设计模式基本介绍

* 设计模式是某类通用问题的进本解决方式，设计模式不是代码，设计模式代表了最佳实践
* 设计模式的本质提高**软件的维护性，通用性和扩展性，并降低软件的复杂度**

## 3.2，设计模式的类型

* 设计模式一共分为三大类，共23小类
* **创建型模式**：单例模式，工厂模式，原型模式，建造者模式
* **结构型模式**：适配器模式，桥接模式，装饰者模式，组合模式，外观模式，享元模式，代理模式
* **行为型模式**：模板方法模式，命令模式，访问者模式，迭代器模式，观察者模式，中介者模式，备忘录模式，解释器模式，状态模式，策略模式，职责链模式（责任链模式）

# 4，单例模式（Singleton）

## 4.1，单例模式基本介绍

* 所谓单例模式，就是通过一定的方式保证在系统中，对某一个类只存在一个对象实例，并且该类只提供一个获取该对象的方法（静态方法）

* 单例模式创建方式比较多，目前大致可以分为五类八种，后面为一一分析，<font color=red>其中标红表明不可取方式</font>，分别如下：
  * 饿汉式：静态常量，静态代码块
  * 懒汉式：<font color=red>线程不安全方式</font>，同步方法，<font color=red>同步代码块</font>
  * 双重检查方式（推荐）
  * 静态内部类方式（推荐）
  * 枚举方式（推荐）

## 4.2，饿汉式_静态常量&静态代码块

### 4.2.1，代码示例

```java
package com.self.designmode.singleton;

/**
 * 饿汉式加载, 包括两种方式:
 * * 静态常量加载
 * * 静态代码块加载
 * @author PJ_ZHANG
 * @create 2020-07-23 14:23
 **/
public class DirectLoading {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(StaticCodeBlock.getInstance())).start();
        }
    }

}

class StaticCodeBlock {
    private StaticCodeBlock() {}

    private static StaticCodeBlock singletion;

    // 静态代码块加载
    static {
        singletion = new StaticCodeBlock();
    }

    public static StaticCodeBlock getInstance() {
        return singletion;
    }
}

class StaticField {

    private StaticField() {}

    // 常量加载
    private static final StaticField SINGLETON = new StaticField();

    public static StaticField getInstance() {
        return SINGLETON;
    }

}
```

### 4.2.2，优缺点分析

* **优点**：写法简单，在类加载时候完成对象初始化，避免了多线程下产生的问题；*静态代码块只在类第一次加载的时候执行一次*
* **缺点**：在类加载的时候就完成了对象初始化，没有达到Lazy Loading的效果。如果始终都不对使用这个对象，则会造成内存浪费
* 因为JVM通过`classLoader`加载类是线程安全的，所以这种方式是依托JVM的性质，从虚拟机层面保证了单例的可行性。<font color=red>PS：记得之前有看过一些分析，说这种方式也不一定，后续！！！</font>

## 4.3，懒加载_线程不安全方式

### 4.3.1，代码示例

```java
class NotSafe {

    private NotSafe() {}

    private static NotSafe notSafe;

    public static NotSafe getInstance() {
        return null == notSafe ? notSafe = new NotSafe() : notSafe;
    }
}
```

### 4.3.2，优缺点分析

* <font color=red>首先，这种方式没有优点，不要用就行</font>

* 通过这种方式进行加载，单纯从代码上来看，感觉是没有问题的，但是在多线程环境下，如果线程A先抢到线程，进行了第一步判断，此时线程B抢到线程，并以此走完了全部流程，再切换到线程A，此时线程A已经判断过，会直接new对象，这样就会产生两个对象，可以在多线程下多跑几次看看效果

  ![1595486982796](E:\gitrepository\study\note\image\designMode\1595486982796.png)

## 4.4，懒加载_同步方法方式

### 4.4.1，代码示例

```java
class SynMethod {
    private SynMethod() {}

    private static SynMethod synMethod;

    public static synchronized SynMethod getInstance() {
        return null == synMethod ? synMethod = new SynMethod() : synMethod;
    }
}
```

### 4.4.2，优缺点分析

* **优点**：首先，这种方式是绝对满足要求的，满足单例模式，满足懒加载
* **缺点**：但是，这种方式在满足要求的时候，有些为了满足要求而满足要求。对象单例化本来就是一次性的过程，在第一次创建的时候可能会存在线程竞争导致创建出多个对象。但是，挺过第一波后，后续对象获取都是基于对象创建完成获取的，其实不需要有同步考虑。同步方法之后，后续对象读取都需要排队进行，性能甚至不如直接加载，优点舍本逐末。<font color=red>我甚至感觉这个破玩意就是凑数的</font>

## 4.5，懒加载_同步代码块

### 4.5.1，代码示例

```java
class SynMethod {
    private SynMethod() {}

    private static SynMethod synMethod;

    public static synchronized SynMethod getInstance() {
        return null == synMethod ? synMethod = new SynMethod() : synMethod;
    }
}
```

### 4.5.2，优缺点分析

* <font color=red>这个也不要用，没有优点</font>
* **缺点**：同步代码块，就是通过`synchronized`关键字，对一段代码进行包裹，在该段代码内，保证线程同步。在创建单例对象时，能满足同步的代码块就是创建对象的代码块。而`synchronized`能同步的代码， 也就是是分为带判断和不带判断语句两种。
  * 带判断语句：等同于整个方法同步，与同步方法方式完全一致
  * 不带判断语句：就是在线程不安全方式的基础上，加了个不安全部分的同步，其实没有任何意义，还是不安全
  * <font color=red>这个也是凑数的</font>

## 4.6，双重校验

### 4.6.1，代码示例

```java
class Singleton {
    private Singleton() {}

    private static Singleton singleton;

    public static Singleton getInstance() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

### 4.6.2，优缺点分析

* Double-Check是多线程开发中经常用到的，是比较推荐的创建方式
* 通过两层循环中间加一层同步代码块进行实现
* 首先，外层判断，对不存在线程竞争的初次创建和其他后续对象获取进行判断，如果对象已经创建直接返回，此处不存在锁处理，不会影响性能
* 其次，中间的同步代码块部分，对存在线程竞争的初次创建，如果存在多道线程同时过了第一道if，则在同步代码块部分必须要排队进行处理，此时只能一道线程一道线程的去执行同步代码快里面的代码；在这一步里面，能同时抢占的可能性本来就很小，所以这部分排队等候的线程也就只有个位数
* 最后，内层判断，同步代码块内代码，多个线程排队执行，所以不会存在重复创建，如果第二个线程排队进入，此时判断对象已经创建完成，返回即可
* <font color=red>该方式是单例模式的推荐方式</font>

## 4.7，静态内部类

### 4.7.1，代码示例

```java
class OuterClass {
    private OuterClass() {}

    public static OuterClass getInstance() {
        return InnerClass.OUTER_CLASS;
    }

    static class InnerClass {
        private static final OuterClass OUTER_CLASS = new OuterClass();
    }
}
```

### 4.7.2，优缺点分析

* 与饿汉式加载原理基本一致，依托类加载机制，通过类加载机制保证线程同步
* 与饿汉式不同的是，在单例类里面会通过一个静态内部类来创建该类对象，在外部类（单例类）加载时，此时不会加载外部类，只有在进行对象创建，需要调用到内部类，此时内部类才会被加载，并同步创建外部类对象，通过外部类的对象获取方法返回
* <font color=red>个人感觉该方法由于双重校验，也是单例模式的推荐方式</font>

## 4.8，枚举方式

### 4.8.1，代码示例

```java
enum EnumSingleton {
    INSTANCE
}
```

### 4.8.2，优缺点分析

* 通过JDK1.5引入的枚举机制来实现单例化。不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象
* <font color=red>这种方式也是Effective Java作者Josh Bloch推荐的方式</font>

# 5，工厂模式（Factory）

* 工厂模式在逻辑上可以分为三种：简单工厂模式，工厂方法模式和抽象工厂模式。其中简单工厂模式不属于23种设计模式。
* 从实际中理解三种工厂模式，大致可以理解为工厂发展的三个阶段，下面将从一个专营炸鸡，汉堡，可乐的小店说起，可能不是很具体，但就是那么回事

## 5.1，简单工厂模式

### 5.1.1，基本介绍

* 简单工厂模式属于**创建者模式**，是工厂模式的一种，由一个工厂对象决定建出哪一种产品类的实例。简单工厂模式是工厂模式家族中最简单使用的模式
* 简单工厂模式：定义了一个创建对象的类，由这个类来封装实例化具体对象的行为

### 5.1.2，具体业务场景分析

* 现在有一家快餐店，主营汉堡包，炸鸡，可乐三种产品
* 在店铺开业之初，所有商品都由一家上游工厂进行提供，客户端只需要操作需要什么即可完成商品构建

### 5.1.3，类图

* 简单工厂模式中存在一个商品的工厂模型和一个向上抽取的商品接口
* 具体商品类型分别作为商品抽象类的子类，并依赖在工厂模型类中
* 工厂类通过`createProduct()`方法创建具体商品模型并多态返回具体商品对象
* 简单来说，简单工厂模式是在代码中通过if-else层级进行封装，使代码职能更加专一

![1595516848762](E:\gitrepository\study\note\image\designMode\1595516848762.png)

### 5.1.4，代码实现

* 顶层产品抽象类：Product

```java
public abstract class Product {

    public abstract void show();

}
```

* 商品类：炸鸡

```java
public class Chicken extends Product {
    @Override
    public void show() {
        System.out.println("这是一只炸鸡...");
    }
}
```

* 商品类：可乐

```java
public class Cola extends Product {
    @Override
    public void show() {
        System.out.println("这是一杯可乐...");
    }
}
```

* 商品类：汉堡包

```java
public class Hamburger extends Product {
    @Override
    public void show() {
        System.out.println("这是一个汉堡包...");
    }
}
```

* 简单工厂类：商品构建类

```java
public class ProductFactory {

    public static Product createProduct(String type) {
        if ("chicken".equals(type)) {
            return new Chicken();
        } else if ("cola".equals(type)) {
            return new Cola();
        } else if ("hamburger".equals(type)) {
            return new Hamburger();
        }
        return null;
    }

}
```

* 客户端

```java
public class Client {

    public static void main(String[] args) {
        // 要一杯可乐
        Product product = ProductFactory.createProduct("cola");
        product.show();
        // 要一个汉堡
        product = ProductFactory.createProduct("hamburger");
        product.show();
        // 再来一个炸鸡
        product = ProductFactory.createProduct("chicken");
        product.show();
    }

}
```

### 5.1.5，优缺点分析

* **优点**：简单工厂模式将具体对象的创建交由工厂来完成，在一定程度上精简了代码结构，从整体上对具体的创建提供了一个范式。
* **缺点**：简单工厂模式只是将创建对象的动作进行了向上抽取。如果店铺中添加了其他产品，则对应的需要在工厂类中添加分支，不符合开闭原则

## 5.2，工厂方法模式

### 5.2.1，基本介绍

* 工厂方法模式是对简单工厂模式的的完善和扩充
* 工厂方法模式在简单工厂模式的基础上，向上抽取了一个抽象工厂，通过抽象工厂派生出一个具体工厂的子类
* 最后通过这个派生出的具体工厂进行商品创建

### 5.2.2，具体业务场景分析

* 接简单工厂模式
* 现在，这家快餐店经营火爆，目前的上游商品提供方式已经不能满足快餐店的基本需求
* 快餐店需要商品更专业，更多元。这样就提出了商品提供的专一化和后续多元扩展的可能性（此处个人感觉可嵌套简单工厂模式）

### 5.2.3，类图

![1595516752351](E:\gitrepository\study\note\image\designMode\1595516752351.png)

* 工厂方法模式提供向上的商品类和工厂类
* 分别创建若干的具体工厂类实现抽象工厂
* 具体商品类实现商品接口并依赖于其对应的具体工厂类
* 多态创建工厂，并通过工厂多态返回具体商品对象，完成对象创建

### 5.2.4，代码实现

* 工厂方法模式已经列出的代码不再罗列，这部分只罗列出工厂和客户端的变化

* 工厂顶层接口

  ```java
  public interface IProductFactory {
      Product createProduct();
  }
  ```

* 具体工厂：HanburgerFactory

  ```java
  public class HambugerFactory implements IProductFactory {
      @Override
      public Product createProduct() {
          // 如果汉堡包类型过多, 可继续套用简单工厂模式
          return new Hamburger();
      }
  }
  ```

* 具体工厂：ChickenFactory

  ```java
  public class ChickenFactory implements IProductFactory {
      @Override
      public Product createProduct() {
          // 如果炸鸡类型过多, 可继续套用简单工厂模式
          return new Chicken();
      }
  }
  ```

* 具体工厂：ColaFactory

  ```java
  public class ColaFactory implements IProductFactory {
      @Override
      public Product createProduct() {
          // 如果可乐类型过多, 可继续套用简单工厂模式
          return new Cola();
      }
  }
  ```

* 客户端

  ```java
  public class Client {
  
      public static void main(String[] args) {
          // 来一个炸鸡
          IProductFactory productFactory = new ChickenFactory();
          productFactory.createProduct().show();
          // 来一个汉堡
          productFactory = new HambugerFactory();
          productFactory.createProduct().show();
          // 来一杯可乐
          productFactory = new ColaFactory();
          productFactory.createProduct().show();
      }
  
  }
  ```

### 5.2.5，优缺点分析

* 工厂方法模式弥补了简单工厂模式中分支过多，代码维护困难的问题，符合Java开闭原则
* 每一个商品的添加，都对应的需要添加一个具体的商品类和工厂类，这不可避免的造成整体类结构复杂。当然，也是设计模式的通病
* 最后，如果业务继续扩展，一个具体工厂依然满足不了商品需求，此时就需要进入到抽象工厂模式

## 5.3，抽象工厂模式

### 5.3.1，基本介绍

* 抽象工厂模式是对简单工厂模式和工厂方法模式的整合，提出了"产品族"的概念

### 5.3.2，具体业务场景分析

* 接工厂方法模式
* 此时快餐店的生意已经好出了天际，开始有了连锁加盟店，而且连锁加盟店扩展到全国加盟甚至全球加盟
* 在这样的体量下，我们刚才提高的简简单单的几个工厂肯定是不够用的。而且针对不同的地方，产品的风味肯定需要因地制宜进行改良
* 这样为了减少成本，更好的当地进行推广，肯定需要在每一个大的地区都有一系列对应的工厂提供对应的商品。但是工厂过多之后需要如何对工厂和商品进行管理，就有了产品族的概念，继续向上抽取

### 5.3.3，类图

![1595518331585](E:\gitrepository\study\note\image\designMode\1595518331585.png)

* 抽象工厂模式提供了用于创建产品族的抽象工厂类和相对应的抽象产品接口
* 具体产品组工厂实现抽象工厂并重写抽象方法
* 具体产品族工厂类依赖具体工厂进行产品组对应产品创建
* 具体工厂创建产品共客户使用

### 5.2.4，代码实现

* 同样在工厂方法基础上填充没有的方法

* 产品族顶层抽象接口：IFactory

  ```java
  public interface IFactory {
  
      Product createChicken();
  
      Product createHamburger();
  
      Product createCola();
  }
  ```

* 产品族具体工厂类：ChinaFactory

  ```java
  public class ChinaFactory implements IFactory {
      @Override
      public Product createChicken() {
          return new ChickenFactory().createProduct();
      }
  
      @Override
      public Product createHamburger() {
          return new HamburgerFactory().createProduct();
      }
  
      @Override
      public Product createCola() {
          return new ColaFactory().createProduct();
      }
  }
  ```

* 产品族具体工厂类：USAFactory

  ```java
  public class USAFactory implements IFactory {
      @Override
      public Product createChicken() {
          return new ChickenFactory().createProduct();
      }
  
      @Override
      public Product createHamburger() {
          return new HamburgerFactory().createProduct();
      }
  
      @Override
      public Product createCola() {
          return new ColaFactory().createProduct();
      }
  }
  ```

* 客户端

  ```java
  public class Client {
  
      public static void main(String[] args) {
          IFactory factory = new ChinaFactory();
          factory.createChicken().show();
          factory.createHamburger().show();
          factory.createCola().show();
      }
  
  }
  ```

* <font color=red>此处只是为了简单说明问题，具体不同地区的工厂肯定有不同方式的产品实现形式，每一种产品又有多种外在的表现形式，这种可以通过三种工厂模式的灵活嵌套来完成。工厂模式，通过符合OCP原则的一系列处理，不可避免的会存在类爆炸</font>

### 5.2.5，优缺点分析

* 抽象工厂模式，提出了产品族的概念，对同一模块下的产品行为进行了整合，有利于模块化的业务模型处理
* 不可否认，不合理的应用抽象工厂模式，极易产生类结构爆炸。<font color=red>合理的也会</font>

# 6，原型模式（Prototype）

## 6.1，基本介绍

* 原型模式是通过原型实例指定创建对象的种类，并通过拷贝这些原型，创建新的对象
* 原型模式是一种**创建型设计模式**，允许通过一个对象再创建一个可定制的对象，且不用对外暴露创建过程
* 原型模式拷贝对象的方式，分为浅拷贝和深拷贝两种：
* 浅拷贝通过JDK提供的API可直接进行处理，只会改变外部对象的地址，对内部引用对象地址不会改变
* 深拷贝需要通过一些其他途径，如序列化，递归拷贝等，关联对内部引用对象地址进行改变，新对象与原对象不会再有任何联系
* <font color=red>在Spring中的使用：Bean创建方式，单例/原型</font>

## 6.2，类图

![1595583402092](E:\gitrepository\study\note\image\designMode\1595583402092.png)

* 原型模式分为浅拷贝和深拷贝两种方式，两种分别对应不同的类结构

* `Prototype`表示需要进行拷贝的类对象

* 无论什么类，都会有顶层父类`Object`，而浅拷贝会依托于父类的`clone()`方法

* 浅拷贝需要使用父类的`clone()`方法，则其类必须实现接口`Cloneable`，否则会报异常`CloneNotSupportedException`，具体可以看`clone()`的方法描述

  > ```
  > CloneNotSupportedException：if the object's class does not support the {@code Cloneable} interface
  > ```

* 深拷贝通常基于对象的序列化完成，对象序列化，其类必须实现序列化接口`Serializable`

## 6.2，浅拷贝 — 克隆方式

### 6.2.1，基本介绍

* 浅拷贝是基于顶层父类`Object`的方法`clone()`进行实现，对应实体类必须实现`Cloneable接口`
* 浅拷贝完成后，拷贝后对象与原对象相比，外层对象即该对象会重新构造地址创建，与原对象地址不同
* 对于原对象内部属性，如果该属性是基本类型属性，浅拷贝会直接进行值传递，后续数据修改不会引起原对象关联修改
* <font color=red>对于内部属性是引用类型的属性，浅拷贝会直接赋值其地址到拷贝后的对象，此时原对象和拷贝后对象内部引用类型的成员属性地址公用一个，此时无论对哪一个进行修改，都会关联影响另外一个属性</font>

### 6.2.1，代码示例

* 原型类

  ```java
  package com.self.designmode.prototype;
  
  import lombok.Getter;
  import lombok.Setter;
  
  /**
   * 浅拷贝
   * * 方法必须实现Cloneable接口, 不然在调用clone()方法时会报异常
   * CloneNotSupportedException  if the object's class does not support the {@code Cloneable} interface
   *
   * * 浅拷贝问题:
   * * 浅拷贝只会对直接对象进行初始化, 如果该对象内部还存在其他引用类型对象, 则不会进行初始化
   * * 此时会将内部引用类型的地址, 直接赋值给新创建的外部对象
   * * 因为地址没有变化, 如果此时对原对象的该内部引用进行修改, 会关联修改现有对象
   * @author PJ_ZHANG
   * @create 2020-07-24 17:12
   **/
  @Getter
  @Setter
  public class ShallowCopy implements Cloneable {
      private String name;
      private String addr;
      private ShallowCopy inner;
  
      @Override
      protected Object clone() throws CloneNotSupportedException {
          return super.clone();
      }
  }
  ```

* 客户端

  ```java
  package com.self.designmode.prototype;
  
  /**
   * 原型模式,
   * * 分为深拷贝和浅拷贝两种
   * @author PJ_ZHANG
   * @create 2020-07-24 17:11
   **/
  public class Prototype {
  
      public static void main(String[] args) throws CloneNotSupportedException {
          ShallowCopy shallowCopy = new ShallowCopy();
          ShallowCopy inner = new ShallowCopy();
          inner.setName("inner张三");
          shallowCopy.setName("张三");
          shallowCopy.setInner(inner);
          ShallowCopy copy = (ShallowCopy) shallowCopy.clone();
          System.out.println("原对象    : " + shallowCopy.getName() + ",      地址: " + shallowCopy);
          System.out.println("拷贝对象  : " + copy.getName() + ",      地址: " + copy);
          ShallowCopy copyIn = copy.getInner();
          System.out.println("原内对象  : " + inner.getName() + ", 地址: " + inner);
          System.out.println("拷贝内对象: " + copyIn.getName() + ", 地址: " + copyIn);
          // 修改名称
          copyIn.setName("Update");
          System.out.println(inner.getName());
          System.out.println(copyIn.getName());
      }
  }
  ```

* 打印结果

  ![1595584315894](E:\gitrepository\study\note\image\designMode\1595584315894.png)

## 6.3，深拷贝 — 序列化方式

### 6.3.1，基本介绍

* 浅拷贝存在内部引用类型属性没有重新构建地址，深拷贝就是解决这个问题
* 深拷贝对基本类型属性进行值复制
* 对于成员类型属性也会重新构造内存地址，并复制该引用类型对象中每个属性的值到新构造的对象中，直到处理完该对象内部的所有可达对象（递归处理）
* 深拷贝方式通常使用序列化来进行实现，也可通过Json进行转换，此处通过序列化实现，更清晰

### 6.3.2，代码示例

* 原型类

  ```java
  package com.self.designmode.prototype;
  
  import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
  import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
  import lombok.Getter;
  import lombok.Setter;
  
  import java.io.*;
  import java.util.stream.Stream;
  
  /**
   * 浅拷贝
   * * 方法必须实现Cloneable接口, 不然在调用clone()方法时会报异常
   * CloneNotSupportedException  if the object's class does not support the {@code Cloneable} interface
   *
   * * 浅拷贝问题:
   * * 浅拷贝只会对直接对象进行初始化, 如果该对象内部还存在其他引用类型对象, 则不会进行初始化
   * * 此时会将内部引用类型的地址, 直接赋值给新创建的外部对象
   * * 因为地址没有变化, 如果此时对原对象的该内部引用进行修改, 会关联修改现有对象
   * @author PJ_ZHANG
   * @create 2020-07-24 17:12
   **/
  @Getter
  @Setter
  public class ShallowCopy implements Serializable {
      private String name;
      private String addr;
      private ShallowCopy inner;
  
      public ShallowCopy deepCopy() {
          ByteArrayOutputStream byteArrayOutputStream = null;
          ObjectOutputStream objectOutputStream = null;
          ByteArrayInputStream byteArrayInputStream = null;
          ObjectInputStream objectInputStream = null;
          try {
              // 写对象到内存中
              byteArrayOutputStream = new ByteArrayOutputStream();
              objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
              objectOutputStream.writeObject(this);
              objectOutputStream.flush();
              // 从内存中读对象
              byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
              objectInputStream = new ObjectInputStream(byteArrayInputStream);
              Object copyObject = objectInputStream.readObject();
              return (ShallowCopy) copyObject;
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              try {
                  byteArrayOutputStream.close();
                  objectOutputStream.close();
                  byteArrayInputStream.close();
                  objectOutputStream.close();
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
          return null;
      }
  }
  ```

* 客户端

  ```java
  package com.self.designmode.prototype;
  
  /**
   * 原型模式,
   * * 分为深拷贝和浅拷贝两种
   * @author PJ_ZHANG
   * @create 2020-07-24 17:11
   **/
  public class Prototype {
  
      public static void main(String[] args) throws CloneNotSupportedException {
          ShallowCopy shallowCopy = new ShallowCopy();
          ShallowCopy inner = new ShallowCopy();
          inner.setName("inner张三");
          shallowCopy.setName("张三");
          shallowCopy.setInner(inner);
          // 浅拷贝
          // ShallowCopy copy = (ShallowCopy) shallowCopy.clone();
          // 深拷贝
          ShallowCopy copy = shallowCopy.deepCopy();
          System.out.println("原对象    : " + shallowCopy.getName() + ",      地址: " + shallowCopy);
          System.out.println("拷贝对象  : " + copy.getName() + ",      地址: " + copy);
          ShallowCopy copyIn = copy.getInner();
          System.out.println("原内对象  : " + inner.getName() + ", 地址: " + inner);
          System.out.println("拷贝内对象: " + copyIn.getName() + ", 地址: " + copyIn);
          // 修改名称
          copyIn.setName("Update");
          System.out.println(inner.getName());
          System.out.println(copyIn.getName());
      }
  }
  ```

* 打印结果

  ![1595585130241](E:\gitrepository\study\note\image\designMode\1595585130241.png)

## 6.4，注意事项和细节

* 创建的新对象比较复杂时，可以通过原型模式简化创建过程，提示也能够提高效率
* 不用重新初始化对象，而是动态的获得对象运行时的状态
* 如果原始对象发生变化（属性增减），其克隆对象也会对应的对该部分变更属性进行处理，无需修改代码
* 在实现深拷贝的时候可能会需要比较复杂的代码（如递归处理，序列化处理）
* **缺点**：需要为每一个类配备一个克隆方法，对新增类影响不大，如果项目中突然引入，需要对全项目进行修改，势必修改大量源代码
* <font color=red>原型模式可完全通过JSON转换来实现，先序列化为字符串，再由字符串转为对象，即可实现一次深拷贝，对实体类零入侵，前面一大堆就是演示过程！！！</font>

# 7，建造者模式（Build）

## 7.1，基本介绍

* 建造者模式（Builder Pattern）是一种对象构建模式，可以将复杂对象的建造过程抽象出来，使这个抽象过程的不同实现过程可以构造出不同的对象
* 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过复杂对象的类型和内容就可以创建它们，用户不需要知道具体的构建细节
* <font color=red>在JDK中的应用：StringBuilder</font>

## 7.2，四个基本角色

* `Product`：**产品角色**，一个具体的产品类
* `AbstractBuilder`：**抽象建造者**，抽取一个向上的抽象类，定义具体产品的创建细节模型
* `ConcreteBuilder`：**具体建造者**，实现抽象建造者，并重写产品创建的细节方法，用于完成装配产品的各个部件
* `Director`：**指挥者**，构建一个使用`AbstractBuilder`类的对象，主要用于构建一个复杂对象。有两部分的功能：
  * 隔离了客户与对象的生产过程：对外连接客户，对内连接建造者
  * 负责控制对象的产品过程：根据客户端传递的参数，合理的控制创建过程，

## 7.3，类图

![1595678859455](E:\gitrepository\study\note\image\designMode\1595678859455.png)

* 建造者的层次结构，定义一个抽象的建造者`AbstractBuilder`，并抽象化构造细节；子类即具体建造者类`ConcreteBuilder`，继承抽象类并重写这部分方法，建造完成后，通过抽象类的`build()`方法直接返回构造完成的对象。
  * 此处抽象类组合了产品类`Product`，是在抽象类进行产品对象的直接初始化
  * 字节依赖产品类`Product`，是在子类中给父类构建好的产品对象赋值
* 指挥者类`Director`中，组合了抽象建造者`AbstractBuilder`，是需要通过具体建造者类进行对象建造，体现了**对内连接建造者**，同时被客户端`Client`依赖，体现了**对外连接客户端**
* 客户端`Client`中，依赖指挥者`Director`创建产品，在创建时传递一系列基本属性，指挥类`Director`对属性进行基本组装，并通过实际建造者类`ConcreteBuilder`传递到对应的实现细节进行细节装配，最后通过`AbstractBuilder.build()`方法返回具体构建完成的产品`Product`

## 7.4，代码实现

* 产品类：`Product`

  ```java
  package com.self.designmode.builder;
  
  import lombok.Getter;
  import lombok.Setter;
  import lombok.ToString;
  
  /**
   * 产品: 房子
   * @author pj_zhang
   * @create 2020-07-25 16:21
   **/
  @Getter
  @Setter
  @ToString
  public class Product {
      private int height;
      private int size;
  }
  ```

* 抽象建造者类：`AbstractBuilder`

  ```java
  package com.self.designmode.builder;
  
  /**
   * 抽象建造类
   * @author pj_zhang
   * @create 2020-07-25 16:35
   **/
  public abstract class AbstractBuilder {
  
      protected Product house = new Product();
  
      protected abstract void buildHeight(int height);
  
      protected abstract void buildSize(int size);
  
      public Product build() {
          return house;
      }
  
  }
  ```

* 具体建造者类1：`CommonBuilder`

  ```java
  package com.self.designmode.builder;
  
  /**
   * 具体建造类: 普通房子建造
   * @author pj_zhang
   * @create 2020-07-25 16:37
   **/
  public class CommonBuilder extends AbstractBuilder {
      @Override
      protected void buildHeight(int height) {
          house.setHeight(height);
      }
  
      @Override
      protected void buildSize(int size) {
          house.setSize(size);
      }
  }
  ```

* 具体建造者类2：`HighBuilder`

  ```java
  package com.self.designmode.builder;
  
  /**
   * 具体建造类: 高楼大厦建造
   * @author pj_zhang
   * @create 2020-07-25 16:37
   **/
  public class HighBuilder extends AbstractBuilder {
      @Override
      protected void buildHeight(int height) {
          house.setHeight(height);
      }
  
      @Override
      protected void buildSize(int size) {
          house.setSize(size);
      }
  }
  ```

* 指挥者类：`BuilderDirector`

  ```java
  package com.self.designmode.builder;
  
  /**
   * 指挥类: 建造指挥类
   * @author pj_zhang
   * @create 2020-07-25 16:39
   **/
  public class BuilderDirector {
      AbstractBuilder abstractBuilder;
  
      public BuilderDirector(AbstractBuilder builder) {
          this.abstractBuilder = builder;
      }
  
      public Product build(int height, int size) {
          abstractBuilder.buildHeight(height);
          abstractBuilder.buildSize(size);
          return abstractBuilder.build();
      }
  }
  ```

* 客户端：`Client`

  ```java
  package com.self.designmode.builder;
  
  /**
   * @author pj_zhang
   * @create 2020-07-25 16:41
   **/
  public class Client {
      public static void main(String[] args) {
          BuilderDirector director = new BuilderDirector(new CommonBuilder());
          Product house = director.build(80, 200);
          System.out.println(house);
      }
  }
  ```

## 7.5，注意事项和细节

* 客户端不需要知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建出不同的对象
* 每一个具体建造者都相对独立，与其他建造者无关；因此可以很方便的替换建造者或者增加新的建造者，用户根据不同的建造者即可得到不同的对象
* **更加精细的控制建造过程**。将复杂产品的创建步骤分解为不同的地方，使得创建过程更加清晰，也更加能方便的进行控制
* 增加新的创建者无需修改原有类库的代码，指挥者类也是面向顶层抽象类，系统扩展方便，符合**开闭原则**
* 通过建造者模式创建的对象具有较多的共同点，其组成部分相似，如果产品的差异性很大，则不适合使用建造者模式，存在一定的限制
* 如果产品内部变化复杂，可能会导致要使用更多的建造者类进行建造，因此在这种情况下，要考虑是否适用
* **抽象工厂模式** VS **建造者模式**
  * 抽象工厂模式是对产品族的管理，不需要关心构建过程，只需要知道什么工厂生产什么产品即可
  * 建造者模式则要求按照指定的蓝图设计产品，主要目的是经过配件组装成为新的产品

# 8，适配器模式（Adapter）

## 8.1，基本介绍

* 适配器模式是将某个类的接口转换为客户端期望的另一个接口表示，**主要目的是兼容性**，让原本不能工作的接口经过一次转换适配后可以正常工作

* 适配器属于**结构性模式**

* 主要可以分为三类：**类适配器**，**对象适配器**，**接口适配器**

  ![1595824104472](E:\gitrepository\study\note\image\designMode\1595824104472.png)

## 8.2，类适配器

### 8.2.1，基本介绍

* 类适配器是基于继承原则，对原有功能进行扩展，并适配到具体功能

### 8.2.2，类图

![1595831985004](E:\gitrepository\study\note\image\designMode\1595831985004.png)

* 定义适配器顶层接口：`IVoltageAdapter`
* 定义具体适配器类：`PhoneVoltageAdapter`
* 具体适配器类继承原有标准类`NormalVoltage`，从该类中获取初始方法，并实现适配器顶层接口，作为后续多态调用
* 具体产品类依赖适配器顶层接口，通过传入的具体实例多态调用实现方法
* 客户端依赖适配器具体类和接口构造多态，传递到产品类中进行调用

### 8.2.3，代码示例

* 原始类：`NormalVoltage`

  ```java
  package com.self.designmode.adapter.classadapter;
  
  /**
   * 适配器: 被适配类, 原类, 标准电压220V
   * @author PJ_ZHANG
   * @create 2020-07-27 14:12
   **/
  public class NormalVoltage {
      public int voltage() {
          System.out.println("适配器源类... 获取220V电压...");
          return 220;
      }
  }
  ```

* 适配器顶层接口：`IVoltageAdapter`

  ```java
  package com.self.designmode.adapter.classadapter;
  
  /**
   * 适配器: 电压适配顶层接口
   * @author PJ_ZHANG
   * @create 2020-07-27 14:12
   **/
  public interface IVoltageAdapter {
      int voltage();
  }
  ```

* 适配器具体类：`PhoneVoltageAdapter`

  ```java
  package com.self.designmode.adapter.classadapter;
  
  /**
   * 适配器类: 手机电压
   * @author PJ_ZHANG
   * @create 2020-07-27 14:17
   **/
  public class PhoneVoltageAdapter extends NormalVoltage implements IVoltageAdapter {
      @Override
      public int voltage() {
          // 获取标准电压
          int voltage = super.voltage();
          // 获取手机充电标准电压5V
          return voltage / 44;
      }
  }
  ```

* 产品类：`Phone`

  ```java
  package com.self.designmode.adapter.classadapter;
  
  /**
   * 使用类
   * @author PJ_ZHANG
   * @create 2020-07-27 14:19
   **/
  public class Phone {
      public void charge(IVoltageAdapter voltageAdapter) {
          int voltage = voltageAdapter.voltage();
          System.out.println("对手机进行充电, 充电电压: " + voltage);
      }
  }
  ```

* 客户端：`Client`

  ```java
  package com.self.designmode.adapter.classadapter;
  
  /**
   * 使用者
   * @author PJ_ZHANG
   * @create 2020-07-27 14:20
   **/
  public class Client {
      public static void main(String[] args) {
          // 构造手机
          Phone phone = new Phone();
          // 构造手机充电的适配器
          IVoltageAdapter voltageAdapter = new PhoneVoltageAdapter();
          // 手机充电
          phone.charge(voltageAdapter);
      }
  }
  ```

## 8.3，对象适配器

### 8.3.1，基本介绍

* 类适配器是基于继承实现
* 对象适配器基于组合实现，将原有类组合到具体适配器类中，对整体类结构进行解耦

### 8.3.2，类图

![1595832592763](E:\gitrepository\study\note\image\designMode\1595832592763.png)

* 大体思路与类适配器基本一致
* 根据合成服用原则，将对原始类的继承改为组合实现
* 具体适配器类初始化时，需要关联实现初始类，并作为参数传递

### 8.3.3，代码示例

* 具体适配器类变更：`PhoneVoltageAdapter`

  ```java
  package com.self.designmode.adapter.objectadapter;
  
  /**
   * 适配器类: 手机电压
   * @author PJ_ZHANG
   * @create 2020-07-27 14:17
   **/
      public class PhoneVoltageAdapter implements IVoltageAdapter {
  
      private NormalVoltage normalVoltage;
  
      public PhoneVoltageAdapter(NormalVoltage normalVoltage) {
          this.normalVoltage = normalVoltage;
      }
  
      @Override
      public int voltage() {
          // 获取标准电压
          int voltage = normalVoltage.voltage();
          // 获取手机充电标准电压5V
          return voltage / 44;
      }
  }
  ```

* 客户端变更：`Client`

  ```java
  package com.self.designmode.adapter.objectadapter;
  
  /**
   * 使用者
   * @author PJ_ZHANG
   * @create 2020-07-27 14:20
   **/
  public class Client {
      public static void main(String[] args) {
          // 构造手机
          Phone phone = new Phone();
          // 构造手机充电的适配器
          IVoltageAdapter voltageAdapter = new PhoneVoltageAdapter(new NormalVoltage());
          // 手机充电
          phone.charge(voltageAdapter);
      }
  }
  ```

## 8.4，接口适配器

### 8.4.1，基本介绍

* 核心思路：当不需要全部实现接口提供的方法时，可先用一个抽象类实现该接口，并为接口中的所有方法构造一个空实现，抽象类的子类可以有选择性的实现具体方法
* 适用于适配器存在多接口，但对某一具体需求可能只需要实现部分接口的场景

### 8.4.2，类图

![1595833830728](E:\gitrepository\study\note\image\designMode\1595833830728.png)

* 在对象适配器的基础上，在具体适配器和顶层接口之间，添加一个抽象类
* 该抽象类对接口方法进行空实现，这样抽象类的子类就不需要重写全部接口方法，只需要重写自己需要的方法

### 8.4.2，代码示例

* 适配器顶层接口：`IVoltageAdapter`

  ```java
  package com.self.designmode.adapter.interfaceadapter;
  
  /**
   * 适配器: 电压适配顶层接口
   * @author PJ_ZHANG
   * @create 2020-07-27 14:12
   **/
  public interface IVoltageAdapter {
      int voltage();
      void m1();
      void m2();
  }
  ```

* 抽象适配器类：`AbstractAdapter`

  ```java
  package com.self.designmode.adapter.interfaceadapter;
  
  /**
   * 抽象适配器类: 用于对适配器顶层接口的方法进行初始化空实现
   * @author PJ_ZHANG
   * @create 2020-07-27 15:12
   **/
  public abstract class AbstractAdapter implements IVoltageAdapter {
  
      @Override
      public int voltage() {
          return 0;
      }
  
      @Override
      public void m1() {
      }
  
      @Override
      public void m2() {
      }
  }
  ```

* 适配器具体类：`PhoneVoltageAdapter`

  ```java
  package com.self.designmode.adapter.interfaceadapter;
  
  /**
   * 适配器类: 手机电压
   * @author PJ_ZHANG
   * @create 2020-07-27 14:17
   **/
  public class PhoneVoltageAdapter extends AbstractAdapter {
  
      private NormalVoltage normalVoltage;
  
      public PhoneVoltageAdapter(NormalVoltage normalVoltage) {
          this.normalVoltage = normalVoltage;
      }
  
      @Override
      public int voltage() {
          // 获取标准电压
          int voltage = normalVoltage.voltage();
          // 获取手机充电标准电压5V
          return voltage / 44;
      }
  }
  ```

## 8.5，注意事项和细节

* 三种适配器方式，是根据原始类以怎么的方式到适配器类中区分的
  * 类适配器：继承方式
  * 对象适配器：组合方式
  * 接口适配器：抽象适配器类实现接口后，再以对象适配器方式实现
* 适配器模式的最大作用还是将原本不兼容的类融合到一起进行工作
* 三种适配器方式只是说法和实现方式不同，具体工作中可随意选择

# 9，桥接模式（Bridge）

## 9.1，问题引入

* 现在对不同类型不同品牌的手机实现操作编程，如下手机外观类型和对应品牌：

  ![1595844062823](E:\gitrepository\study\note\image\designMode\1595844062823.png)

* 则需要编写的代码类图可能如下：

  ![1595844104062](E:\gitrepository\study\note\image\designMode\1595844104062.png)

* 带来的问题如下：
  * 如果我们需要添加一个手机，则需要在各个类型下添加手机
  * 如果我们需要添加一个品牌，则需要在该品牌下添加各个类型的手机
* 这样会造成基本的类爆炸，可以使用桥接模式对实现（手机品牌）和抽象（手机类型）分别进行向上抽取，通过抽象依赖实现的方式增强代码维护性

## 9.2，基本介绍

* 桥接模式是指将**实现**和**抽象**放在两个不同的类层次中，并可以进行独立改变。桥接模式是一种结构性设计模式
* 桥接模式基于类的最小设计原则，使用封装，聚合和继承等行为让不同的类承担不同的职责。主要特点是把**抽象（Abstraction）**和**实现（Implementation）**分离开来，从而保证各部分的独立性及功能扩展

## 9.3，类图

![1595844812102](E:\gitrepository\study\note\image\designMode\1595844812102.png)

* 对抽象和实现进行拆分，拆分为两个独立的模块，并通过组合关系关联在一起
* 抽象模块是手机类型模块，以`PhoneType`为顶层抽象类，并派生出`UpRightType`和`FlodedType`等具体类型类
* 实现模块是手机品牌模块，以`IBrand`为顶层接口，并派生出各个手机品牌
* 手机类型组合手机品牌，将手机品牌作为其内部的构造参数
* 客户端在进行具体操作时，默认先适配机型，再适配具体手机

## 9.4，代码示例

* 实现模块顶层接口：`IBrand`

  ```java
  package com.self.designmode.bridge;
  
  /**
   * 手机品牌: 实现层顶层接口
   * @author PJ_ZHANG
   * @create 2020-07-27 17:51
   **/
  public interface IBrand {
      void open();
      void call();
      void close();
  }
  ```

* 实现模块实现类_1：`Huawei`

  ```java
  package com.self.designmode.bridge;
  
  /**
   * 具体实现类: 华为手机
   * @author PJ_ZHANG
   * @create 2020-07-27 17:55
   **/
  public class Huawei implements IBrand {
      @Override
      public void open() {
          System.out.println("华为手机开机...");
      }
  
      @Override
      public void call() {
          System.out.println("华为手机打电话...");
      }
  
      @Override
      public void close() {
          System.out.println("华为手机关机");
      }
  }
  ```

* 实现模块实现类_2：`Xiaomi`

  ```java
  package com.self.designmode.bridge;
  
  /**
   * 具体实现类: 小米手机
   * @author PJ_ZHANG
   * @create 2020-07-27 17:56
   **/
  public class Xiaomi implements IBrand {
      @Override
      public void open() {
          System.out.println("小米手机开机...");
      }
  
      @Override
      public void call() {
          System.out.println("小米手机打电话...");
      }
  
      @Override
      public void close() {
          System.out.println("小米手机关机");
      }
  }
  ```

* 抽象模块顶层抽象类：`PhoneType`

  ```java
  package com.self.designmode.bridge;
  
  import com.self.designmode.adapter.interfaceadapter.Phone;
  
  /**
   * 手机类型: 抽象层顶层类
   * @author PJ_ZHANG
   * @create 2020-07-27 17:53
   **/
  public abstract class PhoneType {
      private IBrand brand;
      public PhoneType(IBrand brand) {
          this.brand = brand;
      }
      public void open() {
          brand.open();
      }
      public void call() {
          brand.call();
      }
  
      public void close() {
          brand.close();
      }
  }
  ```

* 抽象模块子类_1：`FlodedType`

  ```java
  package com.self.designmode.bridge;
  
  /**
   * 抽象子类: 旋转类型手机
   * @author PJ_ZHANG
   * @create 2020-07-27 17:57
   **/
  public class FlodedType extends PhoneType{
      public FlodedType(IBrand brand) {
          super(brand);
      }
      public void open() {
          super.open();
          System.out.println("旋转类型手机开机...");
      }
      public void call() {
          super.call();
          System.out.println("旋转类型手机打电话...");
      }
  
      public void close() {
          super.close();
          System.out.println("旋转类型手机关机...");
      }
  }
  ```

* 抽象模块子类_2：`UpRightType`

  ```java
  package com.self.designmode.bridge;
  
  /**
   * 抽象子类: 直立类型手机
   * @author PJ_ZHANG
   * @create 2020-07-27 17:57
   **/
  public class UpRightType extends PhoneType{
      public UpRightType(IBrand brand) {
          super(brand);
      }
      public void open() {
          super.open();
          System.out.println("直立类型手机开机...");
      }
      public void call() {
          super.call();
          System.out.println("直立类型手机打电话...");
      }
  
      public void close() {
          super.close();
          System.out.println("直立类型手机关机...");
      }
  }
  ```

* 客户端：`Client`

  ```java
  package com.self.designmode.bridge;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-27 17:58
   **/
  public class Client {
      public static void main(String[] args) {
          PhoneType phoneType = new UpRightType(new Huawei());
          phoneType.call();
  
          phoneType = new FlodedType(new Xiaomi());
          phoneType.open();
      }
  }
  ```

## 9.5，注意事项和细节

* 实现了抽象部分和实现部分的分离，极大的提供了系统的灵活性，有助于系统的分层化设计，从而产生更好的结构化系统
* 对于系统的高层部分，只需要知道抽象部分和实现部分的接口即可，具体业务由底层完成
* **桥接模式替代多层继承关系**，可以有效的介绍子类数量，降低系统管理和维护成本
* 桥接模式的引入增加了系统的理解和设计难度，因为组合关联关系在抽象层，所以需要面向抽象层进行编程
* 桥接模式需要**准确的识别出系统中两个维度（抽象层，实现层）**，因此其使用场景有一定的局限性

## 9.6，常见使用场景

* 银行转账系统
  * 转账分类：网上银行，柜台转账，ATM转账
  * 用户类型：普通用户，会员用户，金卡用户
* 消息管理
  * 消息类型：即时消息，延时消息
  * 消息分类：手机短信，右键信息，QQ消息...

# 10，装饰者模式（Decorator）

## 10.1，问题引入

### 10.1.1，星巴克咖啡订单项目

* **咖啡种类**：Espresso（意大利浓咖啡），LongBlack（美式咖啡），Decaf（无因咖啡）
* **调料**：Mild，Soy，Chocolate
* 要求在增加新的咖啡时能有更好的扩展性，改动方便，维护方便
* 使用OO计算不同种类咖啡的价格：包括咖啡价格和调料价格

### 10.1.2，方式一：穷举类方式

![1595929587095](E:\gitrepository\study\note\image\designMode\1595929587095.png)

* `Drink`：是顶层抽象类，表示饮品，`price`是咖啡价格， `description`是对咖啡的描述，`cost()`方法是计算最终咖啡价格
* 图中第一层的子类表示单品咖啡，即列举出所有的单品咖啡以供单点
* 图中第二层的子类表示单品咖啡+调料，即对所有可能的咖啡+调料进行组合列举
* 这种设计方式会造成绝对的类爆炸，优点扯淡，我也不知道我要列它。。。

### 10.1.3，方式二：聚合方式

![1595930021207](E:\gitrepository\study\note\image\designMode\1595930021207.png)

* 这种方式较第一种方式的改观，是避免了类爆炸，将调料以聚合的方式依赖到单品咖啡中，在买咖啡时候可以进行调料添加
* 但是如果需要添加一份调料，此时需要对所有单品咖啡部分代码进行变更，不符合OCP原则
* 此时可以考虑**装饰者模式**

## 10.2，基本介绍

* 装饰者模式：**动态的将新功能附加到对象上**，在对象功能扩展方面，比继承更具有弹性，装饰者模式符合**开闭原则（OCP原则）**
* 装饰者模式是将整体功能分为两部分，即**主体（被装饰者 Component）部分**和**包装（装饰者 Decorator）部分**，装饰者通过组合顶层接口，对被装饰者或者*装饰者（包装再包装）*进行包装
* 当需要增加被装饰者或者装饰者时，只需要添加对应的类即可，后续在客户端即可适配装饰方式

## 10.3，类图

![1595930769382](E:\gitrepository\study\note\image\designMode\1595930769382.png)

* 顶层抽象类：`IDrink`，保证整个体系中的强一致性
* 被装饰者部分：单品咖啡部分，列举出所有的单品咖啡
* 装饰者部分顶层类：`Decorator`，对整个调料部分进行管理，进行消费计算
* 装饰者部分：调料部分，列举出所有调料
* 最后用装饰者对被装饰者进行组合，再进行对象装饰时构造为一个新的对象，该对象内部的`drink`属性即表示被装饰过的**被装饰者或者装饰者对象**

## 10.4，代码示例

* 顶层抽象类：`IDrink`

  ```java
  package com.self.designmode.decorator;
  
  import lombok.Getter;
  import lombok.Setter;
  
  /**
   * 装饰者模式: 顶层抽象类, 确保强一致
   * @author PJ_ZHANG
   * @create 2020-07-28 18:11
   **/
  @Getter
  @Setter
  public abstract class IDrink {
      // 价格
      private int price;
      // 描述
      private String des;
      // 花费
      abstract int cost();
  }
  ```

* 被装饰者类：`LongBlack`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * 装饰者:被装饰者类
   * @author PJ_ZHANG
   * @create 2020-07-28 18:18
   **/
  public class LongBlack extends IDrink {
      public LongBlack() {
          setDes("美氏咖啡...");
          setPrice(20);
      }
      @Override
      int cost() {
          System.out.println(getDes() + " : " + getPrice());
          return getPrice();
      }
  }
  ```

* 被装饰者类：`Espresso`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-28 18:20
   **/
  public class Espresso extends IDrink {
      public Espresso() {
          setDes("意氏咖啡...");
          setPrice(30);
      }
      @Override
      int cost() {
          System.out.println(getDes() + " : " + getPrice());
          return getPrice();
      }
  }
  ```

* 被装饰者类：`Decaf`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-07-28 18:21
   **/
  public class Decaf extends IDrink {
      public Decaf() {
          setDes("无因咖啡...");
          setPrice(30);
      }
      @Override
      int cost() {
          System.out.println(getDes() + " : " + getPrice());
          return getPrice();
      }
  }
  ```

* 装饰者顶层类：`Decorator`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * 装饰者: 顶层装饰者类
   * @author PJ_ZHANG
   * @create 2020-07-28 18:22
   **/
  public class Decorator extends IDrink {
      private IDrink drink;
      public Decorator(IDrink drink) {
          this.drink = drink;
      }
      @Override
      int cost() {
          System.out.println(this.getDes() + " : " + this.getPrice());
          return drink.cost() + this.getPrice();
      }
  }
  ```

* 装饰者类：`Milk`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * 装饰者: 装饰者类,加牛奶
   * @author PJ_ZHANG
   * @create 2020-07-28 18:25
   **/
  public class Milk extends Decorator {
      public Milk(IDrink drink) {
          super(drink);
          setDes("加牛奶...");
          setPrice(5);
      }
  }
  ```

* 装饰者类：`Soy`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * 装饰者: 装饰者类,加豆浆
   * @author PJ_ZHANG
   * @create 2020-07-28 18:26
   **/
  public class Soy extends Decorator {
      public Soy(IDrink drink) {
          super(drink);
          setDes("加豆浆...");
          setPrice(3);
      }
  }
  ```

* 装饰者类：`Chocolate`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * 装饰者: 装饰者类,加巧克力
   * @author PJ_ZHANG
   * @create 2020-07-28 18:26
   **/
  public class Chocolate extends Decorator {
      public Chocolate(IDrink drink) {
          super(drink);
          setDes("加巧克力...");
          setPrice(2);
      }
  }
  ```

* 客户端：`Client`

  ```java
  package com.self.designmode.decorator;
  
  /**
   * 装饰者模式客户端
   * @author PJ_ZHANG
   * @create 2020-07-28 18:18
   **/
  public class Client {
      public static void main(String[] args) {
          int cost = new Chocolate(new Milk(new LongBlack())).cost();
          System.out.println(cost);
      }
  }
  ```

# 11，组合模式（Composite）

## 11.1，问题引入

* 展示一个学校的体系结构，一个学校有多个学院，一个学院有多个专业

  ![1595996792858](E:\gitrepository\study\note\image\designMode\1595996792858.png)

## 11.2，基本介绍

* 组合模式（Composite），又叫部分整体模式，属于结构性模式，创建了对象组的树形结构，将对象组合成树状结构以表示**整体—部分**的关系
* 组合模式使得用户对单个对象和组合对象的访问具有一致性，即组合模式能让客户以一致的方式处理单个对象和组合对象

## 11.3，类图

![1595996933981](E:\gitrepository\study\note\image\designMode\1595996933981.png)

* 顶层抽象类：`OrgComponent`，定义了组合模式中的强一致类型，并提供了基本属性和方法供子类去继承和重写
* 中间节点：`Composite`，即部分和整体部分，对它的子节点表示整体，对它的父节点表示部分，定义它的部分的集合属性，并重写父类方法
* 叶子节点：`Leaf`，绝对的部分，由中间节点`Composite`通过多态组合

## 11.4，代码示例

* 顶层抽象类：`OrgComponent`

  ```java
  package com.self.designmode.composite;
  
  import lombok.Getter;
  import lombok.Setter;
  
  /**
   * 组合模式: 顶层抽象类
   * @author PJ_ZHANG
   * @create 2020-07-29 12:36
   **/
  @Getter
  @Setter
  public abstract class OrgComponent {
      private String name;
      private String des;
      public void add(OrgComponent component) { throw new UnsupportedOperationException("不支持添加...");}
      public void delete(OrgComponent component) {throw new UnsupportedOperationException("不支持删除...");}
      abstract void print();
  }
  ```

* 中间节点具体类：`OneComposite`

  ```java
  package com.self.designmode.composite;
  
  import java.util.ArrayList;
  import java.util.List;
  
  /**
   * 中间层级: 具体类
   * @author PJ_ZHANG
   * @create 2020-07-29 12:45
   **/
  public class OneComposite extends OrgComponent {
      List<OrgComponent> lstChildComponent;
      public OneComposite(String name, String des) {
          setName(name);
          setDes(des);
          lstChildComponent = new ArrayList<>(10);
      }
      @Override
      public void add(OrgComponent component) {
          lstChildComponent.add(component);
      }
      @Override
      public void delete(OrgComponent component) {
          lstChildComponent.remove(component);
      }
      @Override
      void print() {
          System.out.println("---------------------");
          System.out.println("name: " + getName() + ", des: " + getDes());
          for (OrgComponent component : lstChildComponent) {
              component.print();
          }
          System.out.println("---------------------");
      }
  }
  ```

* 中间节点具体类：`TowComposite`

  ```java
  package com.self.designmode.composite;
  
  import java.util.ArrayList;
  import java.util.List;
  
  /**
   * 中间层级: 具体类
   * @author PJ_ZHANG
   * @create 2020-07-29 12:45
   **/
  public class TowComposite extends OrgComponent {
      List<OrgComponent> lstChildComponent;
      public TowComposite(String name, String des) {
          setName(name);
          setDes(des);
          lstChildComponent = new ArrayList<>(10);
      }
      @Override
      public void add(OrgComponent component) {
          lstChildComponent.add(component);
      }
      @Override
      public void delete(OrgComponent component) {
          lstChildComponent.remove(component);
      }
      @Override
      void print() {
          System.out.println("---------------------");
          System.out.println("name: " + getName() + ", des: " + getDes());
          for (OrgComponent component : lstChildComponent) {
              component.print();
          }
          System.out.println("---------------------");
      }
  }
  ```

* 叶子节点具体类：`Leaf`

  ```java
  package com.self.designmode.composite;
  
  /**
   * 叶子节点: Leaf
   * @author PJ_ZHANG
   * @create 2020-07-29 12:50
   **/
  public class Leaf extends OrgComponent {
      public Leaf(String name, String des) {
          setName(name);
          setDes(des);
      }
      @Override
      void print() {
          System.out.println("---------------------");
          System.out.println("name: " + getName() + ", des: " + getDes());
          System.out.println("---------------------");
      }
  }
  ```

* 客户端：`Client`

  ```java
  package com.self.designmode.composite;
  
  /**
   * 客户端
   * @author PJ_ZHANG
   * @create 2020-07-29 12:51
   **/
  public class Client {
      public static void main(String[] args) {
          OrgComponent university = new OneComposite("学校", "挺好");
          OrgComponent college = new TowComposite("学院", "挺不错");
          university.add(college);
          OrgComponent leaf = new Leaf("专业", "挺棒");
          college.add(leaf);
          university.print();
      }
  }
  ```

## 11.5，注意事项和细节

* 简化客户端操作，客户端只需要面对一致的对象，而不用考虑整体部分或者节点叶子的问题
* 具有将强的扩展性，当需要改变组合对象时，只需要调整内部的层次关系
* 方便创建复杂的层次结构。客户端不用理会组合里面的组成节点，通过添加节点和叶子节点即可创建出复杂的树形结构
* <font color=red>对于组织结构，或者其他类似的树形结构，非常实用组合模式</font>
* <font color=red>对于抽象性较高，差异性较大的节点，不适合实用组合模式</font>

# 12，外观模式（Facade）

## 12.1，问题引入

* 组建一个家庭影院，需要准备屏幕，投影仪，灯光。此时看一场电影的大概过程为：放下屏幕，打开投影仪，调暗灯光；等电影看完后，大致过程为：调两灯光，关闭投影仪，收回屏幕。
* 此时如果不进行各种模式统筹管理，在实际操作中，需要通过三个开关对三种设备进行单独控制，此时如果设备过多，会造成过程混乱，还有可能出现顺序（逻辑）错误
* 这时候可以引入**外观模式**，通过外观类，进行具体操作流程进行管理，面向客户端只包括打开，关闭等基本操作，提高用户体验

## 12.2，基本介绍

* 外观模式（Facade），也叫过程模式，外观模式为子系统中的一组接口提供一个一致的界面，通过定义一个高层接口，似的一系列子系统的接口更容易使用
* 通过定义一个一致的接口，用于屏蔽内部子系统的细节，使得调用端只需要跟这个接口发生调用，而无需关心子系统的内部实现

## 12.3，类图

![1596446164556](E:\gitrepository\study\note\image\designMode\1596446164556.png)

* `Lamplight`，`Projector`，`Screen`：实际业务类，组合在外观类中，用于实际业务执行
* `Facade`：外观类，定义一致接口，组合实际类，按既定顺序进行调用
* `Client`：客户端，客户端组合外观类，并且只组合外观类，通过一个一致的界面操作，进行其他实际业务类操作

## 12.4，代码示例

* `Lamplight`：实际业务类

  ```java
  package com.self.designmode.facade;
  
  /**
   * 外观模式: 灯光
   * @author PJ_ZHANG
   * @create 2020-08-03 17:05
   **/
  public class Lamplight {
      private static Lamplight lamplight = new Lamplight();
      public static Lamplight instance() {
          return lamplight;
      }
      public void lightUp() {
          System.out.println("调亮灯光...");
      }
      public void lightDown() {
          System.out.println("调暗灯光...");
      }
  }
  ```

* `Screen`：实际业务类

  ```java
  package com.self.designmode.facade;
  
  /**
   * 外观模式: 幕布类
   * @author PJ_ZHANG
   * @create 2020-08-03 17:03
   **/
  public class Screen {
      private static Screen screen = new Screen();
      public static Screen instance() {
          return screen;
      }
      public void up() {
          System.out.println("收起屏幕...");
      }
      public void down() {
          System.out.println("放下屏幕...");
      }
  }
  ```

* `Projector`：实际业务类

  ```java
  package com.self.designmode.facade;
  
  /**
   * 外观模式: 投影仪
   * @author PJ_ZHANG
   * @create 2020-08-03 17:04
   **/
  public class Projector {
      private static Projector projector = new Projector();
      public static Projector instance() {
          return projector;
      }
      public void open() {
          System.out.println("打开投影仪...");
      }
      public void close() {
          System.out.println("关闭投影仪...");
      }
  }
  ```

* `Facade`：外观类

  ```java
  package com.self.designmode.facade;
  
  /**
   * 外观模式, 外观类,
   * 抽取顶层接口进行统一管理
   * @author PJ_ZHANG
   * @create 2020-08-03 17:02
   **/
  public class Facade {
      private Lamplight lamplight = null;
      private Projector projector = null;
      private Screen screen = null;
      public Facade() {
          lamplight = Lamplight.instance();
          projector = Projector.instance();
          screen = Screen.instance();
      }
      // 开始观影
      public void start() {
          screen.down();
          projector.open();
          lamplight.lightDown();
      }
      // 结束观影
      public void end() {
          screen.up();
          projector.close();
          lamplight.lightUp();
      }
  }
  ```

* `Client`：客户端

  ```java
  package com.self.designmode.facade;
  
  /**
   * 外观模式: 客户端
   * @author PJ_ZHANG
   * @create 2020-08-03 17:10
   **/
  public class Client {
      public static void main(String[] args) {
          Facade facade = new Facade();
          facade.start();
          System.out.println("------------------------");
          facade.end();
      }
  }
  ```

## 12.5，注意事项和细节

* 外观模式**对外屏蔽了子系统的细节**，因此外观模式降低了客户端对子系统使用的复杂性
* 外部模式让客户端与子系统实现解耦，让子系统内部模块更易维护和扩展
* 通过合理的使用外观模式，可以更好的划分访问层次
* 当系统需要进行分层设计时，可以考虑使用**Facade模式**
* 当需要维护一个大型系统，且系统已经变得非常难以维护和扩展，可以考虑为新系统提供`Facade`类，让新系统与该类交互，提供可复用性

# 13，享元模式（FlyWeight）

## 13.1，问题引入

### 13.1.1，展示网站项目需求

​		小型的外包项目，给客户A做一个产品展示网站，客户A的朋友觉得效果不错，也需要这样的产品展示网站，但是需求有些变化：

* 有客户要求以新闻的形式发布
* 有客户要求以博客的形式发布
* 有客户要求以微信小程序的形式发布

### 13.1.2，传统方式解决网站项目

* 直接将项目复制一份，根据不同客户的需求，进行定制化修改

  ![1598107446663](E:\gitrepository\study\note\image\designMode\1598107446663.png)

### 13.1.3，问题分析

* 需要的网站相似度很高，而且都不是高访问量网站，如果分成多个虚拟机进行部署，相当于一个相同网站的实例有很多，造成服务器资源浪费
* 可以将代码和数据整合到一个网站中，对于硬盘，内存，CPU等资源进行共享，减少服务器资源

## 13.2，享元模式基本概述

* 享元模式，也叫**蝇量模式**：运用共享技术有效的支持大量细粒度的对象。“享”表示共享，“元”表示对象
* 常用于系统底层开发，解决系统的性能问题。像数据库连接池，里面都是已经创建好的数据库连接对象，这些连接对象在我们需要的时候可以直接拿来用，避免重新创建，如果没有我们需要的，则新创建一个
* 享元模型能够解决重复对象的内存浪费问题，当系统中存在大量的对象需要缓冲池时。不需要不断的创建新对象，可以直接从缓冲池中拿。可以降低系统内存，提升效率。如JVM中的常量池
* 在JDK中的应用，`Integer`的缓存池`-127~127`

## 13.3，类图

![1598108907208](E:\gitrepository\study\note\image\designMode\1598108907208.png)

* `FlyWeightFactory`：享元工厂，用于提供一个池容器，并从池中获取对象的方法
* `IFlyWeight`：享元抽象接口，提供产品的抽象接口，并同时定义出对象内部状态和外部状态的接口和实现

* `FlyWeight`：具体享元角色，共享的角色，即具体的产品类，实现内部状态，内部状态角色是被享元工厂托管的角色
* `UnsharedFlyWeight`：特殊享元角色，不可共享的角色，实现外部状态，该部分不会被享元工厂托管

## 13.4，内部状态和外部状态

* 享元模式提出了两个要求：细粒度和共享对象，这就设计到内部状态和外部状态了
* 内部状态是对象共享出来的信息，存储在享元对象内并不会随着环境的不同而改变
* 外部状态是对象得以依赖的一个标记，随环境改变而改变，不可共享的状态
* 举例：围棋理论上是有361个空位可以放棋子，每盘棋都有可能有两三百个棋子产生，因为内存空间有限，一台服务器很难支撑更多的玩家玩围棋游戏，如果用享元模式来处理棋子，则棋子对象减少到两个，空棋盘共用，具体的棋盘布局是特殊角色

## 13.5，代码实现

* `IFlyWeight`：享元角色顶层类

  ```java
  package com.self.designmode.flyweight;
  
  /**
   * 享元模式顶层接口
   * @author PJ_ZHANG
   * @create 2020-08-22 21:16
   **/
  public interface IFlyWeight {
  
      void use();
  
      void setUnsharedFlyWeight(UnsharedFlyWeight unsharedFlyWeight);
  
  }
  ```

* `FlyWeight`：具体角色

  ```java
  package com.self.designmode.flyweight;
  
  /**
   * 享元模式: 具体角色
   * @author PJ_ZHANG
   * @create 2020-08-22 21:20
   **/
  public class FlyWeight implements IFlyWeight {
  
      private String type = "";
  
      private UnsharedFlyWeight unsharedFlyWeight = null;
  
      public FlyWeight(String type) {
          this.type = type;
      }
  
      @Override
      public void use() {
          System.out.println("享元具体角色: " + type);
          if (null != unsharedFlyWeight) {
              unsharedFlyWeight.use();
          }
      }
  
      @Override
      public void setUnsharedFlyWeight(UnsharedFlyWeight unsharedFlyWeight) {
          this.unsharedFlyWeight = unsharedFlyWeight;
      }
  
  }
  ```

* `UnsharedFlyWeight`：特殊角色

  ```java
  package com.self.designmode.flyweight;
  
  /**
   * 享元模式: 特殊角色
   * @author PJ_ZHANG
   * @create 2020-08-22 21:26
   **/
  public class UnsharedFlyWeight implements IFlyWeight {
  
      private String type = "";
  
      private UnsharedFlyWeight unsharedFlyWeight = null;
  
      public UnsharedFlyWeight(String type) {
          this.type = type;
      }
  
      @Override
      public void use() {
          System.out.println("享元特殊角色: " + type);
          if (null != unsharedFlyWeight) {
              unsharedFlyWeight.use();
          }
      }
  
      @Override
      public void setUnsharedFlyWeight(UnsharedFlyWeight unsharedFlyWeight) {
          this.unsharedFlyWeight = unsharedFlyWeight;
      }
  
  }
  ```

* `FlyWeightFactory`：享元工厂

  ```java
  package com.self.designmode.flyweight;
  
  import java.util.Map;
  import java.util.concurrent.ConcurrentHashMap;
  
  /**
   * 享元工厂, 获取具体享元角色
   * @author PJ_ZHANG
   * @create 2020-08-22 21:16
   **/
  public class FlyWeightFactory {
  
      private static Map<String, FlyWeight> map = new ConcurrentHashMap<>(16);
  
      private static Map<String, UnsharedFlyWeight> unsharedMap = new ConcurrentHashMap<>(16);
  
      public static FlyWeight getFlyWeight(String type) {
          if (!map.containsKey(type)) {
              map.put(type, new FlyWeight(type));
          }
          return map.get(type);
      }
  
      public static UnsharedFlyWeight getUnsharedFlyWeight(String type) {
          if (!unsharedMap.containsKey(type)) {
              unsharedMap.put(type, new UnsharedFlyWeight(type));
          }
          return unsharedMap.get(type);
      }
  
  }
  ```

* `Client`：客户端

  ```java
  package com.self.designmode.flyweight;
  
  /**
   * 享元模式
   * @author PJ_ZHANG
   * @create 2020-08-22 21:02
   **/
  public class Client {
  
      public static void main(String[] args) {
          FlyWeight flyWeight = FlyWeightFactory.getFlyWeight("新闻");
          UnsharedFlyWeight unsharedFlyWeight = FlyWeightFactory.getUnsharedFlyWeight("特殊");
          flyWeight.setUnsharedFlyWeight(unsharedFlyWeight);
          flyWeight.use();
          System.out.println("----------------------------");
          flyWeight = FlyWeightFactory.getFlyWeight("博客");
          unsharedFlyWeight = FlyWeightFactory.getUnsharedFlyWeight("特殊");
          flyWeight.setUnsharedFlyWeight(unsharedFlyWeight);
          flyWeight.use();
          System.out.println("----------------------------");
          flyWeight = FlyWeightFactory.getFlyWeight("小程序");
          unsharedFlyWeight = FlyWeightFactory.getUnsharedFlyWeight("特殊");
          flyWeight.setUnsharedFlyWeight(unsharedFlyWeight);
          flyWeight.use();
          System.out.println("----------------------------");
      }
  
  }
  ```

## 13.6，注意事项及细节

* 当系统中存在大量对象，这些对象消耗大量内存，并且对象的状态大部分可以外部化时，可以考虑使用享元模式
* 享元模式可以大大减少对象的创建，降低了程序内存的占用，提高效率
* 享元模式提高了**系统复杂度**。需要分离出内部状态和外部状态，外部状态具有固化特性，不会随着内部状态的改变而改变，这是使用享元模式需要注意的问题
* 享元模式的经典使用场景：String常量池，数据库连接池，线程池

# 14，代理模式（Proxy）

## 14.1，代码模式基本介绍

* 代理对象为对象提供一个替身，以控制对这个对象的访问，即通过代理对象去访问目标对象
* 被代理的对象可以是远程对象，开销大的对象或者是需要安全控制的对象
* 代理模式有不同的形式，大体可以分为三种：静态代理，JDK动态代理（接口代理）和CGLIB动态代理（不需要接口）
* 对象被代理后，可以在目标对象现有的基础上，增加额外的功能操作，即对现有目标对象的扩展。如执行前后日志打印，方法鉴权等等，是AOP的基本思想

## 14.2，静态代理

### 14.2.1，基本介绍

* 静态代码就是简单的代码传递调用，通过代理对象组合目标对象进行强关联，在实际执行的方法外层包裹一层代理方法，实现静态代理

### 14.2.2，类图

![1606975940817](E:\gitrepository\study\note\image\designMode\1606975940817.png)

* 定义一个顶层接口：`IProxy`
* 定义目标对象 `TargetProxy` 并实现顶层接口 `IProxy`
* 定义代理对象 `StaticProxy` 实现顶层接口 `Iproxy`，组合目标对象 `TargetProxy`，并在构造器中直接初始化
* 客户端调用时，初始化代理对象时需要传入初始化好的目标对象，在通过代理对象对象调用方法时，先调用代理对象中的该方法，并最终传递到目标方法

### 14.2.3，代码实现

* `IProxy`：顶层对象

  ```java
  package com.self.designmode.proxy.statics;
  
  /**
   * 静态代理顶层接口
   * @author PJ_ZHANG
   * @create 2020-12-03 13:53
   **/
  public interface IProxy {
  
      void realMethod(String name);
  
  }
  ```

* `TargetProxy`：目标对象

  ```java
  package com.self.designmode.proxy.statics;
  
  /**
   * 实际执行类
   * @author PJ_ZHANG
   * @create 2020-12-03 13:54
   **/
  public class TargetProxy implements IProxy {
  
      @Override
      public void realMethod(String name) {
          System.out.println("实际执行方法: " + name);
      }
  }
  ```

* `StaticProxy`：代理对象

  ```java
  package com.self.designmode.proxy.statics;
  
  /**
   * 代理方法
   * @author PJ_ZHANG
   * @create 2020-12-03 13:54
   **/
  public class StaticProxy implements IProxy {
  
      private IProxy proxy;
  
      public StaticProxy(IProxy proxy) {
          this.proxy = proxy;
      }
  
      @Override
      public void realMethod(String name) {
          System.out.println("静态代码执行...");
          proxy.realMethod(name);
          System.out.println("静态代理执行完成...");
      }
  }
  ```

* `Client`：客户端

  ```java
  package com.self.designmode.proxy.statics;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 13:56
   **/
  public class Client {
  
      public static void main(String[] args) {
          // 创建实际对象
          TargetProxy targetProxy = new TargetProxy();
          // 创建代理对象
          StaticProxy staticProxy = new StaticProxy(targetProxy);
          // 方法执行
          staticProxy.realMethod("执行...");
      }
  
  }
  ```

### 14.2.4，静态代理优缺点

* 优点：在不修改目标对象的前提下，能实现代理对象对目标对象的功能扩展
* 缺点：因为代理对象需要与目标对象实现同样的接口，所以会产生太多的代理类。同样，一旦接口方法增加，同时需要处理代理类和目标类
* 可通过静态代理进行继续优化

## 14.3，JDK动态代理

### 14.3.1，基本介绍

* JDK动态代理又叫做接口代理
* 目标类需要实现接口，并通过JDK提供的API创建代理对象，实现方法代理

### 14.3.2，类图

![1606977597837](E:\gitrepository\study\note\image\designMode\1606977597837.png)

* 定义顶层接口：`IProxy`，动态代理必须需要一个目标类接口
* 定义目标类：`TargetProxy`
* 定义代理工厂：`ProxyFactory`，该代理工厂是生产代理对象的核心部分，通过JDK的API方法`Porxy.newInstance(..)` 创建并返回一个代理对象。在传递参数时，需要实现一个处理接口 `InvocationHandler`，在该接口的实现方法中，进行方法功能扩展及实际方法执行。

### 14.3.3，代码实现

* `IProxy`：顶层接口

  ```java
  package com.self.designmode.proxy.jdk;
  
  /**
   * JDK动态代理顶层接口
   * @author PJ_ZHANG
   * @create 2020-12-03 13:53
   **/
  public interface IProxy {
  
      void realMethod(String name);
  
  }
  ```

* `TargetProxy`：目标类

  ```java
  package com.self.designmode.proxy.jdk;
  
  /**
   * 实际执行类
   * @author PJ_ZHANG
   * @create 2020-12-03 13:54
   **/
  public class TargetProxy implements IProxy {
  
      @Override
      public void realMethod(String name) {
          System.out.println("实际执行方法: " + name);
      }
  }
  ```

* `ProxyFactory`：代理工厂

  ```java
  package com.self.designmode.proxy.jdk;
  
  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.lang.reflect.Proxy;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 14:48
   **/
  public class ProxyFactory {
  
      private Object proxy;
  
      public ProxyFactory(Object proxy) {
          this.proxy = proxy;
      }
  
      public Object getInstance() {
          // 第一次参数:目标对象的类加载器
          // 第二个参数:目标对象的接口集合, 这也是JDK动态代理必须需要是基于接口的原因
          // 第三个参数:处理器对象,真正去进行方法代理执行部分,在该接口的的实现方法中需要定义实际方法执行和功能扩展
          return (IProxy) Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), (proxy, method, args) -> {
              System.out.println("method: " + method.getName() + "执行前...");
              // 基于Java反射的方法执行, 第一个对象参数需要的是目标类对象
              // 该对象如果给代理类对象, 则会构成死循环, 一直触发方法执行,
              // 因为代理对象的方法执行会走到这部分,然后再触发一次代理对象的方法执行,依次循环
              Object result = method.invoke(this.proxy, args);
              System.out.println("method: " + method.getName() + "执行后...");
              return result;
          });
      }
  
  }
  ```

* `Client`：客户端

  ```java
  package com.self.designmode.proxy.jdk;
  
  import sun.misc.ProxyGenerator;
  
  import java.io.FileOutputStream;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 14:46
   **/
  public class Client {
  
      public static void main(String[] args) throws Exception {
          ProxyFactory proxyFactory = new ProxyFactory(new TargetProxy());
          IProxy instance = (IProxy) proxyFactory.getInstance();
          instance.realMethod("JDK动态代理");
          // com.sun.proxy.$Proxy0
          System.out.println(instance.getClass().getName());
          // 打印出Proxy文件
          byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", TargetProxy.class.getInterfaces());
          FileOutputStream fileOutputStream = new FileOutputStream("F:\\$Proxy0.class");
          fileOutputStream.write(bytes);
          fileOutputStream.flush();
          fileOutputStream.close();
      }
  
  }
  ```

### 14.3.4，代理对象.class分析

* 在上一个代码块中，已经把代理对象对应的.class下载下来，具体文件如下

  ```java
  //
  // Source code recreated from a .class file by IntelliJ IDEA
  // (powered by Fernflower decompiler)
  //
  
  import com.self.designmode.proxy.jdk.IProxy;
  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.lang.reflect.Proxy;
  import java.lang.reflect.UndeclaredThrowableException;
  
  public final class $Proxy0 extends Proxy implements IProxy {
      private static Method m1;
      private static Method m2;
      private static Method m3;
      private static Method m0;
  
      public $Proxy0(InvocationHandler var1) throws  {
          super(var1);
      }
  
      public final boolean equals(Object var1) throws  {
          try {
              return ((Boolean)super.h.invoke(this, m1, new Object[]{var1})).booleanValue();
          } catch (RuntimeException | Error var3) {
              throw var3;
          } catch (Throwable var4) {
              throw new UndeclaredThrowableException(var4);
          }
      }
  
      public final String toString() throws  {
          try {
              return (String)super.h.invoke(this, m2, (Object[])null);
          } catch (RuntimeException | Error var2) {
              throw var2;
          } catch (Throwable var3) {
              throw new UndeclaredThrowableException(var3);
          }
      }
  
      public final void realMethod(String var1) throws  {
          try {
              super.h.invoke(this, m3, new Object[]{var1});
          } catch (RuntimeException | Error var3) {
              throw var3;
          } catch (Throwable var4) {
              throw new UndeclaredThrowableException(var4);
          }
      }
  
      public final int hashCode() throws  {
          try {
              return ((Integer)super.h.invoke(this, m0, (Object[])null)).intValue();
          } catch (RuntimeException | Error var2) {
              throw var2;
          } catch (Throwable var3) {
              throw new UndeclaredThrowableException(var3);
          }
      }
  
      static {
          try {
              m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[]{Class.forName("java.lang.Object")});
              m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
              m3 = Class.forName("com.self.designmode.proxy.jdk.IProxy").getMethod("realMethod", new Class[]{Class.forName("java.lang.String")});
              m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
          } catch (NoSuchMethodException var2) {
              throw new NoSuchMethodError(var2.getMessage());
          } catch (ClassNotFoundException var3) {
              throw new NoClassDefFoundError(var3.getMessage());
          }
      }
  }
  ```

* 上面这个.class文件，即动态代理生成的代理对象`com.sun.proxy.$Proxy0` 对应的.class文件

* 通过代理对象调用方法 `instance.realMethod("JDK动态代理")`，实际上调用的是这个类文件中的 `realMethod(..)`方法

* 跟到该类的父类 `java.lang.reflect.Proxy` 中会发现，`super.h` 其实就指的是 `InvocationHandler h`，这个 `h` 对应的就是传参传递的 `InvocationHandler` 的实现类对象

* 在代理类中执行 `super.h.invoke(this, m3, new Object[]{var1})` 方法，实际上就是执行自定义的 `InvocationHandler` 中的 `invoke(..)` 方法

* 调用的传参与 `invoke(..)` 方法的接收参数相对应：

  * `this`：表示的是该代理对象
  * `m3`：即`Method`对象，表示要执行的方法。该参数在代理类中已经通过静态代码块的形式初始化
  * `new Object[]{var1}`：方法参数，将方法参数封装为`args`

* 最终在 `InvocationHandler.invoke(..)` 进行方法调用和方法扩展，方法调用即通过JDK的反射进行调用，***<font color=red>此处对象传递切记传递原对象，如果传递代理对象会陷入方法死递归。</font>***

## 14.4，CGLIB动态代理

### 14.4.1，基本介绍

* 静态代理和JDK代理都需要目标类有一个接口，但有时候目标对象就是一个单独的对象，并没有实现任何接口，这时候可用CGLIB进行动态代理
* CGLIB是一个强大的高性能的代码生成包，是第三方jar包，需要单独引入
* CGLIB代理也被称为子类代理，是在内存中构建一个目标类的子类，并通过该子类对目标类功能进行扩展
* CGLIB的底层是通过字节码处理框架ASM来转换字节码并生成新的类
* 注意事项：
  * 目标类不能是final类，final类不支持继承
  * 目标类方法如果是final/static，也不会被拦截，即不会拦截额外的业务方法

### 14.4.2，类图

![1606984327225](E:\gitrepository\study\note\image\designMode\1606984327225.png)

* 定义目标类：`TargetProxy`，目标类不需要如JDK动态代理般实现接口，但是该类不能是final类
* 定义代理工厂类：`ProxyFactory`，代理工厂需要实现 `net.sf.cglib.proxy.MethodInterceptor` 接口并重写 `intercept(..)` 方法，具体方法调用和扩展逻辑在该方法中实现

### 14.4.3，代码实现

* Maven坐标引入

  ```xml
  <dependency>
      <groupId>cglib</groupId>
      <artifactId>cglib</artifactId>
      <version>3.1</version>
  </dependency>
  ```

* `TargetProxy`：目标类

  ```java
  package com.self.designmode.proxy.cglib;
  
  /**
   * 实际执行类
   * @author PJ_ZHANG
   * @create 2020-12-03 13:54
   **/
  public class TargetProxy {
  
      public void realMethod(String name) {
          System.out.println("实际执行方法: " + name);
      }
  
  }
  ```

* `ProxyFactory`：代理工厂

  ```java
  package com.self.designmode.proxy.cglib;
  
  import net.sf.cglib.proxy.Enhancer;
  import net.sf.cglib.proxy.MethodInterceptor;
  import net.sf.cglib.proxy.MethodProxy;
  
  import java.lang.reflect.Method;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 14:48
   **/
  public class ProxyFactory implements MethodInterceptor {
  
      private Object targetProxy;
  
      public ProxyFactory(Object targetProxy) {
          this.targetProxy = targetProxy;
      }
  
      public Object getInstance() {
          // 创建工具列
          Enhancer enhancer = new Enhancer();
          // 传递父类
          enhancer.setSuperclass(targetProxy.getClass());
          // 设置回调, 即MethodInterceptor的实现类
          enhancer.setCallback(this);
          // 创建代理对象
          return enhancer.create();
      }
  
      @Override
      public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
          System.out.println("CGLIB 执行前...");
          Object result = method.invoke(targetProxy, objects);
          System.out.println("CGLIB 执行后...");
          return result;
      }
  }
  ```

* `Client`：客户端

  ```java
  package com.self.designmode.proxy.cglib;
  
  import net.sf.cglib.core.DebuggingClassWriter;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 15:53
   **/
  public class Client {
  
      public static void main(String[] args) throws Exception {
          // 取代理对象对应的.class文件, 注意这句话一定要放前面
          System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "F://tmp");
          ProxyFactory proxyFactory = new ProxyFactory(new TargetProxy());
          TargetProxy targetProxy = (TargetProxy) proxyFactory.getInstance();
          targetProxy.realMethod("CGLIB...");
          System.out.println(targetProxy.getClass().getName());
      }
  
  }
  ```

### 14.4.4，代理对象.class分析

* 在上一个代码块中，已经将.class输入到本地文件夹，可在对应包路径下找到文件 `TargetProxy$$EnhancerByCGLIB$$99d2ba72.class`

  ```java
  //
  // Source code recreated from a .class file by IntelliJ IDEA
  // (powered by Fernflower decompiler)
  //
  
  package com.self.designmode.proxy.cglib;
  
  import java.lang.reflect.Method;
  import net.sf.cglib.core.ReflectUtils;
  import net.sf.cglib.core.Signature;
  import net.sf.cglib.proxy.Callback;
  import net.sf.cglib.proxy.Factory;
  import net.sf.cglib.proxy.MethodInterceptor;
  import net.sf.cglib.proxy.MethodProxy;
  
  public class TargetProxy$$EnhancerByCGLIB$$99d2ba72 extends TargetProxy implements Factory {
      private boolean CGLIB$BOUND;
      private static final ThreadLocal CGLIB$THREAD_CALLBACKS;
      private static final Callback[] CGLIB$STATIC_CALLBACKS;
      private MethodInterceptor CGLIB$CALLBACK_0;
      private static final Method CGLIB$realMethod$0$Method;
      private static final MethodProxy CGLIB$realMethod$0$Proxy;
      private static final Object[] CGLIB$emptyArgs;
      private static final Method CGLIB$finalize$1$Method;
      private static final MethodProxy CGLIB$finalize$1$Proxy;
      private static final Method CGLIB$equals$2$Method;
      private static final MethodProxy CGLIB$equals$2$Proxy;
      private static final Method CGLIB$toString$3$Method;
      private static final MethodProxy CGLIB$toString$3$Proxy;
      private static final Method CGLIB$hashCode$4$Method;
      private static final MethodProxy CGLIB$hashCode$4$Proxy;
      private static final Method CGLIB$clone$5$Method;
      private static final MethodProxy CGLIB$clone$5$Proxy;
  
      static void CGLIB$STATICHOOK1() {
          CGLIB$THREAD_CALLBACKS = new ThreadLocal();
          CGLIB$emptyArgs = new Object[0];
          Class var0 = Class.forName("com.self.designmode.proxy.cglib.TargetProxy$$EnhancerByCGLIB$$99d2ba72");
          Class var1;
          Method[] var10000 = ReflectUtils.findMethods(new String[]{"finalize", "()V", "equals", "(Ljava/lang/Object;)Z", "toString", "()Ljava/lang/String;", "hashCode", "()I", "clone", "()Ljava/lang/Object;"}, (var1 = Class.forName("java.lang.Object")).getDeclaredMethods());
          CGLIB$finalize$1$Method = var10000[0];
          CGLIB$finalize$1$Proxy = MethodProxy.create(var1, var0, "()V", "finalize", "CGLIB$finalize$1");
          CGLIB$equals$2$Method = var10000[1];
          CGLIB$equals$2$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/Object;)Z", "equals", "CGLIB$equals$2");
          CGLIB$toString$3$Method = var10000[2];
          CGLIB$toString$3$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/String;", "toString", "CGLIB$toString$3");
          CGLIB$hashCode$4$Method = var10000[3];
          CGLIB$hashCode$4$Proxy = MethodProxy.create(var1, var0, "()I", "hashCode", "CGLIB$hashCode$4");
          CGLIB$clone$5$Method = var10000[4];
          CGLIB$clone$5$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/Object;", "clone", "CGLIB$clone$5");
          CGLIB$realMethod$0$Method = ReflectUtils.findMethods(new String[]{"realMethod", "(Ljava/lang/String;)V"}, (var1 = Class.forName("com.self.designmode.proxy.cglib.TargetProxy")).getDeclaredMethods())[0];
          CGLIB$realMethod$0$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/String;)V", "realMethod", "CGLIB$realMethod$0");
      }
  
      final void CGLIB$realMethod$0(String var1) {
          super.realMethod(var1);
      }
  
      public final void realMethod(String var1) {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if(this.CGLIB$CALLBACK_0 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          if(var10000 != null) {
              var10000.intercept(this, CGLIB$realMethod$0$Method, new Object[]{var1}, CGLIB$realMethod$0$Proxy);
          } else {
              super.realMethod(var1);
          }
      }
  
      final void CGLIB$finalize$1() throws Throwable {
          super.finalize();
      }
  
      protected final void finalize() throws Throwable {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if(this.CGLIB$CALLBACK_0 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          if(var10000 != null) {
              var10000.intercept(this, CGLIB$finalize$1$Method, CGLIB$emptyArgs, CGLIB$finalize$1$Proxy);
          } else {
              super.finalize();
          }
      }
  
      final boolean CGLIB$equals$2(Object var1) {
          return super.equals(var1);
      }
  
      public final boolean equals(Object var1) {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if(this.CGLIB$CALLBACK_0 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          if(var10000 != null) {
              Object var2 = var10000.intercept(this, CGLIB$equals$2$Method, new Object[]{var1}, CGLIB$equals$2$Proxy);
              return var2 == null?false:((Boolean)var2).booleanValue();
          } else {
              return super.equals(var1);
          }
      }
  
      final String CGLIB$toString$3() {
          return super.toString();
      }
  
      public final String toString() {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if(this.CGLIB$CALLBACK_0 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          return var10000 != null?(String)var10000.intercept(this, CGLIB$toString$3$Method, CGLIB$emptyArgs, CGLIB$toString$3$Proxy):super.toString();
      }
  
      final int CGLIB$hashCode$4() {
          return super.hashCode();
      }
  
      public final int hashCode() {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if(this.CGLIB$CALLBACK_0 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          if(var10000 != null) {
              Object var1 = var10000.intercept(this, CGLIB$hashCode$4$Method, CGLIB$emptyArgs, CGLIB$hashCode$4$Proxy);
              return var1 == null?0:((Number)var1).intValue();
          } else {
              return super.hashCode();
          }
      }
  
      final Object CGLIB$clone$5() throws CloneNotSupportedException {
          return super.clone();
      }
  
      protected final Object clone() throws CloneNotSupportedException {
          MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
          if(this.CGLIB$CALLBACK_0 == null) {
              CGLIB$BIND_CALLBACKS(this);
              var10000 = this.CGLIB$CALLBACK_0;
          }
  
          return var10000 != null?var10000.intercept(this, CGLIB$clone$5$Method, CGLIB$emptyArgs, CGLIB$clone$5$Proxy):super.clone();
      }
  
      public static MethodProxy CGLIB$findMethodProxy(Signature var0) {
          String var10000 = var0.toString();
          switch(var10000.hashCode()) {
          case -1574182249:
              if(var10000.equals("finalize()V")) {
                  return CGLIB$finalize$1$Proxy;
              }
              break;
          case -508378822:
              if(var10000.equals("clone()Ljava/lang/Object;")) {
                  return CGLIB$clone$5$Proxy;
              }
              break;
          case 1826985398:
              if(var10000.equals("equals(Ljava/lang/Object;)Z")) {
                  return CGLIB$equals$2$Proxy;
              }
              break;
          case 1913648695:
              if(var10000.equals("toString()Ljava/lang/String;")) {
                  return CGLIB$toString$3$Proxy;
              }
              break;
          case 1953784640:
              if(var10000.equals("realMethod(Ljava/lang/String;)V")) {
                  return CGLIB$realMethod$0$Proxy;
              }
              break;
          case 1984935277:
              if(var10000.equals("hashCode()I")) {
                  return CGLIB$hashCode$4$Proxy;
              }
          }
  
          return null;
      }
  
      public TargetProxy$$EnhancerByCGLIB$$99d2ba72() {
          CGLIB$BIND_CALLBACKS(this);
      }
  
      public static void CGLIB$SET_THREAD_CALLBACKS(Callback[] var0) {
          CGLIB$THREAD_CALLBACKS.set(var0);
      }
  
      public static void CGLIB$SET_STATIC_CALLBACKS(Callback[] var0) {
          CGLIB$STATIC_CALLBACKS = var0;
      }
  
      private static final void CGLIB$BIND_CALLBACKS(Object var0) {
          TargetProxy$$EnhancerByCGLIB$$99d2ba72 var1 = (TargetProxy$$EnhancerByCGLIB$$99d2ba72)var0;
          if(!var1.CGLIB$BOUND) {
              var1.CGLIB$BOUND = true;
              Object var10000 = CGLIB$THREAD_CALLBACKS.get();
              if(var10000 == null) {
                  var10000 = CGLIB$STATIC_CALLBACKS;
                  if(CGLIB$STATIC_CALLBACKS == null) {
                      return;
                  }
              }
  
              var1.CGLIB$CALLBACK_0 = (MethodInterceptor)((Callback[])var10000)[0];
          }
  
      }
  
      public Object newInstance(Callback[] var1) {
          CGLIB$SET_THREAD_CALLBACKS(var1);
          TargetProxy$$EnhancerByCGLIB$$99d2ba72 var10000 = new TargetProxy$$EnhancerByCGLIB$$99d2ba72();
          CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
          return var10000;
      }
  
      public Object newInstance(Callback var1) {
          CGLIB$SET_THREAD_CALLBACKS(new Callback[]{var1});
          TargetProxy$$EnhancerByCGLIB$$99d2ba72 var10000 = new TargetProxy$$EnhancerByCGLIB$$99d2ba72();
          CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
          return var10000;
      }
  
      public Object newInstance(Class[] var1, Object[] var2, Callback[] var3) {
          CGLIB$SET_THREAD_CALLBACKS(var3);
          TargetProxy$$EnhancerByCGLIB$$99d2ba72 var10000 = new TargetProxy$$EnhancerByCGLIB$$99d2ba72;
          switch(var1.length) {
          case 0:
              var10000.<init>();
              CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
              return var10000;
          default:
              throw new IllegalArgumentException("Constructor not found");
          }
      }
  
      public Callback getCallback(int var1) {
          CGLIB$BIND_CALLBACKS(this);
          MethodInterceptor var10000;
          switch(var1) {
          case 0:
              var10000 = this.CGLIB$CALLBACK_0;
              break;
          default:
              var10000 = null;
          }
  
          return var10000;
      }
  
      public void setCallback(int var1, Callback var2) {
          switch(var1) {
          case 0:
              this.CGLIB$CALLBACK_0 = (MethodInterceptor)var2;
          default:
          }
      }
  
      public Callback[] getCallbacks() {
          CGLIB$BIND_CALLBACKS(this);
          return new Callback[]{this.CGLIB$CALLBACK_0};
      }
  
      public void setCallbacks(Callback[] var1) {
          this.CGLIB$CALLBACK_0 = (MethodInterceptor)var1[0];
      }
  
      static {
          CGLIB$STATICHOOK1();
      }
  }
  ```

* 具体执行逻辑与JDK动态代理基本类似

## 14.5，集中常见的代码模式变体

* 防火墙代理：内网通过代理穿透防火墙，实现对公网的访问
* 缓存代理：请求图片，文件等大资源时，先从缓存获取，取到则OK，取不到从库中获取并缓存
* 远程代理：远程对象的本地使用，通过远程代理将远程对象本地化。远程代理通过网络和真正的远程对象沟通
* 同步代理：多线程编程中，完成线程间同步

# 15，模板方法模式（TemplateMethod）

## 15.1，基本介绍

* 模板方法模式（Template Method Pattern），在一个抽象类中公开定义执行它的方法的模板。子类中可以按需重写相关方法进行自定义，调用则通过抽象类以多态的形式进行。
* 简单来说，模板方法模式定义了一个算法骨架，将一些步骤的实现延伸到子类中，使得子类可以不改变算法结构，但能重定义算法中的某个步骤

## 15.2，类图

![1606989545919](E:\gitrepository\study\note\image\designMode\1606989545919.png)

* 定义顶层抽象类：`CommonTemplate`，在该抽象类中定义算法骨架，对公共的部分进行公共实现，对需要子类实现的部分提供抽象方法，由子类继承实现
* 定义具体类：`Concrete`，继承自抽象类，并重写抽象方法，实现自定义算法；也可覆盖父类中已经实现的公共方法，进行算法覆盖	

## 15.3，代码实现

* `CommonTemplate`：顶层抽象类

  ```java
  package com.self.designmode.template;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 18:03
   **/
  public abstract class CommonTemplate {
  
      public void common() {
          commonSomething();
          doSomething();
      }
  
      public abstract void doSomething();
  
      private void commonSomething() {
          System.out.println("公共方法...");
      }
  
  }
  ```

* `Concrete_1`：具体类_1

  ```java
  package com.self.designmode.template;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 18:04
   **/
  public class Concrete_1 extends CommonTemplate {
      @Override
      public void doSomething() {
          System.out.println("A doSomething...");
      }
  }
  ```

* `Concrete_2`：具体类_2

  ```java
  package com.self.designmode.template;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 18:04
   **/
  public class Concrete_2 extends CommonTemplate {
      @Override
      public void doSomething() {
          System.out.println("B doSomething...");
      }
  }
  ```

* `Client`：客户端

  ```java
  package com.self.designmode.template;
  
  /**
   * @author PJ_ZHANG
   * @create 2020-12-03 18:04
   **/
  public class Client {
  
      public static void main(String[] args) {
          CommonTemplate concreteA = new Concrete_1();
          concreteA.common();
  
          CommonTemplate concreteB = new Concrete_2();
          concreteB.common();
      }
  
  }
  ```

## 15.4，注意事项和细节

* 基本思想：基础算法只存在在一个地方，即父类中。需要进行算法基础算法修改时，只需要修改一处，子类中会默认继承
* 实现代码最大化复用，父类中的模板方法和已经实现的基础算法会被子类所共用
* 在统一了算法的同时，提供了算法的灵活性。父类模板方法保证算法结构不变，同时子类提供部分算法步骤的实现
* 不足之处：每一个不同的实现都要重写一个子类实现，导致类个数增加，系统庞大
* 一般模板方法需要加上final关键字，防止算法结构被子类重写打散
* 使用场景：需要完成某个过程，该过程需要一系列步骤，步骤逻辑基本一致，但存在个别步骤实现方式不同，可以考虑使用模板方法模式处理

# 16，命令模式（Command）

