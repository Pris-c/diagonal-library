package prisc.librarymanager.controller.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import prisc.librarymanager.exception.InvalidCredentialsException;
import prisc.librarymanager.model.user.*;
import prisc.librarymanager.service.security.AuthenticationService;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for AuthenticationController")
class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    AuthenticationService authenticationServiceMock;

    @Test
    @DisplayName("login: Returns the generated authentication token when successful")
    void login_ReturnsTheGeneratedToken_WhenSuccessful() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("Login", "Pass");
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO("fake-barear-token");

        when(authenticationServiceMock.login(authenticationDTO)).thenReturn(loginResponseDTO);

        ResponseEntity response = authenticationController.login(authenticationDTO);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(loginResponseDTO);
        verify(authenticationServiceMock, times(1)).login(any());
    }

    @Test
    @DisplayName("login: Throws InvalidCredentialsException when credentials are invalid")
    void login_ThrowsInvalidCredentialsException_WhenCredentialsAreInvalid() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("Login", "Pass");
        when(authenticationServiceMock.login(authenticationDTO)).thenThrow(InvalidCredentialsException.class);

        try{
            ResponseEntity response = authenticationController.login(authenticationDTO);
        } catch (Exception e){
            Assertions.assertThat(e).isInstanceOf(InvalidCredentialsException.class);
        }
    }

    @Test
    @DisplayName("register: Returns HTTP Status OK when successfull")
    void register_ReturnsHttpStatusOK_WhenSuccessful() {
        LibraryUser user = new LibraryUser("name", "login", "encryptPass", UserRole.USER);
        user.setUserID(UUID.randomUUID());
        RegisterDTO registerDTO = new RegisterDTO("name", "login", "pass");

        when(authenticationServiceMock.register(registerDTO)).thenReturn(user);

        ResponseEntity response = authenticationController.register(registerDTO);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(authenticationServiceMock, times(1)).register(any());
    }

    @Test
    @DisplayName("register: Returns HTTP Status CONFLICT when username is already registered")
    void register_ReturnsHttpStatusCONFLICT_WhenUsernameIsAlreadyRegistered() {
        RegisterDTO registerDTO = new RegisterDTO("name", "login", "pass");

        when(authenticationServiceMock.register(registerDTO)).thenReturn(null);

        ResponseEntity response = authenticationController.register(registerDTO);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        verify(authenticationServiceMock, times(1)).register(any());
    }
}