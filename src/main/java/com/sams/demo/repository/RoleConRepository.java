package com.sams.demo.repository;

import com.sams.demo.model.entity.RoleCon;
import com.sams.demo.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleConRepository extends JpaRepository<RoleCon, Long> {

    RoleCon findByRole(Role role);
}