package neverless.service.reader.impl;

import neverless.dto.ResponseDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StartNewGameCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/start_new_game";

    @Override
    public ResponseEntity<ResponseDto> read(RestTemplate restTemplate) {
        return restTemplate.postForEntity(CONTRACT, null, ResponseDto.class);
    }
}
