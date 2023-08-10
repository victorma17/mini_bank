package example.micronaut

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import jakarta.inject.Inject

@MicronautTest
class DefaultSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    void 'worrking'() {
        expect:
        application.running
    }

}
