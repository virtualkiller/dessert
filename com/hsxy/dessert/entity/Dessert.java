package com.hsxy.dessert.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Dessert {
    Integer id;
    String name;
    String photoUrl;
    Double price;
    String descp;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date releaseDate;
    Integer catId;
}
