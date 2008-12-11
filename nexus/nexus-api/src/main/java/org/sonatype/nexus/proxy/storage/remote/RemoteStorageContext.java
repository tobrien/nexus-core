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
package org.sonatype.nexus.proxy.storage.remote;

import java.util.Map;

/**
 * The remote storage settings and context.
 * 
 * @author cstamas
 */
public interface RemoteStorageContext
{
    public static final String REMOTE_CONNECTIONS_SETTINGS = "remoteConnectionSettings";

    public static final String REMOTE_HTTP_PROXY_SETTINGS = "remoteHttpProxySettings";

    public static final String REMOTE_AUTHENTICATION_SETTINGS = "remoteAuthenticationSettings";

    long getLastChanged();

    void setLastChanged( long ts );

    Object getRemoteConnectionContextObject( String key );

    void putRemoteConnectionContextObject( String key, Object value );

    void removeRemoteConnectionContextObject( String key );

    Map<String, Object> getRemoteConnectionContext();
}
