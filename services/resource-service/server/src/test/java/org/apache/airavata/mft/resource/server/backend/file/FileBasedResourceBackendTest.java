package org.apache.airavata.mft.resource.server.backend.file;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
//import org.apache.airavata.mft.core.ResourceMetadata;
import org.apache.airavata.mft.resource.server.backend.ResourceBackend;
import org.apache.airavata.mft.resource.server.handler.ResourceServiceHandler;
//import org.apache.airavata.mft.transport.gdrive.GDriveMetadataCollector;
import org.apache.airavata.mft.resource.service.GDriveResource;
import org.apache.airavata.mft.resource.service.GDriveResourceGetRequest;
import org.junit.Test;
import org.lognet.springboot.grpc.autoconfigure.GRpcServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.junit.Assert.*;

public class FileBasedResourceBackendTest {
//    @LocalServerPort
//    private int port;


    @Autowired
    static FileBasedResourceBackend fileBasedResourceBackend;



    @Autowired
    private ResourceBackend backend;

    @Autowired
    public ResourceServiceHandler resourceServiceHandler;

//    @GRpcService
//    ResourceServiceGrpc resourceServiceGrpc;

    protected ManagedChannel managedChannel;

    @Autowired
    protected ApplicationContext context;

  //  private ResourceServiceGrpc.ResourceServiceBlockingStub resourceServiceBlockingStub;

    @Autowired
    GRpcServerProperties gRpcServerProperties;

    InProcessChannelBuilder inProcChannel;

    //@Autowired
  //  GDriveMetadataCollector gDriveMetadataCollector= new GDriveMetadataCollector();


    @Test
    public void getGDriveResource() throws Exception {
        fileBasedResourceBackend = new FileBasedResourceBackend();
        fileBasedResourceBackend.setResourceFile("resources.json");
        GDriveResourceGetRequest request= GDriveResourceGetRequest.newBuilder().build();
        Optional<GDriveResource> gDriveResource=fileBasedResourceBackend.getGDriveResource(request);
        System.out.println(gDriveResource);
        assertNotNull(gDriveResource);
       // assertThat(gDriveResource.isEmpty());

    }

    @Test
    public void createGDriveResource() {
    }

    @Test
    public void updateGDriveResource() {
    }

    @Test
    public void deleteGDriveResource() {
    }


    @Test
    public void RandomTest() throws Exception {
//        gDriveMetadataCollector.init("localhost",7002,"localhost", 7003);
//        ResourceMetadata resourceMetadata= gDriveMetadataCollector.getGetResourceMetadata("gdrive-file","gdrive-cred");
//        assertNotNull(resourceMetadata);
    }
}