package com.mentoring.springbootwebflux.repository;

import com.mentoring.springbootwebflux.model.documents.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
