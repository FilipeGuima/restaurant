package kdg.programming3.RestaurantApp.presentation.controller;

import jakarta.servlet.http.HttpSession; // <-- ADD THIS IMPORT
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuItemController {

    private final Logger logger = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemRepository menuItemRepository;

    public MenuItemController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping("/item/{id}")
    public String showItemDetails(@PathVariable Long id, Model model, HttpSession session) { // <-- ADD HttpSession
        logger.info("Request for menu item details with id: {}", id);

        return menuItemRepository.findById(id)
                .map(item -> {
                    model.addAttribute("item", item);

                    List<Long> cart = (List<Long>) session.getAttribute("cart");
                    boolean isInCart = cart != null && cart.contains(item.getId());
                    model.addAttribute("isInCart", isInCart);
                    model.addAttribute("page", "addOrder");
                    return "itemDetails";
                })
                .orElse("redirect:/orders/add");
    }
}