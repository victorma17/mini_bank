package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import jakarta.validation.constraints.NotBlank

@CompileStatic
@Serdeable 
class UserSaveCommand {

    @NotBlank
    String user_name

    UserSaveCommand(String user_name) {
        this.user_name = user_name
    }
}
