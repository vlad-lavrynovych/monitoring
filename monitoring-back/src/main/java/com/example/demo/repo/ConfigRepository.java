package com.example.demo.repo;

import com.example.demo.data.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {
}
