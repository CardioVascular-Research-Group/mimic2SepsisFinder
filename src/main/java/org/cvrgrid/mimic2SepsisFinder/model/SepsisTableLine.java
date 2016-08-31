package org.cvrgrid.mimic2SepsisFinder.model;

public class SepsisTableLine {

	private boolean Temp, HR, RR, WBC, Simultaneous;
	private boolean TempNull, HRNull, RRNull, WBCNull;
	
	public SepsisTableLine() {
		
		setTemp(false);
		setHR(false);
		setRR(false);
		setWBC(false);
		setSimultaneous(false);
		setTempNull(false);
		setHRNull(false);
		setRRNull(false);
		setWBCNull(false);
		
	}

	public boolean isTemp() {
		return Temp;
	}

	public void setTemp(boolean temp) {
		Temp = temp;
	}

	public boolean isHR() {
		return HR;
	}

	public void setHR(boolean hR) {
		HR = hR;
	}

	public boolean isRR() {
		return RR;
	}

	public void setRR(boolean rR) {
		RR = rR;
	}

	public boolean isWBC() {
		return WBC;
	}

	public void setWBC(boolean wBC) {
		WBC = wBC;
	}

	public boolean isSimultaneous() {
		checkSimultaneous();
		return Simultaneous;
	}

	public void setSimultaneous(boolean simultaneous) {
		Simultaneous = simultaneous;
	}
	
	public boolean isTempNull() {
		return TempNull;
	}

	public void setTempNull(boolean tempNull) {
		TempNull = tempNull;
	}

	public boolean isHRNull() {
		return HRNull;
	}

	public void setHRNull(boolean hRNull) {
		HRNull = hRNull;
	}

	public boolean isRRNull() {
		return RRNull;
	}

	public void setRRNull(boolean rRNull) {
		RRNull = rRNull;
	}

	public boolean isWBCNull() {
		return WBCNull;
	}

	public void setWBCNull(boolean wBCNull) {
		WBCNull = wBCNull;
	}

	private void checkSimultaneous() {
		
		int count = 0;
		if (isTemp()) count++;
		if (isHR()) count++;
		if (isRR()) count++;
		if (isWBC()) count++;
		if (count > 1) setSimultaneous(true);
		
	}
}
