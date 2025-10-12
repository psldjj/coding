package com.psl.coding;

/**
 * volatile保证可见性、禁止重排序
 * 1.多个线程修改后立即可见
 * 2.禁止重排序，双锁检测时防止重排序（构造方法溢出）
 * */
public class VolatileTest {
    //需要加volatile，
    private static volatile VolatileTest INSTANCE;

    public static void main(String[] args) {

    }

    public static VolatileTest getInstance(){

        if(INSTANCE==null){ // ①
            synchronized (VolatileTest.class){
                if(INSTANCE==null){
                    //这里分为3步
                    //(1)分配内存
                    //（2）初始化数据
                    //（3）INSTANCE指向对象
                    //（2）和（3）可能重排序，那么另一个线程在①检测到不为空后就可能返回一个没出初始化数据完成的对象。
                    INSTANCE=new VolatileTest();
                }
            }
        }
        return INSTANCE;
    }
}
