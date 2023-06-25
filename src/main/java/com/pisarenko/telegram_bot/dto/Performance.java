package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

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

    @Column()
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="performance_data", referencedColumnName = "data")
    private PerformanceData data;

    @Column(name = "gust_id")
    @OneToMany(mappedBy = "performance")
    private List<Gust> gusts;

}
