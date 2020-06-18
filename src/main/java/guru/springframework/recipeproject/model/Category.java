package guru.springframework.recipeproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<Recipe>();
}
