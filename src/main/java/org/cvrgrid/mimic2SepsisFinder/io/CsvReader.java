package org.cvrgrid.mimic2SepsisFinder.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Reads CSVs.
 * Created by rliu14 on 7/8/16.
 */
public class CsvReader {

    private List<String> headers;
    private List<CSVRecord> entries;

    public CsvReader(String filename) throws IOException {

        headers = new ArrayList<>();
        entries = new ArrayList<>();

        Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        Iterator<CSVRecord> i = records.iterator();
        CSVRecord r = i.next();
        int count = 0, limit = 999999999;

        for (String s : r) {
            headers.add(s.trim());
        }
        while (i.hasNext() && count < limit) {
            entries.add(i.next());
            count++;
        }
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<CSVRecord> getEntries() {
        return entries;
    }
}
