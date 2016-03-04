package ru.vlk.resources.framework.util;

import org.slf4j.Logger;

public class Util {

    public static void error(Logger logger, Exception e) {
        logger.error(e.getClass() + ":" + e.getMessage());
    }
}
