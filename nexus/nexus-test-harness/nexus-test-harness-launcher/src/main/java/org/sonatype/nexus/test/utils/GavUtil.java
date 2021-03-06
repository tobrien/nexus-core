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
package org.sonatype.nexus.test.utils;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.maven.index.artifact.Gav;
import org.apache.maven.index.artifact.IllegalArtifactCoordinateException;
import org.codehaus.plexus.util.StringUtils;

public class GavUtil
{

    public static Gav newGav( String groupId, String artifactId, String version )
        throws IllegalArtifactCoordinateException
    {
        return newGav( groupId, artifactId, version, "jar" );
    }

    public static Gav newGav( String groupId, String artifactId, String version, String packging )
        throws IllegalArtifactCoordinateException
    {
        return new Gav( groupId, artifactId, version, null, packging, null, null, null, false, false, null, false, null );
    }

    public static String getRelitivePomPath( Gav gav )
        throws FileNotFoundException
    {
        return getRelitiveArtifactPath( gav.getGroupId(), gav.getArtifactId(), gav.getVersion(), "pom", null );
    }

    public static String getRelitiveArtifactPath( Gav gav )
        throws FileNotFoundException
    {
        long timestamp = gav.getSnapshotTimeStamp() != null ? gav.getSnapshotTimeStamp() : 0;
        int buildNumber = gav.getSnapshotBuildNumber() != null ? gav.getSnapshotBuildNumber() : 0;
        
        return getRelitiveArtifactPath( gav.getGroupId(), gav.getArtifactId(), gav.getVersion(), gav.getExtension(),
                                        gav.getClassifier(), gav.isSnapshot(), timestamp, buildNumber );
    }

    public static String getRelitiveArtifactPath( String groupId, String artifactId, String version, String extension,
                                                  String classifier )
        throws FileNotFoundException
    {
        return getRelitiveArtifactPath( groupId, artifactId, version, extension, classifier, false, 0, 0 );
    }

    private static String getRelitiveArtifactPath( String groupId, String artifactId, String version, String extension,
                                                  String classifier, boolean snapshot, long timestamp, int buildNumber )
        throws FileNotFoundException
    {
        String classifierPart = StringUtils.isEmpty( classifier ) ? "" : "-" + classifier;
        String fileVersion = version;
        if( snapshot && timestamp > 0 )
        {
            fileVersion = version.replaceFirst( "SNAPSHOT", new SimpleDateFormat("yyyyMMdd.HHmmss").format( new Date( timestamp ) ) + "-" + buildNumber);
        }
        return groupId.replace( '.', '/' ) + "/" + artifactId + "/" + version + "/" + artifactId + "-" + fileVersion
            + classifierPart + "." + extension;
    }
    
}
