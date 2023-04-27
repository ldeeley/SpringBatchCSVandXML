package com.example.springbatchcsvandxml.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public class MyCsvISO {

        int id;
        String firstName;
        Laptop laptop;

    }


