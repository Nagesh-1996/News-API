package com.robosoftin.evaluation.repository;

import com.robosoftin.evaluation.model.NewsBookmarkModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.repository
 **/
public interface NewsBookmarkRepository extends JpaRepository<NewsBookmarkModel, Integer> {

	List<NewsBookmarkModel> findAllByUserId(int id);
}
