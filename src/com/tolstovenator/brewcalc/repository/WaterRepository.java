package com.tolstovenator.brewcalc.repository;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class WaterRepository extends AbstractRepository <String, Water> {
	
	public WaterRepository(InputStream inputStream, IngredientService service) {
		super(inputStream, service, IngredientService.WATER_XML);
	}
	
	public WaterRepository(IngredientService service) {
		super(service, IngredientService.WATER_XML);
	}
	
	@Override
	public void fillFields(Element waterElement) {
		Water water = new Water();
		water.setName(getValue(waterElement, "name"));
		water.setBestBeer(getValue(waterElement, "bestBeer"));
		water.setBicarbonade(Double.valueOf(getValue(waterElement, "bicarbonade")).intValue());
		water.setCalcium(Double.valueOf(getValue(waterElement, "calcium")).intValue());
		water.setChloride(Double.valueOf(getValue(waterElement, "chloride")).intValue());
		water.setMagnesium(Double.valueOf(getValue(waterElement, "magnesium")).intValue());
		water.setpH(Double.valueOf(getValue(waterElement, "ph")).intValue());
		water.setSodium(Double.valueOf(getValue(waterElement, "sodium")).intValue());
		water.setSulfate(Double.valueOf(getValue(waterElement, "sulfate")).intValue());
		map.put(water.getName(), water);
	}

	@Override
	protected Element createElement(Document document, Water water) {
		Element sugarElement = document.createElement("water");
		appendProperty(document, sugarElement, "name", water.getName());
		appendProperty(document, sugarElement, "bestBeer", water.getBestBeer());
		appendProperty(document, sugarElement, "bicarbonade", water.getBicarbonade());
		appendProperty(document, sugarElement, "calcium", water.getCalcium());
		appendProperty(document, sugarElement, "chloride", water.getChloride());
		appendProperty(document, sugarElement, "magnesium", water.getMagnesium());
		appendProperty(document, sugarElement, "ph", water.getpH());
		appendProperty(document, sugarElement, "sodium", water.getSodium());
		appendProperty(document, sugarElement, "sulfate", water.getSulfate());
		return sugarElement;
	}

}
