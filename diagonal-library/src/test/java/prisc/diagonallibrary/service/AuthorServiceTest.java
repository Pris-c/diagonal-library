package prisc.diagonallibrary.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import prisc.diagonallibrary.model.Author;
import prisc.diagonallibrary.repository.AuthorRepository;
import prisc.diagonallibrary.util.AuthorCreator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for AuthorService")
class AuthorServiceTest {

    @InjectMocks
    AuthorService authorService;
    @Mock
    AuthorRepository authorRepositoryMock;


    @Test
    @DisplayName("processAuthors with persisted Authors: Returns a Set of Authors whenSuccessful")
    void processAuthors_PersistedAuthors_ReturnsASetOfAuthors_WhenSuccessful() {
        when(authorRepositoryMock.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(AuthorCreator.createAuthor()));

        Set<Author> authors = authorService.processAuthors(AuthorCreator.createAuthorSetToSave());
        Assertions.assertThat(authors).isNotNull().isNotEmpty();
        Assertions.assertThat(authors.size()).isEqualTo(1);

        verify(authorRepositoryMock, times(2)).findByNameIgnoreCase(anyString());
        verifyNoMoreInteractions(authorRepositoryMock);
    }

    @Test
    @DisplayName("processAuthors with new authors: Returns a Set of Authors (including ID) whenSuccessful")
    void processAuthors_NewAuthors_ReturnsASetOfAuthorsIncludingID_WhenSuccessful() {
        when(authorRepositoryMock.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(authorRepositoryMock.save(any(Author.class))).thenReturn(AuthorCreator.createAuthor());

        Set<Author> autorsReceivedSet = AuthorCreator.createAuthorSetToSave();
        Set<Author> processedAuthors = authorService.processAuthors(autorsReceivedSet);
        Assertions.assertThat(processedAuthors).isNotNull().isNotEmpty();
        Assertions.assertThat(processedAuthors.size()).isEqualTo(1);

        verify(authorRepositoryMock, times(autorsReceivedSet.size())).findByNameIgnoreCase(anyString());
        verify(authorRepositoryMock, times(autorsReceivedSet.size())).save(any(Author.class));
        verifyNoMoreInteractions(authorRepositoryMock);
    }

    @Test
    @DisplayName("findByName: Returns a List of Authors when successful")
    void findByName_ReturnsListOfAuthors_WhenSuccessful() {
        when(authorRepositoryMock.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(AuthorCreator.createAuthorSet().stream().toList()));

        List<Author> authors = authorService.findByName("Kibuishi");
        Assertions.assertThat(authors).isNotNull().isNotEmpty();
        Assertions.assertThat(authors.size()).isEqualTo(2);
        authors.forEach(a -> Assertions.assertThat(a.getAuthorId()).isNotNull());;
    }
    @Test
    @DisplayName("findByName: Returns null when no author is found")
    void findByName_ReturnsNull_WhenNoAuthorIsFound() {
        when(authorRepositoryMock.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        List<Author> authors = authorService.findByName("Kibuishi");
        Assertions.assertThat(authors).isNull();
    }
}