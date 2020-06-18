package guru.springframework.recipeproject.commands;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal quantity;
    private UnitOfMeasureCommand unitOfMeasure;
    private RecipeCommand recipe;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientCommand that = (IngredientCommand) o;

        return id != null && Objects.equals(id, that.id);
    }

    public int hashCode() {
        return 13;
    }
}
