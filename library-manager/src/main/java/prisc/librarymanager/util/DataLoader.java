package prisc.librarymanager.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import prisc.librarymanager.mapper.VolumeMapper;
import prisc.librarymanager.model.googleapi.GoogleApiResponse;
import prisc.librarymanager.model.volume.Volume;
import prisc.librarymanager.service.VolumeService;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * DataLoader class responsible for loading initial volume data into the database.
 * Implements CommandLineRunner to execute the data loading when the application starts.
 */
@Log4j2
@Component
public class DataLoader implements CommandLineRunner {

    List<GoogleApiResponse> gApiResponse;
    List<Volume> volumes;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    VolumeService volumeService;

    /**
     * Loads initial volume data from a JSON file and saves it to the database if the database is empty.
     *
     * @param args Command-line arguments passed to the application.
     * @throws Exception if an error occurs during data loading.
     */
    @Override
    public void run(String... args) throws Exception {
        try {
            if (volumeService.emptyDatabase()) {
                gApiResponse = mapper.readValue(new File("src/main/resources/volumes-starter.json"), new TypeReference<List<GoogleApiResponse>>() {
                });
                volumes = gApiResponse.stream().map(g -> VolumeMapper.INSTANCE.toVolume(g.getItems().get(0).getVolumeInfo())).toList();
                volumes.forEach(v -> volumeService.dataLoader(v));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            log.error("Something went wrong while loading the initial data");
            e.printStackTrace();
        }
    }
}


