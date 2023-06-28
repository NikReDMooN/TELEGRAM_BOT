package com.pisarenko.telegram_bot.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "performance")
public class Performance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "count_of_seats", insertable = false, nullable = false, columnDefinition = "INT DEFAULT 30")
    private Integer CountOfSeats;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "performance_name", referencedColumnName = "name")
    private Play name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="performance_data", referencedColumnName = "data")
    private PerformanceData data;

    @Column(name = "gust_id")
    @OneToMany(mappedBy = "performance")
    private List<Gust> gusts;

}
