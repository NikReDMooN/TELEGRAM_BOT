package com.pisarenko.telegram_bot.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PerformanceData")
public class PerformanceData implements Serializable {

    @Id
    @Column
    private Timestamp data;

    @OneToOne(mappedBy = "data")
    private Performance performance;

    public String writeData() {
        return data.toString();
    }

}
