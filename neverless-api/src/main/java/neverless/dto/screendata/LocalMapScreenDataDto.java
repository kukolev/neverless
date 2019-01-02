package neverless.dto.screendata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocalMapScreenDataDto {

    private List<MapObjectDto> objects = new ArrayList<>();
    private String signature;
}