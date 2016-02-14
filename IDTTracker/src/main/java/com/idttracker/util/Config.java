package com.idttracker.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;  
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;  
import org.xml.sax.SAXException;  
import org.xml.sax.helpers.DefaultHandler;
/**
 *   
 * @author Ben Guo
 *
 */
public class Config extends DefaultHandler{  
private static String value;	
public static String read(final String xml){
  try {    
   SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();   
   SAXParser saxParser = saxParserFactory.newSAXParser();   
   DefaultHandler defaultHandler = new DefaultHandler(){  
  
    String data ="close";   
 
    public void startElement(String uri, String localName, String qName,  
      Attributes attributes) throws SAXException {  
     
     if (qName.equalsIgnoreCase(xml)) {  
      data = "open";  
     } 
    }  
    
    public void characters(char ch[], int start, int length)  
      throws SAXException {  
       
     if (data.equals("open")) {  
      value = new String(ch, start, length);
     }
    }    
    
    public void endElement(String uri, String localName, String qName)  
      throws SAXException {  
 
     if (qName.equalsIgnoreCase(xml)) {  
      data = "close";
     }
    }
   };
   saxParser.parse("config.xml", defaultHandler);  
  } catch (Exception e) {  
   e.printStackTrace();  
  }
return value;  
 }
public void write(String tag, String value){
try{
	String filepath = "config.xml";
	DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuild = docFac.newDocumentBuilder();
	Document doc = docBuild.parse(filepath);
	
	Node atr = doc.getElementsByTagName("list").item(0);
	NodeList list = atr.getChildNodes();
	
	for (int i = 0; i < list.getLength(); i++) {		
	Node node = list.item(i);
		if (tag.equals(node.getNodeName())) {
			node.setTextContent(value);
		}
	}
	
	TransformerFactory transFac = TransformerFactory.newInstance();
	Transformer trans = transFac.newTransformer();
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File(filepath));
	trans.transform(source, result);
	System.out.println("Done");
	} 

	catch (ParserConfigurationException pce) {
	pce.printStackTrace();
    }

	catch (TransformerException tfe) {
	tfe.printStackTrace();
	}

	catch (IOException ioe) {
	ioe.printStackTrace();
	}

	catch (SAXException sae) {
	sae.printStackTrace();
   	} 
}
}
 