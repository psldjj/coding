package com.psl.coding.dubbo.consumer.inject;

/**
 * @author shoulong.peng
 * @date 2025/10/10
 */
public class InjectObjA implements InjectObj{

    private InjectInj injectInjA;

    @Override
    public void inject(String s) {
        System.out.println("A"+s);
    }

    public void setInjectInjA(InjectInj injectInjA){
        System.out.println("触发set注入");
        this.injectInjA = injectInjA;
    }
}
