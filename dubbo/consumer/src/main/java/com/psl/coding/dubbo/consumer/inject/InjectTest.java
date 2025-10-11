package com.psl.coding.dubbo.consumer.inject;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;

/**
 * dubbo在创建spi实例时可以执行依赖注入，支持两种注入方式
 * 1.set注入(注入的对象还必须要是adaptive的
 * 2.构造方法注入，只能有一个入参，并且入参是当前实现类的SPI接口。这其实是一种包装机制。
 * 会把Inject的实现通过构造方法注入到InjectWrapper中。
 * public class InjectWrapper implements Inject{
 *     public InjectWrapper(Inject inject){
 *         this.inject = inject;
 *     }
 * }
 * 在文件META-INF/dubbo/com.psl.coding.dubbo.consumer.inject.InjectObj中
 * 直接添加com.psl.coding.dubbo.consumer.inject.InjectWrapper，不需要name。
 * @author shoulong.peng
 * @date 2025/10/10
 */
public class InjectTest {
    public static void main(String[] args) {
        ExtensionLoader<InjectObj> extensionLoader = ApplicationModel.defaultModel().getExtensionLoader(InjectObj.class);
        InjectObj injectA = extensionLoader.getExtension("injectA");
        injectA.inject("abc");
    }
}
