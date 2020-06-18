package guru.springframework.recipeproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Ingredient extends BaseEntity {

    @Lob
    private String description;
    private BigDecimal quantity;

    @ManyToOne
    private Recipe recipe;

    @OneToOne
    private UnitOfMeasure unitOfMeasure;
}
