package com.chukkykatz.cooking.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "cooking", name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dish_id")
    private Integer dishId;

    @Column(name = "url")
    private String url;

    @Column(name = "image")
    private String image;
}
