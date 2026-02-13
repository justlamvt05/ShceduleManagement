package com.lamvt.shcedule.entity;

import jakarta.persistence.*;
import com.lamvt.shcedule.constraint.ExceptionType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;


@Entity
@Table(name = "event_exception")
@Getter
@Setter
public class EventException {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private CalendarEvent event;

    @Column(name = "exception_date", nullable = false)
    private LocalDate exceptionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExceptionType type; // CANCELLED | MODIFIED

    @Column(name = "new_start_time")
    private LocalTime newStartTime;

    @Column(name = "new_end_time")
    private LocalTime newEndTime;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
