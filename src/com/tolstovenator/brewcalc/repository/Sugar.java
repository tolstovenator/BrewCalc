package com.tolstovenator.brewcalc.repository;

import com.tolstovenator.brewcalc.R;

public class Sugar {
	
	public static final double MAXIMUM_POTENTIAL = 46.21;

	public enum SugarType {
		GRAIN(R.string.grain),
		EXTRACT(R.string.extract),
		SUGAR(R.string.sugar),
		ADJUNCT(R.string.adjunct);
		
		SugarType(int nameId) {
			this.nameId = nameId;
		}
		
		int nameId;
		
		public int getNameId() {
			return nameId;
		}
	}
	
	private String name = "";
	private String origin = "";
	private String supplier = "";
	private String description = "";
	private SugarType sugarType = SugarType.GRAIN;
	private boolean mustMash;
	
	private int colour;
	private double potential; //in points by default
	private double moisture; //in percents
	private double coarseFineDiff;
	private double fgDry;
	private double fgAsIs;
	private double cgDry;
	private double cgAsIs;
	
	private double maxInBatch;
	//malt only
	private double protein;
	private double diastaticPower;
	private double tsn;
	
	//extract + sugar 
	private double pH;
	private double hop;
	
	public Sugar() {
		
	}
	
	public Sugar(Sugar that) {
		this.name = that.name;
		this.origin = that.origin;
		this.supplier = that.supplier;
		this.description = that.description;
		this.sugarType = that.sugarType;
		this.mustMash = that.mustMash;
		this.colour = that.colour;
		this.potential = that.potential;
		this.moisture = that.moisture;
		this.coarseFineDiff = that.coarseFineDiff;
		this.fgDry = that.fgDry;
		this.fgAsIs = that.fgAsIs;
		this.cgDry = that.cgDry;
		this.cgAsIs = that.cgAsIs;
		this.maxInBatch = that.maxInBatch;
		this.protein = that.protein;
		this.diastaticPower = that.diastaticPower;
		this.tsn = that.tsn;
		this.pH = that.pH;
		this.hop = that.hop;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SugarType getSugarType() {
		return sugarType;
	}
	public void setSugarType(SugarType sugarType) {
		this.sugarType = sugarType;
	}
	public boolean isMustMash() {
		return mustMash;
	}
	public void setMustMash(boolean mustMash) {
		this.mustMash = mustMash;
	}
	public int getColour() {
		return colour;
	}
	public void setColour(int colour) {
		this.colour = colour;
	}
	public double getPotential() {
		return potential;
	}
	public void setPotential(double potential) {
		this.potential = potential;
		fgDry = 100 * potential / MAXIMUM_POTENTIAL;
		recalc(fgDry);
	}
	public double getMoisture() {
		return moisture;
	}
	public void setMoisture(double moisture) {
		this.moisture = moisture;
		recalc(fgDry);
	}
	public double getCoarseFineDiff() {
		return coarseFineDiff;
	}
	public void setCoarseFineDiff(double coarseFineDiff) {
		this.coarseFineDiff = coarseFineDiff;
		recalc(fgDry);
	}
	public double getFgDry() {
		return fgDry;
	}
	public void setFgDry(double fgDry) {
		this.fgDry = fgDry;
		recalc(fgDry);
	}
	public double getFgAsIs() {
		return fgAsIs;
	}
	public void setFgAsIs(double fgAsIs) {
		this.fgAsIs = fgAsIs;
		fgDry = 100 * fgAsIs / (100 - moisture);
		recalc(fgDry);
	}
	public double getCgDry() {
		return cgDry;
	}
	public void setCgDry(double cgDry) {
		this.cgDry = cgDry;
		fgDry = cgDry + coarseFineDiff;
		recalc(fgDry);
	}
	public double getCgAsIs() {
		return cgAsIs;
	}
	public void setCgAsIs(double cgAsIs) {
		this.cgAsIs = cgAsIs;
		cgDry = 100 * cgAsIs / (100 - moisture);
		fgDry = cgDry + coarseFineDiff;
		recalc(fgDry);
	}
	public double getMaxInBatch() {
		return maxInBatch;
	}
	public void setMaxInBatch(double maxInBatch) {
		this.maxInBatch = maxInBatch;
	}
	public double getProtein() {
		return protein;
	}
	public void setProtein(double protein) {
		this.protein = protein;
	}
	public double getDiastaticPower() {
		return diastaticPower;
	}
	public void setDiastaticPower(double diastaticPower) {
		this.diastaticPower = diastaticPower;
	}
	public double getTsn() {
		return tsn;
	}
	public void setTsn(double tsn) {
		this.tsn = tsn;
	}
	public double getpH() {
		return pH;
	}
	public void setpH(double pH) {
		this.pH = pH;
	}
	public double getHop() {
		return hop;
	}
	public void setHop(double hop) {
		this.hop = hop;
	}
	
	private void recalc(double fgDry) {
		potential = MAXIMUM_POTENTIAL * fgDry / 100;
		fgAsIs = fgDry * (100 - moisture) / 100;
		cgDry = fgDry - coarseFineDiff;
		cgAsIs = cgDry * (100 - moisture) / 100;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cgAsIs);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(cgDry);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(coarseFineDiff);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + colour;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		temp = Double.doubleToLongBits(diastaticPower);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fgAsIs);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fgDry);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hop);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxInBatch);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(moisture);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (mustMash ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		temp = Double.doubleToLongBits(pH);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(potential);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(protein);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((sugarType == null) ? 0 : sugarType.hashCode());
		result = prime * result
				+ ((supplier == null) ? 0 : supplier.hashCode());
		temp = Double.doubleToLongBits(tsn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	public SugarKey getSugarKey() {
		return new SugarKey(name, origin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sugar other = (Sugar) obj;
		if (Double.doubleToLongBits(cgAsIs) != Double
				.doubleToLongBits(other.cgAsIs))
			return false;
		if (Double.doubleToLongBits(cgDry) != Double
				.doubleToLongBits(other.cgDry))
			return false;
		if (Double.doubleToLongBits(coarseFineDiff) != Double
				.doubleToLongBits(other.coarseFineDiff))
			return false;
		if (colour != other.colour)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Double.doubleToLongBits(diastaticPower) != Double
				.doubleToLongBits(other.diastaticPower))
			return false;
		if (Double.doubleToLongBits(fgAsIs) != Double
				.doubleToLongBits(other.fgAsIs))
			return false;
		if (Double.doubleToLongBits(fgDry) != Double
				.doubleToLongBits(other.fgDry))
			return false;
		if (Double.doubleToLongBits(hop) != Double.doubleToLongBits(other.hop))
			return false;
		if (Double.doubleToLongBits(maxInBatch) != Double
				.doubleToLongBits(other.maxInBatch))
			return false;
		if (Double.doubleToLongBits(moisture) != Double
				.doubleToLongBits(other.moisture))
			return false;
		if (mustMash != other.mustMash)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (Double.doubleToLongBits(pH) != Double.doubleToLongBits(other.pH))
			return false;
		if (Double.doubleToLongBits(potential) != Double
				.doubleToLongBits(other.potential))
			return false;
		if (Double.doubleToLongBits(protein) != Double
				.doubleToLongBits(other.protein))
			return false;
		if (sugarType != other.sugarType)
			return false;
		if (supplier == null) {
			if (other.supplier != null)
				return false;
		} else if (!supplier.equals(other.supplier))
			return false;
		if (Double.doubleToLongBits(tsn) != Double.doubleToLongBits(other.tsn))
			return false;
		return true;
	}
	
	public static class SugarKey implements Comparable<SugarKey> {
		
		private final String name;
		private final String origin;
		
		private SugarKey(String name, String origin) {
			super();
			this.name = name;
			this.origin = origin;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((origin == null) ? 0 : origin.hashCode());
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
			SugarKey other = (SugarKey) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (origin == null) {
				if (other.origin != null)
					return false;
			} else if (!origin.equals(other.origin))
				return false;
			return true;
		}
		
		public String getName() {
			return name;
		}
		public String getOrigin() {
			return origin;
		}
		
		public static SugarKey fromString(String key) {
			String[] tokens = key.split("\\/");
			if (tokens.length == 1) {
				return new SugarKey(tokens[0], null);
			} else {
				if (tokens[1].equals("null")) {
					return new SugarKey(tokens[0], null);
				} else {
					return new SugarKey(tokens[0], tokens[1]);
				}
			}
		}
		
		@Override
		public String toString() {
			return name + "/" + origin;
		}
		@Override
		public int compareTo(SugarKey another) {
			int result = name.compareTo(another.name);
			if (result != 0) return result;
			if (origin == null && another.origin == null) return 0;
			if (origin == null) return -1;
			if (another.origin == null) return 1;
			return origin.compareTo(another.origin);
		}
	}
	
	
	
}
