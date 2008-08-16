package org.sonatype.nexus.proxy.repository;

public class RepositoryStatusCheckerThread
    extends Thread
{
    private final Repository repository;

    private boolean active;

    public RepositoryStatusCheckerThread( Repository repository )
    {
        super();

        this.repository = repository;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive( boolean active )
    {
        this.active = active;
    }

    public Repository getRepository()
    {
        return repository;
    }

    public void run()
    {
        try
        {
            while ( isActive() && getRepository().getProxyMode() != null )
            {
                if ( RepositoryStatusCheckMode.ALWAYS.equals( getRepository().getRepositoryStatusCheckMode() ) )
                {
                    if ( getRepository().getLocalStatus().shouldServiceRequest() )
                    {
                        getRepository().getRemoteStatus( false );
                    }
                }
                else if ( RepositoryStatusCheckMode.AUTO_BLOCKED_ONLY.equals( getRepository()
                    .getRepositoryStatusCheckMode() ) )
                {
                    if ( getRepository().getProxyMode().shouldAutoUnblock() )
                    {
                        getRepository().getRemoteStatus( false );
                    }
                }
                else if ( RepositoryStatusCheckMode.NEVER.equals( getRepository().getRepositoryStatusCheckMode() ) )
                {
                    // nothing
                }

                Thread.sleep( AbstractRepository.REMOTE_STATUS_RETAIN_TIME );
            }
        }
        catch ( InterruptedException e )
        {

        }
    }
}
