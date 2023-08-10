package example.micronaut

import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Nullable
import io.micronaut.serde.annotation.Serdeable

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

@CompileStatic
@Serdeable
class UserSortingAndOrderArguments {

    @Nullable
    @PositiveOrZero 
    Integer offset

    @Nullable
    @Positive 
    Integer max

    @Nullable
    @Pattern(regexp = 'user_id|user_name')  
    String sort

    @Nullable
    @Pattern(regexp = 'asc|ASC|desc|DESC') 
    String order
}
