package example.micronaut.domain

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

@Table(name = 'Transaction')
class Transaction {

    @Id
    @GeneratedValue(strategy = AUTO)
    Long trx_id

    @Column(name = 'wallet_source', nullable = false, unique = false)
    Long wallet_source

    @Column(name = 'wallet_destination', nullable = false, unique = false)
    Long wallet_destination

    @Column(name = 'quantity', nullable = false, unique = false)
    Long quantity

    @JsonIgnore

    Transaction() {}

    Transaction(@NotNull Long wallet_source) {
        this.wallet_source = wallet_source
    }

    Transaction(@NotNull Long wallet_source, @NotNull Long wallet_destination, @NotNull Long quantity) {
        this.wallet_source = wallet_source
        this.wallet_destination = wallet_destination
        this.quantity = quantity
    }

    @Override
    String toString() {
        "Wallet{trx_id=$trx_id, wallet_source='$wallet_source', wallet_destination='$wallet_destination', quantity='$quantity'}"
    }
}
