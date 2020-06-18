package guru.springframework.recipeproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ToString(callSuper = true)
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Recipe extends BaseEntity {

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "RECIPE_CATEGORY",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<Ingredient>();

    @Lob
    private Byte[] image;

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public void addCategory(Category category){
        this.categories.add(category);
    }

    public void setNotes(Notes notes){
        if(notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }
}
