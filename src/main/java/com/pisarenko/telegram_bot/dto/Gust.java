package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@Table(name = "gust")
public class Gust {

    @Id
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;



}
