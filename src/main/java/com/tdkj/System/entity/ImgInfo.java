package com.tdkj.System.entity;

import lombok.Data;

import java.util.Arrays;

/**
 * @Description
 * @ClassName ImgInfo
 * @Author Chang
 * @date 2020.08.11 10:03
 */
@Data
public class ImgInfo {
    private Integer error;
    private String[] url;
    @Override
    public String toString() {
        return "ImgInfo [error=" + error + ", url=" + Arrays.toString(url) + "]";
    }
}
