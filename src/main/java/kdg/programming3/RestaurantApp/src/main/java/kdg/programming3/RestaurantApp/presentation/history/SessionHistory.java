package kdg.programming3.RestaurantApp.presentation.history;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class SessionHistory implements Serializable {

    private final List<HistoryItem> history = new ArrayList<>();

    public void addHistoryItem(HistoryItem item) {
        history.add(item);
    }

    public List<HistoryItem> getHistory() {
        return history;
    }
}