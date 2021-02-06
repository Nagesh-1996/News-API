package com.robosoftin.evaluation.repository;

import com.robosoftin.evaluation.model.LanguageModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.repository
 **/
public interface LanguageModelRepository extends JpaRepository<LanguageModel, Integer> {
}
