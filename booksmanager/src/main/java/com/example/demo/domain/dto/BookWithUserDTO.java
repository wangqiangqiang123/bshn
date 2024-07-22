package com.example.demo.domain.dto;


import lombok.Data;

import java.util.Date;
@Data
public class BookWithUserDTO {
    private Integer id;
    private String isbn;
    private String bookName;
    private String nickName;
    private Date lendtime;
    private Date deadtime;
    private Integer prolong;
}
