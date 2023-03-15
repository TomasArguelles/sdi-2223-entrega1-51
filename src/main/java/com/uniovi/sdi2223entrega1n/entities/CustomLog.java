package com.uniovi.sdi2223entrega1n.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Locale;

@Entity
public class CustomLog {

    @Id
    @GeneratedValue
    private Long id;

    private String logType; // PET, LOG-EX, LEG-ERR...

    private String endPoint;

    private String requestParams;

    private String httpMethod;

    private int responseStatusCode;

    private String remoteAddr;

    private Locale responseLocale;

    private Timestamp createdAt;

    private String username; // Usuario en sesion

    public CustomLog() {
    }

    public CustomLog(String logType, String httpMethod, int responseStatusCode, String remoteAddr, Locale responseLocale, Timestamp createdAt, String endPoint, String params, String username) {
        this.logType = logType;
        this.httpMethod = httpMethod;
        this.responseStatusCode = responseStatusCode;
        this.remoteAddr = remoteAddr;
        this.responseLocale = responseLocale;
        this.createdAt = createdAt;
        this.endPoint = endPoint;
        this.requestParams = params;
        this.username = username;
    }

    public CustomLog(String logType, String httpMethod, int responseStatusCode, String remoteAddr, Locale responseLocale, Timestamp createdAt, String endPoint, String username) {
        this.logType = logType;
        this.httpMethod = httpMethod;
        this.responseStatusCode = responseStatusCode;
        this.remoteAddr = remoteAddr;
        this.responseLocale = responseLocale;
        this.createdAt = createdAt;
        this.endPoint = endPoint;
        this.username = username;
    }

    public CustomLog(String logType, String httpMethod, int responseStatusCode, String remoteAddr, Locale responseLocale, Timestamp createdAt, String endPoint) {
        this(logType, httpMethod, responseStatusCode, remoteAddr, responseLocale, createdAt, endPoint, "anonymous");
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public Locale getResponseLocale() {
        return responseLocale;
    }

    public void setResponseLocale(Locale responseLocale) {
        this.responseLocale = responseLocale;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    @Override
    public String toString() {
        return "CustomLog{" +
                "logType='" + logType + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", responseStatusCode=" + responseStatusCode +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", responseLocale=" + responseLocale +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                '}';
    }
}
