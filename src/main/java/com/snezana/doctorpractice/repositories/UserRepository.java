package com.snezana.doctorpractice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.snezana.doctorpractice.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);	
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleType='ADMIN'")
	public List<User> findAllAdmins();
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleType='DOCTOR'")
	public List<User> findAllDoctors();
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleType='USER'")
	public List<User> findAllPatients();
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleType='USER' AND NOT u.username='unknown'")
	public List<User> findAllValidPatients();

}
