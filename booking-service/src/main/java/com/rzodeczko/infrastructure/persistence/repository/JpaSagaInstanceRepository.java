package com.rzodeczko.infrastructure.persistence.repository;

import com.rzodeczko.infrastructure.persistence.entity.SagaInstanceEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaSagaInstanceRepository extends JpaRepository<SagaInstanceEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = "steps")
    @Query("select s from SagaInstanceEntity s where s.id = :id")
    Optional<SagaInstanceEntity> findByIdForUpdate(@Param("id") UUID id);

    @EntityGraph(attributePaths = "steps")
    @Query("select s from SagaInstanceEntity s where s.id = :id")
    Optional<SagaInstanceEntity> findByIdWithSteps(@Param("id") UUID id);

    @EntityGraph(attributePaths = "steps")
    Page<SagaInstanceEntity> findAllWithSteps(Pageable pageable);
}
