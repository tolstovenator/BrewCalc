package com.tolstovenator.brewcalc.repository;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tolstovenator.brewcalc.repository.Yeast.Flocculation;
import com.tolstovenator.brewcalc.repository.Yeast.YeastKey;
import com.tolstovenator.brewcalc.repository.Yeast.YeastMedium;
import com.tolstovenator.brewcalc.repository.Yeast.YeastType;


public class YeastRepository extends AbstractRepository <YeastKey, Yeast>{
	
	public YeastRepository(InputStream inputStream, IngredientService service) {
		super(inputStream, service, IngredientService.YEAST_XML);
	}

	@Override
	public void fillFields(Element element) {
		Yeast yeast = new Yeast();
		yeast.setName(getValue(element, "name"));
		yeast.setLab(getValue(element, "lab"));
		yeast.setCatalogId(getValue(element, "catalogId"));
		yeast.setYeastType(YeastType.values()[Integer.valueOf(getValue(element, "type")) % YeastType.values().length]);
		yeast.setYeastMedium(YeastMedium.values()[Integer.valueOf(getValue(element, "form"))]);
		yeast.setFlocculation(Flocculation.values()[Integer.valueOf(getValue(element, "flocculation"))]);
		yeast.setLowAttenuation(Double.valueOf(getValue(element, "minAttenuation")).intValue());
		yeast.setHighAttenuation(Double.valueOf(getValue(element, "maxAttenuation")).intValue());
		yeast.setMinTemp(Double.valueOf(getValue(element, "minTemp")));
		yeast.setMaxTemp(Double.valueOf(getValue(element, "maxTemp")));
		yeast.setMaxReuse(Integer.valueOf(getValue(element, "maxReuse")));
		yeast.setUseStarter(getValue(element, "useStarter").equals("1"));
		yeast.setAddToSecondary(getValue(element, "addToSecondary").equals("1"));
		map.put(yeast.getYeastKey(), yeast);
	}
	
	public YeastRepository(IngredientService service) {
		super(service, IngredientService.YEAST_XML);
	}

	@Override
	protected Element createElement(Document document, Yeast yeast) {
		Element element = document.createElement("yeast");
		appendProperty(document, element, "name", yeast.getName());
		appendProperty(document, element, "lab", yeast.getLab());
		appendProperty(document, element, "catalogId", yeast.getCatalogId());
		appendProperty(document, element, "type", yeast.getYeastType().ordinal());
		appendProperty(document, element, "form", yeast.getYeastMedium().ordinal());
		appendProperty(document, element, "flocculation", yeast.getFlocculation().ordinal());
		appendProperty(document, element, "minAttenuation", yeast.getLowAttenuation());
		appendProperty(document, element, "maxAttenuation", yeast.getHighAttenuation());
		appendProperty(document, element, "minTemp", yeast.getMinTemp());
		appendProperty(document, element, "maxTemp", yeast.getMaxTemp());
		appendProperty(document, element, "maxReuse", yeast.getMaxReuse());
		appendProperty(document, element, "useStarter", yeast.isUseStarter() ? 1 : 0);
		appendProperty(document, element, "addToSecondary", yeast.isAddToSecondary() ? 1 : 0);
		return element;
	}

}
