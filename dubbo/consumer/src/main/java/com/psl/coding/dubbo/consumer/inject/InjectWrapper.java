package com.psl.coding.dubbo.consumer.inject;

/**
 * @author shoulong.peng
 * @date 2025/10/10
 */
public class InjectWrapper implements InjectObj{

    private InjectObj injectObj;
    private InjectInj injectInj;

    public InjectWrapper(InjectObj injectObj) {
        this.injectObj = injectObj;
    }

    @Override
    public void inject(String s) {
        System.out.println("InjectWrapper");
        injectObj.inject(s);
    }

    public void setInjectInj(InjectInj injectInj){
        System.out.println("wrapper触发set注入");
        this.injectInj = injectInj;
    }
}
