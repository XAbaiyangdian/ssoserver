package com.xaicif.sso.repo;

import com.xaicif.sso.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByMobile(String mobile);
    User findByUserId(String userId);
}
