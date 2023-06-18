package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "gust")
public class Gust {

    @Id
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;



}
