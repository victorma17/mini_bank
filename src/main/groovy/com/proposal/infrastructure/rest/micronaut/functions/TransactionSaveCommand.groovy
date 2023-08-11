package com.proposal.infrastructure.rest.micronaut.functions

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@CompileStatic
@Serdeable
class TransactionSaveCommand {

    long wallet_source

    long wallet_destination

    long quantity

    TransactionSaveCommand(long wallet_source, long wallet_destination, long quantity) {
        this.wallet_source = wallet_source
        this.wallet_destination = wallet_destination
        this.quantity = quantity
    }
}
