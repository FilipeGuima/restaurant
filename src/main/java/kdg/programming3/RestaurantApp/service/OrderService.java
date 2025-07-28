package kdg.programming3.RestaurantApp.service;

import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List <Order> getAllOrders();
    Optional<Order> getOrdersById(Long restaurantId);
    List<Order> getOrdersByStatus(OrderStatus status);
    Order addOrder(long customerId, List<Long> menuId, OrderStatus orderStatus);
}
