package com.hsxy.dessert.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    Integer id;
    public String name;
    public String descp;
}
