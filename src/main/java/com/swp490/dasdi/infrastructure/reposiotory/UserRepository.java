package com.swp490.dasdi.infrastructure.reposiotory;

import com.swp490.dasdi.infrastructure.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "where u.deleteFlag = ?2 " +
            "and (u.fullName like %?1% or u.email like %?1%)")
    List<User> findAllPaging(String keyword, boolean deleteFlag, Pageable pageable);

    @Query("select u from User u " +
            "where u.id = ?1 " +
            "and u.deleteFlag = ?2")
    User findById(long id, boolean deleteFlag);

    @Query("select u from User u " +
            "left join fetch u.role " +
            "left join fetch u.team " +
            "where u.email = ?1 and u.deleteFlag = ?2")
    User findByEmail(String email, boolean deleteFlag);

    @Query("select u from User u " +
            "where u.phoneNumber = ?1 and u.deleteFlag = ?2")
    User findByPhoneNumber(String phoneNumber, boolean deleteFlag);
}
