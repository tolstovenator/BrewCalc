package com.tolstovenator.brewcalc.repository;

import com.tolstovenator.brewcalc.repository.Sugar.SugarKey;

public class Yeast {
	
	public enum YeastType {
		ALE,
		LAGER,
		WINE,
		CHAMPAGNE
	}
	
	public enum YeastMedium {
		LIQUID,
		DRY,
		AGAR
	}
	
	public enum Flocculation {
		LOW,
		MEDIUM,
		HIGH,
		VERYHIGH
	}
	
	private String lab = "";
	private String name = "";
	private String catalogId = "";
	private YeastType yeastType = YeastType.ALE;
	private YeastMedium yeastMedium = YeastMedium.LIQUID;
	private Flocculation flocculation = Flocculation.HIGH;
	
	private int lowAttenuation;
	private int highAttenuation;
	private double minTemp;
	private double maxTemp;
	private int maxReuse;
	
	private boolean useStarter;
	private boolean addToSecondary;
	
	
	private String flavours = "";
	private String notes = "";
	
	public static class YeastKey implements Comparable<Yeast>{
		private final String lab;
		private final String catalogId;
		
		private YeastKey (String lab, String catalogId) {
			this.lab = lab;
			this.catalogId = catalogId;
		}

		public String getLab() {
			return lab;
		}

		public String getCatalogId() {
			return catalogId;
		}

		@Override
		public String toString() {
			return lab + "/" + catalogId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((catalogId == null) ? 0 : catalogId.hashCode());
			result = prime * result + ((lab == null) ? 0 : lab.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			YeastKey other = (YeastKey) obj;
			if (catalogId == null) {
				if (other.catalogId != null)
					return false;
			} else if (!catalogId.equals(other.catalogId))
				return false;
			if (lab == null) {
				if (other.lab != null)
					return false;
			} else if (!lab.equals(other.lab))
				return false;
			return true;
		}

		@Override
		public int compareTo(Yeast another) {
			int result =  this.lab.compareTo(another.lab);
			return result != 0 ? result : catalogId.compareTo(another.catalogId);
		}
		
		public static YeastKey fromString(String key) {
			String[] tokens = key.split("\\/");
			if (tokens.length == 1) {
				return new YeastKey(tokens[0], null);
			} else {
				if (tokens[1].equals("null")) {
					return new YeastKey(tokens[0], null);
				} else {
					return new YeastKey(tokens[0], tokens[1]);
				}
			}
		}
		
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public YeastType getYeastType() {
		return yeastType;
	}

	public void setYeastType(YeastType yeastType) {
		this.yeastType = yeastType;
	}

	public YeastMedium getYeastMedium() {
		return yeastMedium;
	}

	public void setYeastMedium(YeastMedium yeastMedium) {
		this.yeastMedium = yeastMedium;
	}

	public Flocculation getFlocculation() {
		return flocculation;
	}

	public void setFlocculation(Flocculation flocculation) {
		this.flocculation = flocculation;
	}

	public int getLowAttenuation() {
		return lowAttenuation;
	}

	public void setLowAttenuation(int lowAttenuation) {
		this.lowAttenuation = lowAttenuation;
	}

	public int getHighAttenuation() {
		return highAttenuation;
	}

	public void setHighAttenuation(int highAttenuation) {
		this.highAttenuation = highAttenuation;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getMaxReuse() {
		return maxReuse;
	}

	public void setMaxReuse(int maxReuse) {
		this.maxReuse = maxReuse;
	}

	public boolean isUseStarter() {
		return useStarter;
	}

	public void setUseStarter(boolean useStarter) {
		this.useStarter = useStarter;
	}

	public boolean isAddToSecondary() {
		return addToSecondary;
	}

	public void setAddToSecondary(boolean addToSecondary) {
		this.addToSecondary = addToSecondary;
	}

	public String getFlavours() {
		return flavours;
	}

	public void setFlavours(String flavours) {
		this.flavours = flavours;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public YeastKey getYeastKey() {
		return new YeastKey(lab, catalogId);
	}
	
	

}
