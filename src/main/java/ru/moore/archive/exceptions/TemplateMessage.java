package ru.moore.archive.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class TemplateMessage {

    private int status;
    private String header;
    private String message;
    private Date timestamp;

    public TemplateMessage(int status, String header, String message) {
        this.status = status;
        this.header = header;
        this.message = message;
        this.timestamp = new Date();
    }

}
