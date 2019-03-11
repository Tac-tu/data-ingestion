package data_ingestion.persister.repository;

import data_ingestion.shared.entity.SourceSetup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "SourceSetups", path = "s-setup")
public interface SourceSetupRepository extends MongoRepository<SourceSetup, String> {

    SourceSetup findOneBySource(@Param("source") String source);

}