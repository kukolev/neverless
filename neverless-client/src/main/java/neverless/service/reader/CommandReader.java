package neverless.service.reader;

import neverless.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public interface CommandReader {

    ResponseEntity<ResponseDto> read(RestTemplate restTemplate) throws IOException;
}