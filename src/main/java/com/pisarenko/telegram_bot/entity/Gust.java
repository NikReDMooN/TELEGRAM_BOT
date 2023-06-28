package com.pisarenko.telegram_bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "gust")
public class Gust implements Serializable {

    @Id
    @Column
    private String id;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;



}
