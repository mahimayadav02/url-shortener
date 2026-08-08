package com.mahima.url_shortener.service;

import com.mahima.url_shortener.model.Url;
import com.mahima.url_shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url createShortUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode("temp");
        Url saved = urlRepository.save(url);

        String shortCode = encodeBase62(saved.getId());
        saved.setShortCode(shortCode);
        return urlRepository.save(saved);
    }

    public Url getByShortCode(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
        url.setClickCount(url.getClickCount() + 1);
        return urlRepository.save(url);
    }

    public List<Url> getAllUrls() {
        return urlRepository.findAllByOrderByIdDesc();
    }

    public void deleteUrl(Long id) {
        urlRepository.deleteById(id);
    }

    private String encodeBase62(Long id) {
        String base62Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        long num = id;
        if (num == 0) return "0";
        while (num > 0) {
            int rem = (int) (num % 62);
            sb.append(base62Chars.charAt(rem));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}