package EventManagement.Event.payload.Request;

import EventManagement.Event.entity.EventImage;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class InsertImageRequest {
    private int eventId;
    private String imagesUrl;


}
