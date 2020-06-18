package guru.springframework.recipeproject.commands;

import lombok.*;

import java.util.Objects;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitOfMeasureCommand {
    private Long id;
    private String description;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitOfMeasureCommand that = (UnitOfMeasureCommand) o;

        return id != null && Objects.equals(id, that.id);
    }

    public int hashCode() {
        return 13;
    }
}
