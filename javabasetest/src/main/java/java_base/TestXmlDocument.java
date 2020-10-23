package java_base;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author kled
 * @version $Id: java_base.TestXmlDocument.java, v 0.1 2018-07-10 16:29:47 kled Exp $
 */
public class TestXmlDocument {
    public static void main(String[] args) throws Exception{
        //newInstance声明 获得一个剖析器parser
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        //DocumentBuilder得到一个文档剖析器实例
        DocumentBuilder db =dbf.newDocumentBuilder();
        //从XML得到一个文档
        Document document=db.parse(TestXmlDocument.class.getClassLoader().getResource("").getPath()+"books.xml");

        NodeList books=document.getElementsByTagName("book");
        for (int i = 0; i < books.getLength(); i++) {
            Element element = (Element) books.item(i);
            Attr attributeNode = element.getAttributeNode("category");
            System.out.println(attributeNode.getNodeType()+ "value:" + attributeNode.getValue());

            //System.out.println("name :"+books.item(i).getNodeName()+" type:"+books.item(i).getNodeType()+" value:"+books.item(i).getNodeValue());
        }
    }
}
