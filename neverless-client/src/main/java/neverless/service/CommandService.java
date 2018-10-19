package neverless.service;

import neverless.dto.ResponseDto;
import neverless.domain.CommandMapping;
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

        // todo: handle http status instead this try ... catch
        int tryCounter = 0;
        while(tryCounter < 100) {
            try {
                ResponseEntity<ResponseDto> response = commandMapping.getReader().read(restTemplate);
                tryCounter = 100;
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    renderService.render(response.getBody());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            tryCounter++;
        }
    }
}
