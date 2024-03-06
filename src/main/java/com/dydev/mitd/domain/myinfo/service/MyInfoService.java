package com.dydev.mitd.domain.myinfo.service;

import com.dydev.mitd.domain.myinfo.entity.MyInfo;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoRequestDto;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyInfoService {

    MyInfo getMyInfoEntityById(Long id);

    MyInfoResponseDto getMyInfoById(Long id);

    Page<MyInfoResponseDto> getMyInfoList(Pageable pageable);

    MyInfoResponseDto createMyInfo(MyInfoRequestDto myInfoRequestDto);

    MyInfoResponseDto updateMyInfo(Long id, MyInfoRequestDto myInfoRequestDto);

    Long deleteMyInfoById(Long id);

}
