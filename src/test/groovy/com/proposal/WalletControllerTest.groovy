package com.proposal

import com.proposal.domain.Wallet
import com.proposal.infrastructure.rest.micronaut.functions

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
class WalletControllerSpec extends Specification {

    private BlockingHttpClient blockingClient

    @Inject
    @Client('/')
    HttpClient client 

    void setup() {
        blockingClient = client.toBlocking()
    }

    void supplyAnInvalidOrderTriggersValidationFailure() {

        when:
        blockingClient.exchange(HttpRequest.GET('/wallets/list?order=vic'))

        then:
        HttpClientResponseException e = thrown()
        e.response
        BAD_REQUEST == e.status
    }

    void testFindNonExistingWalletReturns404() {
        when:
        blockingClient.exchange(HttpRequest.GET('/wallets/99'))

        then:
        HttpClientResponseException e = thrown()
        e.response
        NOT_FOUND == e.status
    }

    void testWalletCrudOperations() {

        given:
        List<Long> walletsIds = []

        when:
        HttpRequest<?> request = HttpRequest.POST('/wallets', new WalletSaveCommand('DevOps', 10, 1))
        HttpResponse<?> response = blockingClient.exchange(request)
        walletsIds << entityId(response)

        then:
        CREATED == response.status

        when:
        request = HttpRequest.POST('/wallets', new WalletSaveCommand('Microservices', 10, 1)) 
        response = blockingClient.exchange(request)

        then:
        CREATED == response.status

        when:
        Long id = entityId(response)
        walletsIds << id
        request = HttpRequest.GET('/wallets/' + id)

        Wallet wallet = blockingClient.retrieve(request, Wallet) 

        then:
        'Microservices' == wallet.wallet_name

        when:
        request = HttpRequest.PUT('/wallets', new WalletUpdateCommand(id, 'Micro-services', 10, 1))
        response = blockingClient.exchange(request)

        then:
        NO_CONTENT == response.status

        when:
        request = HttpRequest.GET('/wallets/' + id)
        wallet = blockingClient.retrieve(request, Wallet)

        then:
        'Micro-services' == wallet.wallet_name

        when:
        request = HttpRequest.GET('/wallets/list')

        List<Wallet> wallets = blockingClient.retrieve(request, Argument.of(List, Wallet))

        then:
        2 == wallets.size()

        when:
        request = HttpRequest.GET('/wallets/list')
        wallets = blockingClient.retrieve(request, Argument.of(List, Wallet))

        then:
        2 == wallets.size()

        when:
        request = HttpRequest.GET('/wallets/list?max=1')
        wallets = blockingClient.retrieve(request, Argument.of(List, Wallet))

        then:
        1 == wallets.size()
        'DevOps' == wallets[0].wallet_name

        when:
        request = HttpRequest.GET('/wallets/list?max=1&order=desc&sort=wallet_name')
        wallets = blockingClient.retrieve(request, Argument.of(List, Wallet))

        then:
        1 == wallets.size()
        'Micro-services' == wallets[0].wallet_name

        when:
        request = HttpRequest.GET('/wallets/list?max=1&offset=10')
        wallets = blockingClient.retrieve(request, Argument.of(List, Wallet))

        then:
        0 == wallets.size()

       
    }

    private Long entityId(HttpResponse response) {
        String path = '/wallets/'
        String value = response.header(LOCATION)
        if (value == null) {
            return null
        }

        int index = value.indexOf(path)
        if (index != -1) {
            return value.substring(index + path.length()) as long
        }

        null
    }
    
}
