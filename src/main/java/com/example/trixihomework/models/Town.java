package com.example.trixihomework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "towns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Town {
    @Id
    private Integer code;
    private String name;
    @OneToMany(mappedBy = "town")
    private List<District> districts;
}
