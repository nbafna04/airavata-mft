package org.apache.airavata.mft.core.api;

import org.apache.airavata.mft.core.MetadataCollectorResolver;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestMetadataCollectorResolver {

    @Test
    public void testOutOfScopeConnector() {
        try {
            Optional<MetadataCollector> connector = MetadataCollectorResolver.resolveMetadataCollector("outOfScope");
            assertTrue(connector.isEmpty());
        } catch (Exception ex) {
            fail("Exception from connector: ", ex);
        }
    }

    @Test
    public void testGDrive() {
        try {
            Optional<MetadataCollector> connector = MetadataCollectorResolver.resolveMetadataCollector("GDRIVE");
            assertTrue(connector.isPresent());
            assertEquals("org.apache.airavata.mft.transport.gdrive.GDriveMetadataCollector", connector.get().getClass().getCanonicalName());
        } catch (Exception ex) {
            fail("Exception from connector: ", ex);
        }
    }
}