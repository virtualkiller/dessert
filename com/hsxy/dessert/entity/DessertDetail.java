package com.hsxy.dessert.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DessertDetail implements Serializable {
    Integer id;
    String name;
    String photoUrl;
    Double price;
    String descp;
    Date releaseDate;
    String categoryName;
}
