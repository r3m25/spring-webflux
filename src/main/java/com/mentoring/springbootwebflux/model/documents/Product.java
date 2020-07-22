package com.mentoring.springbootwebflux.model.documents;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Setter
    private String name;

    private Double price;

    @Setter
    private Date createAt;

    @Valid
    @NotNull
    private Category category;
}
