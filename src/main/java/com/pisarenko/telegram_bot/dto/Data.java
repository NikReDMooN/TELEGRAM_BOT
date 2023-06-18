package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "data")
public class Data {

    @Id
    @Column(nullable = false)
    private Timestamp data;

    @OneToOne
    private Performance performance;

}
