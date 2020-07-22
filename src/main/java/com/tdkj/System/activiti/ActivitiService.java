package com.tdkj.System.activiti;

import java.util.List;

public interface ActivitiService {


    public Boolean startActiviti(Activiti vac, String userName);

    public List<Activiti> myActiviti(String userName);

    public List<ActivitiTask> myApproval(String userName);

    public Boolean passApproval(String userName, ActivitiTask activitiTask);

    public List<Activiti> myActivitiRecord(String userName);

    public List<Activiti> myApprovalRecord(String userName);

}
