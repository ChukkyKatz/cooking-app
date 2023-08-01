package com.chukkykatz.cooking.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "cooking", name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private DishType dishType;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            schema = "cooking", name = "dish_ing",
            joinColumns = {@JoinColumn(name = "dish_id")},
            inverseJoinColumns = {@JoinColumn(name = "ing_id")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private List<Receipt> receipts;

    public Receipt getRandomReceipt() {
        if (receipts == null) {
            return null;
        } else {
            final Random random = new Random();
            final int receiptNum = random.nextInt(receipts.size());
            return receipts.get(receiptNum);
        }
    }
}