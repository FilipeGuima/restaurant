package kdg.programming3.RestaurantApp.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int calories;
    private String imageName;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @ElementCollection(targetClass = Allergen.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "menu_item_allergens", joinColumns = @JoinColumn(name = "menu_item_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "allergen")
    private List<Allergen> allergens;

    public MenuItem(Long id, String name, double price, List<Allergen> allergens, int calories, String imageName, ItemType itemType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.allergens = allergens;
        this.calories = calories;
        this.imageName = imageName;
        this.itemType = itemType;

    }

    public MenuItem() {

    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public List<Allergen> getAllergens() { return allergens; }
    public int getCalories() { return calories; }
    public String getImageName() { return imageName; }
    public ItemType getItemType() { return itemType; }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", allergens=" + allergens +
                ", calories=" + calories +
                ", imageName='" + imageName + '\'' +
                '}';
    }

}
