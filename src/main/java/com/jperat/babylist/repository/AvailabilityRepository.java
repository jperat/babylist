package com.jperat.babylist.repository;

import com.jperat.babylist.entity.Availability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends CrudRepository<Availability, Integer> {
}
