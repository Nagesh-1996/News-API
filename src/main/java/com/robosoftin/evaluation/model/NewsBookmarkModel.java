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
@Table(name = "news_bookmark_model")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsBookmarkModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "user_id")
	private int userId;
	@Column(name = "news_source")
	private String newsSource;
	@Column(name = "author")
	private String author;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "url")
	private String url;
	@Column(name = "url_to_image")
	private String urlToImage;
	@Column(name = "published_at")
	private String publishedAt;
	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;
}
