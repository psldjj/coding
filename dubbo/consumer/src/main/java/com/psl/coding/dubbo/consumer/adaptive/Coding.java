package com.psl.coding.dubbo.consumer.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author shoulong.peng
 * @date 2025/10/9
 */
@SPI(value = "codingA")
public interface Coding {
    /**
     * 根据url的coding参数的值来选择使用哪个实现类。因为是根据url，所以方法参数需要URL
     * ?coding=codingA则表示使用CodingA。
     * condingA在META-INF/dubbo/com.psl.coding.dubbo.consumer.adaptive.Coding 中定义的
     * */
    @Adaptive(value =  "coding")
    String code(URL url);

    @Adaptive(value = "coding")
    String sleep(URL url);
}
