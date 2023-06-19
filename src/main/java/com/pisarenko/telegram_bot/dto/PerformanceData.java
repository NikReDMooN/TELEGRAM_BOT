package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "PerformanceData")
public class PerformanceData {

    @Id
    @Column(nullable = false)
    private Timestamp data;

    @OneToOne
    private Performance performance;

}
