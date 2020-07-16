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
