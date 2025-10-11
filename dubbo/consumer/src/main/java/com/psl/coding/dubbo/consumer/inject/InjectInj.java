package com.psl.coding.dubbo.consumer.inject;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * 被注入的对象
 * @author shoulong.peng
 * @date 2025/10/10
 */
@SPI("injectInjA")
public interface InjectInj {
    @Adaptive
    void inj(URL url);
}
