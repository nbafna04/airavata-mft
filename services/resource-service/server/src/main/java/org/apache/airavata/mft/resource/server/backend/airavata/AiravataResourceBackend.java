/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.airavata.mft.resource.server.backend.airavata;

import org.apache.airavata.mft.resource.server.backend.ResourceBackend;
import org.apache.airavata.mft.resource.service.*;
import org.apache.airavata.model.appcatalog.computeresource.ComputeResourceDescription;
import org.apache.airavata.model.appcatalog.storageresource.StorageResourceDescription;
import org.apache.airavata.model.data.movement.DataMovementInterface;
import org.apache.airavata.model.data.movement.DataMovementProtocol;
import org.apache.airavata.model.data.movement.SCPDataMovement;
import org.apache.airavata.registry.api.RegistryService;
import org.apache.airavata.registry.api.client.RegistryServiceClientFactory;
import org.apache.airavata.registry.api.exception.RegistryServiceException;

import java.util.Optional;

public class AiravataResourceBackend implements ResourceBackend {
    @Override
    public Optional<SCPStorage> getSCPStorage(SCPStorageGetRequest request) throws Exception {

        String resourceId = request.getStorageId();
        String[] parts = resourceId.split(":");
        String type = parts[0];
        String path = parts[1];
        String gateway = parts[2];
        String storageOrComputeId = parts[3];

        RegistryService.Client registryClient = RegistryServiceClientFactory.createRegistryClient("localhost", 8970);
        SCPStorage.Builder builder = SCPStorage.newBuilder().setStorageId(resourceId);
        if ("STORAGE".equals(type)) {

            StorageResourceDescription storageResource = registryClient.getStorageResource(storageOrComputeId);

            Optional<DataMovementInterface> dmInterfaceOp = storageResource.getDataMovementInterfaces()
                    .stream().filter(iface -> iface.getDataMovementProtocol() == DataMovementProtocol.SCP).findFirst();

            DataMovementInterface scpInterface = dmInterfaceOp
                    .orElseThrow(() -> new Exception("Could not find a SCP interface for storage resource " + storageOrComputeId));

            SCPDataMovement scpDataMovement = registryClient.getSCPDataMovement(scpInterface.getDataMovementInterfaceId());

            String alternateHostName = scpDataMovement.getAlternativeSCPHostName();
            String selectedHostName = (alternateHostName == null || "".equals(alternateHostName))?
                    storageResource.getHostName() : alternateHostName;

            int selectedPort = scpDataMovement.getSshPort() == 0 ? 22 : scpDataMovement.getSshPort();

            builder.setHost(selectedHostName);
            builder.setPort(selectedPort);

        } else if ("CLUSTER".equals(type)) {
            ComputeResourceDescription computeResource = registryClient.getComputeResource(storageOrComputeId);
            builder.setHost(computeResource.getHostName());
            builder.setPort(22);
        }
        return Optional.of(builder.build());
    }

    @Override
    public SCPStorage createSCPStorage(SCPStorageCreateRequest request) {
        return null;
    }

    @Override
    public boolean updateSCPStorage(SCPStorageUpdateRequest request) {
        return false;
    }

    @Override
    public boolean deleteSCPStorage(SCPStorageDeleteRequest request) {
        return false;
    }

    @Override
    public Optional<SCPResource> getSCPResource(SCPResourceGetRequest request) throws Exception {
        String resourceId = request.getResourceId();
        String[] parts = resourceId.split(":");
        String type = parts[0];
        String path = parts[1];
        String gateway = parts[2];
        String storageOrComputeId = parts[3];

        SCPResource scpResource = SCPResource.newBuilder()
                .setResourceId(resourceId)
                .setResourcePath(path)
                .setScpStorage(getSCPStorage(SCPStorageGetRequest.newBuilder().setStorageId(resourceId).build()).get())
                .build();
        return Optional.of(scpResource);
    }

    @Override
    public SCPResource createSCPResource(SCPResourceCreateRequest request) {
        return null;
    }

    @Override
    public boolean updateSCPResource(SCPResourceUpdateRequest request) {
        return false;
    }

    @Override
    public boolean deleteSCPResource(SCPResourceDeleteRequest request) {
        return false;
    }

    @Override
    public Optional<LocalResource> getLocalResource(LocalResourceGetRequest request) {
        return Optional.empty();
    }

    @Override
    public LocalResource createLocalResource(LocalResourceCreateRequest request) {
        return null;
    }

    @Override
    public boolean updateLocalResource(LocalResourceUpdateRequest request) {
        return false;
    }

    @Override
    public boolean deleteLocalResource(LocalResourceDeleteRequest request) {
        return false;
    }
}