package guru.springframework.recipeproject.commands;

import guru.springframework.recipeproject.model.BaseEntity;
import lombok.*;

import java.util.Objects;

/**
 * Created by jt on 6/21/17.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCommand {
    private Long id;
    private String name;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryCommand that = (CategoryCommand) o;

        return id != null && Objects.equals(id, that.id);
    }

    public int hashCode() {
        return 13;
    }
}
