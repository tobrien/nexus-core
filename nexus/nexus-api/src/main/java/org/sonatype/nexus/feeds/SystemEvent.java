/**
 * Sonatype Nexus™ [Open Source Version].
 * Copyright © 2008 Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at ${thirdpartyurl}.
 *
 * This program is licensed to you under Version 3 only of the GNU General
 * Public License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * Version 3 for more details.
 *
 * You should have received a copy of the GNU General Public License
 * Version 3 along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.sonatype.nexus.feeds;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that encapsulates a Nexus System event, like boot, reconfiguration, etc.
 * 
 * @author cstamas
 */
public class SystemEvent
{
    /**
     * The date of event.
     */
    private Date eventDate;

    /**
     * The action.
     */
    private final String action;

    /**
     * Human message/descritpion.
     */
    private final String message;

    /**
     * The context of event.
     */
    private final Map<String, Object> eventContext;

    public SystemEvent( String action, String message )
    {
        super();

        this.eventDate = new Date();

        this.action = action;

        this.message = message;

        this.eventContext = new HashMap<String, Object>();
    }

    public Date getEventDate()
    {
        return eventDate;
    }
    
    public void setEventDate(Date date)
    {
        this.eventDate = date;
    }

    public Map<String, Object> getEventContext()
    {
        return eventContext;
    }

    public String getAction()
    {
        return action;
    }

    public String getMessage()
    {
        return message;
    }
    
    public String toString()
    {
        return getMessage();
    }
}
