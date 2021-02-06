package com.robosoftin.evaluation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.model
 **/

@Entity
@Table(name = "user_model")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "preffered_language")
	private String prefferedLanguage;
	@Column(name = "password")
	private String password;
	@Column(name = "unique_user_id")
	private String uniqueUserId;
	@Column(name = "is_registered")
	private boolean isRegistered;
	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;


}
