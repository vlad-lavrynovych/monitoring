package com.example.demo.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "logs")
public class LogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @ToString.Exclude
//    @ManyToOne (optional=false, cascade=CascadeType.ALL)
//    @JoinColumn(name="logs_id", nullable=false)
//    private CheckResultEntity checkResult;
    private Long checkId;
    private String status;
    private String details;
    private Date lastCheck;
    private Long duration;
    private Integer responseCode;
    private Integer responseSize;
}
