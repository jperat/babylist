package com.jperat.babylist.repository;

import com.jperat.babylist.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CardRepository extends PagingAndSortingRepository<Card, Integer> {

    Page<Card> findAllByLocale(Pageable var1, String locale);
}
