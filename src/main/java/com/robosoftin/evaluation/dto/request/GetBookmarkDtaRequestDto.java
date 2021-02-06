package com.robosoftin.evaluation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.dto.request
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookmarkDtaRequestDto {

	@NotEmpty
	private String uniqueUserId;
}
