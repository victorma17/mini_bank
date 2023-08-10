package example.micronaut

import example.micronaut.domain.Transaction
import jakarta.validation.constraints.NotNull

interface TransactionRepository {

    Optional<Transaction> findById(long trx_id)

    Transaction save(long wallet_source, long wallet_destination, long quantity)

    List<Transaction> findAll(@NotNull TransactionSortingAndOrderArguments args)

    List<Transaction> discover(@NotNull long wallet_id)

}
