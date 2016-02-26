package ru.vlk.resources.framework.app;

import org.apache.lucene.queryparser.classic.ParseException;
import ru.vlk.resources.framework.model.Command;
import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.services.ArgumentParser;
import ru.vlk.resources.framework.services.ResourceWriter;
import ru.vlk.resources.framework.cli.*;
import ru.vlk.resources.framework.services.impl.SimpleArgumentParser;
import ru.vlk.resources.framework.services.impl.SimpleResourceWriter;
import ru.vlk.resources.framework.util.ResourceParsingException;

import java.io.File;
import java.io.IOException;

public class App {

    private ArgumentParser argumentParser;
    private ResourceWriter resourceWriter;
    private ResourceManager resourceManager;

    public static void main(String[] args) {
        String result = null;
        try {
            App app = new App();
            result = app.executeCommand(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    public App() throws IOException {
        init();
    }

    private void init() throws IOException {
        //load props, directories, indexes
        argumentParser = new SimpleArgumentParser();
        resourceWriter = new SimpleResourceWriter();
        resourceManager = new ResourceManagerImpl(new File("conf"));
    }

    public String executeCommand(String[] args) {
        Command command = argumentParser.parseCommand(args);
        String result = "";
        switch (command) {
            case SEARCH:
                String term = argumentParser.parseTerm(args);
                try {
                    result = resourceWriter.writeResourcesAsString(resourceManager.search(term));
                } catch (IOException | ParseException e) {
                    //TODO: handle
                    e.printStackTrace();
                }
                break;
            case SEARCH_ALL:
                result = resourceWriter.writeResourcesAsString(resourceManager.searchAll());
                break;
            case ADD:
                try {
                    Resource resource = argumentParser.parseResource(args);
                    resourceManager.addResource(resource);
                    result = "add";
                } catch (IOException | ResourceParsingException e) {
                    //TODO:handle error
                    e.printStackTrace();
                    result = "error";
                }
                break;
        }
        return result;
    }
}
