package com.dydev.mitd.domain.menu.repository;

import com.dydev.mitd.domain.menu.domain.Menu;
import com.dydev.mitd.domain.menu.service.dto.MenuRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "select distinct m from Menu m "
            + "left join fetch m.upperMenu um "
            + "left join fetch m.subMenus sm "
            + "where m.auth = :#{#search.auth} "
            + "and (:#{#search.level} is null or (m.level = :#{#search.level})) "
            + "and (:#{#search.upperMenuId} is null or (m.upperMenu is not null and m.upperMenu.menuId = :#{#search.upperMenuId}))")
    List<Menu> findListWithSearch(@Param("search") MenuRequestDto.Search search);

}
