package data_ingestion.persister.repository;

import data_ingestion.shared.entity.RawDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "RawDocuments", path = "raw-docs")
public interface RawDocumentRepository extends MongoRepository<RawDocument, String> {

    List<RawDocument> findBySource(@Param("source") String source);

}