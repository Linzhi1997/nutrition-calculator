package com.serena.nutritioncalculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingQueryParams {
    private Integer limit;
    private Integer offset;
}
