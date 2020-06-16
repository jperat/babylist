package com.jperat.babylist.repository;

import com.jperat.babylist.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findOneByEmail(String email);

    User findOneByToken(String token);

    User findOneByEmailAndToken(String email, String token);
}
