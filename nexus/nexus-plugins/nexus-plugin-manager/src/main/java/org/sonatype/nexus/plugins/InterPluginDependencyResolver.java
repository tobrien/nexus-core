package org.sonatype.nexus.plugins;

import java.util.List;

import org.sonatype.nexus.plugins.model.PluginMetadata;

public interface InterPluginDependencyResolver
{
    List<PluginCoordinates> resolveDependencyPlugins( NexusPluginManager nexusPluginManager, PluginMetadata pluginMetadata )
        throws NoSuchPluginException;
}