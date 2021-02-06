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
public class BookMarkNewsRequestDto {

	@NotEmpty
	private String author;
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	@NotEmpty
	private String url;
	@NotEmpty
	private String urlToImage;
	@NotEmpty
	private String publishedAt;
	@NotEmpty
	private String newsSource;
	@NotEmpty
	private String uniqueUserId;

}
