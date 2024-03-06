package com.dydev.mitd.domain.myinfo.service;

import com.dydev.mitd.domain.myinfo.service.dto.MyInfoRequestDto;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyInfoServiceTest {

    @Autowired
    private MyInfoService myInfoService;

    @Test
    @DisplayName("MyInfo 조회 by id")
    void MyInfo_조회_by_id_테스트() {
        MyInfoResponseDto myInfoResponseDto = myInfoService.getMyInfoById(3L);

        assertThat(myInfoResponseDto.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("MyInfo 등록 테스트")
    void MyInfo_등록_테스트() {
        MyInfoRequestDto myInfoRequestDto = new MyInfoRequestDto();
        myInfoRequestDto.setNameKor("이동엽");
        myInfoRequestDto.setNameEng("Dong-yeop Lee");
        myInfoRequestDto.setPhoneNum("010-9918-8293");
        myInfoRequestDto.setEmail("ldy033000@gmail.com");
        myInfoRequestDto.setNotice("notice test");
        myInfoRequestDto.setIntroduction("introduction test");
        myInfoRequestDto.setCreateUserId("tester");
        myInfoRequestDto.setUpdateUserId("tester");

        MyInfoResponseDto myInfoResponseDto = myInfoService.createMyInfo(myInfoRequestDto);

        assertThat(myInfoResponseDto.getNameKor()).isEqualTo(myInfoRequestDto.getNameKor());
    }

    @Test
    @DisplayName("MyInfo 수정 테스트")
    void MyInfo_수정_테스트() {
        MyInfoRequestDto myInfoRequestDto = new MyInfoRequestDto();
        myInfoRequestDto.setNameKor("이동엽22");
        myInfoRequestDto.setNameEng("Dong-yeop Lee");
        myInfoRequestDto.setPhoneNum("010-9918-8293");
        myInfoRequestDto.setEmail("ldy033000@gmail.com");
        myInfoRequestDto.setNotice("notice test");
        myInfoRequestDto.setIntroduction("introduction test");
        myInfoRequestDto.setCreateUserId("tester");
        myInfoRequestDto.setUpdateUserId("tester");

        myInfoService.updateMyInfo(3L, myInfoRequestDto);

        MyInfoResponseDto myInfoResponseDto = myInfoService.getMyInfoById(3L);

        assertThat(myInfoResponseDto.getNameKor()).isEqualTo("이동엽22");
    }

    @Test
    @DisplayName("MyInfo 삭제 테스트")
    void MyInfo_삭제_테스트() {
        Long result = myInfoService.deleteMyInfoById(1L);

        assertThat(result).isEqualTo(1L);
    }

}
