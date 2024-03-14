package com.dydev.mitd.domain.user.repository;

import com.dydev.mitd.domain.user.entity.User;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    @Query(value = "select distinct u from User u "
            + "where 1=1"
            // userId
            + "and (:#{#search.userId} is null or u.userId = :#{#search.userId}) "
            // userName
            + "and (:#{#search.userName} is null or u.name = :#{#search.userName})"

            , countQuery = "select count(distinct u) from User u "
            + "where 1=1"
            // userId
            + "and (:#{#search.userId} is null or u.userId = :#{#search.userId}) "
            // userName
            + "and (:#{#search.userName} is null or u.name = :#{#search.userName})"
    )
    Page<User> findUserListWithSearchAndPaging(@Param("search") UserRequestDto.Search search, Pageable pageable);

}
