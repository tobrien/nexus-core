/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://www.sonatype.com/products/nexus/attributions.
 *
 * This program is free software: you can redistribute it and/or modify it only under the terms of the GNU Affero General
 * Public License Version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License Version 3
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License Version 3 along with this program.  If not, see
 * http://www.gnu.org/licenses.
 *
 * Sonatype Nexus (TM) Open Source Version is available from Sonatype, Inc. Sonatype and Sonatype Nexus are trademarks of
 * Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation. M2Eclipse is a trademark of the Eclipse Foundation.
 * All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.proxy.maven;

import java.io.IOException;

import org.apache.maven.index.artifact.Gav;
import org.apache.maven.index.artifact.IllegalArtifactCoordinateException;

public interface MetadataManager
{
    /**
     * Calling this method updates the GAV, GA and G metadatas accordingly. It senses whether it is a snapshot or not.
     * 
     * @param req
     */
    void deployArtifact( ArtifactStoreRequest request )
        throws IOException,
            IllegalArtifactCoordinateException;

    /**
     * Calling this method updates the GAV, GA and G metadatas accordingly. It senses whether it is a snapshot or not.
     * 
     * @param req
     */
    void undeployArtifact( ArtifactStoreRequest request )
        throws IOException,
            IllegalArtifactCoordinateException;

    /**
     * Resolves the artifact, honoring LATEST and RELEASE as version. In case of snapshots, it will try to resolve the
     * timestamped version too, if needed.
     * 
     * @return
     * @throws IOException
     * @throws IllegalArtifactCoordinateException
     */
    Gav resolveArtifact( ArtifactStoreRequest gavRequest )
        throws IOException,
            IllegalArtifactCoordinateException;

    /**
     * Resolves the snapshot base version to a timestamped version if possible. Only when a repo is snapshot.
     * 
     * @return
     * @throws IOException
     * @throws IllegalArtifactCoordinateException
     */
    Gav resolveSnapshot( ArtifactStoreRequest gavRequest, Gav gav )
        throws IOException,
            IllegalArtifactCoordinateException;
}
