package com.tolstovenator.brewcalc.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tolstovenator.brewcalc.repository.Hop.HopForm;
import com.tolstovenator.brewcalc.repository.Hop.HopUsage;

import android.content.res.AssetManager;

public class HopRepository {
	
	private IngredientService service;
	private Map<String, Hop> hops = new TreeMap<String, Hop>();
	
	public HopRepository(InputStream inputStream, IngredientService service) {
		this.service = service;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (inputStream);
			Element root = doc.getDocumentElement();
			NodeList list = root.getChildNodes();
			for (int i = 0; i < list.getLength() ; i ++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element hopElement = (Element)node;
					Hop hop = new Hop();
					hop.setName(hopElement.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
					hop.setAlpha(Double.valueOf(hopElement.getElementsByTagName("alpha").item(0).getChildNodes().item(0).getNodeValue()));
					hop.setBeta(Double.valueOf(hopElement.getElementsByTagName("beta").item(0).getChildNodes().item(0).getNodeValue()));
					hop.setHumulene(Double.valueOf(hopElement.getElementsByTagName("humulene").item(0).getChildNodes().item(0).getNodeValue()));
					hop.setCohumulone(Double.valueOf(hopElement.getElementsByTagName("cohumulone").item(0).getChildNodes().item(0).getNodeValue()));
					hop.setStorageFactor(Double.valueOf(hopElement.getElementsByTagName("hsi").item(0).getChildNodes().item(0).getNodeValue()).intValue());
					hop.setCaryophyllene(Double.valueOf(hopElement.getElementsByTagName("caryophyllene").item(0).getChildNodes().item(0).getNodeValue()));
					hop.setMyrcene(Double.valueOf(hopElement.getElementsByTagName("myrcene").item(0).getChildNodes().item(0).getNodeValue()));
					if (hopElement.getElementsByTagName("form").getLength() == 0) {
						hop.setHopForm(HopForm.PELLET);
					} else {
						hop.setHopForm(HopForm.values()[Integer.valueOf(hopElement.getElementsByTagName("form").item(0).getChildNodes().item(0).getNodeValue())]);
					}
					hop.setHopUsage(HopUsage.values()[Integer.valueOf(hopElement.getElementsByTagName("type").item(0).getChildNodes().item(0).getNodeValue())]);
					hop.setOrigin(hopElement.getElementsByTagName("origin").item(0).getChildNodes().item(0).getNodeValue());
					hop.setDescription(hopElement.getElementsByTagName("notes").item(0).getChildNodes().item(0).getNodeValue());
					
					hops.put(hop.getName(), hop);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to load hops list", e);
		}
	}
	
	public HopRepository(IngredientService service) {
		this.service = service;
	}
	
	public ArrayList<Hop> getHops() {
		return new ArrayList<Hop>(hops.values()); 
	}
	
	public Hop getHopByName(String name) {
		return hops.get(name);
	}

	public void add(Hop changedHop) {
		hops.put(changedHop.getName(), changedHop);
		try {
			FileOutputStream fos = service.openFileOutput(IngredientService.HOPS_XML, IngredientService.MODE_MULTI_PROCESS);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("hops");
			document.appendChild(rootElement);
			for (Hop hop : hops.values()) {
				Element hopElement = document.createElement("hop");
				appendProperty(document, hop, hopElement, "name", hop.getName());
				appendProperty(document, hop, hopElement, "origin", hop.getOrigin());
				appendProperty(document, hop, hopElement, "notes", hop.getDescription());
				appendProperty(document, hop, hopElement, "alpha", hop.getAlpha());
				appendProperty(document, hop, hopElement, "beta", hop.getBeta());
				appendProperty(document, hop, hopElement, "hsi", hop.getStorageFactor());
				appendProperty(document, hop, hopElement, "myrcene", hop.getMyrcene());
				appendProperty(document, hop, hopElement, "humulene", hop.getHumulene());
				appendProperty(document, hop, hopElement, "cohumulone", hop.getCohumulone());
				appendProperty(document, hop, hopElement, "caryophyllene", hop.getCaryophyllene());
				appendProperty(document, hop, hopElement, "type", hop.getHopUsage().ordinal());
				appendProperty(document, hop, hopElement, "form", hop.getHopForm().ordinal());
				rootElement.appendChild(hopElement);
				
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

	private void appendProperty(Document document, Hop hop, Element hopElement, String elementName, Number elementValue) {
		appendProperty(document, hop, hopElement, elementName, elementValue.toString());
	}
	
	private void appendProperty(Document document, Hop hop, Element hopElement, String elementName, String elementValue) {
		Element element = document.createElement(elementName);
		element.appendChild(document.createTextNode(elementValue));
		hopElement.appendChild(element);
	}

	public void update(String selectedItem, Hop changedHop) {
		hops.remove(selectedItem);
		add(changedHop);
	}
	
}
