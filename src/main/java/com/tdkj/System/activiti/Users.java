package com.tdkj.System.activiti;

import lombok.Data;

import java.io.Serializable;
@Data
public class Users implements Serializable {

    private static final long serialVersionUID= 1L;

    private Integer id;
    private String name;

    public Users(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
