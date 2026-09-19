package br.com.palm.devshowcase.repository;

import br.com.palm.devshowcase.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}