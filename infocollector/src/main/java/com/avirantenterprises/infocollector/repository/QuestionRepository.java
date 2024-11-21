package com.avirantenterprises.infocollector.repository;

import com.avirantenterprises.infocollector.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
