package ru.vlk.resources.framework.services;

import ru.vlk.resources.framework.model.Command;
import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.util.ResourceParsingException;

public interface ArgumentParser {

    Command parseCommand(String[] args);

    String parseTerm(String[] args);

    Resource parseResource(String[] args) throws ResourceParsingException;
}
