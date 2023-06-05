package individualassignment.edubridge.domain.users;

import lombok.Data;

@Data
public class NotificationMessage {
    private String id;
    private String from;
    private String to;
    private String text;
}
