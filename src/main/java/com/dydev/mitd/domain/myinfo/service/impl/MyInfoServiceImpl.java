package com.dydev.mitd.domain.myinfo.service.impl;

import com.dydev.mitd.common.exception.ApiException;
import com.dydev.mitd.common.exception.ErrorMessage;
import com.dydev.mitd.domain.myinfo.entity.MyInfo;
import com.dydev.mitd.domain.myinfo.repository.MyInfoRepository;
import com.dydev.mitd.domain.myinfo.service.MyInfoService;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoRequestDto;
import com.dydev.mitd.domain.myinfo.service.dto.MyInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyInfoServiceImpl implements MyInfoService {

    private final MyInfoRepository myInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    public MyInfo getMyInfoEntityById(Long id) {
        Optional<MyInfo> myInfoOp = myInfoRepository.findById(id);

        return myInfoOp.orElseThrow(() -> new ApiException(String.valueOf(id), ErrorMessage.DATA_NOT_FOUND));
    }

    @Override
    public MyInfoResponseDto getMyInfoById(Long id) {
        return modelMapper.map(getMyInfoEntityById(id), MyInfoResponseDto.class);
    }

    @Override
    public Page<MyInfoResponseDto> getMyInfoList(Pageable pageable) {
        Page<MyInfo> myInfoList = myInfoRepository.getMyInfoWithPaging(pageable);

        return new PageImpl<>(myInfoList.stream()
                .map(myInfo -> modelMapper.map(myInfo, MyInfoResponseDto.class))
                .collect(Collectors.toList()),
                myInfoList.getPageable(),
                myInfoList.getTotalElements());
    }

    @Override
    @Transactional
    public MyInfoResponseDto createMyInfo(MyInfoRequestDto myInfoRequestDto) {
        MyInfo myInfo = modelMapper.map(myInfoRequestDto, MyInfo.class);
        return modelMapper.map(
                myInfoRepository.save(myInfo),
                MyInfoResponseDto.class);
    }

    @Override
    @Transactional
    public MyInfoResponseDto updateMyInfo(Long id, MyInfoRequestDto myInfoRequestDto) {
        MyInfo myInfo = getMyInfoEntityById(id);

        modelMapper.map(myInfoRequestDto, myInfo);

        return modelMapper.map(myInfo, MyInfoResponseDto.class);
    }

    @Override
    @Transactional
    public Long deleteMyInfoById(Long id) {
        myInfoRepository.deleteById(id);
        return id;
    }
}