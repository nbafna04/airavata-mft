package org.apache.airavata.mft.resource.server.handler;

import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.airavata.mft.resource.server.backend.ResourceBackend;
import org.apache.airavata.mft.resource.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestResourceServiceHandler {

    @Test
    public void testGetGDriveResource() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceGetRequest resourceRequest = Mockito.mock(GDriveResourceGetRequest.class);
        StreamObserver<GDriveResource> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);
        GDriveResource gdriveStorage = Mockito.mock(GDriveResource.class);

        try {
            Mockito.when(resourceBackend.getGDriveResource(resourceRequest)).thenReturn(Optional.of(gdriveStorage));
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        resourceServiceHandler.getGDriveResource(resourceRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(1)).onNext(gdriveStorage);
        Mockito.verify(streamObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    public void testGetGDriveResource_EmptyStorage() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceGetRequest GDriveResourceGetRequest = Mockito.mock(GDriveResourceGetRequest.class);
        StreamObserver<GDriveResource> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.getGDriveResource(GDriveResourceGetRequest)).thenReturn(Optional.empty());
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        resourceServiceHandler.getGDriveResource(GDriveResourceGetRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(0)).onNext(Mockito.any(GDriveResource.class));
        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
        Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
    }

    @Test
    public void testGetGDriveResource_SCPStorageThrowsError() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceGetRequest GDriveResourceGetRequest = Mockito.mock(GDriveResourceGetRequest.class);
        StreamObserver<GDriveResource> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.getGDriveResource(GDriveResourceGetRequest)).thenThrow(new Exception());
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        resourceServiceHandler.getGDriveResource(GDriveResourceGetRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(0)).onNext(Mockito.any(GDriveResource.class));
        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
        Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
    }

    @Test
    public void testCreateGDriveResource() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceCreateRequest GDriveResourceCreateRequest = Mockito.mock(GDriveResourceCreateRequest.class);
        StreamObserver<GDriveResource> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);
        GDriveResource GDriveResource = Mockito.mock(GDriveResource.class);

        try {
            Mockito.when(resourceBackend.createGDriveResource(GDriveResourceCreateRequest)).thenReturn(GDriveResource);
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        resourceServiceHandler.createGDriveResource(GDriveResourceCreateRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(1)).onNext(GDriveResource);
        Mockito.verify(streamObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    public void testCreateGDriveResource_CreateThrowsException() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceCreateRequest GDriveResourceCreateRequest = Mockito.mock(GDriveResourceCreateRequest.class);
        StreamObserver<GDriveResource> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);
        GDriveResource GDriveResource = Mockito.mock(GDriveResource.class);

        try {
            Mockito.when(resourceBackend.createGDriveResource(GDriveResourceCreateRequest)).thenThrow(new Exception());
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        resourceServiceHandler.createGDriveResource(GDriveResourceCreateRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(0)).onNext(GDriveResource);
        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
        Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
    }

    @Test
    public void testUpdateGDriveResource() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceUpdateRequest GDriveResourceUpdateRequest = Mockito.mock(GDriveResourceUpdateRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.updateGDriveResource(GDriveResourceUpdateRequest)).thenReturn(false);
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();

            resourceServiceHandler.updateGDriveResource(GDriveResourceUpdateRequest, streamObserver);

            Mockito.verify(resourceBackend, Mockito.times(1)).updateGDriveResource(GDriveResourceUpdateRequest);
            Mockito.verify(streamObserver, Mockito.times(1)).onCompleted();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testUpdateGDriveResource_CreateThrowsException() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceUpdateRequest GDriveResourceUpdateRequest = Mockito.mock(GDriveResourceUpdateRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.updateGDriveResource(GDriveResourceUpdateRequest)).thenThrow(new Exception());
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();

            resourceServiceHandler.updateGDriveResource(GDriveResourceUpdateRequest, streamObserver);

            Mockito.verify(resourceBackend, Mockito.times(1)).updateGDriveResource(GDriveResourceUpdateRequest);
            Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
            Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDeleteGDriveResourceSuccessful() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceDeleteRequest GDriveResourceDeleteRequest = Mockito.mock(GDriveResourceDeleteRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.deleteGDriveResource(GDriveResourceDeleteRequest)).thenReturn(true);
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();

            resourceServiceHandler.deleteGDriveResource(GDriveResourceDeleteRequest, streamObserver);

            Mockito.verify(resourceBackend, Mockito.times(1)).deleteGDriveResource(GDriveResourceDeleteRequest);
            Mockito.verify(streamObserver, Mockito.times(1)).onCompleted();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDeleteGDriveResourceFail() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceDeleteRequest GDriveResourceDeleteRequest = Mockito.mock(GDriveResourceDeleteRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.deleteGDriveResource(GDriveResourceDeleteRequest)).thenReturn(false);
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();

            resourceServiceHandler.deleteGDriveResource(GDriveResourceDeleteRequest, streamObserver);

            Mockito.verify(resourceBackend, Mockito.times(1)).deleteGDriveResource(GDriveResourceDeleteRequest);
            Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
            Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(Exception.class));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDeleteGDriveResource_CreateThrowsException() {
        ResourceServiceHandler resourceServiceHandler = Mockito.spy(new ResourceServiceHandler());
        GDriveResourceDeleteRequest GDriveResourceDeleteRequest = Mockito.mock(GDriveResourceDeleteRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        ResourceBackend resourceBackend = Mockito.mock(ResourceBackend.class);

        try {
            Mockito.when(resourceBackend.deleteGDriveResource(GDriveResourceDeleteRequest)).thenThrow(new Exception());
            Mockito.doReturn(resourceBackend).when(resourceServiceHandler).getBackend();

            resourceServiceHandler.deleteGDriveResource(GDriveResourceDeleteRequest, streamObserver);

            Mockito.verify(resourceBackend, Mockito.times(1)).deleteGDriveResource(GDriveResourceDeleteRequest);
            Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
            Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
        } catch (Exception e) {
            fail(e);
        }
    }
}