package com.example.demo.data;

import lombok.Data;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "checks")
public class CheckResultEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @OneToMany(mappedBy = "checkResult")
//    private Set<LogsEntity> logs;
    @ToString.Exclude
    @OneToOne(mappedBy = "checkResult")
    private ConfigEntity config;
    private String status;
    private String details;
    private Date lastCheck;
    private Long duration;
    private Integer responseCode;
    private Integer responseSize;
}
