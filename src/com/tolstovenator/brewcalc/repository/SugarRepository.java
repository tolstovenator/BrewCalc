package com.tolstovenator.brewcalc.repository;

import java.io.InputStream;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tolstovenator.brewcalc.repository.Sugar.SugarKey;
import com.tolstovenator.brewcalc.repository.Sugar.SugarType;

public class SugarRepository extends AbstractRepository<SugarKey, Sugar>{
	
	public SugarRepository(InputStream inputStream, IngredientService service) {
		super(inputStream, service, IngredientService.SUGARS_XML);
	}
	
	public SugarRepository(IngredientService service) {
		super(service, IngredientService.SUGARS_XML);
	}
	
	@Override
	public void fillFields(Element sugarElement) {
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
		map.put(sugar.getSugarKey(), sugar);
	}

	@Override
	protected Element createElement(Document document, Sugar sugar) {
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
				return sugarElement;
	}
}
