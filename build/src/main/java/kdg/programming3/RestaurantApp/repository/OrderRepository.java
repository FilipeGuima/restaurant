package kdg.programming3.RestaurantApp.repository;

import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    void deleteById(Long id);
    Order save(Order order);
    List<Order> findByStatus(OrderStatus status);
}
