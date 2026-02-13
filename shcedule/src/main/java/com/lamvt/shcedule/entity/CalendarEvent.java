package com.lamvt.shcedule.entity;

import com.lamvt.shcedule.constraint.RecurrenceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "calendar_event")
@Getter
@Setter
public class CalendarEvent {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;


    private LocalTime startTime;
    private LocalTime endTime;

    private String timezone;

    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;

    private String recurrenceDays;

    private Integer notifyBeforeMinutes;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

