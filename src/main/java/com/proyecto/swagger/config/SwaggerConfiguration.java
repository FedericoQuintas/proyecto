package com.proyecto.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration bean to set up Swagger.
 */
@Component
public class SwaggerConfiguration {
 
    @Value("${swagger.resourcePackage}")
    private String resourcePackage;
 
    @Value("${swagger.basePath}")
    private String basePath;
 
    @Value("${swagger.apiVersion}")
    private String apiVersion;
 
    public String getResourcePackage() {
        return resourcePackage;
    }
 
    public void setResourcePackage(String resourcePackage) {
        this.resourcePackage = resourcePackage;
    }
 
    public String getBasePath() {
        return basePath;
    }
 
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
 
    public String getApiVersion() {
        return apiVersion;
    }
 
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}