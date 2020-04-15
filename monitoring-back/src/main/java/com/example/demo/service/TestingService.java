package com.example.demo.service;

import com.example.demo.data.CheckResultEntity;
import com.example.demo.data.ConfigEntity;
import com.example.demo.dto.CheckResultDto;
import com.example.demo.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class TestingService {

    public CheckResultEntity performCheck(ConfigEntity config) {
        HttpURLConnection conn;
        List<CheckResultDto> results;
        long startTime = System.currentTimeMillis();
        int code;
        int size = 0;
        long duration;
        try {
            URL url = new URL(config.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            code = conn.getResponseCode();
            conn.getInputStream();
            size = conn.getContentLength();
            duration = System.currentTimeMillis() - startTime;
            results = getResults(config, code, duration, size);
            log.info("Finished testing url, size: {}, code: {}, duration: {}", size, code, duration);
        } catch (IOException e) {
            duration = System.currentTimeMillis() - startTime;
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                log.info("Connection url status 404: {}", e.getCause().getMessage());
                // 404 is a special case because it will throw a FileNotFoundException instead of having "404" in the message
                code = 404;
            } else {
                log.error("Connection url error: {}", e.getCause().getLocalizedMessage());
                code = 0;
            }
            results = getResults(config, code, duration, size);
        }
        CheckResultEntity checkResultsEntity = mapResultsToResponse(results, config);
        checkResultsEntity.setDuration(duration);
        checkResultsEntity.setResponseCode(code);
        checkResultsEntity.setResponseSize(size);
        return checkResultsEntity;
    }

    private List<CheckResultDto> getResults(ConfigEntity config, int code, long duration, int size) {
        return Arrays.asList(
                checkHttpResponseCode(config.getExpectedHttpResponseCode(), code),
                checkResponseSize(config.getMinExpectedResponseSize(),
                        config.getMaxExpectedResponseSize(),
                        size),
                checkResponseTime(
                        config.getResponseTimeOk(),
                        config.getResponseTimeWarning(),
                        config.getResponseTimeCritical(),
                        duration
                )
        );
    }

    private CheckResultDto checkHttpResponseCode(int expected, int actual) {
        CheckResultDto checkResultDto = new CheckResultDto();
        if (expected == actual) {
            checkResultDto.setStatus(StatusEnum.OK);
        } else {
            checkResultDto.setStatus(StatusEnum.CRITICAL);
            checkResultDto.setDetails("CRITICAL: Invalid Http response code");
        }
        return checkResultDto;
    }

    private CheckResultDto checkResponseSize(int min, int max, int actual) {
        CheckResultDto checkResultDto = new CheckResultDto();
        if (actual >= min && actual <= max) {
            checkResultDto.setStatus(StatusEnum.OK);
        } else {
            checkResultDto.setStatus(StatusEnum.CRITICAL);
            checkResultDto.setDetails("CRITICAL: Invalid response size");
        }
        return checkResultDto;
    }

    private CheckResultDto checkResponseTime(long ok, long warning, long critical, long actual) {
        CheckResultDto checkResultDto = new CheckResultDto();
        if (actual <= ok) {
            checkResultDto.setStatus(StatusEnum.OK);
        } else if (actual <= warning) {
            checkResultDto.setStatus(StatusEnum.WARNING);
            checkResultDto.setDetails("WARNING: response time is longer than requested");
        } else {
            checkResultDto.setStatus(StatusEnum.CRITICAL);
            checkResultDto.setDetails("CRITICAL: response time is critically long");
        }
        return checkResultDto;
    }

    private CheckResultEntity mapResultsToResponse(List<CheckResultDto> list, ConfigEntity configEntity) {
        CheckResultEntity checkResultsEntity = new CheckResultEntity();
        checkResultsEntity.setId(configEntity.getId());
        checkResultsEntity.setLastCheck(new Date());

        StringBuilder description = new StringBuilder();
        String status = getGeneralStatus(list);
        checkResultsEntity.setStatus(status);

        list.forEach(s -> {
            if (!s.getStatus().equals(StatusEnum.OK)) {
                description.append(s.getDetails()).append("\n");
            }
        });

        if (description.toString().isEmpty()) {
            checkResultsEntity.setDetails("Site is available");
        } else {
            checkResultsEntity.setDetails(description.toString());
        }

        return checkResultsEntity;
    }

    private String getGeneralStatus(List<CheckResultDto> list) {
        return Objects.requireNonNull(list.stream().map(CheckResultDto::getStatus)
                .filter(s -> s.equals(StatusEnum.CRITICAL))
                .findFirst()
                .orElseGet(() ->
                        list.stream().map(CheckResultDto::getStatus)
                                .filter(s -> s.equals(StatusEnum.WARNING))
                                .findFirst()
                                .orElseGet(() ->
                                        list.stream().map(CheckResultDto::getStatus)
                                                .filter(s -> s.equals(StatusEnum.OK))
                                                .findFirst()
                                                .orElse(null)))).getValue();
    }

}
