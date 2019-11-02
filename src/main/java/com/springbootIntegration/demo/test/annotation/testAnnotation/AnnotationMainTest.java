package com.springbootIntegration.demo.test.annotation.testAnnotation;

import com.springbootIntegration.demo.test.annotation.AnnotationConfigApplicationContext;

/**
 * @author liukun
 * @description 测试类，用于测试是否通过
 * @date 2019/11/1
 */
public class AnnotationMainTest {
    public static void main(String[] args) throws Exception {
        //设置扫描包
        String packageScanPath = "com.springbootIntegration.demo";
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(packageScanPath);
        AppleBox appleBox = (AppleBox)annotationConfigApplicationContext.getBean("AppleBox");
        System.out.println(appleBox);

        BananaBox bananaBox = (BananaBox)annotationConfigApplicationContext.getBean("BananaBox");
        System.out.println(bananaBox);

        Box Box = (Box)annotationConfigApplicationContext.getBean("Box");
        System.out.println(Box);

        MixBox MixBox = (MixBox)annotationConfigApplicationContext.getBean("MixBox");
        System.out.println(MixBox);
    }
}
