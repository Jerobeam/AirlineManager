package com.backend.departments;

public class PlaneUpgrade {

	private boolean winglets = false; //Verbessert Treibstoffverbrauch bis 3,5%
	private boolean newEngine = false; //Verbessert Treibstoffverauch bis 15% -> steiger Image(Umweltschutz, L�rmschutz)-> erh�t Reichweite bis 950km
	private boolean outsideCleaning = false; //Verbessert Treibstoffverauch
	
	private int entertainmentPackage = 0; // 0 = Keins; 3 = das Beste
	private int seat = 1;// 1 = Standart, 2 = Ledersitze, 3 = Luxussitze
	public boolean isWinglets() {
		return winglets;
	}
	public void setWinglets(boolean winglets) {
		this.winglets = winglets;
	}
	public boolean isNewEngine() {
		return newEngine;
	}
	public void setNewEngine(boolean newEngine) {
		this.newEngine = newEngine;
	}
	public boolean isOutsideCleaning() {
		return outsideCleaning;
	}
	public void setOutsideCleaning(boolean outsideCleaning) {
		this.outsideCleaning = outsideCleaning;
	}
	public int getEntertainmentPackage() {
		return entertainmentPackage;
	}
	public void setEntertainmentPackage(int entertainmentPackage) {
		this.entertainmentPackage = entertainmentPackage;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	
}