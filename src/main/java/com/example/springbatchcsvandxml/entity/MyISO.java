package com.example.springbatchcsvandxml.entity;


import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyISO {
//laptopId","laptopBrandName","laptopOwner","ownerId","ownerName","ownerTeam"
    private Integer myISOid;
    private String myISOfirstName;
    private Laptop laptop;
//    private String laptopBrandName;
//    private Integer ownerId;
//    private String ownerName;
//    private String ownerTeam;

}
