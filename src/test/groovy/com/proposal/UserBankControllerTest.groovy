package com.proposal

import com.proposal.domain.UserBank
import com.proposal.infrastructure.rest.micronaut.functions.UserSaveCommand
import com.proposal.infrastructure.rest.micronaut.functions.UserUpdateCommand

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
class UserBankControllerSpec extends Specification {

    private BlockingHttpClient blockingClient

    @Inject
    @Client('/')
    HttpClient client 

    void setup() {
        blockingClient = client.toBlocking()
    }

    void supplyAnInvalidOrderTriggersValidationFailure() {

        when:
        blockingClient.exchange(HttpRequest.GET('/users/list?order=vic'))

        then:
        HttpClientResponseException e = thrown()
        e.response
        BAD_REQUEST == e.status
    }

    void testFindNonExistingUserBankReturns404() {
        when:
        blockingClient.exchange(HttpRequest.GET('/users/99'))

        then:
        HttpClientResponseException e = thrown()
        e.response
        NOT_FOUND == e.status
    }

    void testUserBankCrudOperations() {

        given:
        List<Long> userBankIds = []

        when:
        HttpRequest<?> request = HttpRequest.POST('/users', new UserSaveCommand('DevOps'))
        HttpResponse<?> response = blockingClient.exchange(request)
        userBankIds << entityId(response)

        then:
        CREATED == response.status

        when:
        request = HttpRequest.POST('/users', new UserSaveCommand('Microservices')) 
        response = blockingClient.exchange(request)

        then:
        CREATED == response.status

        when:
        Long id = entityId(response)
        userBankIds << id
        request = HttpRequest.GET('/users/' + id)

        UserBank userBank = blockingClient.retrieve(request, UserBank) 

        then:
        'Microservices' == userBank.user_name

        when:
        request = HttpRequest.PUT('/users', new UserUpdateCommand(id, 'Micro-services'))
        response = blockingClient.exchange(request)

        then:
        NO_CONTENT == response.status

        when:
        request = HttpRequest.GET('/users/' + id)
        userBank = blockingClient.retrieve(request, UserBank)


        then:
        'Micro-services' == userBank.user_name

        when:
        request = HttpRequest.GET('/users/list')
        List<UserBank> usersBank = blockingClient.retrieve(request, Argument.of(List, UserBank))

        then:
        2 == usersBank.size()

        when:
        request = HttpRequest.GET('/users/list')
        usersBank = blockingClient.retrieve(request, Argument.of(List, UserBank))

        then:
        2 == usersBank.size()

        when:
        request = HttpRequest.GET('/users/list?max=1')
        usersBank = blockingClient.retrieve(request, Argument.of(List, UserBank))

        then:
        1 == usersBank.size()
        'DevOps' == usersBank[0].user_name

        when:
        request = HttpRequest.GET('/users/list?max=1&order=desc&sort=user_name')
        usersBank = blockingClient.retrieve(request, Argument.of(List, UserBank))

        then:
        1 == usersBank.size()
        'Micro-services' == usersBank[0].user_name

        when:
        request = HttpRequest.GET('/users/list?max=1&offset=10')
        usersBank = blockingClient.retrieve(request, Argument.of(List, UserBank))

        then:
        0 == usersBank.size()

       
    }

    private Long entityId(HttpResponse response) {
        String path = '/users/'
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