package com.chukkykatz.cooking_app.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "cooking", name = "dishes_types")
public class DishType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String typeName;

    @Column(name = "advice_schedule")
    private String adviceSchedule;

    @Column(name = "last_advice_date")
    private Date lastAdviceDate;
}
