package prisc.diagonallibrary.util;

import prisc.diagonallibrary.model.Category;

import java.util.Set;
import java.util.UUID;

public class CategoryCreator {

    public static Category createCategory(){
        return Category.builder().categoryId(UUID.randomUUID()).name("Arts").build();
    }

    public static Set<Category> createCategorySetToSave(){
        return  Set.of(
                Category.builder().name("Art").build(),
                Category.builder().name("Fiction").build());
    }
    public static Set<Category> createCategorySet(){
        return  Set.of(
                Category.builder().categoryId(UUID.randomUUID()).name("Art").build(),
                Category.builder().categoryId(UUID.randomUUID()).name("Fiction").build());
    }

}
