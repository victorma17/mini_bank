package com.proposal.application.repository

import com.proposal.domain.Wallet
import com.proposal.infrastructure.rest.micronaut.functions.WalletSortingAndOrderArguments

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

interface WalletRepository {

    Optional<Wallet> findById(long wallet_id)

    Wallet save(String wallet_name, long amount, long owner_id)

    List<Wallet> findAll(@NotNull WalletSortingAndOrderArguments args)

    int update(long wallet_id, @NotBlank String wallet_name, long amount, long owner_id)

}