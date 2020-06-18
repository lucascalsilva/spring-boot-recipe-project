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
public class NotesCommand {
    private Long id;
    private String recipeNotes;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotesCommand that = (NotesCommand) o;

        return id != null && Objects.equals(id, that.id);
    }

    public int hashCode() {
        return 13;
    }
}
