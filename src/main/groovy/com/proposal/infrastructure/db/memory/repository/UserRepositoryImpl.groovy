package com.proposal.infrastructure.db.memory.repository

import com.proposal.domain.UserBank
import com.proposal.application.repository.UserRepository
import com.proposal.infrastructure.rest.micronaut.functions.UserSortingAndOrderArguments
import com.proposal.infrastructure.config.micronaut.ApplicationConfiguration

import io.micronaut.transaction.annotation.ReadOnly

import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceException
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Singleton 
class UserRepositoryImpl implements UserRepository {

    private static final List<String> VALID_PROPERTY_NAMES = ['user_id', 'user_name']

    private final EntityManager entityManager 
    private final ApplicationConfiguration applicationConfiguration

    UserRepositoryImpl(EntityManager entityManager,
                        ApplicationConfiguration applicationConfiguration) { 
        this.entityManager = entityManager
        this.applicationConfiguration = applicationConfiguration
    }

    @Override
    @ReadOnly 
    Optional<UserBank> findById(long user_id) {
        Optional.ofNullable(entityManager.find(UserBank, user_id))
    }

    @Override
    @Transactional 
    UserBank save(@NotBlank String user_name) {
        UserBank userBank = new UserBank(user_name)
        entityManager.persist(userBank)
        userBank
    }

    @ReadOnly 
    List<UserBank> findAll(@NotNull UserSortingAndOrderArguments args) {
        String qlString = 'SELECT u FROM UserBank as u'
        if (args.order && args.sort && VALID_PROPERTY_NAMES.contains(args.sort)) {
            qlString += ' ORDER BY u.' + args.sort + ' ' + args.order.toLowerCase()
        }
        TypedQuery<UserBank> query = entityManager.createQuery(qlString, UserBank)
        query.maxResults = args.max != null ? args.max : applicationConfiguration.max
        if (args.offset) {
            query.firstResult = args.offset
        }
        query.resultList
    }

    @Override
    @Transactional 
    int update(long user_id, @NotBlank String user_name) {
        entityManager.createQuery('UPDATE UserBank g SET user_name = :user_name where user_id = :user_id')
                .setParameter('user_name', user_name)
                .setParameter('user_id', user_id)
                .executeUpdate()
    }
}
