package prisc.diagonallibrary.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import prisc.diagonallibrary.controller.response.VolumeResponse;
import prisc.diagonallibrary.exception.VolumeIsAlreadyRegisteredException;
import prisc.diagonallibrary.model.Volume;
import prisc.diagonallibrary.repository.VolumeRepository;
import prisc.diagonallibrary.util.AuthorCreator;
import prisc.diagonallibrary.util.CategoryCreator;
import prisc.diagonallibrary.util.GoogleApiConsumer;
import prisc.diagonallibrary.util.VolumeCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for VolumeService")
class VolumeServiceTest {

    @InjectMocks
    VolumeService volumeService;
    @Mock
    VolumeRepository volumeRepositoryMock;
    @Mock
    GoogleApiConsumer googleApiConsumerMock;
    @Mock
    AuthorService authorServiceMock;
    @Mock
    CategoryService categoryServiceMock;


    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("save: Persists the Volume when successful")
    void save_PersistsTheVolume_WhenSuccessful(String isbn) {
        when(volumeRepositoryMock.findByIsbn10(anyString())).thenReturn(Optional.empty());
        when(volumeRepositoryMock.findByIsbn13(anyString())).thenReturn(Optional.empty());
        when(googleApiConsumerMock.get(isbn)).thenReturn(VolumeCreator.createValidVolumeToSave());
        when(authorServiceMock.processAuthors(any())).thenReturn(AuthorCreator.createAuthorSet());
        when(categoryServiceMock.processCategories(any())).thenReturn(CategoryCreator.createCategorySet());
        when(volumeRepositoryMock.save(any(Volume.class))).thenReturn(VolumeCreator.createValidVolume());

        VolumeResponse savedVolume = volumeService.save(isbn);

        Assertions.assertThat(savedVolume).isNotNull();
        Assertions.assertThat(savedVolume.getVolumeId()).isNotNull();

        verify(authorServiceMock, times(1)).processAuthors(any());
        verify(categoryServiceMock, times(1)).processCategories(any());
        verify(googleApiConsumerMock, times(1)).get(anyString());
        verifyNoMoreInteractions(categoryServiceMock);
        verifyNoMoreInteractions(authorServiceMock);
        verifyNoMoreInteractions(googleApiConsumerMock);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("save: ")
    void save_ThrowsVolumeIsAlreadyRegisteredException_WhenTheVolumeIsAlreadyInDb(String isbn) {
        when(volumeRepositoryMock.findByIsbn10(anyString())).thenReturn(Optional.of(VolumeCreator.createValidVolume()));
        when(volumeRepositoryMock.findByIsbn13(anyString())).thenReturn(Optional.of(VolumeCreator.createValidVolume()));

        Assertions.assertThatThrownBy(() -> volumeService.save(isbn)).isInstanceOf(VolumeIsAlreadyRegisteredException.class);

        verifyNoInteractions(authorServiceMock);
        verifyNoInteractions(categoryServiceMock);
        verifyNoInteractions(googleApiConsumerMock);
    }

    @Test
    @DisplayName("getAll: Returns a List of all Volumes in Database when successful")
    void getAll_ReturnsAListOfAllVolumesInDatabase_WhenSuccessful() {
        when(volumeRepositoryMock.findAll()).thenReturn(VolumeCreator.createValidVolumeList());

        List<VolumeResponse> volumes = volumeService.getAll();

        Assertions.assertThat(volumes).isNotNull();
        Assertions.assertThat(volumes.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findById: Returns the unique Volume when successful")
    void findById_ReturnsTheUniqueVolume_WhenSuccessful() {
        UUID volumeId = UUID.randomUUID();
        Volume volumeMock = VolumeCreator.createValidVolume();
        volumeMock.setVolumeId(volumeId);

        when(volumeRepositoryMock.findById(volumeId)).thenReturn(Optional.of(volumeMock));

        VolumeResponse volumeResponse = volumeService.findById(volumeId);

        Assertions.assertThat(volumeResponse).isNotNull();
        Assertions.assertThat(volumeResponse.getVolumeId()).isEqualTo(volumeId);
    }

    @Test
    @DisplayName("findById: Returns null when no Volume is found")
    void findById_ReturnsNull_WhenNoVolumeIsFound() {
         when(volumeRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.empty());

        VolumeResponse volumeResponse = volumeService.findById(UUID.randomUUID());
        Assertions.assertThat(volumeResponse).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("findByIsbn: Returns the unique Volume when successful")
    void findByIsbn_ReturnsTheUniqueVolume_WhenSuccessful(String validIsbn) {
        Volume volumeMock = VolumeCreator.createValidVolume();
        when(volumeRepositoryMock.findByIsbn10(anyString())).thenReturn(Optional.of(volumeMock));
        when(volumeRepositoryMock.findByIsbn13(anyString())).thenReturn(Optional.of(volumeMock));
        VolumeResponse volumeResponse = volumeService.findByIsbn(validIsbn);

        Assertions.assertThat(volumeResponse).isNotNull();
        Assertions.assertThat(volumeResponse.getVolumeId()).isEqualTo(volumeMock.getVolumeId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("findByIsbn: Returns null when no Volume is found")
    void findByIsbn_ReturnsNull_WhenNoVolumeIsFound(String validIsbn) {
        when(volumeRepositoryMock.findByIsbn10(anyString())).thenReturn(Optional.empty());
        when(volumeRepositoryMock.findByIsbn13(anyString())).thenReturn(Optional.empty());
        VolumeResponse volumeResponse = volumeService.findByIsbn(validIsbn);

        Assertions.assertThat(volumeResponse).isNull();
    }

    @Test
    @DisplayName("findByTitle: Returns a list of Volumes when successful")
    void findByTitle_ReturnsListOfVolumes_WhenSuccessful() {
        when(volumeRepositoryMock.findByTitleContainingIgnoreCase(anyString()))
                .thenReturn(Optional.of(VolumeCreator.createValidVolumeList()));

        List<VolumeResponse> volumes = volumeService.findByTitle("The Elements of Style");
        Assertions.assertThat(volumes).isNotNull().isNotEmpty();
        Assertions.assertThat(volumes.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByIsbn: Returns null when no Volume is found")
    void findByTitle_ReturnsNull_WhenNoVolumeIsFound() {
        when(volumeRepositoryMock.findByTitleContainingIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        VolumeResponse volumeResponse = volumeService.findByIsbn("The Elements of Style");

        Assertions.assertThat(volumeResponse).isNull();
    }

    @Test
    @DisplayName("findByAuthor: Returns a list of Volumes when successful")
    void findByAuthor_ReturnsListOfVolumes_WhenSuccessful() {
        when(authorServiceMock.findByName(anyString())).thenReturn(AuthorCreator.createAuthorList());
        when(volumeRepositoryMock.findByAuthorsAuthorId(any(UUID.class)))
                .thenReturn(Optional.of(VolumeCreator.createValidVolumeList()));

        List<VolumeResponse> volumes = volumeService.findByAuthor("Strunk");
        Assertions.assertThat(volumes).isNotNull().isNotEmpty();
        Assertions.assertThat(volumes.size()).isEqualTo(2);

        verify(authorServiceMock).findByName(anyString());
    }

    @Test
    @DisplayName("findByAuthor: Returns null when no Volume is found")
    void findByAuthor_ReturnsNull_WhenNoVolumeIsFound() {
        when(authorServiceMock.findByName(anyString())).thenReturn(AuthorCreator.createAuthorList());
        when(volumeRepositoryMock.findByAuthorsAuthorId(any(UUID.class)))
                .thenReturn(Optional.empty());

        List<VolumeResponse> volumes = volumeService.findByAuthor("Strunk");
        Assertions.assertThat(volumes).isNull();

        verify(authorServiceMock).findByName(anyString());
    }

    @Test
    @DisplayName("findByAuthor: Returns null when no Author is found")
    void findByAuthor_ReturnsNull_WhenNoAuthorIsFound() {
        when(authorServiceMock.findByName(anyString())).thenReturn(null);

        List<VolumeResponse> volumes = volumeService.findByAuthor("Strunk");
        Assertions.assertThat(volumes).isNull();

        verifyNoInteractions(volumeRepositoryMock);
    }

    @Test
    @DisplayName("findByCategory: Returns a list of Volumes when successful")
    void findByCategory_ReturnsListOfVolumes_WhenSuccessful() {
        when(categoryServiceMock.findByName(anyString())).thenReturn(CategoryCreator.createCategoryList());
        when(volumeRepositoryMock.findByCategoriesCategoryId(any(UUID.class)))
                .thenReturn(Optional.of(VolumeCreator.createValidVolumeList()));

        List<VolumeResponse> volumes = volumeService.findByCategory("Art");
        Assertions.assertThat(volumes).isNotNull().isNotEmpty();
        Assertions.assertThat(volumes.size()).isEqualTo(2);

        verify(categoryServiceMock).findByName(anyString());
    }

    @Test
    @DisplayName("findByCategory: Returns null when no Volume is found")
    void findByCategory_ReturnsNull_WhenNoVolumeIsFound() {
        when(categoryServiceMock.findByName(anyString())).thenReturn(CategoryCreator.createCategoryList());
        when(volumeRepositoryMock.findByCategoriesCategoryId(any(UUID.class)))
                .thenReturn(Optional.empty());

        List<VolumeResponse> volumes = volumeService.findByCategory("Art");
        Assertions.assertThat(volumes).isNull();

        verify(categoryServiceMock).findByName(anyString());
    }

    @Test
    @DisplayName("findByCategory: Returns null when no Category is found")
    void findByCategory_ReturnsNull_WhenNoCategoryIsFound() {
        when(categoryServiceMock.findByName(anyString())).thenReturn(null);

        List<VolumeResponse> volumes = volumeService.findByCategory("Art");
        Assertions.assertThat(volumes).isNull();

        verifyNoInteractions(volumeRepositoryMock);
    }

}