package org.apache.airavata.mft.transport.gdrive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    GDriveMetadataCollector gDriveMetadataCollector(){
        return new GDriveMetadataCollector();
    }
}
