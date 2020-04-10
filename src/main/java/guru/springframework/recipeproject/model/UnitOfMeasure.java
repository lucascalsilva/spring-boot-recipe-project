package guru.springframework.recipeproject.model;

import lombok.*;

import javax.persistence.Entity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
@Entity
public class UnitOfMeasure extends BaseEntity {

    private String description;
}
