package com.multibite.model;

import java.time.LocalDate;

public class Report {

    private String reportType;
    private LocalDate generatedDate;
    private Object data; // The actual report data can be a complex object depending on the report type

    // Constructor
    public Report(String reportType, LocalDate generatedDate, Object data) {
        this.reportType = reportType;
        this.generatedDate = generatedDate;
        this.data = data;
    }

    // Getters and Setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
