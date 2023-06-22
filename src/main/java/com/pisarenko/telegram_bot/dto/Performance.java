package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "performance")
public class Performance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "count_of_seats", insertable = false, nullable = false, columnDefinition = "INT DEFAULT 30")
    private Integer CountOfSeats;

    @Column()
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="performance_data", referencedColumnName = "data")
    private PerformanceData data;

    @Column(name = "gust_id")
    @OneToMany(mappedBy = "performance")
    private List<Gust> gusts;


}
