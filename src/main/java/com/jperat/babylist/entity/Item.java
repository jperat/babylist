package com.jperat.babylist.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @Min(1)
    private Integer quantity;

    private boolean enable;

    @OneToMany(mappedBy = "item")
    private Collection<Availability> availabilities;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "item")
    private Collection<UserItem> userItems = new ArrayList<UserItem>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Collection<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Collection<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<UserItem> getUserItems() {
        return userItems;
    }

    public void setUserItems(Collection<UserItem> userItems) {
        this.userItems = userItems;
    }

    public Integer getAvailableQuantity() {
        return this.getAvailableQuantityByEmail(null);
    }

    public Integer getAvailableQuantityByEmail(String email) {
        Integer quantity = 0;
        for (UserItem userItem: getUserItems()) {
            if (userItem.getUser().getEmail() != email) {
                quantity += userItem.getQuantity();
            }
        }
        return this.quantity - quantity;
    }
}
