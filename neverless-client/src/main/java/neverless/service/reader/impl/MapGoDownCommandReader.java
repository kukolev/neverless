package neverless.service.reader.impl;

import neverless.dto.ResponseDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class MapGoDownCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/map_go_down";

    @Override
    public ResponseEntity<ResponseDto> read(RestTemplate restTemplate) {
        return restTemplate.postForEntity(CONTRACT, null, ResponseDto.class);
    }
}
