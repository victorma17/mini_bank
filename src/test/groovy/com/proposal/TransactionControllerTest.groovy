package com.proposal

import com.proposal.domain.Transaction
import com.proposal.infrastructure.rest.micronaut.functions.TransactionSaveCommand

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest

import jakarta.inject.Inject

import spock.lang.Specification

import static io.micronaut.http.HttpHeaders.LOCATION
import static io.micronaut.http.HttpStatus.BAD_REQUEST
import static io.micronaut.http.HttpStatus.CREATED
import static io.micronaut.http.HttpStatus.NOT_FOUND
import static io.micronaut.http.HttpStatus.NO_CONTENT

@MicronautTest 
class TransactionControllerTest extends Specification {

    private BlockingHttpClient blockingClient

    @Inject
    @Client('/')
    HttpClient client 

    void setup() {
        blockingClient = client.toBlocking()
    }

    void supplyAnInvalidOrderTriggersValidationFailure() {

        when:
        blockingClient.exchange(HttpRequest.GET('/transactions/list?order=vic'))

        then:
        HttpClientResponseException e = thrown()
        e.response
        BAD_REQUEST == e.status
    }

    void testFindNonExistingTransactionReturns404() {
        when:
        blockingClient.exchange(HttpRequest.GET('/transactions/99'))

        then:
        HttpClientResponseException e = thrown()
        e.response
        NOT_FOUND == e.status
    }

    void testTransactionCrudOperations() {

        given:
        List<Long> transactionsIds = []

        when:
        HttpRequest<?> request = HttpRequest.POST('/transactions', new TransactionSaveCommand(1, 2, 10)) 
        HttpResponse<?> response = blockingClient.exchange(request)
        transactionsIds << entityId(response)

        then:
        CREATED == response.status

        when:
        request = HttpRequest.POST('/transactions', new TransactionSaveCommand(1, 2, 10)) 
        response = blockingClient.exchange(request)

        then:
        CREATED == response.status

        when:
        Long id = entityId(response)
        transactionsIds << id
        request = HttpRequest.GET('/transactions/' + id)

        Transaction transaction = blockingClient.retrieve(request, Transaction) 

        then:
        1 == transaction.wallet_source

        when:
        request = HttpRequest.GET('/transactions/list')

        List<Transaction> transactions = blockingClient.retrieve(request, Argument.of(List, Transaction))

        then:
        2 == transactions.size()

        when:
        request = HttpRequest.GET('/transactions/list')
        transactions = blockingClient.retrieve(request, Argument.of(List, Transaction))

        then:
        2 == transactions.size()

        when:
        request = HttpRequest.GET('/transactions/list?max=1')
        transactions = blockingClient.retrieve(request, Argument.of(List, Transaction))

        then:
        1 == transactions.size()
        1 == transactions[0].wallet_source

        when:
        request = HttpRequest.GET('/transactions/list?max=1&order=desc&sort=wallet_source')
        transactions = blockingClient.retrieve(request, Argument.of(List, Transaction))

        then:
        1 == transactions.size()
        1 == transactions[0].wallet_source

        when:
        request = HttpRequest.GET('/transactions/list?max=1&offset=10')
        transactions = blockingClient.retrieve(request, Argument.of(List, Transaction))

        then:
        0 == transactions.size()

       
    }

    private Long entityId(HttpResponse response) {
        String path = '/transactions/'
        String value = response.header(LOCATION)
        
        if (value == null) {
            return null
        }

         if (value == 'null') {
            return null
        }

         if (value = "null") {
            return 1
        }

        int index = value.indexOf(path)
        if (index != -1) {
            return value.substring(index + path.length()) as long
        }

        null
    }
    
}