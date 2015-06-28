package com.booking.suite;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanley on 6/28/2015.
 */
public class Util {
    public static String getXmlString() throws IOException {

        Namespace ns = Namespace.getNamespace("http://xml.ycs.agoda.com");
        Element req=new Element("GetAllLanguagesRequest",ns);
        Element auth=new Element("Authentication");
        auth.setAttribute("APIKey","a81016e4-b356-4ce6-87b2-bdfcc3270832");
        auth.setAttribute("HotelID","148832");
        req.addContent(auth);
        req.addContent(new Element("RequestedLanguage").setText("en"));
        Document doc=new Document(req);
        ByteArrayOutputStream byteRsp=new ByteArrayOutputStream();
        XMLOutputter xmlOut=new XMLOutputter();
        xmlOut.output(doc, byteRsp);
        //System.out.println(byteRsp.toString());
        String result=byteRsp.toString();
        byteRsp.close();
        return result;
    }

    public static String doPost(String url,String xmlString) throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        HttpPost httppost = new HttpPost(url);
        StringEntity myEntity = new StringEntity(xmlString,"UTF-8");
        httppost.addHeader("Content-Type", "text/xml");
        httppost.setEntity(myEntity);
        CloseableHttpResponse response = httpclient.execute(httppost);

        HttpEntity entity = response.getEntity();
        //System.out.println(EntityUtils.toString(entity));
        return EntityUtils.toString(entity);

    }

    public static String parseXML(String XMLString) throws JDOMException, IOException {
        StringReader xmlReader=new StringReader(XMLString);
        InputSource xmlSource=new InputSource(xmlReader);
        SAXBuilder xmlBuilder=new SAXBuilder();
        Document doc=xmlBuilder.build(xmlSource);
        Element rootEle=doc.getRootElement();
        List<Element> eleList=rootEle.getChildren();
        for(Element ele:eleList){
            if(ele.hasAttributes())
            {

            }
        }
        return null;

    }

    public static void main(String[] args)  throws IOException{

        String result=doPost("http://sandbox.xml-ycs.agoda.com/xmlservice.aspx",getXmlString());
        System.out.println(result);
    }


}
