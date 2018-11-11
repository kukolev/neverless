package neverless.service.reader.impl;

import neverless.dto.screendata.player.ResponseDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WaitCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/wait";

    @Override
    public ResponseEntity<ResponseDto> read(RestTemplate restTemplate) {
        return restTemplate.postForEntity(CONTRACT, null, ResponseDto.class);
    }
}
