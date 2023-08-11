package com.proposal.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

import static jakarta.persistence.GenerationType.AUTO

@CompileStatic
@Serdeable
@Entity

@Table(name = 'Wallet')
class Wallet {

    @Id
    @GeneratedValue(strategy = AUTO)
    Long wallet_id

    @NotNull
    @Column(name = 'wallet_name', nullable = false, unique = true)
    String wallet_name

    @Column(name = 'amount', nullable = true, unique = false)
    Long amount

    @Column(name = 'owner_id', nullable = false, unique = false)
    Long owner_id

    @JsonIgnore

    Wallet() {}

    Wallet(@NotNull String wallet_name, Long amount, @NotNull Long owner_id) {
        this.wallet_name = wallet_name
        this.amount = amount
        this.owner_id = owner_id
    }

    @Override
    String toString() {
        "Wallet{wallet_id=$wallet_id, wallet_name='$wallet_name', amount='$amount', owner_id='$owner_id'}"
    }
}
