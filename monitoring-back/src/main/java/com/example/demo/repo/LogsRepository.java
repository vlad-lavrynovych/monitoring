package com.example.demo.repo;

import com.example.demo.data.CheckResultEntity;
import com.example.demo.data.ConfigEntity;
import com.example.demo.data.LogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<LogsEntity, Long>, JpaSpecificationExecutor<LogsEntity> {
    @Query(
            value = "SELECT * FROM logs WHERE check_id = :check_id",
            nativeQuery = true)
    List<LogsEntity> findLogsById(@Param("check_id") Long check_id);
    @Query(
            value = "SELECT min(last_check) FROM logs WHERE check_id = :check_id",
            nativeQuery = true)
    Timestamp findLogsTimer(@Param("check_id") Long check_id);
    @Query(
            value = "SELECT * FROM logs WHERE check_id = :check_id and last_check > :time",
            nativeQuery = true)
    List<LogsEntity> findLogs(@Param("check_id") Long check_id, @Param("time") Timestamp time);
    List<LogsRepository> findAllByOrderByIdAsc();
}

