package data_ingestion.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseApplication implements StoppableApplicationInterface {
    private static final Logger log = LoggerFactory.getLogger(BaseApplication.class);

    protected void start() {
        log.debug("Add shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(this)));
    }
}
