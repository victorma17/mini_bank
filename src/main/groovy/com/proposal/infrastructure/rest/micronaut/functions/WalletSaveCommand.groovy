package com.proposal.infrastructure.rest.micronaut.functions

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import jakarta.validation.constraints.NotBlank

@CompileStatic
@Serdeable 
class WalletSaveCommand {

    @NotBlank
    String wallet_name

    long amount

    long owner_id

    WalletSaveCommand(String wallet_name, long amount, long owner_id) {
        this.wallet_name = wallet_name
        this.amount = amount
        this.owner_id = owner_id
    }
}
