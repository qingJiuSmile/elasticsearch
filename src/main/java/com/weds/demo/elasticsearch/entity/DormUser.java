package com.weds.demo.elasticsearch.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class DormUser {
    private Integer id;

    private Integer dormId;

    private Integer userId;

    private Date beginDate;

    private Integer bedId;

    private Integer useStatusId;

    private String remark;

    private Integer ctUserId;

    private Timestamp ctDate;

    private Integer ltUserId;

    private Timestamp ltDate;

    private String version;

    private String placeName;

    private String dormName;

    private String bedName;

    private String userName;

    private Integer userSex;

    private String userSexStr;

    private String userNo;

    private String className;

}