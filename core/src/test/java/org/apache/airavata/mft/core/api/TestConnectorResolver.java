package org.apache.airavata.mft.core.api;

import org.apache.airavata.mft.core.ConnectorResolver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestConnectorResolver {

    @Test
    public void testOutOfScopeConnector() {
        try {
            Optional<Connector> connector = ConnectorResolver.resolveConnector("outOfScope", "outOfScope");
            assertTrue(connector.isEmpty());
        } catch(Exception ex) {
            fail("Exception from connector: ", ex);
        }
    }

    @Test
    public void testGDriveIn() {
        try {
            Optional<Connector> connector = ConnectorResolver.resolveConnector("GDRIVE", "IN");
            assertTrue(connector.isPresent());
            assertEquals("org.apache.airavata.mft.transport.gdrive.GDriveReceiver", connector.get().getClass().getCanonicalName());
        } catch(Exception ex) {
            fail("Exception from connector: ", ex);
        }
    }

    @Test
    public void testGDriveOut() {
        try {
            Optional<Connector> connector = ConnectorResolver.resolveConnector("GDRIVE", "OUT");
            assertTrue(connector.isPresent());
            assertEquals("org.apache.airavata.mft.transport.gdrive.GDriveSender", connector.get().getClass().getCanonicalName());
        } catch(Exception ex) {
            fail("Exception from connector: ", ex);
        }
    }

    @Test
    public void testGDrive_WrongDirection() {
        try {
            Optional<Connector> connector = ConnectorResolver.resolveConnector("GDRIVE", "wrong input");
            assertTrue(connector.isEmpty());
        } catch(Exception ex) {
            fail("Exception from connector: ", ex);
        }
    }
}