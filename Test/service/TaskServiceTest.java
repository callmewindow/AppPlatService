package service;

import entity.TaskInfo;
import entity.databaseEntity.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by winter on 2015/3/14.
 */
public class TaskServiceTest {

    private AllServices allServices;
    @Before
    public void setUp() throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext-server.xml");
        allServices = (AllServices)factory.getBean("allServices");
    }

    @Test
    public void testIsTaskCreater() throws Exception {
        String userId = "534bbbe18cd23317f008f0b8";
        String taskId = "402883084c0bca12014c0bcb72ed0000";
        assertTrue(allServices.getTaskService().isTaskCreater(userId, taskId));
    }

    @Test
    public void testAddTaskNode() throws Exception{
        Task task = new Task();
        String userId = "534bbbe18cd23317f008f0b8";
        task.setTaskName("单元测试任务");
        task.setStartTime(new Timestamp(System.currentTimeMillis()));
        task.setSimulationStartTime(new Timestamp(System.currentTimeMillis()));
        task.setSimulationEndTime(new Timestamp(System.currentTimeMillis()));
        task.setStep(70);
        assertTrue(allServices.getTaskService().addTaskNode(userId,task));
    }
}
