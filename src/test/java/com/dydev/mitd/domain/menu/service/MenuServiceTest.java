package com.dydev.mitd.domain.menu.service;

import com.dydev.mitd.common.exception.exception.ApiException;
import com.dydev.mitd.domain.menu.domain.Menu;
import com.dydev.mitd.domain.menu.repository.MenuRepository;
import com.dydev.mitd.domain.menu.service.dto.MenuRequestDto;
import com.dydev.mitd.domain.menu.service.dto.MenuResponseDto;
import com.dydev.mitd.domain.menu.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MenuServiceImpl menuService;

    @Test
    @DisplayName("Menu 조회 성공 by id")
    void findMenuByIdSuccessTest() {
        // given
        Long testMenuId = 0L;
        doReturn(Optional.of(Menu.builder().build())).when(menuRepository).findById(testMenuId);

        // when
        Menu menu = menuService.getMenuEntityById(testMenuId);

        // then
        assertThat(menu.getMenuId()).isEqualTo(testMenuId);
    }

    @Test
    @DisplayName("Menu 조회 실패 by id")
    void findMenuByIdFailTest() {
        // given
        doReturn(Optional.empty()).when(menuRepository).findById(anyLong());

        // when, then
        assertThatThrownBy(() -> menuService.getMenuEntityById(anyLong())).isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("Menu Response 조회 by id")
    void findMenuResponseByIdTest() {
        // given
        doReturn(Optional.of(Menu.builder().build())).when(menuRepository).findById(anyLong());

        // when
        menuService.getMenuById(0L);

        // then
        verify(modelMapper, times(1)).map(any(Menu.class), eq(MenuResponseDto.class));
    }

    @Test
    @DisplayName("Menu 등록 테스트")
    void createMenuTest() {
        // given
        Menu menu = Menu.builder().build();
        doReturn(menu).when(menuRepository).save(any(Menu.class));
        doReturn(menu).when(modelMapper).map(any(MenuRequestDto.class), eq(Menu.class));

        // when
        Long id = menuService.createMenu(new MenuRequestDto());

        // then
        assertThat(id).isEqualTo(0L);
    }

    @Test
    @DisplayName("Menu 수정 테스트")
    void updateMenuTest() {
        // given
        Long testMenuId = 0L;
        doReturn(Optional.of(Menu.builder().build())).when(menuRepository).findById(anyLong());

        // when
        Long id = menuService.updateMenu(testMenuId, new MenuRequestDto());

        // then
        assertThat(id).isEqualTo(testMenuId);
    }
    
    @Test
    @DisplayName("Menu 삭제 테스트")
    void deleteMenuTest() {
        // given
        doReturn(Optional.of(Menu.builder().build())).when(menuRepository).findById(anyLong());

        // when
        menuService.deleteMenuById(0L);

        // then
        verify(menuRepository, times(1)).delete(any(Menu.class));
    }

}
