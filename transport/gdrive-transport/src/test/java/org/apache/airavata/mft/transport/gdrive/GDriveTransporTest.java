package org.apache.airavata.mft.transport.gdrive;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import org.apache.airavata.mft.core.CircularStreamingBuffer;
import org.apache.airavata.mft.core.ConnectorContext;
import org.apache.airavata.mft.core.DoubleStreamingBuffer;
import org.apache.airavata.mft.core.ResourceMetadata;
import org.apache.airavata.mft.resource.client.ResourceServiceClient;
import org.apache.airavata.mft.resource.server.ResourceServiceApplication;
import org.apache.airavata.mft.resource.server.backend.ResourceBackend;
import org.apache.airavata.mft.resource.server.backend.file.FileBasedResourceBackend;
import org.apache.airavata.mft.resource.server.handler.ResourceServiceHandler;
import org.apache.airavata.mft.resource.service.GDriveResource;
import org.apache.airavata.mft.resource.service.GDriveResourceGetRequest;
import org.apache.airavata.mft.resource.service.ResourceServiceGrpc;
import org.apache.airavata.mft.secret.client.SecretServiceClient;
import org.apache.airavata.mft.secret.server.SecretServiceApplication;
import org.apache.airavata.mft.secret.service.GDriveSecret;
import org.apache.airavata.mft.secret.service.GDriveSecretGetRequest;
import org.apache.airavata.mft.secret.service.SecretServiceGrpc;
import org.apache.airavata.mft.transport.gdrive.GDriveMetadataCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lognet.springboot.grpc.autoconfigure.GRpcServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;


@SpringBootTest(webEnvironment = NONE)
@ContextConfiguration(classes = {org.apache.airavata.mft.transport.gdrive.TestConfiguration.class,GDriveMetadataCollector.class})
@RunWith(SpringJUnit4ClassRunner.class)

public class GDriveTransporTest {


//    @LocalServerPort
//    private int resourcePort;
//
//    @LocalServerPort
//    private int secretPort;



    @Autowired
    private GDriveMetadataCollector gDriveMetadataCollector ;

//   @Autowired
//    HttpTransport transport;

    @Autowired
    JsonFactory jsonFactory;


    @Value("${resource.server.port}")
    int resourcePort;

    @Value("${service.server.port}")
    int secretPort;

    @Value("${resource.server.host}")
    String resourceServiceHost;

    @Value("${service.server.host}")
    String secretServiceHost;


    // @Autowired
    private ResourceServiceGrpc.ResourceServiceBlockingStub resourceServiceBlockingStub;

    //@Autowired
    private SecretServiceGrpc.SecretServiceBlockingStub secretServiceBlockingStub;

    // @Autowired
    GDriveResourceGetRequest gDriveResourceGetRequest;

    //@Autowired
    GDriveSecretGetRequest gDriveSecretGetRequest;

    @Autowired
    FileBasedResourceBackend fileBasedResourceBackend;

    @Autowired
    GDriveSender gDriveSender;

//    @MockBean
//    ConnectorContext connectorContext;






    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void init() {
    }

    @org.junit.Test
    public void getGetResourceMetadata() throws Exception {


        resourceServiceBlockingStub= ResourceServiceClient.buildClient(resourceServiceHost,resourcePort);
        GDriveResource gDriveResource= resourceServiceBlockingStub.getGDriveResource(gDriveResourceGetRequest.newBuilder().setResourceId("gdrive-file").build());

        secretServiceBlockingStub= SecretServiceClient.buildClient(secretServiceHost,secretPort);
        GDriveSecret gDriveSecret= secretServiceBlockingStub.getGDriveSecret(gDriveSecretGetRequest.newBuilder().setSecretId("gdrive-cred").build());

        gDriveMetadataCollector.init(resourceServiceHost,resourcePort,secretServiceHost, secretPort);
        ResourceMetadata resourceMetadata= gDriveMetadataCollector.getGetResourceMetadata(gDriveResource.getResourceId(),gDriveSecret.getSecretId());
        assertNotNull(resourceMetadata);

    }

    @org.junit.Test
    public void isAvailable() throws Exception {
        gDriveMetadataCollector.init(resourceServiceHost,resourcePort,secretServiceHost, secretPort);
        boolean resourceAvailable=gDriveMetadataCollector.isAvailable("gdrive-file","gdrive-cred");

        assertEquals(true,resourceAvailable);

        try {
            boolean resourceUnAvailable=gDriveMetadataCollector.isAvailable("NSA-file","gdrive-cred");

            assertEquals(true,resourceUnAvailable);
        } catch(Exception ex) {
            assertFalse("No such resource",false);
        }

    }

    @org.junit.Test
    public void getGDriveResourceTest() throws Exception {

        // Get the resources by hitting the getDriveResource API
        fileBasedResourceBackend.setResourceFile("resources.json");
        GDriveResourceGetRequest request= GDriveResourceGetRequest.newBuilder().build();
        Optional<GDriveResource> gDriveResource2=fileBasedResourceBackend.getGDriveResource(request);
        System.out.println(gDriveResource2);
        assertNotNull(gDriveResource2);

    }


//    @org.junit.Test
//    public void getGDriveSenderTest() throws Exception {
//        gDriveSender.init("gdrive-file","gdrive-cred",resourceServiceHost,resourcePort,secretServiceHost,secretPort);
//
//       ConnectorContext connectorContext= new ConnectorContext();
//
////       connectorContext.setTransferId("829420482094709489080408");
////       connectorContext.setStreamBuffer(new DoubleStreamingBuffer());
////       gDriveSender.startStream(connectorContext);
////        TransferCommand
//
//
//    }
}