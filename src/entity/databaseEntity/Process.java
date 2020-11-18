package entity.databaseEntity;

import java.util.Collection;

/**
 * Created by winter on 2014/6/28.
 */
public class Process {
    private String processId;
    private String processName;
    private Collection<TaskProcess> taskProcessesByProcessId;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Process process = (Process) o;

        if (processId != null ? !processId.equals(process.processId) : process.processId != null) return false;
        if (processName != null ? !processName.equals(process.processName) : process.processName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = processId != null ? processId.hashCode() : 0;
        result = 31 * result + (processName != null ? processName.hashCode() : 0);
        return result;
    }

    public Collection<TaskProcess> getTaskProcessesByProcessId() {
        return taskProcessesByProcessId;
    }

    public void setTaskProcessesByProcessId(Collection<TaskProcess> taskProcessesByProcessId) {
        this.taskProcessesByProcessId = taskProcessesByProcessId;
    }
}
