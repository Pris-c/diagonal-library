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
import prisc.librarymanager.model.volume.VolumeResponse;
import prisc.librarymanager.exception.InvalidIsbnException;
import prisc.librarymanager.exception.InvalidUserInputException;
import prisc.librarymanager.service.VolumeService;
import prisc.librarymanager.util.VolumeCreator;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for VolumeController")
class VolumeControllerTest {


    @InjectMocks
    VolumeController volumeController;
    @Mock
    VolumeService volumeServiceMock;


 /*   @ParameterizedTest
    @ValueSource(strings = {"0439554934", "9780439554930"})
    @DisplayName("save: Returns the persisted VolumeResponse when successful")
    void save_PersistsVolume_WhenSuccessful(String validIsbn) {
        when(volumeServiceMock.save(any(String.class), any(Integer.class)))
                .thenReturn(VolumeCreator.createValidVolumeResponse());

        VolumeResponse volumeResponse = volumeController.save(validIsbn, 10).getBody();
        Assertions.assertThat(volumeResponse.getVolumeId())
                .isNotNull();
        Assertions.assertThat(volumeResponse.getVolumeId()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "0012", "00011122233", "000111222333444"})
    @DisplayName("save: InvalidIsbnException is thrown by intern method when isbn is invalid")
    void save_InvalidIsbn_ThrowsInvalidIsbnException(String invalidIsbn, Integer units) {
        try {
            volumeController.save(invalidIsbn, 10);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(InvalidIsbnException.class);
        }
        verifyNoInteractions(volumeServiceMock);
    }

  */

    @Test
    @DisplayName("getAll: Returns a List of all Volumes in Database when successful")
    void getAll_ReturnsAListOfAllVolumesInDatabase_WhenSuccessful() {
        when(volumeServiceMock.getAll()).thenReturn(VolumeCreator.createValidVolumeResponseList());

        List<VolumeResponse> volumeResponseList = volumeController.getAll().getBody();
        Assertions.assertThat(volumeResponseList).isNotNull();
        Assertions.assertThat(volumeResponseList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findById: Returns the unique Volume when successful")
    void findById_ReturnsTheUniqueVolume_WhenSuccessful() {
        UUID volumeId = UUID.randomUUID();
        VolumeResponse volumeResponseMock = VolumeCreator.createValidVolumeResponse();
        volumeResponseMock.setVolumeId(volumeId);

        when(volumeServiceMock.findById(volumeId)).thenReturn(volumeResponseMock);

        VolumeResponse volumeResponse = volumeController.findById(volumeId).getBody();

        Assertions.assertThat(volumeResponse).isNotNull().isEqualTo(volumeResponseMock);
        Assertions.assertThat(volumeResponse.getVolumeId()).isEqualTo(volumeId);
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

        VolumeResponse volumeResponse = volumeController.findByIsbn(validIsbn).getBody();

        Assertions.assertThat(volumeResponse)
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
    @DisplayName("findByIsbn: Returns HTTP Status NOT_FOUND when volume is not found")
    void findByIsbn_ReturnsHttpStatusNotFound_WhenNoVolumeIsFound() {
        when(volumeServiceMock.findByIsbn(any(String.class))).thenReturn(null);

        ResponseEntity<VolumeResponse> responseEntity = volumeController.findByIsbn("0439554934");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("findByTitle: Returns a list of Volumes when successful")
    void findByTitle_ReturnsListOfVolumes_WhenSuccessful() {
        when(volumeServiceMock.findByTitle(any(String.class))).thenReturn(VolumeCreator.createValidVolumeResponseList());

        ResponseEntity<List<VolumeResponse>> responseEntity = volumeController.findByTitle("Harry Potter and the Prisoner of Azkaban");
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
    @DisplayName("findByTitle: Returns HTTP Status NOT_FOUND when volume is not found")
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