package data_ingestion.persister.repository;

import data_ingestion.shared.entity.ParsedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "ParsedDocuments", path = "parsed-docs")
public interface ParsedDocumentRepository extends MongoRepository<ParsedDocument, String> {

    List<ParsedDocument> findBySource(@Param("source") String source);

}