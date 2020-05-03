package org.apache.airavata.mft.transport.gdrive;

import static org.junit.Assert.*;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import org.apache.airavata.mft.core.ResourceMetadata;
import org.apache.airavata.mft.resource.server.backend.ResourceBackend;
import org.apache.airavata.mft.resource.server.handler.ResourceServiceHandler;
import org.apache.airavata.mft.transport.gdrive.GDriveMetadataCollector;
import org.junit.Test;
import org.lognet.springboot.grpc.autoconfigure.GRpcServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest(classes = {TestConfiguration.class})
@ContextConfiguration(classes = {TestConfiguration.class})
public class GDriveMetadataCollectorTest {

   @Autowired
    private GDriveMetadataCollector gDriveMetadataCollector ;//s=new GDriveMetadataCollector();

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void init() {
    }

    @org.junit.Test
    public void getGetResourceMetadata() throws Exception {
        gDriveMetadataCollector.init("localhost",7002,"localhost", 7003);
        ResourceMetadata resourceMetadata= gDriveMetadataCollector.getGetResourceMetadata("gdrive-file","gdrive-cred");
        assertNotNull(resourceMetadata);
    }

    @org.junit.Test
    public void isAvailable() {
    }
}