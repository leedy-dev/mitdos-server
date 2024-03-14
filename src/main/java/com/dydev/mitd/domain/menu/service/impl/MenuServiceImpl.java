package com.dydev.mitd.domain.menu.service.impl;

import com.dydev.mitd.common.exception.exception.ApiException;
import com.dydev.mitd.common.exception.message.ErrorMessage;
import com.dydev.mitd.common.utils.CommonObjectUtils;
import com.dydev.mitd.domain.menu.domain.Menu;
import com.dydev.mitd.domain.menu.repository.MenuRepository;
import com.dydev.mitd.domain.menu.service.MenuService;
import com.dydev.mitd.domain.menu.service.dto.MenuRequestDto;
import com.dydev.mitd.domain.menu.service.dto.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final ModelMapper modelMapper;

    @Override
    public Menu getMenuEntityById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(String.valueOf(menuId), ErrorMessage.DATA_NOT_FOUND));
    }

    @Override
    public MenuResponseDto getMenuById(Long menuId) {
        Menu menu = getMenuEntityById(menuId);
        return modelMapper.map(menu, MenuResponseDto.class);
    }

    @Override
    public List<MenuResponseDto> getMenuList(MenuRequestDto.Search search) {
        // auth 필수
        if (CommonObjectUtils.isNull(search.getAuth())) {
            throw new ApiException("Auth is Required", ErrorMessage.INVALID_VALUE);
        }

        List<Menu> menus = menuRepository.findListWithSearch(search);

        return menus.stream()
                .map(m -> modelMapper.map(m, MenuResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long createMenu(MenuRequestDto menuRequestDto) {
        // set
        Menu menu = modelMapper.map(menuRequestDto, Menu.class);

        // apply upper menu
        Long upperMenuId = menuRequestDto.getUpperMenuId();
        if (CommonObjectUtils.nonNull(upperMenuId)) {
            Menu upperMenu = getMenuEntityById(upperMenuId);
            menu.applyUpperMenu(upperMenu);
        }

        // save
        menu = menuRepository.save(menu);

        return menu.getMenuId();
    }

    @Override
    @Transactional
    public Long updateMenu(Long menuId, MenuRequestDto menuRequestDto) {
        // get
        Menu menu = getMenuEntityById(menuId);

        // update
        modelMapper.map(menuRequestDto, menu);

        // apply upper menu
        Long upperMenuId = menuRequestDto.getUpperMenuId();

        // request - not null upperMenuId
        if (CommonObjectUtils.nonNull(upperMenuId)) {
            // change upperMenu if update request
            if (!menu.getUpperMenu().getMenuId().equals(upperMenuId)) {
                Menu upperMenu = getMenuEntityById(upperMenuId);
                menu.applyUpperMenu(upperMenu);
            }
        } else {
            // set null to upperMenu
            menu.applyUpperMenu(null);
        }

        return menu.getMenuId();
    }

    @Override
    @Transactional
    public Long deleteMenuById(Long menuId) {
        // get
        Menu menu = getMenuEntityById(menuId);

        // delete
        menuRepository.delete(menu);

        return menu.getMenuId();
    }
}
