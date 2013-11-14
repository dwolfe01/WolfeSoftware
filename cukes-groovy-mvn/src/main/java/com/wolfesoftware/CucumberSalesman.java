package com.wolfesoftware;
public class CucumberSalesman {
	
	private int cucumbers = 0;
	
	public void setCucumbers(int cucumbers) {
		this.cucumbers = cucumbers;
	}

	public int getCucumbers() {
		return cucumbers;
	}

	public void sellCucumbers(int cucumbers) {
		this.cucumbers -= cucumbers;
	}

}
