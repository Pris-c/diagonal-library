package prisc.librarymanager.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import prisc.librarymanager.exception.InvalidIsbnException;
import prisc.librarymanager.exception.InvalidUserInputException;
import prisc.librarymanager.model.volume.VolumeFavoriteRequest;
import prisc.librarymanager.model.volume.VolumePostRequest;
import prisc.librarymanager.model.volume.VolumeResponse;
import prisc.librarymanager.service.VolumeService;
import prisc.librarymanager.util.VolumeCreator;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for VolumeController")
class VolumeControllerTest {


    @InjectMocks
    VolumeController volumeController;
    @Mock
    VolumeService volumeServiceMock;

    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("save: Returns the persisted VolumeResponse when successful")
    void save_PersistsVolume_WhenSuccessful(VolumePostRequest volumePostRequest) {
        when(volumeServiceMock.save(any(String.class)))
                .thenReturn(VolumeCreator.createValidVolumeResponse());

        VolumeResponse responseBody = volumeController.save(VolumeCreator.createValidVolumePostRequest()).getBody();
        Assertions.assertThat(responseBody.getVolumeId())
                .isNotNull();
        Assertions.assertThat(responseBody.getVolumeId()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "0012", "00011122233", "000111222333444"})
    @DisplayName("save: InvalidIsbnException is thrown by intern method when isbn is invalid")
    void save_InvalidIsbn_ThrowsInvalidIsbnException(String invalidIsbn) {
        try {
            volumeController.save(VolumePostRequest.builder().isbn(invalidIsbn).build());
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(InvalidIsbnException.class);
        } finally {
            verifyNoInteractions(volumeServiceMock);
        }

    }

