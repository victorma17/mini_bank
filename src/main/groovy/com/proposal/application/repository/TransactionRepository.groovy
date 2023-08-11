package com.proposal.application.repository

import com.proposal.domain.Transaction
import com.proposal.infrastructure.rest.micronaut.functions.TransactionSortingAndOrderArguments

import jakarta.validation.constraints.NotNull

interface TransactionRepository {

    Optional<Transaction> findById(long trx_id)

    Transaction save(long wallet_source, long wallet_destination, long quantity)

    List<Transaction> findAll(@NotNull TransactionSortingAndOrderArguments args)

    List<Transaction> discover(@NotNull long wallet_id)

}
