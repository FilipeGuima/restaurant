package kdg.programming3.RestaurantApp.presentation.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kdg.programming3.RestaurantApp.domain.*;
import kdg.programming3.RestaurantApp.presentation.viewmodel.OrderViewModel;
import kdg.programming3.RestaurantApp.service.CustomerService;
import kdg.programming3.RestaurantApp.service.MenuItemService;
import kdg.programming3.RestaurantApp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final MenuItemService menuItemService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, MenuItemService menuItemService, CustomerService customerService) {
        this.orderService = orderService;
        this.menuItemService = menuItemService;
        this.customerService = customerService;
    }

    @GetMapping
    public String showOrders(
            @RequestParam(value = "status", required = false) OrderStatus status,
            Model model) {
        log.debug("GET request for orders page with status: {}", status);

        List<Order> orders;
        if (status != null) {
            orders = orderService.getOrdersByStatus(status);
        } else {
            orders = orderService.getAllOrders();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("page", "orders");
        return "orders";
    }

    @GetMapping("/add")
    public String showAddOrderForm(Model model, HttpSession session) {
        log.debug("Fetching add order form");

        if (!model.containsAttribute("orderViewModel")) {
            model.addAttribute("orderViewModel", new OrderViewModel());
        }

        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        model.addAttribute("allStatuses", OrderStatus.values());

        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("selectedItemIds", cart);
        model.addAttribute("page", "addOrder");

        Boolean success = (Boolean) session.getAttribute("orderSuccess");
        session.removeAttribute("orderSuccess");

        if (Boolean.TRUE.equals(success)) {
            model.addAttribute("orderSuccess", true);
        }


        return "addOrder";
    }

    @PostMapping("/clear-cart")
    public String clearCart(HttpSession session) {
        log.info("Clearing all items from cart");
        session.removeAttribute("cart");

        session.setAttribute("orderSuccess", true);
        return "redirect:/orders/add";
    }


    @PostMapping("/add-item")
    public String addItemToCart(@RequestParam("itemId") Long itemId, HttpSession session) {
        log.info("Adding item {} to cart", itemId);
        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        if (!cart.contains(itemId)) {
            cart.add(itemId);
        }
        session.setAttribute("cart", cart);
        return "redirect:/menu/item/" + itemId;
    }

    @PostMapping("/remove-item")
    public String removeItemFromCart(@RequestParam("itemId") Long itemId, HttpSession session) {
        log.info("Removing item {} from cart", itemId);
        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(itemId);
        }
        session.setAttribute("cart", cart);
        return "redirect:/menu/item/" + itemId;
    }

    @ModelAttribute("orderViewModel") public OrderViewModel populateOrderViewModel(HttpSession session) {
        OrderViewModel orderViewModel = new OrderViewModel();
        orderViewModel.setMenuItemIds((List<Long>) session.getAttribute("cart"));
        return orderViewModel; }

    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("orderViewModel") OrderViewModel orderViewModel,
                           BindingResult bindingResult,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            log.warn("Validation failed for order submission. Errors: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderViewModel", bindingResult);
            redirectAttributes.addFlashAttribute("orderViewModel", orderViewModel);
            return "redirect:/orders/add";
        }

        orderService.addOrder(
                orderViewModel.getCustomer().getId(),
                orderViewModel.getMenuItemIds(),
                orderViewModel.getStatus()
        );


        session.removeAttribute("cart");

        session.setAttribute("orderSuccess", true);

        return "redirect:/orders";
    }

    @GetMapping("/{id}")
    public String showOrderDetails(@PathVariable("id") Long id, Model model) {
        log.debug("Request for details of order with id: {}", id);
        return orderService.getOrdersById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    double totalPrice = order.getItems().stream()
                            .mapToDouble(MenuItem::getPrice)
                            .sum();
                    model.addAttribute("totalPrice", totalPrice);
                    model.addAttribute("page", "orderDetails");
                    return "orderDetails";
                })
                .orElse("redirect:/orders");
    }
}