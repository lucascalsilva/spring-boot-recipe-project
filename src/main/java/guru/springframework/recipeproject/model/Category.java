package guru.springframework.recipeproject.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category extends BaseEntity {

    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<Recipe>();

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;

    }

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    @Override
    public String toString() {
        return "Category{" +
                ", id=" + id +
                "categoryName='" + categoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return categoryName.equals(category.categoryName);
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }
}
