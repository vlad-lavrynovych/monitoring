package com.example.demo.controller;

import com.example.demo.data.CheckResultEntity;
import com.example.demo.data.ConfigEntity;
import com.example.demo.data.LogsEntity;
import com.example.demo.repo.CheckResultRepository;
import com.example.demo.repo.ConfigRepository;
import com.example.demo.repo.LogsRepository;
import com.example.demo.service.TestingService;
import com.example.demo.service.TimerService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
    @Autowired
    private CheckResultRepository checkResultRepository;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private TestingService testingService;
    @Autowired
    private TimerService timerService;
    @Autowired
    private LogsRepository logsRepository;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<CheckResultEntity> getAll() {
        log.info("Received findAll request");
        return checkResultRepository.findAllByOrderByIdAsc();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public List<CheckResultEntity> add(@RequestBody ConfigEntity configEntity) {
        log.info("Received creating request");
        CheckResultEntity checkResultEntity = testingService.performCheck(configEntity);
        checkResultRepository.save(checkResultEntity);
        checkResultEntity.setConfig(configEntity);
        configEntity.setCheckResult(checkResultEntity);
        configRepository.save(configEntity);
        timerService.runTimer(configEntity.getId(), configEntity.getQueryingInterval());
        return getAll();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public List<CheckResultEntity> updateMonitoringStatus(@PathVariable(name = "id") long id) {
        log.info("Received change monitoring status request");
        CheckResultEntity checkResultEntity = checkResultRepository.findById(id).get();
        checkResultEntity.getConfig().setMonitored(!checkResultEntity.getConfig().getMonitored());
        if (checkResultEntity.getConfig().getMonitored()) {
            timerService.runTimer(checkResultEntity.getConfig().getId(), checkResultEntity.getConfig().getQueryingInterval());
        }

        checkResultRepository.save(checkResultEntity);
//        logsRepository.save(logsEntity);
        return getAll();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public List<CheckResultEntity> delete(@PathVariable(name = "id") long id) {
        log.info("Received deletion request, id :: {}", id);
        CheckResultEntity checkResultEntity = checkResultRepository.findById(id).get();
        configRepository.deleteById(checkResultEntity.getConfig().getId());
        return getAll();
    }
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public List<LogsEntity> logs(@RequestParam String id, @RequestParam String min) {
        Timestamp time = logsRepository.findLogsTimer(Long.valueOf(id));
        System.out.println(time.getTime());
        List<LogsEntity> logsEnt = logsRepository.findLogs(Long.valueOf(id), time);
        List<LogsEntity> logsReturn = new ArrayList<LogsEntity>();
        for (int i = 0; i < logsEnt.size(); i++) {
            if (((logsEnt.get(i).getLastCheck().getTime() - time.getTime()) - (logsEnt.get(i).getLastCheck().getTime() - time.getTime()) % 1000)%(Long.valueOf(min)*53000) == 0){
                logsReturn.add(logsEnt.get(i));
            }

        }
        return logsReturn;

    }


}
