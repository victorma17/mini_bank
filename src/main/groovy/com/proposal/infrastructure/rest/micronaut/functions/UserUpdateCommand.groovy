package com.proposal.infrastructure.rest.micronaut.functions

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import jakarta.validation.constraints.NotBlank

@CompileStatic
@Serdeable
class UserUpdateCommand {

    long user_id

    @NotBlank
    String user_name

    UserUpdateCommand(long user_id, String user_name) {
        this.user_id = user_id
        this.user_name = user_name
    }
}
