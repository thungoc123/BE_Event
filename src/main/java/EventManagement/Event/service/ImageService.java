package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.EventImage;
import EventManagement.Event.payload.Request.InsertImageRequest;
import EventManagement.Event.repository.EventImageRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.service.imp.EventServiceImp;
import EventManagement.Event.service.imp.ImageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements ImageServiceImp {


    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventImageRepository eventImageRepository;


    public List<EventImage> getAllEventImage(){ return eventImageRepository.findAll();}
    @Override
    public boolean insertImage(InsertImageRequest insertImageRequest) {

            try {
                int eventId = insertImageRequest.getEventId();
                Event event = eventRepository.findById(eventId).orElse(null);
                System.out.println("Fetching event with ID: " + eventId);

                if (event == null) {
                    return false;
                }
                List<EventImage> images = new ArrayList<>();
                for (String url : insertImageRequest.getImagesUrl()) {
                    EventImage eventImage = new EventImage();
                    eventImage.setEvent(event);
                    eventImage.setUrl(url);
                    images.add(eventImage);
                }
                eventImageRepository.saveAll(images);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }


