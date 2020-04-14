package com.cadastral.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HandlerResult {
    private String mensagem;
    private String tipo;
}
