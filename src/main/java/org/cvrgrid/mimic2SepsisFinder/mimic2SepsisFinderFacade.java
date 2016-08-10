package org.cvrgrid.mimic2SepsisFinder;

import org.apache.commons.csv.CSVRecord;
import org.cvrgrid.mimic2SepsisFinder.io.CsvReader;
import org.cvrgrid.mimic2SepsisFinder.model.ChartEvents;
import org.cvrgrid.mimic2SepsisFinder.model.ShockTableLine;
import org.cvrgrid.mimic2SepsisFinder.model.TotalBalEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Facade for Mimic2SepsisFinder.
 * Created by sgranite on 8/5/16.
 */
@Component
public class mimic2SepsisFinderFacade {

	@Autowired
	private String fileName;

	@Autowired
	private String severeFileName;

	@Autowired
	private String shockFileName;

	@Autowired
	private String tableFile;

	@Autowired
	private String subjectsWithSepsisFile;

	@Autowired
	private String subjectsWithSevereFile;

	@Autowired
	private String subjectsWithShockFile;

	@Autowired
	private String physionetMatchedDir;

	public void validateTrew(String filename) throws IOException, ParseException {
		if (filename.isEmpty()) filename = fileName;
		HashMap<String,ArrayList<ChartEvents>> info = new HashMap<String,ArrayList<ChartEvents>>();
		CsvReader reader = new CsvReader(filename);
		for (CSVRecord entry : reader.getEntries()) {
			ArrayList<ChartEvents> temp = new ArrayList<ChartEvents>();
			if (info.containsKey(entry.get(0))) temp = info.get(entry.get(0));
			ChartEvents chartEvents = new ChartEvents();
			chartEvents.setSubject_id(entry.get(0));
			chartEvents.setCharttime(entry.get(1));
			chartEvents.setLabel(entry.get(2));
			chartEvents.setValue1(entry.get(3));
			chartEvents.setValue1num(entry.get(4));
			chartEvents.setValue1uom(entry.get(5));
			chartEvents.setValue2(entry.get(6));
			chartEvents.setValue2num(entry.get(7));
			chartEvents.setValue2uom(entry.get(8));
			temp.add(chartEvents);
			info.put(entry.get(0), temp);
		}
		Set<String> keys = info.keySet();
		TreeSet<String> sortedKeys = new TreeSet<String>(keys);
		HashMap<String,HashMap<String,ShockTableLine>> organizedInfo = new HashMap<String,HashMap<String,ShockTableLine>>();
		for (String key : sortedKeys) {
			ArrayList<ChartEvents> temp = info.get(key);
			HashMap<String,ShockTableLine> line = new HashMap<String,ShockTableLine>();
			for (ChartEvents entry : temp)	{
				if (organizedInfo.containsKey(key)) line = organizedInfo.get(key);
				ShockTableLine tempLine = new ShockTableLine();
				if (line.containsKey((entry.getCharttime()))) tempLine = line.get(entry.getCharttime());
				if (entry.isSepsisFlag())
					if (entry.getLabel().equalsIgnoreCase("Heart Rate")) {
						tempLine.setHR(entry.isSepsisFlag());
					} else if (entry.getLabel().equalsIgnoreCase("Respiratory Rate")) {
						tempLine.setRR(entry.isSepsisFlag());
					} else if (entry.getLabel().startsWith("Arterial")) {
						tempLine.setRR(entry.isSepsisFlag());
					} else if (entry.getLabel().startsWith("Temperature")) {
						tempLine.setTemp(entry.isSepsisFlag());
					} else if (entry.getLabel().startsWith("WBC")) {
						tempLine.setWBC(entry.isSepsisFlag());
					}
				line.put(entry.getCharttime(), tempLine);
			}
			organizedInfo.put(key, line);
		}
		info = new HashMap<String,ArrayList<ChartEvents>>();
		reader = new CsvReader(severeFileName);
		for (CSVRecord entry : reader.getEntries()) {
			ArrayList<ChartEvents> temp = new ArrayList<ChartEvents>();
			if (info.containsKey(entry.get(0))) temp = info.get(entry.get(0));
			ChartEvents chartEvents = new ChartEvents();
			chartEvents.setSubject_id(entry.get(0));
			chartEvents.setCharttime(entry.get(1));
			chartEvents.setLabel(entry.get(2));
			chartEvents.setValue1(entry.get(3));
			chartEvents.setValue1num(entry.get(4));
			chartEvents.setValue1uom(entry.get(5));
			chartEvents.setValue2(entry.get(6));
			chartEvents.setValue2num(entry.get(7));
			chartEvents.setValue2uom(entry.get(8));
			temp.add(chartEvents);
			info.put(entry.get(0), temp);
		}
		keys = info.keySet();
		sortedKeys = null;
		sortedKeys = new TreeSet<String>(keys);
		for (String key : sortedKeys) {
			ArrayList<ChartEvents> temp = info.get(key);
			HashMap<String,ShockTableLine> line = new HashMap<String,ShockTableLine>();
			for (ChartEvents entry : temp)	{
				if (organizedInfo.containsKey(key)) line = organizedInfo.get(key);
				ShockTableLine tempLine = new ShockTableLine();
				if (line.containsKey((entry.getCharttime()))) tempLine = line.get(entry.getCharttime());
				if (entry.isSevereFlag())
					if (entry.getLabel().equalsIgnoreCase("Arterial BP")) {
						tempLine.setBP(entry.isSevereFlag());
					} else if (entry.getLabel().startsWith("Lactic Acid")) {
						tempLine.setBP(entry.isSevereFlag());
					} else if (entry.getLabel().startsWith("pH") || entry.getLabel().endsWith("pH")) {
						tempLine.setBP(entry.isSevereFlag());
					} 

				line.put(entry.getCharttime(), tempLine);
			}
			organizedInfo.put(key, line);
		}
		HashMap<String,ArrayList<TotalBalEvents>> tbeInfo = new HashMap<String,ArrayList<TotalBalEvents>>();
		reader = new CsvReader(shockFileName);
		for (CSVRecord entry : reader.getEntries()) {
			ArrayList<TotalBalEvents> temp = new ArrayList<TotalBalEvents>();
			if (tbeInfo.containsKey(entry.get(0))) temp = tbeInfo.get(entry.get(0));
			TotalBalEvents totalBalEvents = new TotalBalEvents();
			totalBalEvents.setSubject_id(entry.get(0));
			totalBalEvents.setCharttime(entry.get(1));
			totalBalEvents.setLabel(entry.get(2));
			totalBalEvents.setCumvolume(entry.get(3));
			temp.add(totalBalEvents);
			tbeInfo.put(entry.get(0), temp);
		}
		keys = tbeInfo.keySet();
		sortedKeys = null;
		sortedKeys = new TreeSet<String>(keys);
		for (String key : sortedKeys) {
			ArrayList<TotalBalEvents> temp = tbeInfo.get(key);
			HashMap<String,ShockTableLine> line = new HashMap<String,ShockTableLine>();
			for (TotalBalEvents entry : temp)	{
				if (organizedInfo.containsKey(key)) line = organizedInfo.get(key);
				ShockTableLine tempLine = new ShockTableLine();
				if (line.containsKey((entry.getCharttime()))) tempLine = line.get(entry.getCharttime());
				if (entry.isShockFlag())
					if (entry.getLabel().equalsIgnoreCase("24h Total In")) {
						tempLine.setFluid(entry.isShockFlag());
					} 
				line.put(entry.getCharttime(), tempLine);
			}
			organizedInfo.put(key, line);
		}
		try {
			keys = organizedInfo.keySet();
			sortedKeys = null;
			sortedKeys = new TreeSet<String>(keys);
			FileWriter writer = new FileWriter(tableFile);
			writer.write("Subject,ChartTime,HR,RR,Temp,WBC,HasSepsis,BP,pH,LacticAcid,HasSevere,Fluid,HasShock\r\n");
			HashMap<String,Boolean> subjectsWithSepsis = new HashMap<String,Boolean>();
			ArrayList<String> subjectsWithSevere = new ArrayList<String>();
			ArrayList<String> subjectsWithShock = new ArrayList<String>();
			for (String key : sortedKeys) {
				HashMap<String,ShockTableLine> line = organizedInfo.get(key);
				Set<String> keys2 = line.keySet();
				TreeSet<String> sortedKeys2 = new TreeSet<String>(keys2);
				for (String key2 : sortedKeys2) {
					ShockTableLine tempLine = line.get(key2);
					if (tempLine.isSimultaneous()) {
						if (!(subjectsWithSepsis.containsKey(convertSubjectId(key)))) subjectsWithSepsis.put(convertSubjectId(key),checkForSubjectDir(key));
						if (!(subjectsWithSevere.contains(convertSubjectId(key))) && tempLine.isSevere()) subjectsWithSevere.add(convertSubjectId(key));
						if (!(subjectsWithShock.contains(convertSubjectId(key))) && tempLine.isShock()) subjectsWithShock.add(convertSubjectId(key));
						writer.write(key + ", " + key2 + ", " + tempLine.isHR() + ", " + tempLine.isRR() + ", " + tempLine.isTemp() + ", " + tempLine.isWBC() + ", " + tempLine.isSimultaneous() + ", " + tempLine.isBP() + ", " + tempLine.ispH() + ", " + tempLine.isLacticAcid() + ", " + tempLine.isSevere() + ", " + tempLine.isFluid() + ", " + tempLine.isShock() + "\r\n");
					}
				}
			}
			writer.close();
			writer = new FileWriter(subjectsWithSepsisFile);
			for (String key : new TreeSet<String>(subjectsWithSepsis.keySet())) writer.write(key + "," + subjectsWithSepsis.get(key) + "\r\n");
			writer.close();
			writer = new FileWriter(subjectsWithSevereFile);
			Collections.sort(subjectsWithSevere);
			for (String subject : subjectsWithSevere) writer.write(subject + "," + subjectsWithSepsis.get(subject) + "\r\n");
			writer.close();
			writer = new FileWriter(subjectsWithShockFile);
			Collections.sort(subjectsWithShock);
			for (String subject : subjectsWithShock) writer.write(subject + "," + subjectsWithSepsis.get(subject) + "\r\n");
			writer.close();
			System.out.println("Total Subjects with Sepsis: " + subjectsWithSepsis.size());
			System.out.println("Total Subjects with Severe: " + subjectsWithSevere.size());
			System.out.println("Total Subjects with Shock: " + subjectsWithShock.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Boolean checkForSubjectDir(String subjectId) {

		Path subjectFolderPath = Paths.get(physionetMatchedDir, convertSubjectId(subjectId));
		if (Files.exists(subjectFolderPath)) return new Boolean(true);
		return new Boolean(false);

	}

	private String convertSubjectId(String subjectId) {
		int sId = new Integer(subjectId).intValue();
		if (sId < 10) { 
			subjectId = "s0000" + subjectId; 
		} else if (sId < 100) { 
			subjectId = "s000" + subjectId;
		} else if (sId < 1000) { 
			subjectId = "s00" + subjectId;
		} else if (sId < 10000) { 
			subjectId = "s0" + subjectId;
		} else {
			subjectId = "s" + subjectId;			
		}
		return subjectId;
	}

}
