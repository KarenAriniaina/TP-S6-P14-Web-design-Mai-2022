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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author Ari
 */
public class CachedResource extends ByteArrayResource {

    public CachedResource(Resource resource) throws IOException {
        super(org.apache.commons.io.IOUtils.toByteArray(resource.getInputStream()));
    }

    @Override
    public long lastModified() throws IOException {
        return -1;
    }

    @Override
    public File getFile() throws IOException {
        return null;
    }

    @Override
    public URL getURL() throws IOException {
        return null;
    }

    @Override
    public URI getURI() throws IOException {
        return null;
    }
}
