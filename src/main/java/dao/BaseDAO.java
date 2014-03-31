package dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.*;

import entities.AbstractEntity;

public class BaseDAO {
    protected static final String UNIT_NAME = "flights";
    protected static EntityManagerFactory factory;
    protected static EntityManager em;

    static {
        factory = Persistence.createEntityManagerFactory(UNIT_NAME);
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static Date str2date(String date) {
        Date dt = null;

        try {
            dt = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime());
        } catch (Exception e) {
        }

        return dt;
    }

    public static <T> T find(Class<T> entityClass, int id) {
        Object entity = null;
        EntityManager entityManager = getEntityManager();

        try {
            entity = entityManager.find(entityClass, id);
        } finally {
            entityManager.close();
        }

        return entityClass.cast(entity);
    }

    public static <T extends AbstractEntity> T save(T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        if (entity.getId() < 1) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }

        entityManager.flush();
        tx.commit();

        return entity;
    }

    public static <T> List<T> query(Class<T> entityClass, String query) {
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

    public static <T> int updateQuery(String query) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        int results = 0;

        tx.begin();
        results = entityManager.createQuery(query).executeUpdate();
        tx.commit();

        return results;
    }

    public static <T> List<T> all(Class<T> entityClass) {
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

    public static <T> void destroy(Class<T> entityClass, int id) {
        EntityManager entityManager = getEntityManager();
        T entity = entityManager.find(entityClass, id);
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();
        entityManager.remove(entity);
        tx.commit();
    }

    public static <T> void destroy(Class<T> entityClass, T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        if (!entityManager.contains(entity)) {
            entity = entityManager.merge(entity);
        }

        entityManager.remove(entity);
        tx.commit();
    }

    public static <T> void destroyAll(Class<T> entityClass) {
        List<T> entities = all(entityClass);

        for (T entity : entities) {
            destroy(entityClass, entity);
        }
    }
}
