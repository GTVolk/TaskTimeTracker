package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.EntityItem;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext(unitName = "tttPU")
    protected EntityManager em;

    private final Class<T> type;

    public GenericDaoImpl(Class<T> type1) {
        this.type = type1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public T find(ID id) {
        return (T) em.find(type, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(T o) {
        em.persist(o);
        em.flush();

        if (o instanceof EntityItem) {

            EntityItem<ID> item = (EntityItem<ID>) o;
            ID id = item.getId();
            logger.info("The " + o.getClass().getName() + " record with ID=" + id + " has been inserted");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T merge(T o) {

        o = em.merge(o);
        return o;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(T o) {
        o = merge(o);
        em.remove(o);

        if (o instanceof EntityItem) {

            EntityItem<ID> item = (EntityItem<ID>) o;
            ID id = item.getId();
            logger.warn("The " + o.getClass().getName() + " record with ID=" + id + " has been deleted");
        }
    }
}
