package application.repository;

import java.util.List;

import application.entity.RawDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "rawdocuments", path = "raw-documents")
public interface RawDocumentRepository extends MongoRepository<RawDocument, String> {

    List<RawDocument> findByUrl(@Param("url") String url);

}