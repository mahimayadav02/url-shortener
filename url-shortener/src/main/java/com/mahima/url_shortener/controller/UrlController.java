package com.mahima.url_shortener.controller;

import com.mahima.url_shortener.model.Url;
import com.mahima.url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/api/shorten")
    public Url shortenUrl(@Valid @RequestBody UrlRequest request) {
        return urlService.createShortUrl(request.getOriginalUrl());
    }

    @GetMapping("/api/urls")
    public List<Url> getAllUrls() {
        return urlService.getAllUrls();
    }

    @DeleteMapping("/api/urls/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id) {
        urlService.deleteUrl(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        try {
            Url url = urlService.getByShortCode(shortCode);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", url.getOriginalUrl())
                    .build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public static class UrlRequest {
        @NotBlank(message = "URL cannot be empty")
        @Pattern(regexp = "^(https?://)[\\w.-]+(\\.[a-zA-Z]{2,})+.*$", message = "Invalid URL format")
        private String originalUrl;

        public String getOriginalUrl() { return originalUrl; }
        public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(org.springframework.web.bind.MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}