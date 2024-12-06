package pe.edu.i202215458.DAWI_QUISPE_BERNARDO_SEBASTIAN.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPk {
    private Integer film_id;
    private Integer category_id;
}
