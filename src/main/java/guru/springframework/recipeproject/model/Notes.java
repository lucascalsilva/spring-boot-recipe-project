package guru.springframework.recipeproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Notes extends BaseEntity {

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;
}
