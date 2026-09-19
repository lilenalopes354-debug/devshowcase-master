package br.com.palm.devshowcase.repository;

import br.com.palm.devshowcase.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByEmail(String email);
}