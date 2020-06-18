package guru.springframework.recipeproject.data;

import guru.springframework.recipeproject.model.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void testFindByDescription(){
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescriptionIgnoreCase("Teaspoon");
        assertThat(unitOfMeasure.isPresent()).isTrue();
        assertThat(unitOfMeasure.get().getDescription()).isEqualTo("Teaspoon");
    }

    @Test
    public void findByDescriptionCup() throws Exception {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescriptionIgnoreCase("Cup");
        assertThat(uomOptional.get().getDescription()).isEqualTo("Cup");
    }
}
