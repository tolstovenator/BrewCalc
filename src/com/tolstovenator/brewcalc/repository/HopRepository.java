package com.tolstovenator.brewcalc.repository;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
