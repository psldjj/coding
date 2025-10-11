package com.psl.coding.dubbo.consumer.inject;

import org.apache.dubbo.common.URL;

/**
 * @author shoulong.peng
 * @date 2025/10/10
 */
public class InjectInjA implements InjectInj{
    @Override
    public void inj(URL url) {
        System.out.println("inj");
    }
}
