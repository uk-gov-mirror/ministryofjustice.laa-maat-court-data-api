package gov.uk.courtdata.repository;

import gov.uk.courtdata.entity.VerdictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerdictRepository extends JpaRepository<VerdictEntity,Integer> {
}