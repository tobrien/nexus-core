/**
 * Sonatype Nexus (TM) Open Source Version.
 * Copyright (c) 2008 Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://nexus.sonatype.org/dev/attributions.html
 * This program is licensed to you under Version 3 only of the GNU General Public License as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License Version 3 for more details.
 * You should have received a copy of the GNU General Public License Version 3 along with this program.
 * If not, see http://www.gnu.org/licenses/.
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc.
 * "Sonatype" and "Sonatype Nexus" are trademarks of Sonatype, Inc.
 */
package org.sonatype.nexus.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sonatype.nexus.configuration.application.ApplicationConfiguration;
import org.sonatype.nexus.proxy.events.ConfigurationEvent;

/**
 * An event fired on configuration change.
 * 
 * @author cstamas
 */
public class ConfigurationChangeEvent
    extends ConfigurationEvent
{
    private final ApplicationConfiguration configuration;

    private final List<Object> changes;

    public ConfigurationChangeEvent( ApplicationConfiguration configuration, Collection<Object> changes )
    {
        super();

        this.configuration = configuration;

        this.changes = new ArrayList<Object>();

        if ( changes != null )
        {
            this.changes.addAll( changes );
        }
    }

    public ApplicationConfiguration getApplicationConfiguration()
    {
        return configuration;
    }

    public List<Object> getChanges()
    {
        return changes;
    }

}