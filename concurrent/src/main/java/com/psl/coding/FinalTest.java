package com.psl.coding;

public class FinalTest {

    /**
     * 必须在类加载时就初始化
     * */
    private static final Test T;
    private static final TestB B;

    static {
        T = new Test(1,2);
        B = new TestB(1,2);
    }


    /**
     * 对于final域，必须要在构造方法中就初始化，
     * 或者直接初始化
     * */
    public static class Test{

        private final int a;
        private final int b;

        public Test(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class  TestB{
        private  int a;
        private  int b;

        public TestB(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
