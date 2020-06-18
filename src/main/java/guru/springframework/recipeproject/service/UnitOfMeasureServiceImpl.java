package guru.springframework.recipeproject.service;

import guru.springframework.recipeproject.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeproject.exceptions.NotFoundException;
import guru.springframework.recipeproject.model.UnitOfMeasure;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @Override
    public UnitOfMeasureCommand findById(Long id) {
        return unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasureRepository.findById(id).orElseThrow(() ->
                new NotFoundException(UnitOfMeasure.class, "id", id.toString())));
    }

    @Override
    public UnitOfMeasureCommand save(UnitOfMeasureCommand object) {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(object);
        return unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasureRepository.save(unitOfMeasure));
    }

    @Override
    public void deleteById(Long id) {
        unitOfMeasureRepository.deleteById(id);

    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        Set<UnitOfMeasureCommand> unitOfMeasures = new HashSet<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(unitOfMeasure -> {
            unitOfMeasures.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure));
        });
        return unitOfMeasures;
    }
}
