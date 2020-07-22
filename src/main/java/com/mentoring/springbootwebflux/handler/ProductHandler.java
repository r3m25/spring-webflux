package com.mentoring.springbootwebflux.handler;

import com.mentoring.springbootwebflux.business.ProductService;
import com.mentoring.springbootwebflux.model.documents.Product;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class ProductHandler {

    private final static String ID = "id";

    private final ProductService productService;

    public ProductHandler(ProductService productService) {
        this.productService = productService;
    }

    public Mono<ServerResponse> productList(ServerRequest serverRequest) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll(), Product.class);
    }

    public Mono<ServerResponse> productDetail(ServerRequest serverRequest) {

        return productService
                .findById(serverRequest.pathVariable(ID))
                .flatMap(detail ->
                        ok().contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(detail))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> productSave(ServerRequest serverRequest) {
        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);

        return productMono.flatMap(productService::save).flatMap(product -> ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product));
    }

    public Mono<ServerResponse> productUpdate(ServerRequest serverRequest) {
        Mono<Product> productRequest = serverRequest.bodyToMono(Product.class);
        Mono<Product> productBd = productService.findById(serverRequest.pathVariable(ID));

        return productBd.zipWith(productRequest, (fromBd, fromReq) -> {
            return productService.save(buildProduct(fromBd, fromReq));
        }).flatMap(product -> ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product, Product.class)
                .switchIfEmpty(notFound().build())
        );
    }

    public Mono<ServerResponse> productDeleted(ServerRequest serverRequest) {
        Mono<Product> productBd = productService.findById(serverRequest.pathVariable(ID));

        return productBd.flatMap(product ->
                productService.delete(product)
                        .then(noContent().build()))
                .switchIfEmpty(notFound().build());
    }

    private Product buildProduct(Product productBd, Product productReq) {
        return Product.builder()
                .id(productBd.getId())
                .name(productReq.getName())
                .price(productReq.getPrice())
                .category(productReq.getCategory()).build();
    }

}