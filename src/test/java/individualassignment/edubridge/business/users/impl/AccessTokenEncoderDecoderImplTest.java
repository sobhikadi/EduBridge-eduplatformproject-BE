package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.exceptions.InvalidAccessTokenException;
import individualassignment.edubridge.domain.users.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenEncoderDecoderImplTest {

    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;
    private final String secretKey = "ASHFSHFZXKJDSHGAJSHIUYSDFIUDAHIUDHHVXKJCBVJGSHDIUFT76563";

    @BeforeEach
    public void setUp() {
        this.accessTokenEncoderDecoder = new AccessTokenEncoderDecoderImpl(secretKey);
    }

    @Test
    void encode_ShouldReturnEncodedJwt() {
        AccessToken accessToken = AccessToken.builder()
                .subject("subject")
                .roles(Arrays.asList("ROLE_USER"))
                .studentId(1L)
                .build();

        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);

        assertNotNull(encodedToken);
    }

    @Test
    void decode_ShouldReturnDecodedAccessToken() {
        AccessToken accessToken = AccessToken.builder()
                .subject("subject")
                .roles(Arrays.asList("ROLE_USER"))
                .studentId(1L)
                .build();

        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);

        AccessToken decodedToken = accessTokenEncoderDecoder.decode(encodedToken);

        assertEquals(accessToken.getSubject(), decodedToken.getSubject());
        assertEquals(accessToken.getRoles(), decodedToken.getRoles());
        assertEquals(accessToken.getStudentId(), decodedToken.getStudentId());
    }

    @Test
    void decode_WithInvalidToken_ShouldThrowInvalidAccessTokenException() {
        String invalidToken = "invalidToken";

        assertThrows(InvalidAccessTokenException.class, () -> accessTokenEncoderDecoder.decode(invalidToken));
    }


}