package com.example.springbatchcsvandxml.entity;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
public class MyXmlISO {

    int id;
    String firstName;
    Laptop laptop;

}


