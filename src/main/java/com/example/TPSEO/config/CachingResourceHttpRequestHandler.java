/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.config;

import com.google.api.client.util.IOUtils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 *
 * @author Ari
 */
public class CachingResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        Resource resource = super.getResource(request);
        if (resource != null) {
            String contentType = getServletContext().getMimeType(resource.getFilename());
            if (contentType != null && contentType.startsWith("text/css")) {
                return new CachedResource(resource);
            }
        }
        return resource;
    }
}

