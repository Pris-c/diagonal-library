package prisc.diagonallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.repository.AuthorRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Set<Author> processAuthors(Set<Author> authors){
        return  authors.stream().map(this::processAuthor).collect(Collectors.toSet());
    }

    private Author processAuthor(Author author){
        return authorRepository.findByNameIgnoreCase(author.getName()).orElse(authorRepository.save(author));
    }


}
