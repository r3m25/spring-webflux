package com.mentoring.springbootwebflux.model.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Document(collection = "categories")
public class Category {

    @Id
    @NotEmpty
    private String id;

    private String name;
}
