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
package org.sonatype.nexus.integrationtests.nexus3615;

import static org.sonatype.nexus.integrationtests.AbstractPrivilegeTest.TEST_USER_NAME;
import static org.sonatype.nexus.integrationtests.AbstractPrivilegeTest.TEST_USER_PASSWORD;

import java.io.IOException;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionContaining;
import org.restlet.data.MediaType;
import org.sonatype.nexus.integrationtests.TestContainer;
import org.sonatype.nexus.rest.model.ArtifactInfoResource;
import org.sonatype.nexus.test.utils.RoleMessageUtil;
import org.sonatype.nexus.test.utils.UserMessageUtil;
import org.sonatype.security.rest.model.RoleResource;
import org.sonatype.security.rest.model.UserResource;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;

public class Nexus3615ArtifactInfoSecurityIT
    extends AbstractArtifactInfoIT
{

    protected UserMessageUtil userUtil;

    protected RoleMessageUtil roleUtil;

    public Nexus3615ArtifactInfoSecurityIT()
    {

    }
    
    @BeforeClass
    public void setSecureTest(){
        TestContainer.getInstance().getTestContext().setSecureTest( true );
        XStream xstream = this.getXMLXStream();
        this.userUtil = new UserMessageUtil( this, xstream, MediaType.APPLICATION_XML );
        this.roleUtil = new RoleMessageUtil( this, xstream, MediaType.APPLICATION_XML );
    }

    protected void giveUserRole( String userId, String roleId, boolean overwrite )
        throws IOException
    {
        // use admin
        TestContainer.getInstance().getTestContext().useAdminForRequests();

        // add it
        UserResource testUser = this.userUtil.getUser( userId );
        if ( overwrite )
        {
            testUser.getRoles().clear();
        }
        testUser.addRole( roleId );
        this.userUtil.updateUser( testUser );
    }

    protected void giveUserPrivilege( String userId, String priv )
        throws IOException
    {
        // use admin
        TestContainer.getInstance().getTestContext().useAdminForRequests();

        RoleResource role = null;

        // first try to retrieve
        for ( RoleResource roleResource : roleUtil.getList() )
        {
            if ( roleResource.getName().equals( priv + "Role" ) )
            {
                role = roleResource;

                if ( !role.getPrivileges().contains( priv ) )
                {
                    role.addPrivilege( priv );
                    // update the permissions
                    RoleMessageUtil.update( role );
                }
                break;
            }
        }

        if ( role == null )
        {
            // now give create
            role = new RoleResource();
            role.setDescription( priv + " Role" );
            role.setName( priv + "Role" );
            role.setSessionTimeout( 60 );
            role.addPrivilege( priv );
            // save it
            role = this.roleUtil.createRole( role );
        }

        // add it
        this.giveUserRole( userId, role.getId(), false );
    }

    @Test
    public void checkViewAccess()
        throws Exception
    {
        this.giveUserRole( TEST_USER_NAME, "ui-search", true );
        this.giveUserPrivilege( TEST_USER_NAME, "T1" ); // all m2 repo, read
        this.giveUserPrivilege( TEST_USER_NAME, "repository-" + REPO_TEST_HARNESS_REPO );

        TestContainer.getInstance().getTestContext().setUsername( TEST_USER_NAME );
        TestContainer.getInstance().getTestContext().setPassword( TEST_USER_PASSWORD );

        ArtifactInfoResource info =
            getSearchMessageUtil().getInfo( REPO_TEST_HARNESS_REPO, "nexus3615/artifact/1.0/artifact-1.0.jar" );

        Assert.assertEquals( REPO_TEST_HARNESS_REPO, info.getRepositoryId() );
        Assert.assertEquals( "/nexus3615/artifact/1.0/artifact-1.0.jar", info.getRepositoryPath() );
        Assert.assertEquals( "b354a0022914a48daf90b5b203f90077f6852c68", info.getSha1Hash() );
        // view priv no longer controls search results, only read priv
        Assert.assertEquals( 3, info.getRepositories().size() );
        MatcherAssert.assertThat( getRepositoryId( info.getRepositories() ),
                           IsCollectionContaining.hasItems( REPO_TEST_HARNESS_REPO ) );
        Assert.assertEquals( "application/java-archive", info.getMimeType() );
        Assert.assertEquals( 1364, info.getSize() );
    }

}
