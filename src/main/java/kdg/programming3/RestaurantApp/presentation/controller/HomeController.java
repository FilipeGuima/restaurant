package kdg.programming3.RestaurantApp.presentation.controller;

import kdg.programming3.RestaurantApp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private  CustomerServiceImpl customerService;
    private  OrderServiceImpl orderService;
    private MenuItemServiceImpl menuItemService;

    public HomeController(CustomerServiceImpl customerService, OrderServiceImpl orderService, MenuItemServiceImpl menuItemService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.menuItemService = menuItemService;
    }

    @GetMapping("/home")
    public String index(){
        return "Index";
    }
}
