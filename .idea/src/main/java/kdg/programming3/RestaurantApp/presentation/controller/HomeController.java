package kdg.programming3.RestaurantApp.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        model.addAttribute("page", "home");
        return "home";
    }
}