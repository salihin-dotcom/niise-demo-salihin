package my.gov.imi.niisesali_demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Passportrepository extends JpaRepository<Passport, Integer> {
    // Standard CRUD operations are automatically included
    // Finds records matching both criteria if both are provided
    List<Passport> findByFullnameContainingIgnoreCaseAndIcnoContainingIgnoreCase(String fullname, String icno);

    // Finds records matching only name
    List<Passport> findByFullnameContainingIgnoreCase(String fullname);

    // Finds records matching only IC number
    List<Passport> findByIcnoContainingIgnoreCase(String icno);
}