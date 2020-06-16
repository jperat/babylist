package com.jperat.babylist.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class UserItem {

    @EmbeddedId
    private UserItemId id;

    @MapsId("userId")
    @ManyToOne
    private User user;

    @MapsId("itemId")
    @ManyToOne
    private Item item;

    @NotNull
    @Min(1)
    private Integer quantity = 1;

    public UserItem () {}

    public UserItem (User user, Item item) {
        this.user = user;
        this.item = item;
        this.id = new UserItemId(user.getId(), item.getId());
    }

    public UserItemId getId() {
        return id;
    }

    public void setId(UserItemId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserItem that = (UserItem) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, item);
    }
}
