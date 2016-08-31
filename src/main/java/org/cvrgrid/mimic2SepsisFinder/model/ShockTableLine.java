package org.cvrgrid.mimic2SepsisFinder.model;

public class ShockTableLine extends SevereTableLine {

	private boolean fluid, Shock;
	private boolean fluidNull;
	
	public ShockTableLine() {
		
		super();
		setFluid(false);
		setShock(false);
		setFluidNull(false);
		
	}

	
	public boolean isFluid() {
		return fluid;
	}


	public void setFluid(boolean fluid) {
		this.fluid = fluid;
	}


	public boolean isShock() {
		checkShock();
		return Shock;
	}


	public void setShock(boolean shock) {
		Shock = shock;
	}


	public boolean isFluidNull() {
		return fluidNull;
	}


	public void setFluidNull(boolean fluidNull) {
		this.fluidNull = fluidNull;
	}


	private void checkShock() {
		
		int count = 0;
		if (isSevere()) count++;
		if (isFluid()) count++;
		if (count > 1) setShock(true);
		 
	}
}
