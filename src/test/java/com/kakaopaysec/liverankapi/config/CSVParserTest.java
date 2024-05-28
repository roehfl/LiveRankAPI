package com.kakaopaysec.liverankapi.config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParserTest {
    private CSVParser csvParser;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream inputStream = new ClassPathResource("SampleData.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String headerLine = reader.readLine();
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(headerLine.split(","));

        Reader csvFileReader = new InputStreamReader(new ClassPathResource("SampleData.csv").getInputStream());
        csvParser = new CSVParser(csvFileReader, csvFormat);
    }

    @Test
    public void testCSVParser() throws IOException {
        assertNotNull(csvParser, "CSVParser should not be null!");

        int count = 0;
        Iterable<CSVRecord> records = csvParser.getRecords();
        for (CSVRecord record : records) {
            String id = record.get("id");
            String code = record.get("code");
            assertNotNull(id, "Id should not be null!");
            assertNotNull(code, "Code should not be null!");
            count++;
        }
        assertEquals(count, 121);
        assertFalse(csvParser.iterator().hasNext(), "CSVParser should have been fully read");
    }
}
