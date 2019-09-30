package com.springbootIntegration.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * @author liukun
 * @description
 * @date 2019/9/14
 */
public final class XmlUtil {
    private XmlUtil() {
    }

    public static final Map parseXml2Map(String pStrXml) {
        Map map = new HashMap();
        String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        Document document = null;

        try {
            if (pStrXml.indexOf("<?xml") < 0) {
                pStrXml = strTitle + pStrXml;
            }

            document = DocumentHelper.parseText(pStrXml);
        } catch (DocumentException var7) {
            throw new RuntimeException(var7);
        }

        Element elNode = document.getRootElement();
        Iterator it = elNode.elementIterator();

        while(it.hasNext()) {
            Element leaf = (Element)it.next();
            map.put(leaf.getName().toLowerCase(), leaf.getData());
        }

        return map;
    }

    public static Map dom2Map(Element e) {
        Map map = new HashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
                Element iter = (Element)list.get(i);
                List mapList = new ArrayList();
                if (iter.elements().size() > 0) {
                    Map m = dom2Map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
                            mapList = new ArrayList();
                            ((List)mapList).add(obj);
                            ((List)mapList).add(m);
                        } else if ("java.util.ArrayList".equals(obj.getClass().getName())) {
                            mapList = (List)obj;
                            ((List)mapList).add(m);
                        }

                        map.put(iter.getName(), mapList);
                    } else {
                        map.put(iter.getName(), m);
                    }
                } else if (map.get(iter.getName()) != null) {
                    Object obj = map.get(iter.getName());
                    if (!"java.util.ArrayList".equals(obj.getClass().getName())) {
                        mapList = new ArrayList();
                        ((List)mapList).add(obj);
                        ((List)mapList).add(iter.getText());
                    } else if ("java.util.ArrayList".equals(obj.getClass().getName())) {
                        mapList = (List)obj;
                        ((List)mapList).add(iter.getText());
                    }

                    map.put(iter.getName(), mapList);
                } else {
                    map.put(iter.getName(), iter.getText());
                }
            }
        } else {
            map.put(e.getName(), e.getText());
        }

        return map;
    }

    public static final Map parseXml2Map(String pStrXml, String pXPath) {
        Map map = new HashMap();
        String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        Document document = null;

        try {
            if (pStrXml.indexOf("<?xml") < 0) {
                pStrXml = strTitle + pStrXml;
            }

            document = DocumentHelper.parseText(pStrXml);
        } catch (DocumentException var8) {
            throw new RuntimeException(var8);
        }

        Element elNode = document.getRootElement();
        Iterator it = elNode.elementIterator();

        while(it.hasNext()) {
            Element leaf = (Element)it.next();
            map.put(leaf.getName().toLowerCase(), leaf.getData());
        }

        return map;
    }

    public static final String parseDto2Xml(Map<String, String> map, String pRootNodeName) {
        Document document = DocumentHelper.createDocument();
        document.addElement(pRootNodeName);
        Element root = document.getRootElement();
        Iterator var4 = map.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, String> entry = (Entry)var4.next();
            Element leaf = root.addElement((String)entry.getKey());
            leaf.setText((String)entry.getValue());
        }

        String outXml = document.asXML().substring(39);
        return outXml;
    }

    public static final String parseDto2XmlHasHead(Map<String, String> map, String pRootNodeName) {
        Document document = DocumentHelper.createDocument();
        document.addElement(pRootNodeName);
        Element root = document.getRootElement();
        Iterator var4 = map.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, String> entry = (Entry)var4.next();
            Element leaf = root.addElement((String)entry.getKey());
            leaf.setText((String)entry.getValue());
        }

        String outXml = document.asXML();
        return outXml;
    }

    public static final String parseMap2Xml(Map<String, String> map, String pRootNodeName, String pFirstNodeName) {
        Document document = DocumentHelper.createDocument();
        document.addElement(pRootNodeName);
        Element root = document.getRootElement();
        root.addElement(pFirstNodeName);
        Element firstEl = (Element)document.selectSingleNode("/" + pRootNodeName + "/" + pFirstNodeName);
        Iterator var6 = map.entrySet().iterator();

        while(var6.hasNext()) {
            Entry<String, String> entry = (Entry)var6.next();
            firstEl.addAttribute((String)entry.getKey(), (String)entry.getValue());
        }

        String outXml = document.asXML().substring(39);
        return outXml;
    }

    public static final String parseList2Xml(List pList, String pRootNodeName, String pFirstNodeName) {
        Document document = DocumentHelper.createDocument();
        Element elRoot = document.addElement(pRootNodeName);

        for(int i = 0; i < pList.size(); ++i) {
            Map map = (Map)pList.get(i);
            Element elRow = elRoot.addElement(pFirstNodeName);
            Iterator it = map.entrySet().iterator();

            while(it.hasNext()) {
                Entry entry = (Entry)it.next();
                elRow.addAttribute((String)entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        String outXml = document.asXML().substring(39);
        return outXml;
    }

    public static final String parseList2XmlBasedNode(List pList, String pRootNodeName, String pFirstNodeName) {
        Document document = DocumentHelper.createDocument();
        Element output = document.addElement(pRootNodeName);

        for(int i = 0; i < pList.size(); ++i) {
            Map map = (Map)pList.get(i);
            Element elRow = output.addElement(pFirstNodeName);
            Iterator it = map.entrySet().iterator();

            while(it.hasNext()) {
                Entry entry = (Entry)it.next();
                Element leaf = elRow.addElement((String)entry.getKey());
                leaf.setText(String.valueOf(entry.getValue()));
            }
        }

        String outXml = document.asXML().substring(39);
        return outXml;
    }

    public static final List parseXml2List(String pStrXml) {
        List lst = new ArrayList();
        String strTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        Document document = null;

        try {
            if (pStrXml.indexOf("<?xml") < 0) {
                pStrXml = strTitle + pStrXml;
            }

            document = DocumentHelper.parseText(pStrXml);
        } catch (DocumentException var10) {
            throw new RuntimeException(var10);
        }

        Element elRoot = document.getRootElement();
        Iterator elIt = elRoot.elementIterator();

        while(elIt.hasNext()) {
            Element el = (Element)elIt.next();
            Iterator attrIt = el.attributeIterator();
            HashMap map = new HashMap();

            while(attrIt.hasNext()) {
                Attribute attribute = (Attribute)attrIt.next();
                map.put(attribute.getName().toLowerCase(), attribute.getData());
            }

            lst.add(map);
        }

        return lst;
    }

    public static Map<String, Object> dom2Map(Document doc) {
        Map<String, Object> maproot = new HashMap();
        if (doc == null) {
            return maproot;
        } else {
            Element root = doc.getRootElement();
            List list1 = root.elements();
            Iterator var4 = list1.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                Element element = (Element)obj;
                Map<String, Object> map = new HashMap();
                element2Map(element, map);
                maproot.put(element.getName(), map);
            }

            return maproot;
        }
    }

    public static void element2Map(Element e, Map<String, Object> map) {
        List<Element> list = e.elements();
        Iterator var3;
        Object aList;
        if (e.attributeCount() > 0) {
            var3 = e.attributes().iterator();

            while(var3.hasNext()) {
                aList = var3.next();
                Attribute at = (Attribute)aList;
                map.put(at.getName(), at.getValue());
            }
        }

        if (list.size() >= 1 || !DataUtil.isEmpty(e.getText())) {
            if (list.size() < 1 && !DataUtil.isEmpty(e.getText())) {
                map.put("text", e.getText());
            }

            var3 = list.iterator();

            while(var3.hasNext()) {
                aList = var3.next();
                Element iter = (Element)aList;
                Map<String, Object> cMap = new HashMap();
                element2Map(iter, cMap);
                map.put(iter.getName(), cMap);
            }

        }
    }
}

