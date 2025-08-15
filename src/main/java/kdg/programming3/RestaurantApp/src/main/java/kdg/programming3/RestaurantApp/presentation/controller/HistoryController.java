package kdg.programming3.RestaurantApp.presentation.controller;

import kdg.programming3.RestaurantApp.presentation.history.SessionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private SessionHistory sessionHistory;

    @GetMapping
    public String showHistory(Model model) {
        model.addAttribute("historyItems", sessionHistory.getHistory());
        model.addAttribute("page", "history");
        return "history";
    }
}