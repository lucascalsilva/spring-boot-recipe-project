package guru.springframework.recipeproject.repositories;

import guru.springframework.recipeproject.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescriptionIgnoreCase(String description);
}
