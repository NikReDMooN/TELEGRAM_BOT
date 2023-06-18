package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private Data data;

    @Column(nullable = false, name = "gust_id")
    @OneToMany(mappedBy = "performance")
    private List<Gust> gusts;


}
