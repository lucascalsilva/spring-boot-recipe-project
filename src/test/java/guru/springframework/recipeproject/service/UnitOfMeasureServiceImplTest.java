package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.IngredientCommand;
import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeproject.model.Ingredient;
import guru.springframework.recipeproject.model.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    UnitOfMeasureServiceImpl unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    UnitOfMeasureCommand unitOfMeasureCommand;


    @BeforeEach
    public void init(){
        unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(1L).description("Some uom").build();

        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand,
                unitOfMeasureCommandToUnitOfMeasure);
    }

    @Test
    void findById() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));

        UnitOfMeasureCommand unitOfMeasureCommandById = unitOfMeasureService.findById(1L);

        assertThat(unitOfMeasureCommand).isEqualTo(unitOfMeasureCommandById);

        verify(unitOfMeasureRepository, times(1)).findById(1L);
        verify(unitOfMeasureRepository, never()).findAll();
    }

    @Test
    void findByIdNotFound() {
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException findByIdNotFoundException = assertThrows(RuntimeException.class, () -> unitOfMeasureService.findById(1L)
                , "Expected RuntimeException when cant find unit of measure by ID");

        assertThat(findByIdNotFoundException.getMessage()).containsIgnoringCase("not found");
    }

    @Test
    void save() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);
        doAnswer(returnsFirstArg()).when(unitOfMeasureRepository).save(any(UnitOfMeasure.class));

        UnitOfMeasureCommand savedUnitOfMeasureCommand = unitOfMeasureService.save(unitOfMeasureCommand);

        verify(unitOfMeasureRepository, times(1)).save(unitOfMeasure);

        assertThat(savedUnitOfMeasureCommand).isEqualTo(unitOfMeasureCommand);
    }

    @Test
    void deleteById() {
        unitOfMeasureService.deleteById(1L);

        verify(unitOfMeasureRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);
        when(unitOfMeasureRepository.findAll()).thenReturn(Arrays.asList(unitOfMeasure));

        Set<UnitOfMeasureCommand> unitOfMeasureCommandsAll = unitOfMeasureService.findAll();

        assertThat(unitOfMeasureCommandsAll.contains(unitOfMeasureCommand));
        assertThat(unitOfMeasureCommandsAll.size()).isEqualTo(1);

        verify(unitOfMeasureRepository, times(1)).findAll();
        verify(unitOfMeasureRepository, never()).findById(anyLong());
    }
}