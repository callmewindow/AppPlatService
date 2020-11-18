package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.databaseEntity.TaskProcess;

/**
 * A data access object (DAO) providing persistence and search support for TaskProcess
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see
 * @author MyEclipse Persistence Tools
 */

public class TaskProcessDAO extends BaseDAO {
    private static final Logger log = LoggerFactory.getLogger(TaskProcessDAO.class);
    // property constants
    private static String PROCESS_BY_PROCESSID = "processByProcessId";
    private static String TASK_BY_TASKID = "taskByTaskId";

    public void save(TaskProcess transientInstance) {
        log.debug("saving TaskProcess instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TaskProcess persistentInstance) {
        log.debug("deleting TaskProcess instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TaskProcess findById(String id) {
        log.debug("getting TaskProcess instance with id: " + id);
        try {
            TaskProcess instance = (TaskProcess) getSession().get("entity.databaseEntity.TaskProcess", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TaskProcess instance) {
        log.debug("finding TaskProcess instance by example");
        try {
            List results = getSession().createCriteria("entity.databaseEntity.TaskProcess")
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    public List findByProcess(Object process) {
        return findByProperty(PROCESS_BY_PROCESSID, process);
    }
    public List findByTask(Object task) {
        return findByProperty(TASK_BY_TASKID, task);
    }
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TaskProcess instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from TaskProcess as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all TaskProcess instances");
        try {
            String queryString = "from TaskProcess";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TaskProcess merge(TaskProcess detachedInstance) {
        log.debug("merging TaskProcess instance");
        try {
            TaskProcess result = (TaskProcess) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TaskProcess instance) {
        log.debug("attaching dirty TaskProcess instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TaskProcess instance) {
        log.debug("attaching clean TaskProcess instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}