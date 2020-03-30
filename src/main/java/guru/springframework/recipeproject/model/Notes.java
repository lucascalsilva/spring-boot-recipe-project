package guru.springframework.recipeproject.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Notes extends BaseEntity {

    @OneToOne
    private Recipe recipe;
    @Lob
    private String recipeNotes;

    public Notes() {
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }

    @Override
    public String toString() {
        return "Notes{" +
                ", id=" + id +
                ", recipeNotes='" + recipeNotes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notes notes = (Notes) o;

        return recipeNotes.equals(notes.recipeNotes);
    }

    @Override
    public int hashCode() {
        return recipeNotes.hashCode();
    }
}
