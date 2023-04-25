package org.burgers;

import lombok.Data;
import java.util.List;

@Data
public class Burger {
    private String name;
    private List<Ingredient> ingredients;
}
