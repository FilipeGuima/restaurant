package kdg.programming3.RestaurantApp.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate creationDate;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders;

    public Customer(Long id, String firstName, String lastName, String email, LocalDate creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
        this.orders = new ArrayList<>();
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return
                firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrders() {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Optional<Order> getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer Details:\n");
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("CreationDate: ").append(creationDate).append("\n");
        return sb.toString();
    }
}