package com.springbootIntegration.demo.test.sax;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author liukun
 * @description
 * @date 2020/4/1
 */
public class Test {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        //1.得到xml文件对应的资源，可以是xml的输入流，文件和uri
        Resource resource = new ClassPathResource("config/sax/student.xml");
        InputStream inputStream = resource.getInputStream();
        //2.得到SAX解析工厂（SAXParserFactory）
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        //3.由解析工厂生产一个SAX解析器（SAXParser）
        SAXParser saxParser = saxParserFactory.newSAXParser();
        //4.传入输入流和handler给解析器，调用parse()解析
        MyXmlParseHandler myXmlParseHandler = new MyXmlParseHandler();
        saxParser.parse(inputStream,myXmlParseHandler);

        // 获取解析出的属性
        List<Student> list = myXmlParseHandler.getList();
        list.forEach(System.out::println);
    }
}