    @Test
    @DisplayName("delete: Returns HTTP Status OK when successful")
    void delete_ReturnsHttpStatusOK_WhenSuccessful(){
        when(volumeServiceMock.findById(any(UUID.class)))
                .thenReturn(VolumeCreator.createValidVolumeResponse());

        doNothing().when(volumeServiceMock).delete(any(UUID.class));
        ResponseEntity<Void> response = volumeController.delete(UUID.randomUUID());

        verify(volumeServiceMock, times(1)).findById(any(UUID.class));
        verify(volumeServiceMock, times(1)).delete(any(UUID.class));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete: Returns HTTP Status NOT_FOUND when no book is found")
    void delete_ReturnsHttpStatusNOTFOUND_WhenNoBookIsFound(){
        when(volumeServiceMock.findById(any(UUID.class)))
                .thenReturn(null);

        doNothing().when(volumeServiceMock).delete(any(UUID.class));
        ResponseEntity<Void> response = volumeController.delete(UUID.randomUUID());

        verify(volumeServiceMock, times(1)).findById(any(UUID.class));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("addFavorite: Returns HTTP Status OK when successful")
    void addFavorite_ReturnsHttpStatusOK_WhenSuccessful(){
        doNothing().when(volumeServiceMock).addFavorite(any(VolumeFavoriteRequest.class));
        ResponseEntity<Void> response = volumeController.addFavorite(VolumeCreator.createValidVolumeFavoriteRequest());

        verify(volumeServiceMock, times(1)).addFavorite(any(VolumeFavoriteRequest.class));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("removeFavorite: Returns HTTP Status OK when successful")
    void removeFavorite_ReturnsHttpStatusOK_WhenSuccessful(){
        doNothing().when(volumeServiceMock).removeFavorite(any(VolumeFavoriteRequest.class));
        ResponseEntity<Void> response = volumeController.removeFavorite(VolumeCreator.createValidVolumeFavoriteRequest());

        verify(volumeServiceMock, times(1)).removeFavorite(any(VolumeFavoriteRequest.class));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("getUserFavorites: Returns a list of Volumes when successful")
    void getUserFavorites_ReturnsListOfVolumes_WhenSuccessful(){
        List<VolumeResponse> volumes = VolumeCreator.createValidVolumeResponseList();
        when(volumeServiceMock.getUserFavorites()).thenReturn(volumes);

        ResponseEntity<List<VolumeResponse>> response = volumeController.getUserFavorites();

        Assertions.assertThat(response.getBody()).isEqualTo(volumes);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("getUserFavorites: Returns null if no Volume is found")
    void getUserFavorites_ReturnsNull_WhenNoVolumeIsFound(){
        when(volumeServiceMock.getUserFavorites()).thenReturn(null);

        ResponseEntity<List<VolumeResponse>> response = volumeController.getUserFavorites();

        Assertions.assertThat(response.getBody()).isNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findTop5Favorites: Returns a list of Volumes when successful")
    void findTop5Favorites_ReturnsListOfVolumes_WhenSuccessful(){
        List<VolumeResponse> volumes = VolumeCreator.createValidVolumeResponseList();
        when(volumeServiceMock.findTop5Favorites()).thenReturn(volumes);

        ResponseEntity<List<VolumeResponse>> response = volumeController.findTop5Favorites();

        Assertions.assertThat(response.getBody()).isEqualTo(volumes);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findTop5Favorites: Returns null if no Volume is found")
    void findTop5Favorites_ReturnsNull_WhenNoVolumeIsFound(){
        when(volumeServiceMock.findTop5Favorites()).thenReturn(null);

        ResponseEntity<List<VolumeResponse>> response = volumeController.findTop5Favorites();

        Assertions.assertThat(response.getBody()).isNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("getAll: Returns a List of all Volumes when successful")
    void getAll_ReturnsAListOfAllVolumes_WhenSuccessful() {
        List<VolumeResponse> volumes = VolumeCreator.createValidVolumeResponseList();;
        when(volumeServiceMock.getAll()).thenReturn(volumes);

        ResponseEntity<List<VolumeResponse>> volumeResponse = volumeController.getAll();

        Assertions.assertThat(volumeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(volumeResponse.getBody()).isEqualTo(volumes);
    }

    @Test
    @DisplayName("findById: Returns the unique Volume when successful")
    void findById_ReturnsTheUniqueVolume_WhenSuccessful() {
        UUID volumeId = UUID.randomUUID();
        VolumeResponse volumeResponseMock = VolumeCreator.createValidVolumeResponse();
        volumeResponseMock.setVolumeId(volumeId);

        when(volumeServiceMock.findById(volumeId)).thenReturn(volumeResponseMock);

        ResponseEntity<VolumeResponse> volumeResponse = volumeController.findById(volumeId);

        Assertions.assertThat(volumeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(volumeResponse.getBody()).isNotNull().isEqualTo(volumeResponseMock);
        Assertions.assertThat(volumeResponse.getBody().getVolumeId()).isEqualTo(volumeId);
    }

    @Test
    @DisplayName("findById: Returns HTTP Status NOT_FOUND when volume is not found")
    void findById_ReturnsHttpStatusNotFound_WhenNoVolumeIsFound() {
        UUID volumeId = UUID.randomUUID();
        when(volumeServiceMock.findById(volumeId)).thenReturn(null);

        ResponseEntity<VolumeResponse> responseEntity = volumeController.findById(volumeId);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("findByIsbn: Returns the unique Volume when successful")
    void findByIsbn_ReturnsTheUniqueVolume_WhenSuccessful(String validIsbn) {
        VolumeResponse volumeResponseMock = VolumeCreator.createValidVolumeResponse();
        when(volumeServiceMock.findByIsbn(validIsbn)).thenReturn(volumeResponseMock);

        ResponseEntity<VolumeResponse> volumeResponse = volumeController.findByIsbn(validIsbn);

        Assertions.assertThat(volumeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(volumeResponse.getBody())
                .isNotNull()
                .isEqualTo(volumeResponseMock);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("findByIsbn: Returns HTTP Status NOT_FOUND when volume is not found ")
    void findByIsbn_ReturnsHttpStatusNotFound_WhenNoVolumeIsFound(String validIsbn) {
        when(volumeServiceMock.findByIsbn(validIsbn)).thenReturn(null);

        ResponseEntity<VolumeResponse> responseEntity = volumeController.findByIsbn(validIsbn);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "0012", "00011122233", "000111222333444"})
    @DisplayName("findByIsbn: InvalidIsbnException is thrown by intern method when isbn is invalid")
    void findByIsbn_InvalidIsbn_ThrowsInvalidIsbnException(String invalidIsbn) {
        try {
            volumeController.findByIsbn(invalidIsbn);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(InvalidIsbnException.class);
        }
        verifyNoInteractions(volumeServiceMock);
    }

    @Test
    @DisplayName("findByTitle: Returns a list of Volumes when successful")
    void findByTitle_ReturnsListOfVolumes_WhenSuccessful() {
        when(volumeServiceMock.findByTitle(any(String.class))).thenReturn(VolumeCreator.createValidVolumeResponseList());

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController.findByTitle("Harry Potter and the Prisoner of Azkaban");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ",
            "Echoes of Eternity: Exploring the Boundless Depths of Time and Space in Search of Meaning and Connection"})
    @DisplayName("findByTitle: InvalidUserInputException is thrown by intern method when input is not valid")
    void findByTitle_ThrowsInvalidUserInputException_WhenInputIsInvalid(String invalidTitle) {
        try {
            volumeController.findByTitle(invalidTitle);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(InvalidUserInputException.class);
        }
        verifyNoInteractions(volumeServiceMock);
    }

    @Test
    @DisplayName("findByTitle: Returns HTTP Status NOT_FOUND when no volume is found")
    void findByTitle_ReturnsHttpStatusNotFound_WhenNoVolumeIsFound() {
        when(volumeServiceMock.findByTitle(any(String.class))).thenReturn(null);

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController
                .findByTitle("Harry Potter and the Prisoner of Azkaban");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("findByAuthor: Returns a list of Volumes when successful")
    void findByAuthor_ReturnsListOfVolumes_WhenSuccessful() {
        when(volumeServiceMock.findByAuthor(any(String.class)))
                .thenReturn(VolumeCreator.createValidVolumeResponseList());

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController.findByAuthor("William");

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ",
            "Pedro de Alcântara Francisco António João Carlos Xavier de Paula Miguel Rafael Joaquim José Gonzaga " +
                    "Pascoal Cipriano Serafim de Bragança e Bourbon"})
    @DisplayName("findByAuthor: InvalidUserInputException is thrown by intern method when input is not valid")
    void findByAuthor_ThrowsInvalidUserInputException_WhenInputIsInvalid(String invalidName) {
        try {
            volumeController.findByAuthor(invalidName);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(InvalidUserInputException.class);
        }
        verifyNoInteractions(volumeServiceMock);
    }

    @Test
    @DisplayName("findByAuthor: Returns HTTP Status NOT_FOUND when volume is not found")
    void findByAuthor_ReturnsHttpStatusNotFound_WhenNoVolumeIsFound() {
        when(volumeServiceMock.findByAuthor(any(String.class))).thenReturn(null);

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController
                .findByAuthor("William");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("findByCategory: Returns a list of Volumes when successful")
    void findByCategoryReturnsListOfVolumes_WhenSuccessful() {
        when(volumeServiceMock.findByCategory(any(String.class)))
                .thenReturn(VolumeCreator.createValidVolumeResponseList());

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController.findByCategory("Arts");

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().size()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec eros quis" +
            " turpis malesuada fermentum"})
    @DisplayName("findByCategory: InvalidUserInputException is thrown by intern method when input is not valid")
    void findByCategory_ThrowsInvalidUserInputException_WhenInputIsInvalid(String invalidName) {
        try {
            volumeController.findByCategory(invalidName);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(InvalidUserInputException.class);
        }
        verifyNoInteractions(volumeServiceMock);
    }

    @Test
    @DisplayName("findByCategory: Returns HTTP Status NOT_FOUND when volume is not found")
    void findByCategory_ReturnsHttpStatusNotFound_WhenNoVolumeIsFound() {
        when(volumeServiceMock.findByCategory(any(String.class))).thenReturn(null);

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController
                .findByAuthor("Arts");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }
}