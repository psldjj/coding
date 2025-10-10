package com.psl.coding.dubbo.consumer.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ScopeModel;
import org.apache.dubbo.rpc.model.ScopeModelUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * adaptive,自适应拓展点：
 * 一个SPI接口有多个实现类，需要根据URL中的参数来加载指定的实现类，并调用该类的方法。这就是自适应拓展。
 * 举例：Coding接口有两个实现类A和B，现在调用方需要调用Coding的某个方法。
 * @Adaptive 放到方法上，就表示这个方法支持自适应拓展。调用没有@Adaptive的方法会直接抛出异常。
 * @Adaptive 放到实现类上，表示不使用自动生成的代理类，而是使用这个实现类来处理。
 *
 *
 * @author shoulong.peng
 * @date 2025/10/9
 */
public class AdaptiveExtensionTest {

    public static void main(String[] args) {
        //硬编码
//        hardCodeInvoke(URL.valueOf("test:127.0.0.1:9090?Coding=codingA&methodName=code&className=com.psl.coding.dubbo.consumer.adaptive.Coding"));

        //反射实现adaptive
//        refInvoke(URL.valueOf("test:127.0.0.1:9090?Coding=codingB&methodName=sleep&className=com.psl.coding.dubbo.consumer.adaptive.Coding"));

        //生成代理类（compile的方法）
        //dubbo的实现方式
//        compileInvoke(URL.valueOf("test:127.0.0.1:9090?Coding=codingB&methodName=sleep&className=com.psl.coding.dubbo.consumer.adaptive.Coding"));


        //adaptive的使用
        Coding adaptiveExtension = ApplicationModel.defaultModel()
                .getExtensionLoader(Coding.class)
                .getAdaptiveExtension();
        adaptiveExtension.code(URL.valueOf("test:127.0.0.1:9090?coding=codingB"));
        adaptiveExtension.sleep(URL.valueOf("test:127.0.0.1:9090?coding=codingB"));

    }




    /**
     * 假设需要调用Coding的methodName接口。
     * 对于dubbo，provider只能拿到需要调用方法的元信息，比如方法名，参数
     * 那么代码的一种写法如下：
     *
     * 存在的问题：需要很多if else来判断调用哪个方法
     * 这中情况可以使用反射处理
     * */
    public static void hardCodeInvoke(URL url){

        ExtensionLoader<Coding> extensionLoader = ApplicationModel.defaultModel().getDefaultModule().getExtensionLoader(Coding.class);

        Coding coding = extensionLoader.getExtension(url.getParameter("Coding"));

        String methodName = url.getParameter("methodName");
        if(methodName.equals("code")){
            coding.code(url);
        }else if(methodName.equals("sleep")){
            coding.sleep(url);
        }
    }

    /**
     * 使用反射的方式替换硬编码
     * 但是反射执行速度非常慢
     * */
    public static void refInvoke(URL url)  {
        try {
            ExtensionLoader<Coding> extensionLoader = ApplicationModel.defaultModel().getDefaultModule().getExtensionLoader(Coding.class);
            Coding coding = extensionLoader.getExtension(url.getParameter("Coding"));
            String methodName = url.getParameter("methodName");
            Method method = coding.getClass().getMethod(methodName);
            Adaptive annotation = method.getAnnotation(Adaptive.class);
            if(annotation == null){
                throw new RuntimeException("当前方法不支持自适应调用");
            }
            method.setAccessible(true);
            //
            method.invoke(coding);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 代码生成，就是生成一个类，然后调用目标对象的方法。
     * 非反射实现，性能比较好
     * */
    public static void compileInvoke(URL url){

        String className = url.getParameter("className");
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        StringBuilder code = new StringBuilder();
        //package com.psl.coding.dubbo.consumer.adaptive;
        String packageInfo = String.format("package %s;\n",aClass.getPackage().getName());


        //import org.apache.dubbo.rpc.model.ScopeModel;
        //import org.apache.dubbo.rpc.model.ScopeModelUtil
        String import1 = String.format("import %s;\n", ScopeModel.class.getName());
        String import2 = String.format("import %s;\n", ScopeModelUtil.class.getName());

        //public class Coding$Adaptive implements com.psl.coding.dubbo.consumer.adaptive.Coding{
        String classDesc = String.format("public class %s$Adaptive implements %s {", aClass.getSimpleName(),aClass.getCanonicalName());


        // public java.lang.String code(java.lang.String arg0){
        //
        // }
        for (Method method : aClass.getMethods()) {
            String.format("public %s %s(%s) %s {\n%s}\n",method.getReturnType().getCanonicalName(),
                    method.getName(),
                    paramTypes(method),
                    methodThrows(method),
                    methodContent(method)
                    );
        }

//        String className = String.format("")



    }

    private static String methodThrows(Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if(exceptionTypes.length >0){
            return String.format("throws %s",Arrays.stream(exceptionTypes).map(e->e.getCanonicalName()).collect(Collectors.joining(",")));
        }
        return "";
    }

    public static String methodContent(Method method){

        //没有注解，那么这个方法不支持自适应拓展，追截抛异常
        Adaptive annotation = method.getAnnotation(Adaptive.class);
        if(annotation == null){
            return "throw new UnsupportedOperationException()";
        }

        //todo
        //ExtensionLoader<Coding> extensionLoader = ApplicationModel.defaultModel().getDefaultModule().getExtensionLoader(Coding.class);
        String content = "";

        return content;



    }

    //java.lang.String arg0,java.lang.String arg1
    public static String paramTypes(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        String collect = IntStream.range(0, parameterTypes.length)
                .mapToObj(e -> String.format("%s arg%s", parameterTypes[e].getCanonicalName(), e))
                .collect(Collectors.joining(","));
        return collect;

    }
}

