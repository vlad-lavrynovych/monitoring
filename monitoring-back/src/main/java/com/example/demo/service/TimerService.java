package com.example.demo.service;

import com.example.demo.data.CheckResultEntity;
import com.example.demo.data.ConfigEntity;
import com.example.demo.repo.CheckResultRepository;
import com.example.demo.repo.ConfigRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
public class TimerService {
    @Autowired
    private TestingService testingService;
    @Autowired
    private ConfigRepository configRepository;

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
                    log.info("Refreshed check results, config id :: {}", id);
                } else {
                    log.info("Task was cancelled, config id :: {}", id);
                    cancel();
                }
            }
        }, queryingInterval, queryingInterval);
    }
}
