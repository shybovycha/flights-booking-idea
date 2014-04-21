package sources.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import sources.entities.AbstractEntity;

@Repository
public abstract class BaseDAO {
    public abstract EntityManager getEntityManager();

    public static Date str2date(String date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        Date dt = new Date(fmt.parseDateTime(date).toDate().getTime());

        return dt;
    }

    public <T> T find(Class<T> entityClass, int id) {
        Object entity = null;
        EntityManager entityManager = getEntityManager();

        try {
            entity = entityManager.find(entityClass, id);
        } finally {
            entityManager.close();
        }

        return entityClass.cast(entity);
    }

    public <T extends AbstractEntity> T save(T entity) {
        EntityManager entityManager = getEntityManager();

        if (entity.getId() > 0) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }

        entityManager.flush();

        return entity;
    }

    public <T> List<T> query(Class<T> entityClass, String query) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<T> q = entityManager.createQuery(query, entityClass);
        List<T> entities = null;

        try {
            entities = q.getResultList();
        } finally {
            entityManager.close();
        }

        return entities;
    }

    public <T> int updateQuery(String query) {
        EntityManager entityManager = getEntityManager();
        int results = 0;

        results = entityManager.createQuery(query).executeUpdate();

        return results;
    }

    public <T> List<T> all(Class<T> entityClass) {
        EntityManager entityManager = getEntityManager();
        String query = String.format("SELECT e FROM %s e", entityClass.getSimpleName());
        TypedQuery<T> q = entityManager.createQuery(query, entityClass);
        List<T> entities = null;

        try {
            entities = q.getResultList();
        } finally {
            entityManager.close();
        }

        return entities;
    }

    public <T> void destroy(Class<T> entityClass, int id) {
        EntityManager entityManager = getEntityManager();

        T entity = entityManager.find(entityClass, id);

        entityManager.merge(entity);

        entityManager.remove(entity);

        entityManager.flush();
    }

    public <T> void destroy(Class<T> entityClass, T entity) {
        EntityManager entityManager = getEntityManager();

        if (!entityManager.contains(entity)) {
            entity = entityManager.merge(entity);
        }

        entityManager.remove(entity);
    }

    public <T> void destroyAll(Class<T> entityClass) {
        List<T> entities = all(entityClass);

        for (T entity : entities) {
            destroy(entityClass, entity);
        }
    }
}
