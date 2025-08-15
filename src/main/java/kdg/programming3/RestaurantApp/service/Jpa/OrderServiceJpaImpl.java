package kdg.programming3.RestaurantApp.service.Jpa;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.Jpa.CustomerRepositoryJpa;
import kdg.programming3.RestaurantApp.repository.Jpa.MenuItemRepositoryJpa;
import kdg.programming3.RestaurantApp.repository.Jpa.OrderRepositoryJpa;
import kdg.programming3.RestaurantApp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Profile("jpa")
public class OrderServiceJpaImpl implements OrderService {

    private final CustomerRepositoryJpa customerRepository;
    private final OrderRepositoryJpa orderRepository;
    private final MenuItemRepositoryJpa menuItemRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceJpaImpl.class);


    @Autowired
    public OrderServiceJpaImpl(CustomerRepositoryJpa customerRepository, OrderRepositoryJpa orderRepository, MenuItemRepositoryJpa menuItemRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrdersById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order addOrder(long customerId, List<Long> menuIds, OrderStatus status) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot create order: Customer with ID " + customerId + " not found."));

//        might not work
        List<MenuItem> items = menuItemRepository.findAll();

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setItems(items);
        newOrder.setStatus(status);
        newOrder.setDate(LocalDate.now());

        log.debug("Saving new JPA order for customer: {}", newOrder.getCustomer().getName());

        return orderRepository.save(newOrder);
    }
}
