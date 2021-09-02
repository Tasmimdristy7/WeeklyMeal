package com.dristy.WeeklyMealSpring.db;

import com.dristy.WeeklyMealSpring.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;


@Repository
public class UserRepository extends CommonRepository {

    @Transactional
    public User saveOrUpdate(User user) {
        return super.saveOrUpdate(user);
    }

    @Transactional
    public Optional<User> findByUsername(String userName) {
        TypedQuery<User> query = em.createQuery("FROM User WHERE userName = :userName", User.class);
        query.setParameter("userName", userName);

        if (!query.getResultList().isEmpty()) {
            User user = query.getSingleResult();
            return Optional.ofNullable(user);
        }

        return Optional.empty();
    }
}
