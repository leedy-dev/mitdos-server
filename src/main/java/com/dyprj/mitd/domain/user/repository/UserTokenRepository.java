package com.dyprj.mitd.domain.user.repository;

import com.dyprj.mitd.domain.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {

}
