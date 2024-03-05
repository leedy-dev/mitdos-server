package com.dydev.mitd.domain.user.controller;

import com.dydev.mitd.common.constants.CommonApiUrls;
import com.dydev.mitd.domain.user.service.dto.UserRequestDto;
import com.dydev.mitd.domain.user.service.dto.UserResponseDto;
import com.dydev.mitd.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CommonApiUrls.API_PACKAGE_USER)
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> doGetUserByUserId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserResponseDto>> doGetUserListWithSearchAndPaging(UserRequestDto.Search search, Pageable pageable) {
        return new ResponseEntity<>(userService.getUserListWithSearchAndPaging(search, pageable), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> doCreateUser(
            @Validated @RequestBody UserRequestDto userRequestDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> doDeleteUser(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

}
