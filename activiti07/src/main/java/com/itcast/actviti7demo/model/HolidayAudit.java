package com.itcast.actviti7demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class HolidayAudit {
    private String holidayauditId;

    private String employeeName;

    private String auditName;

    private String auditinfo;

    private String auditType;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createtime;

    public String getHolidayauditId() {
        return holidayauditId;
    }

    public void setHolidayauditId(String holidayauditId) {
        this.holidayauditId = holidayauditId == null ? null : holidayauditId.trim();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName == null ? null : auditName.trim();
    }

    public String getAuditinfo() {
        return auditinfo;
    }

    public void setAuditinfo(String auditinfo) {
        this.auditinfo = auditinfo == null ? null : auditinfo.trim();
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType == null ? null : auditType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}