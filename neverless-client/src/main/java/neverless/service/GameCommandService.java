package neverless.service;

import neverless.dto.CommandDto;
import neverless.dto.ResponseDto;
import neverless.domain.Command;
import neverless.util.CommandBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GameCommandService {

    @Autowired
    private CommandBuilder commandBuilder;
    @Autowired
    private RenderService renderService;
    @Autowired
    private RestTemplate restTemplate;

    public void resolve(Command command, Map<String, String> bundle) {
        System.out.println("Game Command = " + command.getShortName());
        CommandDto dto = commandBuilder.build(command, bundle);
        // todo: handle http status instead this try ... catch
        int tryCounter = 0;
        while(tryCounter < 100) {
            try {
                ResponseEntity<ResponseDto> response = restTemplate.postForEntity("http://localhost:8080/command", dto, ResponseDto.class);
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
