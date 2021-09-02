package com.dristy.WeeklyMealSpring.db;

import com.dristy.WeeklyMealSpring.domain.Persistent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CommonRepository {

    @PersistenceContext(unitName = "restaurantDB")
    EntityManager em;

    public <T extends Persistent> Optional<T> find(Class<T> entityType, int entityId) {
        T result = em.find(entityType, entityId);

        return Optional.ofNullable(result);
    }

    @Transactional
    public <T extends Persistent> T saveOrUpdate(T entity) {
        if (Objects.isNull(entity.getId())) {
            entity.setCreated(new Date());
        } else {
            entity.setUpdated(new Date());
        }

        // If the Item entity is detached, merging it.
        entity = em.contains(entity) ? entity : em.merge(entity);
        em.persist(entity);

        em.flush();
        return entity;
    }

    @Transactional
    public <T extends Persistent> void remove(T entity) {
        entity = em.contains(entity) ? entity : em.merge(entity);
        em.remove(entity);

        em.flush();
    }
}
