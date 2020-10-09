package com.tdkj.APP.entity;

import com.tdkj.System.entity.User;
import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/10/9 11:41
 */
@Data
public class AppUser extends User {

    private String token;


}
