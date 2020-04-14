package com.cadastral.config.impl;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        // TODO implementar o get para o usuário corrente.
        return Optional.of("admin");
    }
}
