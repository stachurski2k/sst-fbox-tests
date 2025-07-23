package pl.silsense.fboxtester.log;

import java.io.IOException;
import java.time.Instant;

public class LogEntryCsvConverter {

    public static String toCsv(LogEntry logEntry) {
        long timestampInSeconds = logEntry.getTimestamp().getEpochSecond();
        int deviceOrdinal = logEntry.getDevice().ordinal();
        int logTypeOrdinal = logEntry.getType().ordinal();

        String wallPositionString = "";
        if (logEntry.getWallPosition() != null) {
            wallPositionString = "," + logEntry.getWallPosition().getX() + "," + logEntry.getWallPosition().getY();
        }

        return timestampInSeconds + "," + deviceOrdinal + "," + logTypeOrdinal + wallPositionString + ";";
    }

    public static LogEntry fromCsv(String csv) throws IOException {
        String[] fields = csv.substring(0, csv.length() - 1).split(",");

        if (fields.length < 4) {
            throw new IOException("Invalid CSV format");
        }

        long timestampInSeconds = Long.parseLong(fields[0]);
        Instant timestamp = Instant.ofEpochSecond(timestampInSeconds);
        Device device = Device.values()[Integer.parseInt(fields[1])];
        LogType logType = LogType.values()[Integer.parseInt(fields[2])];

        WallPosition wallPosition = null;
        if (fields.length >= 5) {
            float x = Float.parseFloat(fields[3]);
            float y = Float.parseFloat(fields[4]);
            wallPosition = new WallPosition(x, y);
        }

        return new LogEntry(timestamp, device, logType, wallPosition);
    }
}
