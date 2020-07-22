package com.mentoring.springbootwebflux.controller;

import com.mentoring.springbootwebflux.business.ProductService;
import com.mentoring.springbootwebflux.model.documents.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "api/v1/products/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Product>>> productList() {
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll()
                ));
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> productDetail(@PathVariable String id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product)
                ).defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> productCreate(@RequestBody Product productMono){

        return productService.save(productMono).map(p-> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p)
        );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> productUpdate(@RequestBody Product product, @PathVariable String id){
        return productService.findById(id).flatMap(p -> productService.save(Product.builder()
                .id(p.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .build())).map(p->ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> productDeleted(@PathVariable String id){
        return productService.findById(id).flatMap(p ->
                productService.delete(p)
                        .then(
                                Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}