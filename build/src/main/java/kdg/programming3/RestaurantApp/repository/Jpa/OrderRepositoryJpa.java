package kdg.programming3.RestaurantApp.repository.Jpa;

import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("jpa")
public interface OrderRepositoryJpa extends OrderRepository {

    @Override
    List<Order> findByStatus(OrderStatus status);
}