package com.example.demo.dto;

import com.example.demo.enums.StatusEnum;
import java.util.Objects;

public class CheckResultDto {
    private StatusEnum status;
    private String details;

    public CheckResultDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckResultDto)) return false;
        CheckResultDto that = (CheckResultDto) o;
        return getStatus() == that.getStatus() &&
                getDetails().equals(that.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getDetails());
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
