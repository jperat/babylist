package com.jperat.babylist.repository;

import com.jperat.babylist.entity.Item;
import com.jperat.babylist.entity.User;
import com.jperat.babylist.entity.UserItem;
import org.mariadb.jdbc.ClientSidePreparedStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserItemRepository extends PagingAndSortingRepository<UserItem, Integer> {

    UserItem findOneByUserAndItem(User user, Item item);

}
