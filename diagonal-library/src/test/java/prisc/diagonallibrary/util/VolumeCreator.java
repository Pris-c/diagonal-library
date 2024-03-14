package prisc.diagonallibrary.util;

import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.model.Category;
import prisc.diagonallibrary.model.Volume;

import java.util.Set;

public class VolumeCreator {

    public static Volume createValidVolume(){
        return Volume.builder()
                .title("Harry Potter and the Prisoner of Azkaban")
                .isbn10("0545582938")
                .isbn13("9780545582933")
                .authors(createAuthor())
                .categories(createCategory())
                .publishedDate("2013-08-27")
                .language("en")
                .build();
    }




    private static Set<Author> createAuthor(){
        return  Set.of(
                Author.builder().name("J. K. Rowling").build(),
                Author.builder().name("Kazu Kibuishi").build());
    }
    private static Set<Category> createCategory(){
        return  Set.of(
                Category.builder().name("Juvenile Fiction").build());
    }


}
