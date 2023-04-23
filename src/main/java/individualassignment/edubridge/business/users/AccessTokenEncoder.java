package individualassignment.edubridge.business.users;

import individualassignment.edubridge.domain.users.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
