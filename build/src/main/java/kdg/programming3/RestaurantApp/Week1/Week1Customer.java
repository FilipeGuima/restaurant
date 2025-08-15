package kdg.programming3.RestaurantApp.Week1;

import kdg.programming3.RestaurantApp.domain.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Week1Customer {
    private Long id;
    private  String name;
    private String email;
    private List<Week1Order> orders;
    private LocalDate creationDate;

    public Week1Customer(Long id, String name, String email, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.creationDate = creationDate;
        this.orders = new ArrayList<>();
    }

    public Week1Customer() {

    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Week1Order> getOrders() {
        return orders;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<Week1Order> orders) {
        this.orders = orders;
    }

    public Optional<Week1Order> getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    public void addOrder(Week1Order order) {
        this.orders.add(order);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer Details:\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("CreationDate: ").append(creationDate).append("\n");
        return sb.toString();
    }
}
