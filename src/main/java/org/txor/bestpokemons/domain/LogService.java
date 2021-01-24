package org.txor.bestpokemons.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    public void log(Exception exception) {
        LOGGER.error(exception.getLocalizedMessage(), exception);
    }
}
