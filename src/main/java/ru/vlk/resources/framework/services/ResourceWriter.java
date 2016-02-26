package ru.vlk.resources.framework.services;

import ru.vlk.resources.framework.model.Resource;

import java.util.Set;

public interface ResourceWriter {

    String writeResourcesAsString(Set<Resource> resources);
}
