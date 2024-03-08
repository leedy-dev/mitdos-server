package com.dydev.mitd.domain.myinfo.service;

import com.dydev.mitd.common.exception.ApiException;
import com.dydev.mitd.domain.myinfo.entity.MyInfo;
import com.dydev.mitd.domain.myinfo.repository.MyInfoRepository;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoRequestDto;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoResponseDto;
import com.dydev.mitd.domain.myinfo.service.impl.MyInfoServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyInfoServiceTest {

    @Mock
    private MyInfoRepository myInfoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MyInfoServiceImpl myInfoService;

    private static final MyInfo TEST_MY_INFO = MyInfo.builder()
            .nameKor("김테스트")
            .nameEng("Test Kim")
            .email("test@test.com")
            .notice("notice test")
            .introduction("introduction test")
            .build();

    @Test
    @DisplayName("MyInfo 조회 성공 by id")
    void findByIdSuccessTest() {
        // given
        doReturn(Optional.of(TEST_MY_INFO)).when(myInfoRepository).findById(any(Long.class));

        // when
        MyInfo myInfo = myInfoService.getMyInfoEntityById(1L);

        // then
        assertThat(myInfo.getNameKor()).isEqualTo(TEST_MY_INFO.getNameKor());
    }

    @Test
    @DisplayName("MyInfo 조회 실패 by id")
    void findByIdFailTest() {
        // given
        doReturn(Optional.empty()).when(myInfoRepository).findById(any(Long.class));

        // when, then
        assertThatThrownBy(() -> myInfoService.getMyInfoEntityById(1L)).isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("MyInfo Response 조회 by id")
    void findResponseByIdTest() {
        // given
        doReturn(Optional.of(TEST_MY_INFO)).when(myInfoRepository).findById(anyLong());

        // when
        MyInfoResponseDto responseDto = myInfoService.getMyInfoById(0L);

        // then
        verify(modelMapper, times(1)).map(any(MyInfo.class), eq(MyInfoResponseDto.class));
    }

    @Test
    @DisplayName("MyInfo 등록 테스트")
    void createMyInfoTest() {
        // given
        doReturn(TEST_MY_INFO).when(myInfoRepository).save(any(MyInfo.class));
        doReturn(TEST_MY_INFO).when(modelMapper).map(any(MyInfoRequestDto.class), eq(MyInfo.class));

        // when
        Long id = myInfoService.createMyInfo(new MyInfoRequestDto());

        // then
        assertThat(id).isEqualTo(0L);
    }

    @Test
    @DisplayName("MyInfo 수정 테스트")
    void updateMyInfoTest() {
        // given
        doReturn(Optional.of(TEST_MY_INFO)).when(myInfoRepository).findById(anyLong());

        // when
        Long id = myInfoService.updateMyInfo(0L, new MyInfoRequestDto());

        // then
        assertThat(id).isEqualTo(0L);
    }

    @Test
    @DisplayName("MyInfo 삭제 테스트")
    void deleteMyInfoTest() {
        // given
        doReturn(Optional.of(TEST_MY_INFO)).when(myInfoRepository).findById(anyLong());

        // when
        Long id = myInfoService.deleteMyInfoById(0L);

        // then
        verify(myInfoRepository, times(1)).delete(any(MyInfo.class));
    }

}
