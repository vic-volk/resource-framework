package ru.vlk.resources.framework.cli;

import org.apache.lucene.queryparser.classic.ParseException;
import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.model.Tag;

import java.io.IOException;
import java.util.Set;

public interface ResourceManager {

    void addResource(Resource resource) throws IOException;

    Set<Resource> searchResources(Tag tag);

    Set<Resource> searchResources(Set<Tag> tags);

    Set<Resource> searchAll();

    Set<Resource> search(String term) throws IOException, ParseException;
}
