package kdg.programming3.RestaurantApp.repository.Memory;

import kdg.programming3.RestaurantApp.Week1.DataFactory;
import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("inmemory")
public class InMemoryOrderRepository implements OrderRepository {

    @Override
    public List<Order> findAll() {
        return InMemoryDataFactory.getOrders();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return InMemoryDataFactory.getOrders().stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        InMemoryDataFactory.getOrders().removeIf(order -> order.getId().equals(id));
    }

    @Override
    public Order save(Order order) {
        long nextId = InMemoryDataFactory.getOrders().stream()
                .mapToLong(Order::getId)
                .max()
                .orElse(0L) + 1;

        order.setId(nextId);
        order.setDate(LocalDate.now());
//        order.setStatus(OrderStatus.PENDING);
        InMemoryDataFactory.getOrders().add(order);
        return order;
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return InMemoryDataFactory.getOrders().stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }
}
