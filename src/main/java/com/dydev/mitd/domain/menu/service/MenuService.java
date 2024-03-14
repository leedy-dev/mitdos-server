package com.dydev.mitd.domain.menu.service;

import com.dydev.mitd.domain.menu.domain.Menu;
import com.dydev.mitd.domain.menu.service.dto.MenuRequestDto;
import com.dydev.mitd.domain.menu.service.dto.MenuResponseDto;

import java.util.List;

public interface MenuService {

    Menu getMenuEntityById(Long menuId);

    MenuResponseDto getMenuById(Long menuId);

    List<MenuResponseDto> getMenuList(MenuRequestDto.Search search);

    Long createMenu(MenuRequestDto menuRequestDto);

    Long updateMenu(Long menuId, MenuRequestDto menuRequestDto);

    Long deleteMenuById(Long menuId);

}
