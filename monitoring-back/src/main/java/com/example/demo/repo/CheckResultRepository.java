package com.example.demo.repo;

import com.example.demo.data.CheckResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckResultRepository extends JpaRepository<CheckResultEntity, Long> {
    List<CheckResultEntity> findAllByOrderByIdAsc();
}
