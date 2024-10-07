package vn.hoidanit.laptopshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;

    @NotNull
    @Min(value = 1, message = "Price must be greater than 1")
    private String price;
    private String image;

    @NotNull
    @NotEmpty(message = "detailDesc không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    @NotNull
    @Size(min = 5, message = "Short description must be at least 5 characters")
    private String shortDesc;

    @NotNull
    @Min(value = 0, message = "Quantity must be greater than 1")
    private long quantity;
    private long sold;

    @NotNull(message = "Factory cannot be empty")
    @Size(min = 2, message = "Factory must be at least 2 characters")
    private String factory;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String target;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getSold() {
        return sold;
    }

    public String getFactory() {
        return factory;
    }

    public String getTarget() {
        return target;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name = " + name + ", price=" + price + ", image=" + image + ", detailDesc="
                + detailDesc + ", shortDesc=" + shortDesc + ", quantity=" + quantity + ", sold=" + sold + ", factory="
                + factory + ", target=" + target + "]";
    }
}
