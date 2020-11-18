package dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.databaseEntity.Node;

/**
 * A data access object (DAO) providing persistence and search support for Node
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see
 * @author MyEclipse Persistence Tools
 */

public class NodeDAO extends BaseDAO {
    private static final Logger log = LoggerFactory.getLogger(NodeDAO.class);
    // property constants

    private static String PARENT_ID = "parentId";
    private static String DATA_ID = "dataId";

    public void save(Node transientInstance) {
        log.debug("saving Node instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Node persistentInstance) {
        log.debug("deleting Node instance");
        try {
            String sql = " delete from node "
                    + " where node.NODE_ID = '" + persistentInstance.getId() + "'";
            getSession().createSQLQuery(sql).executeUpdate();

//            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Node findById(String id) {
        log.debug("getting Node instance with id: " + id);
        try {
            Node instance = (Node) getSession().get("entity.databaseEntity.Node", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Node instance) {
        log.debug("finding Node instance by example");
        try {
            List results = getSession().createCriteria("entity.databaseEntity.Node")
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    public List findByParentId(Object parentId) {
        return findByProperty(PARENT_ID, parentId);
    }
    public List findByDataId(Object dataId) {
        return findByProperty(DATA_ID, dataId);
    }
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Node instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Node as model where model."
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
        log.debug("finding all Node instances");
        try {
            String queryString = "from Node";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Node merge(Node detachedInstance) {
        log.debug("merging Node instance");
        try {
            Node result = (Node) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Node instance) {
        log.debug("attaching dirty Node instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Node instance) {
        log.debug("attaching clean Node instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}