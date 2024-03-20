package prisc.diagonallibrary.util;

import prisc.diagonallibrary.model.Author;

import java.util.Set;
import java.util.UUID;

public class AuthorCreator {

    public static Author createAuthor(){
        return Author.builder().authorId(UUID.randomUUID()).name("J. K. Rowling").build();
    }

    public static Set<Author> createAuthorSetToSave(){
        return  Set.of(
                Author.builder().name("J. K. Rowling").build(),
                Author.builder().name("Kazu Kibuishi").build());
    }
    public static Set<Author> createAuthorSet(){
        return  Set.of(
                Author.builder().authorId(UUID.randomUUID()).name("J. K. Rowling").build(),
                Author.builder().authorId(UUID.randomUUID()).name("Kazu Kibuishi").build());
    }
}
