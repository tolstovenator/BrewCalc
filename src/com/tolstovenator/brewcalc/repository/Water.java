package com.tolstovenator.brewcalc.repository;

public class Water {

	private String name = "";
	private String bestBeer = "";
	
	private int calcium;
	private int magnesium; // MG
	private int sulfate; //SO4
	private int sodium; // Na
	private int bicarbonade; //HCO3
	private int chloride; // Cl
	private double pH; //PH
	
	
	public Water() {
		
	}
	
	public Water(Water other) {
		this.name = other.name;
		this.bestBeer = other.bestBeer;
		this.calcium = other.calcium;
		this.magnesium = other.magnesium;
		this.sulfate = other.sulfate;
		this.sodium = other.sodium;
		this.bicarbonade = other.bicarbonade;
		this.chloride = other.chloride;
		this.pH = other.pH;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBestBeer() {
		return bestBeer;
	}
	public void setBestBeer(String bestBeer) {
		this.bestBeer = bestBeer;
	}
	public int getCalcium() {
		return calcium;
	}
	public void setCalcium(int calcium) {
		this.calcium = calcium;
	}
	public int getMagnesium() {
		return magnesium;
	}
	public void setMagnesium(int magnesium) {
		this.magnesium = magnesium;
	}
	public int getSulfate() {
		return sulfate;
	}
	public void setSulfate(int sulfate) {
		this.sulfate = sulfate;
	}
	public int getSodium() {
		return sodium;
	}
	public void setSodium(int sodium) {
		this.sodium = sodium;
	}
	public int getBicarbonade() {
		return bicarbonade;
	}
	public void setBicarbonade(int bicarbonade) {
		this.bicarbonade = bicarbonade;
	}
	public int getChloride() {
		return chloride;
	}
	public void setChloride(int chloride) {
		this.chloride = chloride;
	}
	public double getpH() {
		return pH;
	}
	public void setpH(double pH) {
		this.pH = pH;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bestBeer == null) ? 0 : bestBeer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(bicarbonade);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(calcium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(chloride);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(magnesium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(pH);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sodium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sulfate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Water other = (Water) obj;
		if (bestBeer == null) {
			if (other.bestBeer != null)
				return false;
		} else if (!bestBeer.equals(other.bestBeer))
			return false;
		if (Double.doubleToLongBits(bicarbonade) != Double
				.doubleToLongBits(other.bicarbonade))
			return false;
		if (Double.doubleToLongBits(calcium) != Double
				.doubleToLongBits(other.calcium))
			return false;
		if (Double.doubleToLongBits(chloride) != Double
				.doubleToLongBits(other.chloride))
			return false;
		if (Double.doubleToLongBits(magnesium) != Double
				.doubleToLongBits(other.magnesium))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(pH) != Double.doubleToLongBits(other.pH))
			return false;
		if (Double.doubleToLongBits(sodium) != Double
				.doubleToLongBits(other.sodium))
			return false;
		if (Double.doubleToLongBits(sulfate) != Double
				.doubleToLongBits(other.sulfate))
			return false;
		return true;
	}
	
	
	
	
}
