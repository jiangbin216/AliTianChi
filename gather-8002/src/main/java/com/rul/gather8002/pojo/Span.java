package com.rul.gather8002.pojo;

public class Span {
    //全局唯一的Id,用作整个链路的唯一标识与组装
    private String traceId;
    //调用的开始时间
    private Long startTime;
    //调用链中某条数据的Id
    private String spanId;
    //调用链中某条数据的父Id，头节点的父Id为0
    private String parentSpanId;
    //调用耗时
    private Integer duration;
    //调用的服务名
    private String serviceName;
    //调用的埋点名
    private String spanName;
    //机器标识
    private String host;
    //链路中的tag信息
    private String tags;

    public Span(String traceId, Long startTime, String spanId, String parentSpanId, Integer duration,
                String serviceName, String spanName, String host, String tags) {
        this.traceId = traceId;
        this.startTime = startTime;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
        this.duration = duration;
        this.serviceName = serviceName;
        this.spanName = spanName;
        this.host = host;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "traceId=" + traceId + '|' +
                "startTime=" + startTime + '|' +
                "spanId='" + spanId + '|' +
                "parentSpanId='" + parentSpanId + '|' +
                "duration=" + duration + '|' +
                "serviceName='" + serviceName + '|' +
                "spanName='" + spanName + '|' +
                "host='" + host + '|' +
                "tags=" + tags;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSpanName() {
        return spanName;
    }

    public void setSpanName(String spanName) {
        this.spanName = spanName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
