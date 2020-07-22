package com.mentoring.springbootwebflux.business;

import com.mentoring.springbootwebflux.model.documents.Product;
import com.mentoring.springbootwebflux.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public Mono<Product> save(Product product) {

        if(Objects.isNull(product.getCreateAt())) {
            product.setCreateAt(new Date());
        }

        return productRepository.save(product);
    }

    public Mono<Void> delete(Product product) {
        return productRepository.delete(product);
    }

    public Flux<Product> findAllWithNameUpperCase() {
        return productRepository.findAll().map(product -> {
                product.setName(product.getName().toUpperCase());
            return product;
        });
    }

    public Flux<Product> findAllWithNameUpperCaseRepeat() {
        return findAllWithNameUpperCase().repeat(5000);
    }

}
