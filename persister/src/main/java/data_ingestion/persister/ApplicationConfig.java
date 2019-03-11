package data_ingestion.persister;

import data_ingestion.shared.entity.ExtractionSetup;
import data_ingestion.shared.entity.SourceSetup;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class ApplicationConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(SourceSetup.class, ExtractionSetup.class);
    }
}