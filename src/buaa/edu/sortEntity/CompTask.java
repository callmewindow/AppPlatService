package buaa.edu.sortEntity;

import entity.databaseEntity.Task;

import java.util.Comparator;
/**
 * Created by winter on 2014/6/11.
 */
public class CompTask implements Comparator<Task> {

    public int compare(Task o1, Task o2){
            return o2.getStartTime().compareTo(o1.getStartTime());
    }
}
