package com.proposal.infrastructure.rest.micronaut.resources

import com.proposal.domain.Wallet
import com.proposal.application.repository.WalletRepository
import com.proposal.infrastructure.rest.micronaut.functions.WalletSaveCommand
import com.proposal.infrastructure.rest.micronaut.functions.WalletUpdateCommand
import com.proposal.infrastructure.rest.micronaut.functions.WalletSortingAndOrderArguments

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
@Controller('/wallets')  
class WalletController {

    private final WalletRepository walletRepository

    WalletController(WalletRepository walletRepository) { 
        this.walletRepository = walletRepository
    }

    @Get('/{wallet_id}') 
    Wallet show(Long wallet_id) {
        walletRepository
            .findById(wallet_id)
            .orElse(null) 
    }

    @Put 
    HttpResponse<?> update(@Body @Valid WalletUpdateCommand command) { 
        int numberOfEntitiesUpdated = walletRepository.update(command.wallet_id, command.wallet_name, command.amount, command.owner_id)

        HttpResponse
            .noContent()
            .header(LOCATION, location(command.wallet_id).path)
    }

    @Get(value = '/list{?args*}') 
    List<Wallet> list(@Valid WalletSortingAndOrderArguments args) {
        walletRepository.findAll(args)
    }

    @Post 
    HttpResponse<Wallet> save(@Body @Valid WalletSaveCommand cmd) {
        Wallet wallet = walletRepository.save(cmd.wallet_name, cmd.amount, cmd.owner_id)

        HttpResponse
            .created(wallet)
            .headers(headers -> headers.location(location(wallet.wallet_id)))
    }

    private URI location(Long wallet_id) {
        URI.create('/wallets/' + wallet_id)
    }
}
