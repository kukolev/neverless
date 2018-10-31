package neverless.service;

import neverless.dto.ResponseDto;
import neverless.service.reader.CommandMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommandService {

    @Autowired
    private RenderService renderService;
    @Autowired
    private RestTemplate restTemplate;

    public void execute(CommandMapping commandMapping) {

        try {
            ResponseEntity<ResponseDto> response = commandMapping.getReader().read(restTemplate);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                renderService.setCurResponse(response.getBody());
                renderService.render();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
