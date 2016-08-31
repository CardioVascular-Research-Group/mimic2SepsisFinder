package org.cvrgrid.mimic2SepsisFinder.model;

public class TotalBalEvents {

	private String subject_id;
	private String charttime;
	private String label;
	private String cumvolume;
	private boolean shockFlag, shockFlagNull;

	public TotalBalEvents() {
		setSubject_id("s");
		setCharttime("");
		setLabel("");
		setShockFlag(false);
		setShockFlagNull(false);
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getCharttime() {
		return charttime;
	}

	public void setCharttime(String charttime) {
		this.charttime = charttime;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCumvolume() {
		return cumvolume;
	}

	public void setCumvolume(String cumvolume) {
		this.cumvolume = cumvolume;
	}

	public boolean isShockFlag() {
		shockCheck();
		return shockFlag;
	}

	public void setShockFlag(boolean shockFlag) {
		this.shockFlag = shockFlag;
	}

	public boolean isShockFlagNull() {
		return shockFlagNull;
	}

	public void setShockFlagNull(boolean shockFlagNull) {
		this.shockFlagNull = shockFlagNull;
	}

	private void shockCheck() {
		if (!(cumvolume.isEmpty()))
			if (!(cumvolume.equalsIgnoreCase("no data") || cumvolume.equalsIgnoreCase("error") ||
					cumvolume.equalsIgnoreCase("")))
				if (label.equalsIgnoreCase("24h Total In")) {
					if (new Double(cumvolume).doubleValue() > 1200 || new Double(cumvolume).doubleValue() > 1200) {
						setShockFlag(true);
					}
				}
	}	
}
