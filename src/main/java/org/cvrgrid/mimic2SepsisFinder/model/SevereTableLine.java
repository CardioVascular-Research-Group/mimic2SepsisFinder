package org.cvrgrid.mimic2SepsisFinder.model;

public class SevereTableLine extends SepsisTableLine {

	private boolean BP, pH, lacticAcid, Severe;
	
	public SevereTableLine() {
		
		super();
		setBP(false);
		setpH(false);
		setLacticAcid(false);
		setSevere(false);
		
	}

	public boolean isBP() {
		return BP;
	}

	public void setBP(boolean bP) {
		BP = bP;
	}

	public boolean ispH() {
		return pH;
	}

	public void setpH(boolean pH) {
		this.pH = pH;
	}

	public boolean isLacticAcid() {
		return lacticAcid;
	}

	public void setLacticAcid(boolean lacticAcid) {
		this.lacticAcid = lacticAcid;
	}

	public boolean isSevere() {
		checkSevere();
		return Severe;
	}

	public void setSevere(boolean severe) {
		Severe = severe;
	}
	
	private void checkSevere() {
		
		int count = 0;
		if (isSimultaneous()) count++;
		if (isBP()) count++;
		if (ispH() && isLacticAcid()) count++;
		if (count > 1) setSevere(true);
		 
	}
}
