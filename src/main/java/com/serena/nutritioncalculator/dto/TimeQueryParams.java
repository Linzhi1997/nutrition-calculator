package com.serena.nutritioncalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Getter
@Setter
public class TimeQueryParams {
    private Date    beginTime;
    private Date    endTime;

    // 默認時間為當天 24H
    public TimeQueryParams() {
        LocalDate today = LocalDate.now();
        this.beginTime = Date.from(today.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
        this.endTime = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
    }

    public TimeQueryParams(Date beginTime, Date endTime) {
        setTimeRange(beginTime, endTime);
    }

    // 設定時間範圍 & 檢查
    public void setTimeRange(Date beginTime, Date endTime) {
        Date now = new Date();
        if (beginTime == null || endTime == null) {
            // 避免NullPoint
            LocalDate today = LocalDate.now();
            this.beginTime = Date.from(today.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
            this.endTime = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        } else if (beginTime.after(endTime)) {
            log.warn("起始時間晚於結束時間");
            throw new IllegalArgumentException();
        } else if (beginTime.after(now) || endTime.after(now)) {
            log.warn("輸入時間晚於當前時間");
            throw new IllegalArgumentException();
        } else {
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }

}
