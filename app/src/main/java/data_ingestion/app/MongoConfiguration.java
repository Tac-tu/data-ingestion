package data_ingestion.app;

public class MongoConfiguration {
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}