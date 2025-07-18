package pl.silsense.fboxtester.session;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import pl.silsense.fboxtester.log.LogEntry;

public class LogEntryCsvConverter {

    private static final CsvMapper csvMapper = new CsvMapper();

    @SuppressWarnings("unchecked")
    public static List<LogEntry> fromCsv(BufferedReader csvFile) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(LogEntry.class).withHeader().withColumnReordering(true);
        return (List) csvMapper.readerFor(LogEntry.class).with(schema).readValues(csvFile).readAll();
    }

    public static String toCsv(LogEntry logEntry, File csvFile) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(LogEntry.class).withHeader();
        return csvMapper.writerFor(LogEntry.class).with(schema).writeValueAsString(logEntry);
    }
}
