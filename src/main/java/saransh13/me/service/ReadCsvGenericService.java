package saransh13.me.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import saransh13.me.util.IFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ReadCsvGenericService<T> {

    @Value("${file.path}")
    private String filePath;


    T field;

    public List<T> getDataFromCSv(IFactory<T> factory) {
        List<T> objectList= new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                log.info(line);
                field = factory.newObject(line.split("\\|"));
                objectList.add(field);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return objectList;
    }


}
