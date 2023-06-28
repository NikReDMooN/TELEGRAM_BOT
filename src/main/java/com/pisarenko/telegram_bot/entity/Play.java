package com.pisarenko.telegram_bot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "play")
public class Play {

    @Id
    @Column
    private String name;

    @OneToMany(mappedBy = "name")
    private List<Performance> performance;

}
