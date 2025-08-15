package kdg.programming3.RestaurantApp.service.Jpa;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import kdg.programming3.RestaurantApp.repository.OrderRepository;
import kdg.programming3.RestaurantApp.service.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile({"jpa-h2", "jpa-postgres"})
public class OrderServiceJpaImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderServiceJpaImpl(OrderRepository orderRepository,
                               CustomerRepository customerRepository,
                               MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menuItemRepository = menuItemRepository;
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
    public Order addOrder(long customerId, List<Long> menuIds, OrderStatus orderStatus) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        List<MenuItem> menuItems = menuIds.stream()
                .map(id -> menuItemRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid menu item ID: " + id)))
                .toList();

        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(menuItems);
        order.setStatus(orderStatus);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
