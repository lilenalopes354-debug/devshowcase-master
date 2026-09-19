package br.com.palm.devshowcase.repository;

import br.com.palm.devshowcase.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}