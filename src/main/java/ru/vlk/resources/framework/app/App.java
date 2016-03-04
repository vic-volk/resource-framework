package ru.vlk.resources.framework.app;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vlk.resources.framework.model.Command;
import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.services.ArgumentParser;
import ru.vlk.resources.framework.services.ResourceWriter;
import ru.vlk.resources.framework.cli.*;
import ru.vlk.resources.framework.services.impl.SimpleArgumentParser;
import ru.vlk.resources.framework.services.impl.SimpleResourceWriter;
import ru.vlk.resources.framework.util.ResourceParsingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.vlk.resources.framework.util.Util.*;

public class App {

    final static Logger logger = LoggerFactory.getLogger(App.class);

    private ArgumentParser argumentParser;
    private ResourceWriter resourceWriter;
    private ResourceManager resourceManager;

    private Properties properties;
    private String PROPERTY_PATH = "META-INF/framework.properties";
    private String PROPERTY_PATH_ALTERNATIVE = "src/main/resources/META-INF/framework.properties";
    private String INDEX_PATH_PROPERTY = "index.default.directory";

    public static void main(String[] args) {
        String result = null;
        try {
            App app = new App();
            result = app.executeCommand(args);
        } catch (IOException e) {
            logger.error(e.getClass()  + " : " + e.getMessage());
        }
        System.out.println(result);
    }

    public App() throws IOException {
        init();
    }

    private void init() throws IOException {
        //loadProperties props, directories, indexes
        loadProperties();
        argumentParser = new SimpleArgumentParser();
        resourceWriter = new SimpleResourceWriter();
        resourceManager = new ResourceManagerImpl(new File(properties.getProperty(INDEX_PATH_PROPERTY)));
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
                    e.printStackTrace();
                    error(logger, e);
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
                    error(logger, e);
                    result = "error";
                }
                break;
        }
        return result;
    }

    private Properties loadProperties() throws IOException {
        properties = new Properties();
        FileInputStream fileIS = null;
        if (new File(PROPERTY_PATH).exists()) {
            fileIS = new FileInputStream(PROPERTY_PATH);
        } else {
            fileIS = new FileInputStream(PROPERTY_PATH_ALTERNATIVE);
        }
        properties.load(fileIS);
        return properties;
    }
}
