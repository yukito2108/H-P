package com.snezana.doctorpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snezana.doctorpractice.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByRoleType(String roleType);

}
