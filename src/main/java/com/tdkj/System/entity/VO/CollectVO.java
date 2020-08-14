package com.tdkj.System.entity.VO;

import com.tdkj.System.entity.Collect;
import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/8/14 13:45
 */
@Data
public class CollectVO extends Collect {

    /**
     * 所属公司
     */
    private String corpname;
    /**
     * 领用部门
     */
    private String deptname;
    /**
     * 领用人
     */
    private String name;


}
