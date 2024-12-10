package com.baitap.cuoiky.eventmanager.model;

import com.baitap.cuoiky.eventmanager.service.Event;

import java.util.List;

public class BaseResponse {
    private String errorCode;
    private String errorMessage;
    private Event event;
    private List<Event> listEvent;

    public List<Event> getListEvent() {
        return listEvent;
    }

    public void setListEvent(List<Event> listEvent) {
        this.listEvent = listEvent;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BaseResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseResponse() {
    }


    public BaseResponse(String errorCode, String errorMessage, Event event) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.event = event;
    }

    public BaseResponse(String errorCode, String errorMessage, List<Event> listEvent) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.listEvent = listEvent;
    }
}
