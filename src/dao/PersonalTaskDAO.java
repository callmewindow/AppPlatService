package dao;
import entity.databaseEntity.PersonalTask;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by winter on 2014/8/27.
 */
public class PersonalTaskDAO extends BaseDAO {
    private static final Logger log = LoggerFactory.getLogger(PersonalTaskDAO.class);
    // property constants
    // property constants

    public static final String PER_TASK_NAME = "perTaskName";
    public static final String USER_ID = "userId";
    public static final String TASK_TYPE = "taskType";
    public void save(PersonalTask transientInstance) {
        log.debug("saving PersonalTask instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PersonalTask persistentInstance) {
        log.debug("deleting PersonalTask instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    public List findByPerTaskName(String perTaskName){
        return findByProperty(PER_TASK_NAME,perTaskName);
    }
    public List findByUserId(String userId){
        return findByProperty(USER_ID,userId);
    }
    public List findByDataId(Object dataId) {
        return findByProperty("perTaskId", dataId);
    }

    public PersonalTask findById(String id) {
        log.debug("getting PersonalTask instance with id: " + id);
        try {
            PersonalTask instance = (PersonalTask) getSession().get("entity.databaseEntity.PersonalTask", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PersonalTask instance) {
        log.debug("finding PersonalTask instance by example");
        try {
            List results = getSession().createCriteria("entity.databaseEntity.PersonalTask")
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    public List findBy_User_Name_Type(String userId,String taskNasme,String taskType){
        return findByThreeProperty(USER_ID,userId,PER_TASK_NAME,taskNasme,TASK_TYPE,taskType);
    }
    public List findByThreeProperty(String propertyName1,String value1,String propertyName2, String value2,String propertyName3, String value3) {
        log.debug("finding PersonalTask instance with property: " + propertyName1
                + ", value: " + value1 + ";"+
                propertyName2 + ", value: " + value2 + ";" +
                propertyName3 + ", value: " + value3);
        try {
            String queryString = "from PersonalTask as model where model."
                    + propertyName1 + "= ?" + " and model." + propertyName2 + " = ?" + " and model." + propertyName3 + " = ?"  ;
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value1);
            queryObject.setParameter(1, value2);
            queryObject.setParameter(2, value3);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }


    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PersonalTask instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from PersonalTask as model where model."
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
        log.debug("finding all PersonalTask instances");
        try {
            String queryString = "from PersonalTask";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PersonalTask merge(PersonalTask detachedInstance) {
        log.debug("merging PersonalTask instance");
        try {
            PersonalTask result = (PersonalTask) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PersonalTask instance) {
        log.debug("attaching dirty PersonalTask instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PersonalTask instance) {
        log.debug("attaching clean PersonalTask instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
