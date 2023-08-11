package com.proposal.infrastructure.rest.micronaut.functions

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import jakarta.validation.constraints.NotBlank

@CompileStatic
@Serdeable
class WalletUpdateCommand {

    long wallet_id

    @NotBlank
    String wallet_name

    long amount

    long owner_id

    WalletUpdateCommand(long wallet_id, String wallet_name, long amount, long owner_id) {
        this.wallet_id = wallet_id
        this.wallet_name = wallet_name
        this.amount = amount
        this.owner_id = owner_id
    }
}
