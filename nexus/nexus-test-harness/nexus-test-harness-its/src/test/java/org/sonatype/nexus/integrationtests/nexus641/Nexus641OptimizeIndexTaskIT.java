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
package org.sonatype.nexus.integrationtests.nexus641;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.sonatype.nexus.integrationtests.AbstractNexusIntegrationTest;
import org.sonatype.nexus.rest.model.ScheduledServicePropertyResource;
import org.sonatype.nexus.tasks.descriptors.OptimizeIndexTaskDescriptor;
import org.sonatype.nexus.test.utils.TaskScheduleUtil;
import org.testng.annotations.Test;

/**
 * Test task OptimizeIndex Repositories.
 * 
 * @author marvin
 */
public class Nexus641OptimizeIndexTaskIT
    extends AbstractNexusIntegrationTest
{
    protected static Logger logger = Logger.getLogger( Nexus641OptimizeIndexTaskIT.class );

    public Nexus641OptimizeIndexTaskIT()
        throws IOException
    {
        super( "nexus641" );
    }

    @Test
    public void testIndexOptimizer()
        throws Exception
    {
        // reindex
        ScheduledServicePropertyResource prop = new ScheduledServicePropertyResource();
        prop.setKey( "repositoryOrGroupId" );
        prop.setValue( "repo_nexus-test-harness-repo" );

        // reindex
        TaskScheduleUtil.runTask( OptimizeIndexTaskDescriptor.ID, prop );

    }

}
