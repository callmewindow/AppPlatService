package dao;

/**
 * Created by yezhang989@163.com on 14-5-31.
 */
public class AllDAO {
    private NodeDAO nodeDAO;
    private PreTaskUserDAO preTaskUserDAO;
    private ProcessDAO processDAO;
    private TaskDAO taskDAO;
    private TaskProcessDAO taskProcessDAO;
    private TaskUserDAO taskUserDAO;
    private UserDAO userDAO;
    private PersonalTaskDAO personalTaskDAO;




    public TaskDAO getTaskDAO() {
        return taskDAO;
    }
    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }



    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ProcessDAO getProcessDAO() {
        return processDAO;
    }

    public void setProcessDAO(ProcessDAO processDAO) {
        this.processDAO = processDAO;
    }

    public TaskUserDAO getTaskUserDAO() {
        return taskUserDAO;
    }

    public void setTaskUserDAO(TaskUserDAO taskUserDAO) {
        this.taskUserDAO = taskUserDAO;
    }

    public NodeDAO getNodeDAO() {
        return nodeDAO;
    }

    public void setNodeDAO(NodeDAO nodeDAO) {
        this.nodeDAO = nodeDAO;
    }

    public TaskProcessDAO getTaskProcessDAO() {
        return taskProcessDAO;
    }

    public void setTaskProcessDAO(TaskProcessDAO taskProcessDAO) {
        this.taskProcessDAO = taskProcessDAO;
    }

    public PreTaskUserDAO getPreTaskUserDAO() {
        return preTaskUserDAO;
    }

    public void setPreTaskUserDAO(PreTaskUserDAO preTaskUserDAO) {
        this.preTaskUserDAO = preTaskUserDAO;
    }

    public PersonalTaskDAO getPersonalTaskDAO() {
        return personalTaskDAO;
    }

    public void setPersonalTaskDAO(PersonalTaskDAO personalTaskDAO) {
        this.personalTaskDAO = personalTaskDAO;
    }
}
