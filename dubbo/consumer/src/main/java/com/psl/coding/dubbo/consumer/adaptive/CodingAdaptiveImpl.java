package com.psl.coding.dubbo.consumer.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

/**
 * @author shoulong.peng
 * @date 2025/10/10
 */
@Adaptive
public class CodingAdaptiveImpl implements Coding{
    private CodingA codingA;
    private CodingB codingB;

    @Override
    public String code(URL url) {
        return codingA.code(url);
    }

    @Override
    public String sleep(URL url) {
        return codingB.sleep(url);
    }
}
