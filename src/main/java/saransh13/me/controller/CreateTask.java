package saransh13.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import saransh13.me.model.User;
import saransh13.me.service.GetEnrichedService;
import saransh13.me.service.ReadCsvGenericService;
import saransh13.me.service.ReadCsvService;
import saransh13.me.util.UserFactoryImpl;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@RestController
public class CreateTask {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    ReadCsvService readCvsService;
    @Autowired
    GetEnrichedService getEnrichedService;

    @Autowired
    ReadCsvGenericService<User> csvGenericService;

    @Autowired
    UserFactoryImpl factory;

    @GetMapping("/enrichfile")
    public String createTask() throws FileNotFoundException {
        List<User> fileArray = readCvsService.getFileArray(Arrays.asList("id", "name"), '|', filePath);
        for(User user: fileArray){
            getEnrichedService.modelUserToAdditionalDetails();
        }
        return "Done";
    }

    @GetMapping("/enrichfile/v2")
    public ResponseEntity createTaskv2()  {
        List<User> userFromCSV = csvGenericService.getDataFromCSv(factory);
        return new ResponseEntity(userFromCSV, HttpStatus.OK);
    }

}