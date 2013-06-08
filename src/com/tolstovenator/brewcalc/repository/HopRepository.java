package com.tolstovenator.brewcalc.repository;

import java.io.InputStream;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tolstovenator.brewcalc.repository.Hop.HopForm;
import com.tolstovenator.brewcalc.repository.Hop.HopUsage;

public class HopRepository extends AbstractRepository<String, Hop>{
	
	
	public HopRepository(InputStream inputStream, IngredientService service) {
		super(inputStream, service, IngredientService.HOPS_XML);
	}

	public HopRepository(IngredientService service) {
		super(service, IngredientService.HOPS_XML);
	}

	@Override
	public void fillFields(Element hopElement) {
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
		hop.setOrigin(getValue(hopElement, "origin"));
		hop.setDescription(getValue(hopElement, "notes"));
		
		map.put(hop.getName(), hop);
		
	}

	@Override
	protected Element createElement(Document document, Hop hop) {
		Element hopElement = document.createElement("hop");
		appendProperty(document, hopElement, "name", hop.getName());
		appendProperty(document, hopElement, "origin", hop.getOrigin());
		appendProperty(document, hopElement, "notes", hop.getDescription());
		appendProperty(document, hopElement, "alpha", hop.getAlpha());
		appendProperty(document, hopElement, "beta", hop.getBeta());
		appendProperty(document, hopElement, "hsi", hop.getStorageFactor());
		appendProperty(document, hopElement, "myrcene", hop.getMyrcene());
		appendProperty(document, hopElement, "humulene", hop.getHumulene());
		appendProperty(document, hopElement, "cohumulone", hop.getCohumulone());
		appendProperty(document, hopElement, "caryophyllene", hop.getCaryophyllene());
		appendProperty(document, hopElement, "type", hop.getHopUsage().ordinal());
		appendProperty(document, hopElement, "form", hop.getHopForm().ordinal());
		return hopElement;
	}
	
}
