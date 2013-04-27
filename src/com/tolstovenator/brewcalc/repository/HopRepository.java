package com.tolstovenator.brewcalc.repository;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tolstovenator.brewcalc.repository.Hop.HopForm;
import com.tolstovenator.brewcalc.repository.Hop.HopUsage;

import android.content.res.AssetManager;

public class HopRepository {
	
	private Map<String, Hop> hops = new TreeMap<String, Hop>();
	
	public HopRepository(AssetManager assetManager) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (assetManager.open("hops.xml"));
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
					hop.setHopForm(HopForm.PELLET);
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
	
	public ArrayList<Hop> getHops() {
		return new ArrayList<Hop>(hops.values()); 
	}
	
	public Hop getHopByName(String name) {
		return hops.get(name);
	}
	
}
