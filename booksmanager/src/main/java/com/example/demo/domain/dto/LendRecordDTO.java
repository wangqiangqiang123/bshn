package com.example.demo.domain.dto;

import lombok.Data;

import java.util.Date;
@Data
public class LendRecordDTO {
    private Integer id;
    private Integer readerId;
    private String isbn;
    private String bookname;
    private Date lendTime;
    private Date returnTime;
    private String status;
    private Integer borrownum;
}
