package com.dydev.mitd.domain.myinfo.repository;

import com.dydev.mitd.domain.myinfo.entity.MyInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyInfoRepository extends JpaRepository<MyInfo, Long> {

    @Query(value = "select distinct mi from MyInfo mi "
            + "where 1=1"

            , countQuery = "select count(distinct mi) from MyInfo mi "
            + "where 1=1")
    Page<MyInfo> getMyInfoWithPaging(Pageable pageable);

}
