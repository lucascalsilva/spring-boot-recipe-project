package guru.springframework.recipeproject.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
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
