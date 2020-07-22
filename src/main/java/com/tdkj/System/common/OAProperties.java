package com.tdkj.System.common;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @Description
 * @ClassName RnsProperties
 * @Author Chang
 * @date 2020.06.16 16:09
 */
@Data
@SpringBootConfiguration
public class OAProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();
}
