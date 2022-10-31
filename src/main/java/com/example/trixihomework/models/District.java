package com.example.trixihomework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "districts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    private int code;
    private String name;
    @ManyToOne
    @JoinColumn(name = "town_code")
    private Town town;
}
