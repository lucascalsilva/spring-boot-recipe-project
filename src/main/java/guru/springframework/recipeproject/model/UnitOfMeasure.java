package guru.springframework.recipeproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class UnitOfMeasure extends BaseEntity {

    private String description;
}
