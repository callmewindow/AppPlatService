package entity;

import buaa.edu.global.AppPlatConstant;

import java.sql.Timestamp;

/**
 * Created by winter on 2015/3/11.
 */
//发送给云平台任务信息的数据格式类
public class CoTask {
    private String taskId;
    private String taskName;
    private String recentlyModified;
    private String taskType;
    private String modelId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String taskType) {
        if(taskType.equals(AppPlatConstant.PER_TASK_TYPE_ORBIT_DESIGN)){
            this.modelId = AppPlatConstant.MODEL_ID_ORBIT_DESIGN;
        }else if(taskType.equals(AppPlatConstant.PER_TASK_TYPE_STRUCTURE_DESIGN)){
            this.modelId = AppPlatConstant.MODEL_ID_STRUCT_DESIGN;
        }else if(taskType.equals(AppPlatConstant.PER_TASK_TYPE_COVER_ANALYSIS)){
            this.modelId = AppPlatConstant.MODEL_ID_COVER_ANALYSIS;
        }else if(taskType.equals(AppPlatConstant.PER_TASK_TYPE_DATATRANS_ANALYSIS)){
            this.modelId = AppPlatConstant.MODEL_ID_DATA_TRANS_ANALYSIS;
        }else if(taskType.equals(AppPlatConstant.TASK_TYPE_COLLABORATIVE_ARGUMENT)){
            this.modelId = AppPlatConstant.MODEL_ID_COLLABORATIVE_ARGUMENT;
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRecentlyModified() {
        return recentlyModified;
    }

    public void setRecentlyModified(String recentlyModified) {
        this.recentlyModified = recentlyModified;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
