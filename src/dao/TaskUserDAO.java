package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.databaseEntity.TaskUser;

/**
 * A data access object (DAO) providing persistence and search support for TaskUser
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see
 * @author MyEclipse Persistence Tools
 */

public class TaskUserDAO extends BaseDAO {
    private static final Logger log = LoggerFactory.getLogger(TaskUserDAO.class);
    // property constants
    private static String USER_ACCOUNT = "userAccount";
    private static String TASK_BY_TASKID = "taskByTaskId";
    public void save(TaskUser transientInstance) {
        log.debug("saving TaskUser instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TaskUser persistentInstance) {
        log.debug("deleting TaskUser instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TaskUser findById(String id) {
        log.debug("getting TaskUser instance with id: " + id);
        try {
            TaskUser instance = (TaskUser) getSession().get("entity.databaseEntity.TaskUser", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TaskUser instance) {
        log.debug("finding TaskUser instance by example");
        try {
            List results = getSession().createCriteria("entity.databaseEntity.TaskUser")
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    public List findByAccount(String userAccount) {
        return findByProperty(USER_ACCOUNT, userAccount);
    }
    public List findByTask(Object task) {
        return findByProperty(TASK_BY_TASKID, task);
    }
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TaskUser instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from TaskUser as model where model."
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
        log.debug("finding all TaskUser instances");
        try {
            String queryString = "from TaskUser";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TaskUser merge(TaskUser detachedInstance) {
        log.debug("merging TaskUser instance");
        try {
            TaskUser result = (TaskUser) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TaskUser instance) {
        log.debug("attaching dirty TaskUser instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TaskUser instance) {
        log.debug("attaching clean TaskUser instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}