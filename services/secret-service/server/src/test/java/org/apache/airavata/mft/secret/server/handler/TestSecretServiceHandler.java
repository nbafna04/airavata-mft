package org.apache.airavata.mft.secret.server.handler;

import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.airavata.mft.secret.service.*;
import org.apache.airavata.mft.secret.server.backend.SecretBackend;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class TestSecretServiceHandler {

    @Test
    public void testGetGDriveSecret() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretGetRequest gdriveSecretGetRequest = Mockito.mock(GDriveSecretGetRequest.class);
        StreamObserver<GDriveSecret> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);
        GDriveSecret gdriveSecret = Mockito.mock(GDriveSecret.class);

        try {
            Mockito.when(secretBackend.getGDriveSecret(gdriveSecretGetRequest)).thenReturn(Optional.of(gdriveSecret));
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        secretServiceHandler.getGDriveSecret(gdriveSecretGetRequest, streamObserver);

//        Mockito.verify(streamObserver, Mockito.times(0)).onNext(gdriveSecret);
//        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
    }

    @Test
    public void testGetGDriveSecret_EmptySecret() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretGetRequest gdriveSecretGetRequest = Mockito.mock(GDriveSecretGetRequest.class);
        StreamObserver<GDriveSecret> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);

        try {
            Mockito.when(secretBackend.getGDriveSecret(gdriveSecretGetRequest)).thenReturn(Optional.empty());
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        secretServiceHandler.getGDriveSecret(gdriveSecretGetRequest, streamObserver);
        Mockito.verify(streamObserver, Mockito.times(0)).onNext(Mockito.any(GDriveSecret.class));
        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
//        Mockito.verify(streamObserver, Mockito.times(0)).onError(Mockito.any(StatusRuntimeException.class));

    }

    @Test
    public void testGetGDriveSecret_GDriveSecretThrowsError() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretGetRequest gdriveSecretGetRequest = Mockito.mock(GDriveSecretGetRequest.class);
        StreamObserver<GDriveSecret> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);

        try {
            Mockito.when(secretBackend.getGDriveSecret(gdriveSecretGetRequest)).thenThrow(new Exception());
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        secretServiceHandler.getGDriveSecret(gdriveSecretGetRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(0)).onNext(Mockito.any(GDriveSecret.class));
        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
//        Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
    }

    @Test
    public void testCreateGDriveSecret() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretCreateRequest gdriveSecretCreateRequest = Mockito.mock(GDriveSecretCreateRequest.class);
        StreamObserver<GDriveSecret> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);
        GDriveSecret gdriveSecret = Mockito.mock(GDriveSecret.class);

        try {
            Mockito.when(secretBackend.createGDriveSecret(gdriveSecretCreateRequest)).thenReturn(gdriveSecret);
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();
        } catch (Exception e) {
            fail(e);
        }

        secretServiceHandler.createGDriveSecret(gdriveSecretCreateRequest, streamObserver);

        Mockito.verify(streamObserver, Mockito.times(0)).onNext(gdriveSecret);
        Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
    }

    @Test
    public void testUpdateGDriveSecret() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretUpdateRequest gdriveSecretUpdateRequest = Mockito.mock(GDriveSecretUpdateRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);

        try {
            Mockito.when(secretBackend.updateGDriveSecret(gdriveSecretUpdateRequest)).thenReturn(false);
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();

            secretServiceHandler.updateGDriveSecret(gdriveSecretUpdateRequest, streamObserver);

//            Mockito.verify(secretBackend, Mockito.times(0)).updateGDriveSecret(gdriveSecretUpdateRequest);
//            Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDeleteGDriveSecretSuccessful() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretDeleteRequest gdriveSecretDeleteRequest = Mockito.mock(GDriveSecretDeleteRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);

        try {
            Mockito.when(secretBackend.deleteGDriveSecret(gdriveSecretDeleteRequest)).thenReturn(true);
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();

            secretServiceHandler.deleteGDriveSecret(gdriveSecretDeleteRequest, streamObserver);

//            Mockito.verify(secretBackend, Mockito.times(0)).deleteGDriveSecret(gdriveSecretDeleteRequest);
//            Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testDeleteGDriveSecretFail() {
        SecretServiceHandler secretServiceHandler = Mockito.spy(new SecretServiceHandler());
        GDriveSecretDeleteRequest gdriveSecretDeleteRequest = Mockito.mock(GDriveSecretDeleteRequest.class);
        StreamObserver<Empty> streamObserver = Mockito.mock(StreamObserver.class);
        SecretBackend secretBackend = Mockito.mock(SecretBackend.class);

        try {
            Mockito.when(secretBackend.deleteGDriveSecret(gdriveSecretDeleteRequest)).thenReturn(false);
            Mockito.doReturn(secretBackend).when(secretServiceHandler).getBackend();

            secretServiceHandler.deleteGDriveSecret(gdriveSecretDeleteRequest, streamObserver);

//            Mockito.verify(secretBackend, Mockito.times(0)).deleteGDriveSecret(gdriveSecretDeleteRequest);
            Mockito.verify(streamObserver, Mockito.times(0)).onCompleted();
//            Mockito.verify(streamObserver, Mockito.times(1)).onError(Mockito.any(StatusRuntimeException.class));
        } catch (Exception e) {
            fail(e);
        }
    }
}