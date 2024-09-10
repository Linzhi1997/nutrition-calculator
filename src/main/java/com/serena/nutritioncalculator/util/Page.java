package com.serena.nutritioncalculator.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T> {
    private List<T> resultsList;
    private Integer limit;
    private Integer offset;
    private Integer total;
}
