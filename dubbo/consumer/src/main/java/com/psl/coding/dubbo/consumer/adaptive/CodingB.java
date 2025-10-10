package com.psl.coding.dubbo.consumer.adaptive;

import org.apache.dubbo.common.URL;

/**
 * @author shoulong.peng
 * @date 2025/10/9
 */
public class CodingB implements Coding{
    @Override
    public String code(URL url) {
        System.out.println("codeB");
        return "codeB";
    }

    @Override
    public String sleep(URL url) {
        System.out.println("sleepB");
        return "sleepB ";
    }
}
