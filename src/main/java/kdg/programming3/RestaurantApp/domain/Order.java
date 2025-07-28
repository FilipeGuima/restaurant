package kdg.programming3.RestaurantApp.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Order {
    private Long id;
    private LocalDate date;
    private OrderStatus status;
    private Customer customer;
    private List<MenuItem> items;


    public Order(Long id, LocalDate date, OrderStatus status, Customer customer) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.items = new ArrayList<>();
        this.customer = customer;

    }

    public Order() {

    }

    public void addMenuItem(MenuItem item) {
        this.items.add(item);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order # ").append(id).append(" by ").
                append(customer.getName()).append(" (").append(status).
                append(")").append(" placed on ").append(date).append("\n");
        if (items.isEmpty()) {
            sb.append("  No items in this order.\n");
        } else {
            sb.append("  Items:\n");
            items.forEach(item -> sb.append("    *")
                            .append(item.getName())
                            .append(" ($")
                            .append(String.format("%.2f", item.getPrice()))
                            .append(")\n")
            );
        }
        return sb.toString();
    }
}
