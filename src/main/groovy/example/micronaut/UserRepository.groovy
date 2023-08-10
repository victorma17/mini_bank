package example.micronaut

import example.micronaut.domain.UserBank

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

interface UserRepository {

    Optional<UserBank> findById(long user_id)

    UserBank save(@NotBlank String user_name)

    List<UserBank> findAll(@NotNull UserSortingAndOrderArguments args)

    int update(long user_id, @NotBlank String user_name)
    
}
