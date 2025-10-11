package com.psl.coding.dubbo.consumer.inject;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author shoulong.peng
 * @date 2025/10/10
 */
@SPI(value = "injectObj")
public interface InjectObj {
    void inject(String s);
}
