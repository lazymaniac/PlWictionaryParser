package com.mindmap.jane.testutils;

import com.mindmap.jane.config.ApplicationProperties;

public class ApplicationPropertiesUtils {

    public static ApplicationProperties getApplicationPropertiesForTestFile(String testFilePath) {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.setWiktionaryFileName(testFilePath);
        return applicationProperties;
    }

}
