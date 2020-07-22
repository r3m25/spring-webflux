package com.mentoring.springbootwebflux.business;

import com.mentoring.springbootwebflux.model.documents.Category;
import com.mentoring.springbootwebflux.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    public Mono<Category> findCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    public Mono<Category> saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
