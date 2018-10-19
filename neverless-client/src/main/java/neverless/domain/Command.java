package neverless.domain;

import lombok.Data;

@Data
public class Command {

    private String path;
    private Object dto;
}
