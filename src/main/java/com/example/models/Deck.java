package com.example.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Data
@Entity
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String desc;
    private String userName;
}
