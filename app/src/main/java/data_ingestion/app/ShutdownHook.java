package data_ingestion.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ShutdownHook implements Runnable {
    static private final Logger log = LoggerFactory.getLogger(ShutdownHook.class);
    private final StoppableApplicationInterface application;

    ShutdownHook(StoppableApplicationInterface application) {
        this.application = application;
    }

    public void run() {
        log.info("Stop everything!");
        this.application.stop();
    }
}
