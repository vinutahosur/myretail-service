package myRetail.nosql.repo;

import myRetail.model.Price;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface PriceRepository extends CrudRepository<Price, Integer> {
    
}