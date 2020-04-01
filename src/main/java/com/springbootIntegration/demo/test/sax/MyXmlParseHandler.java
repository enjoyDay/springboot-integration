package com.springbootIntegration.demo.test.sax;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liukun
 * @description 解析XML文件真正的类，因为java中的sax是事件驱动的，因此要继承DefaultHandler
 * @date 2020/4/1
 */
@Slf4j
public class MyXmlParseHandler extends DefaultHandler {
    //存放遍历集合
    private List<Student> list;
    //构建Student对象
    private Student student;
    //用来存放每次遍历后的元素名称(节点名称)
    private String tagName;

    //只调用一次  初始化list集合
    @Override
    public void startDocument() throws SAXException {
        list = new ArrayList<Student>();
        System.out.println("文档解析开始");
    }

    /**
     * 调用多次    开始解析
     *
     * @param uri        xml文档的命名空间
     * @param localName  标签的名字
     * @param qName      带命名空间的标签的名字
     * @param attributes 标签的属性集
     * @throws SAXException 解析异常
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            student = new Student();
            // 由于不知道属性的顺序，这里应该遍历
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("attribute_name：" + attributes.getLocalName(i)
                        + "  attribute_value：" + attributes.getValue(i));
                if ("id".equals(attributes.getLocalName(i))) {
                    student.setId(attributes.getValue(i));
                }
                if ("group".equals(attributes.getLocalName(i))) {
                    student.setGroup(attributes.getValue(i));
                }
            }
        }
        this.tagName = qName;
    }

    /**
     * 调用多次 标签结束的地方
     *
     * @param uri       xml文档的命名空间
     * @param localName 标签的名字
     * @param qName     带命名空间的标签的名字
     * @throws SAXException 解析异常
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("student")) {
            this.list.add(this.student);
        }
        this.tagName = null;
    }

    //只调用一次
    @Override
    public void endDocument() throws SAXException {
        System.out.println("文档解析结束");
    }

    //调用多次，解析标签内容的时候调用
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (this.tagName != null) {
            String date = new String(ch, start, length);
            if (this.tagName.equals("name")) {
                this.student.setName(date);
            } else if (this.tagName.equals("sex")) {
                this.student.setSex(date);
            } else if (this.tagName.equals("age")) {
                this.student.setAge(date);
            } else if (this.tagName.equals("email")) {
                this.student.setEmail(date);
            } else if (this.tagName.equals("birthday")) {
                this.student.setBirthday(date);
            } else if (this.tagName.equals("memo")) {
                this.student.setMemo(date);
            }
        }
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


}
