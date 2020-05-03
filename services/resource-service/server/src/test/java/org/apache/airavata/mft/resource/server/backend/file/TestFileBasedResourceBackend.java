package org.apache.airavata.mft.resource.server.backend.file;

import org.apache.airavata.mft.resource.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestFileBasedResourceBackend {

    static FileBasedResourceBackend fileBasedResourceBackend;

    @BeforeAll
    public static void before() {
        fileBasedResourceBackend = Mockito.spy(new FileBasedResourceBackend());
        Mockito.doReturn("resources.json").when(fileBasedResourceBackend).getResourceFile();
    }

    @Test
    public void testGetGDriveResource_WithProperResourceId() {
        GDriveResourceGetRequest resourceGetRequest = Mockito.mock(GDriveResourceGetRequest.class);
        Mockito.when(resourceGetRequest.getResourceId()).thenReturn("WrongResourceId");

        try {
            assertTrue(fileBasedResourceBackend.getGDriveResource(resourceGetRequest).isEmpty());
        } catch (Exception e) {
            fail("Exception from connector: ", e);
        }
    }

    @Test
    public void testGetGDriveResource_WithWrongResourceId() {
        String resourceId = "gdrive-file";
        GDriveResourceGetRequest resourceGetRequest = Mockito.mock(GDriveResourceGetRequest.class);
        Mockito.when(resourceGetRequest.getResourceId()).thenReturn(resourceId);

        try {
            Optional<GDriveResource> gdriveResourceOptional = fileBasedResourceBackend.getGDriveResource(resourceGetRequest);
            assertTrue(gdriveResourceOptional.isPresent());

            GDriveResource gdriveResource = gdriveResourceOptional.get();
            assertEquals(resourceId, gdriveResource.getResourceId());
            assertNotNull(gdriveResource.getResourcePath());
        } catch (Exception e) {
            fail("Exception from connector: ", e);
        }
    }
}