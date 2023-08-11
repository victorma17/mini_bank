package com.proposal.infrastructure.rest.micronaut.functions

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Nullable
import io.micronaut.serde.annotation.Serdeable

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

@CompileStatic
@Serdeable
class WalletSortingAndOrderArguments {

    @Nullable
    @PositiveOrZero 
    Integer offset

    @Nullable
    @Positive
    Integer max

    @Nullable
    @Pattern(regexp = 'wallet_id|wallet_name|amount|owner_id') 
    String sort

    @Nullable
    @Pattern(regexp = 'asc|ASC|desc|DESC') 
    String order
}
