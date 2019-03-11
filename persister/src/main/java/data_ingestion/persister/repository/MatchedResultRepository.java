package data_ingestion.persister.repository;

import data_ingestion.shared.entity.MatchedResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "MatchedResults", path = "matched-res")
public interface MatchedResultRepository extends MongoRepository<MatchedResult, String> {

    List<MatchedResult> findBySource(@Param("source") String source);

}