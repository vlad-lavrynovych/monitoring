package com.example.demo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "configs")
public class ConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private CheckResultEntity checkResult;
    // all intervals are in millis
    private Integer queryingInterval;
    private Integer responseTimeOk;
    private Integer responseTimeWarning;
    private Integer responseTimeCritical;
    private Integer expectedHttpResponseCode;
    // response size in bytes
    private Integer minExpectedResponseSize;
    private Integer maxExpectedResponseSize;
    private Boolean monitored = true;
}
