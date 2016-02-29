package ru.vlk.resources.framework.services.impl;

import com.google.common.collect.Sets;
import ru.vlk.resources.framework.model.Command;
import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.model.Tag;
import ru.vlk.resources.framework.services.ArgumentParser;
import ru.vlk.resources.framework.util.ResourceParsingException;

import java.util.HashSet;
import java.util.Objects;

public class SimpleArgumentParser implements ArgumentParser {

    public Command parseCommand(String[] args) {
        if (Objects.equals(args[0], "add")) {
            return Command.ADD;
        } else if (Objects.equals(args[0], "search")) {
            if (args[1] == null) {
                return Command.SEARCH_ALL;
            }
            return Command.SEARCH;
        }
        return null;
    }

    public String parseTerm(String[] args) {
        if (args[1] == null) {
            //TODO:handle error
            return "error";
        }
        return args[1];
    }

    public Resource parseResource(String[] args) throws ResourceParsingException {
        if (args[1] == null || args[2] == null || args[3] == null) {
            throw new ResourceParsingException();
        }
        //TODO: refactor
        Tag tag = new Tag();
        tag.setName(args[3]);
        Resource resource = new Resource();
        resource.setUrl(args[1]);
        resource.setDescription(args[2]);
        resource.setTags(Sets.newHashSet(tag));
        return resource;
    }
}
