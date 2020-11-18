package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.databaseEntity.PreTaskUser;

/**
 * A data access object (DAO) providing persistence and search support for Task
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see
 * @author MyEclipse Persistence Tools
 */

public class PreTaskUserDAO extends BaseDAO {
    private static final Logger log = LoggerFactory.getLogger(PreTaskUserDAO.class);
    // property constants

    public void save(PreTaskUser transientInstance) {
        log.debug("saving PreTaskUser instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PreTaskUser persistentInstance) {
        log.debug("deleting PreTaskUser instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PreTaskUser findById(String id) {
        log.debug("getting PreTaskUser instance with id: " + id);
        try {
            PreTaskUser instance = (PreTaskUser) getSession().get("entity.databaseEntity.PreTaskUser", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PreTaskUser instance) {
        log.debug("finding PreTaskUser instance by example");
        try {
            List results = getSession().createCriteria("entity.databaseEntity.PreTaskUser")
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PreTaskUser instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from PreTaskUser as model where model."
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
        log.debug("finding all PreTaskUser instances");
        try {
            String queryString = "from PreTaskUser";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PreTaskUser merge(PreTaskUser detachedInstance) {
        log.debug("merging PreTaskUser instance");
        try {
            PreTaskUser result = (PreTaskUser) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PreTaskUser instance) {
        log.debug("attaching dirty PreTaskUser instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PreTaskUser instance) {
        log.debug("attaching clean PreTaskUser instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}