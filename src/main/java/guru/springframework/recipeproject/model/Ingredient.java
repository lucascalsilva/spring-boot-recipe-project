package guru.springframework.recipeproject.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Ingredient extends BaseEntity {

    @Lob
    private String description;
    private BigDecimal quantity;
    @ManyToOne
    private Recipe recipe;
    @OneToOne
    private UnitOfMeasure unitOfMeasure;

    public Ingredient(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                ", id=" + id +
                "description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitOfMeasure=" + unitOfMeasure +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (!description.equals(that.description)) return false;
        if (!quantity.equals(that.quantity)) return false;
        return unitOfMeasure.equals(that.unitOfMeasure);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + unitOfMeasure.hashCode();
        return result;
    }
}
