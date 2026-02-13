package com.lamvt.shcedule.repository;

import com.lamvt.shcedule.entity.EventException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventExceptionRepository extends JpaRepository<EventException, UUID> {
}
