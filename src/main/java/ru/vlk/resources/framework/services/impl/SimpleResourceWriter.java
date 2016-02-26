package ru.vlk.resources.framework.services.impl;

import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.services.ResourceWriter;

import java.util.Set;

public class SimpleResourceWriter implements ResourceWriter {

    public String writeResourcesAsString(Set<Resource> resources) {
        String output = "";
        for (Resource resource : resources) {
            output += resource.getTime() + " " + resource.getUrl() + " " + resource.getDescription() + "\n";
        }
        return output;
    }
}
