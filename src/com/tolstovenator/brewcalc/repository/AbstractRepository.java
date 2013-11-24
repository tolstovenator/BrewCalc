package com.tolstovenator.brewcalc.repository;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class  AbstractRepository <K,V> {

	protected IngredientService service;
	protected Map<K, V> map = new TreeMap<K, V>();
	private String fileName;

	protected String getValue(Element element, String subTag) {
		NodeList childNodes = element.getElementsByTagName(subTag).item(0).getChildNodes();
		if (childNodes.getLength() == 0) return "";
		return childNodes.item(0).getNodeValue();
	}

	protected void appendProperty(Document document, Element sugarElement, String elementName,
			Number elementValue) {
				appendProperty(document, sugarElement, elementName, elementValue.toString());
			}

	protected void appendProperty(Document document, Element sugarElement, String elementName,
			String elementValue) {
				Element element = document.createElement(elementName);
				element.appendChild(document.createTextNode(elementValue));
				sugarElement.appendChild(element);
			}
	
	public AbstractRepository(InputStream inputStream, IngredientService service, String fileName) {
		this.service = service;
		this.fileName = fileName;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (inputStream);
			Element root = doc.getDocumentElement();
			NodeList list = root.getChildNodes();
			for (int i = 0; i < list.getLength() ; i ++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)node;
					fillFields(element);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to load yeast list", e);
		}
	}
	
	public AbstractRepository(IngredientService service, String fileName) {
		this.service = service;
		this.fileName = fileName;
	}
	
	public abstract void fillFields(Element element);
	
	public ArrayList<V> getValues() {
		return new ArrayList<V>(map.values());
	}
	
	public V getValueByKey(K key) {
		return map.get(key);
	}
	
	public void add(K key, V value) {
		map.put(key, value);
		try {
			FileOutputStream fos = service.openFileOutput(fileName, IngredientService.MODE_MULTI_PROCESS);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("sugars");
			document.appendChild(rootElement);
			for (V v: map.values()) {
				rootElement.appendChild(createElement(document, v));
			}
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result result = new StreamResult(fos);
			Source source = new DOMSource(document);
			transformer.transform(source, result);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	protected abstract Element createElement(Document document, V value);
	
	public void update(K key, V value) {
		map.remove(key);
		add(key, value);
	}

}
