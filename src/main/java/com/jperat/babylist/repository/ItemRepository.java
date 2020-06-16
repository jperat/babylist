package com.jperat.babylist.repository;

import com.jperat.babylist.entity.Category;
import com.jperat.babylist.entity.Item;
import com.jperat.babylist.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

    @Query("SELECT i FROM Item i INNER JOIN UserItem ui ON ui.item = i WHERE ui.user = :user")
    Page<Item> findAllByUser(Pageable var1, @Param("user")User user);

    @Query("SELECT i FROM Item i INNER JOIN Category c ON i.category = c WHERE c.id = :id")
    Page<Item> findAllByCategoryId(Pageable var1, @Param("id")Integer id);
}
