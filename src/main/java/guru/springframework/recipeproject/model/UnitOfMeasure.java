package guru.springframework.recipeproject.model;

import javax.persistence.Entity;

@Entity
public class UnitOfMeasure extends BaseEntity {

    private String description;

    public UnitOfMeasure() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitOfMeasure that = (UnitOfMeasure) o;

        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return "UnitOfMeasure{" +
                ", id=" + id +
                "description='" + description + '\'' +
                '}';
    }
}
