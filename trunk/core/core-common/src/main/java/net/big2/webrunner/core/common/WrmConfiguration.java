package net.big2.webrunner.core.common;

import java.util.Map;

public class WrmConfiguration {
    private String name;
    private String description;
    private Map<String, String> urlMap;

    public WrmConfiguration(String name, String description, Map<String, String> urlMap) {
        this.name = name;
        this.description = description;
        this.urlMap = urlMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(Map<String, String> urlMap) {
        this.urlMap = urlMap;
    }
}
