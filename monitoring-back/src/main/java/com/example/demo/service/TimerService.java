package com.example.demo.service;

import com.example.demo.data.CheckResultEntity;
import com.example.demo.data.ConfigEntity;

import com.example.demo.data.LogsEntity;
import com.example.demo.repo.CheckResultRepository;

import com.example.demo.repo.ConfigRepository;
import com.example.demo.repo.LogsRepository;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
public class TimerService {
    @Autowired
    private TestingService testingService;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private LogsRepository logsRepository;


    public void runTimer(long id, Integer queryingInterval) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConfigEntity configEntity = configRepository.findById(id).orElse(null);
                if (configEntity != null && configEntity.getMonitored()) {
                    CheckResultEntity checkResultEntity = testingService.performCheck(configEntity);
                    configEntity.getCheckResult().setLastCheck(new Date());
                    configEntity.getCheckResult().setDetails(checkResultEntity.getDetails());
                    configEntity.getCheckResult().setDuration(checkResultEntity.getDuration());
                    configEntity.getCheckResult().setResponseCode(checkResultEntity.getResponseCode());
                    configEntity.getCheckResult().setResponseSize(checkResultEntity.getResponseSize());
                    configEntity.getCheckResult().setStatus(checkResultEntity.getStatus());
                    configRepository.save(configEntity);
                    LogsEntity logsEntity = new LogsEntity();
                    logsEntity.setDetails(checkResultEntity.getDetails());
                    logsEntity.setDuration(checkResultEntity.getDuration());
                    logsEntity.setLastCheck(checkResultEntity.getLastCheck());
                    logsEntity.setResponseCode(checkResultEntity.getResponseCode());
                    logsEntity.setResponseSize(checkResultEntity.getResponseSize());
                    logsEntity.setStatus(checkResultEntity.getStatus());
                    logsEntity.setCheckId(checkResultEntity.getId() - Long.valueOf(1));
                    logsRepository.save(logsEntity);
                    log.info("Refreshed check results, config id :: {}", id);
                } else {
                    log.info("Task was cancelled, config id :: {}", id);
                    cancel();
                }
            }
        }, queryingInterval, queryingInterval);
    }

    public void runTimersOnStartup() {
        List<ConfigEntity> configs = configRepository.findAll();
        configs.forEach(s -> runTimer(s.getId(), s.getQueryingInterval()));
    }

}
