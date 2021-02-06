package com.robosoftin.evaluation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.model
 **/

@Entity
@Table(name = "language_model")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "language_name")
	private String languageName;
	@Column(name = "language_code")
	private String languageCode;
}
