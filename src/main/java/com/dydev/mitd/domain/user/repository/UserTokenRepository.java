package com.dydev.mitd.domain.user.repository;

import com.dydev.mitd.domain.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {

}
