package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "performance")
public class Performance {

    @Id
    private Integer id;

    @Column(name = "count_of_seats", columnDefinition = "int default 30", nullable = false)
    private Integer CountOfSeats;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "data")
    private PerformanceData data;

    @Column(nullable = false, name = "gust_id")
    @OneToMany(mappedBy = "performance")
    private List<Gust> gusts;


}
