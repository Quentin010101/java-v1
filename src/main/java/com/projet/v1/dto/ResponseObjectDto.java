package com.projet.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseObjectDto<T> {
    private ResponseDto responseDto;
    private T object;
}
