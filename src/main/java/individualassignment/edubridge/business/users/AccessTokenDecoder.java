package individualassignment.edubridge.business.users;

import individualassignment.edubridge.domain.users.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
