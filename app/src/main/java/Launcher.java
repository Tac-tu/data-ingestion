import data_ingestion.app.config.Configuration;
import com.google.gson.GsonBuilder;
import data_ingestion.app.DataIngestionApplication;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import data_ingestion.app.helper.Helper;

import java.io.IOException;
import java.io.InputStream;

class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);
    @Option(name="-s", aliases="--source", usage="Name of the source that will be crawled.", required=true)
    private static String sourceName;

    public static void main(String[] args) {
        final Launcher instance = new Launcher();
        try {
            instance.validateArgs(args);

            InputStream configContent = Launcher.class.getResourceAsStream("/config.json");
            Configuration configuration = getConfiguration(Helper.inputStreamToString(configContent));
            String level = configuration.getLog();
            reconfigureLogger(level);
            new DataIngestionApplication(sourceName, configuration).start();
        } catch (IOException ioEx)
        {
            System.exit(1);
            log.error("ERROR: I/O Exception encountered: " + ioEx);
        }

    }

    private void validateArgs(final String[] arguments) throws IOException
    {
        final CmdLineParser parser = new CmdLineParser(this);
        if (arguments.length < 1)
        {
            parser.printUsage(System.out);
            System.exit(-1);
        }
        try
        {
            parser.parseArgument(arguments);
        }
        catch (CmdLineException clEx)
        {
            log.error("ERROR: Unable to parse command-line options: " + clEx);
            System.exit(1);
        }
        log.info("The source '" + sourceName + "' is valid.");
    }

    private static Configuration getConfiguration(String config) {
        return (new GsonBuilder().create()).fromJson(config, Configuration.class);
    }

    private static void reconfigureLogger(String level) {
        if(level != null && ! level.isEmpty()) {
            LogManager.getLogger("log.data-ingestion").setLevel(Level.toLevel(level));
        }
    }
}
