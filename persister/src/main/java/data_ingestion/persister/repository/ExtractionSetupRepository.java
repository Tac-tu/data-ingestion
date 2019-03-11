package data_ingestion.persister.repository;

import data_ingestion.shared.entity.ExtractionSetup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ExtractionSetups", path = "e-setup")
public interface ExtractionSetupRepository extends MongoRepository<ExtractionSetup, String> {

    ExtractionSetup findOneBySource(@Param("source") String source);

}