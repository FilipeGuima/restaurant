package kdg.programming3.RestaurantApp.presentation.controller;

import jakarta.servlet.http.HttpSession;
import kdg.programming3.RestaurantApp.domain.*;
import kdg.programming3.RestaurantApp.service.CustomerService;
import kdg.programming3.RestaurantApp.service.MenuItemService;
import kdg.programming3.RestaurantApp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        if (status != null) {
            orders = orderService.getOrdersByStatus(status);
            log.debug("Found {} orders with status {}", orders.size(), status);
        } else {
            orders = orderService.getAllOrders();
            log.debug("No status provided ");
        }

        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("items", menuItems);
        model.addAttribute("page", "orders");
        return "orders";
    }

    @GetMapping("/add")
    public String showAddOrderForm(Model model, HttpSession session) {
        log.debug("Fetching add order form");

        List<Customer> customers = customerService.getAllCustomers();
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();

        model.addAttribute("order", new Order());
        model.addAttribute("customers", customers);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("status", OrderStatus.values());
        model.addAttribute("itemTypes", ItemType.values());

        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("selectedItemIds", cart);

        model.addAttribute("selectedCustomerId", session.getAttribute("selectedCustomerId"));
        model.addAttribute("selectedStatus", session.getAttribute("selectedStatus"));

        model.addAttribute("page", "addOrder");
        return "addOrder";
    }

    @PostMapping("/update-details")
    public String updateOrderDetails(@RequestParam(value = "customerId", required = false) Long customerId,
                                     @RequestParam(value = "status", required = false) OrderStatus status,
                                     HttpSession session) {
        if (customerId != null) {
            session.setAttribute("selectedCustomerId", customerId);
            log.debug("Saved customerId {} to session", customerId);
        }
        if (status != null) {
            session.setAttribute("selectedStatus", status);
            log.debug("Saved status {} to session", status);
        }
        return "redirect:/orders/add";
    }

    @PostMapping("/clear-cart")
    public String clearCart(HttpSession session) {
        log.info("Clearing all items from cart");
        session.removeAttribute("cart");
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
            session.setAttribute("cart", cart);
        }

        return "redirect:/menu/item/" + itemId;
    }

    @PostMapping("/add")
    public String addOrder(HttpSession session,
                           RedirectAttributes redirectAttributes) {
        log.debug("Post request for ids");

        Long customerId = (Long) session.getAttribute("selectedCustomerId");
        OrderStatus status = (OrderStatus) session.getAttribute("selectedStatus");
        List<Long> menuItemIds = (List<Long>) session.getAttribute("cart");

        List<String> errors = new ArrayList<>();

        if (customerId == null) {
            errors.add("You must select a customer.");

        }

        if (menuItemIds == null || menuItemIds.isEmpty()) {
            errors.add("You cannot place an empty order. Please add at least one item.");
        }

        if (status == null) {
            errors.add("You cannot have an empty status. Please choose a status.");
        }

        if (!errors.isEmpty()) {
            log.warn("Attempted to place order with missing information: {}", errors);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/orders/add";
        }

        log.debug("Creating order for customerId: {}, status: {}, with Menu Item IDs: {}", customerId, status, menuItemIds);
        orderService.addOrder(customerId, menuItemIds, status);

        session.removeAttribute("cart");
        session.removeAttribute("selectedCustomerId");
        session.removeAttribute("selectedStatus");

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
