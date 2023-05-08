/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ari
 */
@Service
public class ImageService {

    @Autowired
    private CacheManager cacheManager;

    @Cacheable("imageCache")
    public boolean isImageCached(String imageUrl) {
        Cache cache = cacheManager.getCache("imageCache");
        if (cache != null) {
            return cache.get(imageUrl) != null;
        }
        return false;
    }
}
