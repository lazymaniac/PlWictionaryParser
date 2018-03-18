package com.mindmap.jane.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Parser.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String wiktionaryFileName;

    public String getWiktionaryFileName() {
        return wiktionaryFileName;
    }

    public void setWiktionaryFileName(String wiktionaryFileName) {
        this.wiktionaryFileName = wiktionaryFileName;
    }
}
