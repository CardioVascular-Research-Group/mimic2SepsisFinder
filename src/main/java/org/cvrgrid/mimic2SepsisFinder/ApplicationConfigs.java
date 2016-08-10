package org.cvrgrid.mimic2SepsisFinder;


import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Annotated application configuration.
 * Created by sgranite on 8/5/16.
 */
@Configuration
@ComponentScan(basePackages = "org.cvrgrid.mimic2SepsisFinder")
public class ApplicationConfigs {

    @Bean
    public String version() {
        return "v1.0, updated 8/9/2016";
    }

    @Bean
    public String dateFormat() {
        return "yyyy-MM-dd HH:mm";
    }

    @Bean
    public org.apache.commons.configuration2.Configuration configurationFile() throws ConfigurationException {
        Configurations configurations = new Configurations();
        return configurations.ini(new File("Mimic2SepsisFinder.conf"));
    }

    @Bean
    public String fileName(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("file-name");
    }

    @Bean
    public String severeFileName(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("severe-file-name");
    }

    @Bean
    public String shockFileName(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("shock-file-name");
    }
    
    @Bean
    public String tableFile(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("table-file");
    }
    
    @Bean
    public String subjectsWithSepsisFile(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("subjectswithsepsis-file");
    }

    @Bean
    public String subjectsWithSevereFile(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("subjectswithsevere-file");
    }
    
    @Bean
    public String subjectsWithShockFile(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("subjectswithshock-file");
    }

    @Bean
    public String physionetMatchedDir(org.apache.commons.configuration2.Configuration configurationFile) { // Chaining dependency.
        return configurationFile.getString("physionetmatched-dir");
    }

}
