package com.dydev.mitd.domain.menu.controller;

import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.domain.menu.service.MenuService;
import com.dydev.mitd.domain.menu.service.dto.MenuRequestDto;
import com.dydev.mitd.domain.menu.service.dto.MenuResponseDto;
import com.dydev.mitd.domain.role.enums.RoleTypes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CommonApiUrls.API_PACKAGE_MENU)
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> doGetMenuById(@PathVariable("menuId") Long menuId) {
        return new ResponseEntity<>(menuService.getMenuById(menuId), HttpStatus.OK);
    }

    @GetMapping("/list/{auth}")
    public ResponseEntity<List<MenuResponseDto>> doGetMenuList(@PathVariable("auth") RoleTypes auth, MenuRequestDto.Search search) {
        // set auth
        search.setAuth(auth);
        
        return new ResponseEntity<>(menuService.getMenuList(search), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> doCreateMenu(@Valid @RequestBody MenuRequestDto menuRequestDto, BindingResult bindingResult) {
        return new ResponseEntity<>(menuService.createMenu(menuRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<Long> doUpdateMenu(
            @PathVariable("menuId") Long menuId,
            @Valid @RequestBody MenuRequestDto menuRequestDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(menuService.updateMenu(menuId, menuRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Long> doDeleteMenu(@PathVariable("menuId") Long menuId) {
        return new ResponseEntity<>(menuService.deleteMenuById(menuId), HttpStatus.OK);
    }

}
