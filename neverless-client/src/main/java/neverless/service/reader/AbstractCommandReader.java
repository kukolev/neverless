package neverless.service.reader;

import neverless.dto.screendata.player.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstractCommandReader implements CommandReader {

    public static final String COMMAND_PREFIX = "http://localhost:8080/commands";

    @Override
    public abstract ResponseEntity<ResponseDto> read(RestTemplate restTemplate) throws IOException;

    protected String input(String paramName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(">> " + paramName + " == ");
        return br.readLine();
    }
}
