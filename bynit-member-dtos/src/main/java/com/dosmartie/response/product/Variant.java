package com.dosmartie.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Variant {
    private String sku;
    private String color;
    private String size;
    private Double price;
    private Integer quantity;
    private List<String> images;
    private double rating;
    private int totalNumberOfRatingProvided;
    private List<Map<String, String>> reviews;
}
