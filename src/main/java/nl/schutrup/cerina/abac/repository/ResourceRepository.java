package nl.schutrup.cerina.abac.repository;


import nl.schutrup.cerina.abac.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {


}
