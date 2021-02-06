package com.robosoftin.evaluation.repository;

import com.robosoftin.evaluation.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.repository
 **/
public interface UserRepository extends JpaRepository<UserModel, Integer> {

	boolean existsByEmailId(String emailId);

	UserModel findByEmailId(String trim);

	UserModel findByUniqueUserId(String uniqueUserId);
}
