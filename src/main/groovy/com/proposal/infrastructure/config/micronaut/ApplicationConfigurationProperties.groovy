package com.proposal.infrastructure.config.micronaut

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.ConfigurationProperties

@CompileStatic
@ConfigurationProperties('application')
class ApplicationConfigurationProperties implements ApplicationConfiguration {

    private final int DEFAULT_MAX = 10

    int max = DEFAULT_MAX
}
