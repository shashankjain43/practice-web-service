package com.snapdeal.cps.common.utils;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class PogXmlWriter {

    private File file;
    private String[] fieldMap;
    
    public PogXmlWriter(File file, String fieldMap){
        this.file = file;
        this.fieldMap = fieldMap.split(",");
    }
    
    public File getFile() {
        return file;
    }

    public String[] getFieldMap() {
        return fieldMap;
    }
    
    public void writeToFile(List<Object> objects) throws Exception{
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        // Create root element
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("feed");
        doc.appendChild(root);
        
        // Add entry elements
        for(Object obj: objects){
            Element entry = doc.createElement("entry");
            
            for(int i=0;i<fieldMap.length;i++){
                Element dataTag = doc.createElement(fieldMap[i].split(":")[0]);
                dataTag.appendChild(doc.createTextNode(BeanUtils.getProperty(obj, fieldMap[i].split(":")[1])));
                
                entry.appendChild(dataTag);
            }
            
            root.appendChild(entry);
        }
        
        // Write to file
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        
        DOMSource dom = new DOMSource(doc);
        StreamResult outStream = new StreamResult(file);
        transformer.transform(dom, outStream);
    }
}
