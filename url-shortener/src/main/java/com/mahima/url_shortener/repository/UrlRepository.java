package com.mahima.url_shortener.repository;

import com.mahima.url_shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
    List<Url> findAllByOrderByIdDesc();
}