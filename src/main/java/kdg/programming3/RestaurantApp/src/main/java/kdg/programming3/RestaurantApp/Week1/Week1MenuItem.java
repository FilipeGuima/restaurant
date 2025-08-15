package kdg.programming3.RestaurantApp.Week1;

public class Week1MenuItem {
    private Long id;
    private String name;
    private double price;

    public Week1MenuItem(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MenuItem id= ").append(id).append('\n');
        sb.append("name= ").append(name).append('\n');
        sb.append("price= ").append(price);

        return toString();
    }

}
