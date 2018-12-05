package neverless.dto.screendata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static neverless.Constants.LOCAL_MAP_HEIGH;
import static neverless.Constants.LOCAL_MAP_WIDTH;

@Data
public class LocalMapScreenDataDto {

    private List<MapObjectDto> objects = new ArrayList<>();
}