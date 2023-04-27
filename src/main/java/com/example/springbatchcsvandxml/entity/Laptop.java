package com.example.springbatchcsvandxml.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Laptop {

    Integer laptopId;
    String laptopBrandName;
    Owner laptopOwner;


}
