package com.robosoftin.evaluation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

	@NotEmpty
	private String emailId;
	@NotEmpty
	private String password;
}
