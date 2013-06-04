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

import com.tolstovenator.brewcalc.repository.Hop.HopForm;
import com.tolstovenator.brewcalc.repository.Hop.HopUsage;
import com.tolstovenator.brewcalc.repository.Sugar.SugarKey;
import com.tolstovenator.brewcalc.repository.Sugar.SugarType;

public class SugarRepository {
	
	private IngredientService service;
	private Map<SugarKey, Sugar> sugars = new TreeMap<SugarKey, Sugar>();
	
	public SugarRepository(InputStream inputStream, IngredientService service) {
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
					Element sugarElement = (Element)node;
					Sugar sugar = new Sugar();
					sugar.setName(getValue(sugarElement, "name"));
					sugar.setOrigin(getValue(sugarElement, "origin"));
					sugar.setDescription(getValue(sugarElement, "description"));
					sugar.setSupplier(getValue(sugarElement, "supplier"));
					int type = Integer.valueOf(getValue(sugarElement, "sugarType"));
					if (type == 4) type = 1;
					sugar.setSugarType(SugarType.values()[type]);
					sugar.setMustMash(getValue(sugarElement, "mustMash").equals("1"));
					sugar.setColour(Double.valueOf(getValue(sugarElement, "colour")).intValue());
					sugar.setFgDry(Double.valueOf(getValue(sugarElement, "fgDry")));
					sugar.setMoisture(Double.valueOf(getValue(sugarElement, "moisture")));
					sugar.setCoarseFineDiff(Double.valueOf(getValue(sugarElement, "coarseFineDiff")));
					sugar.setMaxInBatch(Double.valueOf(getValue(sugarElement, "maxInBatch")));
					sugar.setProtein(Double.valueOf(getValue(sugarElement, "protein")));
					sugar.setTsn(Double.valueOf(getValue(sugarElement, "tsn")));
					sugar.setHop(Double.valueOf(getValue(sugarElement, "hop")));
					sugars.put(sugar.getSugarKey(), sugar);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to load sugar list", e);
		}
	}
	
	private String getValue(Element element, String subTag) {
		NodeList childNodes = element.getElementsByTagName(subTag).item(0).getChildNodes();
		if (childNodes.getLength() == 0) return "";
		return childNodes.item(0).getNodeValue();
	}
	
	public SugarRepository(IngredientService service) {
		this.service = service;
	}
	
	public ArrayList<Sugar> getSugars() {
		return new ArrayList<Sugar>(sugars.values()); 
	}
	
	public Sugar getSugarByName(SugarKey sugarKey) {
		return sugars.get(sugarKey);
	}

	public void add(Sugar editSugar) {
		sugars.put(editSugar.getSugarKey(), editSugar);
		try {
			FileOutputStream fos = service.openFileOutput(IngredientService.HOPS_XML, IngredientService.MODE_MULTI_PROCESS);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("sugars");
			document.appendChild(rootElement);
			for (Sugar sugar: sugars.values()) {
				Element sugarElement = document.createElement("sugar");
				appendProperty(document, sugarElement, "name", sugar.getName());
				appendProperty(document, sugarElement, "origin", sugar.getOrigin());
				appendProperty(document, sugarElement, "description", sugar.getDescription());
				appendProperty(document, sugarElement, "supplier", sugar.getSupplier());
				appendProperty(document, sugarElement, "sugarType", sugar.getSugarType().ordinal());
				appendProperty(document, sugarElement, "mustMash", sugar.isMustMash() ? 1 : 0);
				appendProperty(document, sugarElement, "colour", sugar.getColour());
				appendProperty(document, sugarElement, "fgDry", sugar.getFgDry());
				appendProperty(document, sugarElement, "moisture", sugar.getMoisture());
				appendProperty(document, sugarElement, "coarseFineDiff", sugar.getCoarseFineDiff());
				appendProperty(document, sugarElement, "maxInBatch", sugar.getMaxInBatch());
				appendProperty(document, sugarElement, "protein", sugar.getProtein());
				appendProperty(document, sugarElement, "tsn", sugar.getTsn());
				appendProperty(document, sugarElement, "hop", sugar.getHop());
				appendProperty(document, sugarElement, "ph", sugar.getpH());
				rootElement.appendChild(sugarElement);
				
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
	
	private void appendProperty(Document document, Element sugarElement, String elementName, Number elementValue) {
		appendProperty(document, sugarElement, elementName, elementValue.toString());
	}
	
	private void appendProperty(Document document, Element sugarElement, String elementName, String elementValue) {
		Element element = document.createElement(elementName);
		element.appendChild(document.createTextNode(elementValue));
		sugarElement.appendChild(element);
	}

	public void update(SugarKey selectedItem, Sugar editSugar) {
		sugars.remove(selectedItem);
		add(editSugar);
	}

}
