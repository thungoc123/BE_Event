package EventManagement.Event.service.imp;

import EventManagement.Event.payload.Request.InsertImageRequest;

public interface ImageServiceImp {

    boolean insertImage(InsertImageRequest insertImageRequest);
    boolean deleteImage(int imageId);
    boolean deleteImagebyEvent(int eventId);
}
