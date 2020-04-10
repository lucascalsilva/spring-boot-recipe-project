package guru.springframework.recipeproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
@Entity
public class Category extends BaseEntity {

    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<Recipe>();
}
