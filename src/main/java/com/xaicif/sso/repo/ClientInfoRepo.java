package com.xaicif.sso.repo;

import com.xaicif.sso.entity.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInfoRepo extends JpaRepository<ClientInfo, Long> {
    ClientInfo findByClientCode(String clientCode);
}
