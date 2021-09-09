package nl.schutrup.cerina.abac.repository;

import nl.schutrup.cerina.abac.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    public Policy findPolicyByName (String name);
}
