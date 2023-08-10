package example.micronaut

import example.micronaut.domain.UserBank
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
@Controller('/users')
class UserController {

    private final UserRepository userRepository

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Get('/{user_id}')
    UserBank show(Long user_id) {
        userRepository
            .findById(user_id)
            .orElse(null) 
    }

    @Put 
    HttpResponse<?> update(@Body @Valid UserUpdateCommand command) { 
        int numberOfEntitiesUpdated = userRepository.update(command.user_id, command.user_name)
        HttpResponse
            .noContent()
            .header(LOCATION, location(command.user_id).path) 
    }

    @Get(value = '/list{?args*}') 
    List<UserBank> list(@Valid UserSortingAndOrderArguments args) {
        userRepository.findAll(args)
    }

    @Post 
    HttpResponse<UserBank> save(@Body @Valid UserSaveCommand cmd) {
        UserBank userb = userRepository.save(cmd.user_name)
        HttpResponse
            .created(userb)
            .headers(headers -> headers.location(location(userb.user_id)))
    }

    private URI location(Long user_id) {
        URI.create('/users/' + user_id)
    }
}
