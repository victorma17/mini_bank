package example.micronaut

import example.micronaut.domain.Transaction
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

import jakarta.persistence.PersistenceException
import jakarta.validation.Valid

import static io.micronaut.http.HttpHeaders.LOCATION

@CompileStatic
@ExecuteOn(TaskExecutors.IO) 
@Controller('/transactions') 
class TransactionController {

    private final TransactionRepository transactionRepository

    TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository
    }

    @Get('/{trx_id}')
    Transaction show(Long trx_id) {
        transactionRepository
                .findById(trx_id)
                .orElse(null)
    }

    @Get(value = '/list{?args*}')
    List<Transaction> list(@Valid TransactionSortingAndOrderArguments args) {
        transactionRepository.findAll(args)
    }

    @Get(value = '/discover/{wallet_id}')
    List<Transaction> list(long wallet_id) {
        transactionRepository.discover(wallet_id)
    }

    @Post
    HttpResponse<Transaction> save(@Body @Valid TransactionSaveCommand cmd) {
        Transaction transaction = transactionRepository.save(cmd.wallet_source, cmd.wallet_destination, cmd.quantity)

        HttpResponse
                .created(transaction)
                .headers(headers -> headers.location(location(transaction.trx_id)))
    }

    private URI location(Long trx_id) {
        URI.create('/transactions/' + trx_id)
    }
}
