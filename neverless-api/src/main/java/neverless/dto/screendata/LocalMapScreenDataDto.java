package neverless.dto.screendata;

import lombok.Data;

import static neverless.Constants.LOCAL_MAP_HEIGH;
import static neverless.Constants.LOCAL_MAP_WIDTH;

@Data
public class LocalMapScreenDataDto {

    private String[][] localMap = new String[LOCAL_MAP_WIDTH][LOCAL_MAP_HEIGH];
    private int width = LOCAL_MAP_WIDTH;
    private int height = LOCAL_MAP_HEIGH;

    public void setCell(int x, int y, String val) {
        localMap[x][y] = val;
    }
}