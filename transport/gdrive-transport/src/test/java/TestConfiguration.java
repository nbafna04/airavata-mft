package org.apache.airavata.mft.transport.gdrive;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.airavata.mft.resource.server.backend.file.FileBasedResourceBackend;
import org.apache.airavata.mft.resource.service.GDriveResource;
import org.apache.airavata.mft.resource.service.GDriveResourceGetRequest;
import org.apache.airavata.mft.resource.service.ResourceServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    GDriveMetadataCollector gDriveMetadataCollector(){
        return new GDriveMetadataCollector();
    }

    @Bean
    JsonFactory jsonFactory(){
        return new JacksonFactory();
    }


    @Bean
    FileBasedResourceBackend fileBasedResourceBackend(){
        return new FileBasedResourceBackend();
    }

    @Bean
    GDriveSender gDriveSender(){
        return  new GDriveSender();
    }

}
