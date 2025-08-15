package kdg.programming3.RestaurantApp.presentation.history;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryItem {
    private final String url;
    private final LocalDateTime timestamp;

    public HistoryItem(String url) {
        this.url = url;
        this.timestamp = LocalDateTime.now();
    }

    public String getUrl() {
        return url;
    }

    public String getTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}