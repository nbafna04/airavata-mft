package org.apache.airavata.mft.secret.server.backend.file;

import org.apache.airavata.mft.secret.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileBasedSecretBackend {

    static FileBasedSecretBackend fileBasedSecretBackend;

    @BeforeAll
    public static void before() {
        fileBasedSecretBackend = Mockito.spy(new FileBasedSecretBackend());
        Mockito.doReturn("secrets.json").when(fileBasedSecretBackend).getSecretFile();
    }

    @Test
    public void testGetGDriveSecret_WithProperSecretId() {
        GDriveSecretGetRequest SecretGetRequest = Mockito.mock(GDriveSecretGetRequest.class);
        Mockito.when(SecretGetRequest.getSecretId()).thenReturn("WrongSecretId");

        try {
            assertTrue(fileBasedSecretBackend.getGDriveSecret(SecretGetRequest).isEmpty());
        } catch (Exception e) {
            fail("Exception from connector: ", e);
        }
    }

    @Test
    public void testGetGDriveSecret_WithWrongSecretId() {
        String secretId = "gdrive-cred";
        GDriveSecretGetRequest secretGetRequest = Mockito.mock(GDriveSecretGetRequest.class);
        Mockito.when(secretGetRequest.getSecretId()).thenReturn(secretId);

        try {
            Optional<GDriveSecret> gdriveSecretOptional = fileBasedSecretBackend.getGDriveSecret(secretGetRequest);
            assertTrue(gdriveSecretOptional.isPresent());

            GDriveSecret gdriveSecret = gdriveSecretOptional.get();
            assertEquals(secretId, gdriveSecret.getSecretId());
            assertNotNull(gdriveSecret.getSecretId());
            assertNotNull(gdriveSecret.getCredentialsJson());
        } catch (Exception e) {
            fail("Exception from connector: ", e);
        }
    }
}