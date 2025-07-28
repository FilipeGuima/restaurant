package kdg.programming3.RestaurantApp.service;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import kdg.programming3.RestaurantApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrdersById(Long restaurantId) {
        return orderRepository.findById(restaurantId);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order addOrder(long customerId, List<Long> menuIds, OrderStatus status) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            log.error("Cannot create order. Customer with ID {} not found.", customerId);
            return null;
        }

        List<MenuItem> items = new ArrayList<>();
        if(menuIds != null) {
            for (Long menuId : menuIds) {
                menuItemRepository.findById(menuId).ifPresent(items::add);
            }
        }

        if (items.isEmpty()) {
            log.error("Cannot create order. No valid menu items were selected.");
            return null;
        }

        Order newOrder  = new Order();
        newOrder.setCustomer(customerOpt.get());
        newOrder.setItems(items);
        newOrder.setStatus(status);

        log.debug("Saving new order for customer: {}", newOrder.getCustomer().getName());

        return orderRepository.save(newOrder);
    }


}
