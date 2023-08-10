package example.micronaut

import example.micronaut.domain.Wallet
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceException
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Singleton
class WalletRepositoryImpl implements WalletRepository {

    private static final List<String> VALID_PROPERTY_NAMES = ['wallet_id', 'wallet_name', 'amount', 'owner_id']
    private final EntityManager entityManager 
    private final ApplicationConfiguration applicationConfiguration

    WalletRepositoryImpl(EntityManager entityManager, ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager
        this.applicationConfiguration = applicationConfiguration
    }

    @Override
    @ReadOnly
    Optional<Wallet> findById(long wallet_id) {
        Optional.ofNullable(entityManager.find(Wallet, wallet_id))
    }

    @Override
    @Transactional
    Wallet save(@NotBlank String wallet_name, long amount, long owner_id) {
        Wallet wallet = new Wallet(wallet_name, amount, owner_id)
        entityManager.persist(wallet)
        wallet
    }

    @ReadOnly
    List<Wallet> findAll(@NotNull WalletSortingAndOrderArguments args) {
        String qlString = 'SELECT w FROM Wallet as w'
        if (args.order && args.sort && VALID_PROPERTY_NAMES.contains(args.sort)) {
            qlString += ' ORDER BY w.' + args.sort + ' ' + args.order.toLowerCase()
        }
        TypedQuery<Wallet> query = entityManager.createQuery(qlString, Wallet)
        query.maxResults = args.max != null ? args.max : applicationConfiguration.max
        if (args.offset) {
            query.firstResult = args.offset
        }
        query.resultList
    }

    @Override
    @Transactional
    int update(long wallet_id, @NotBlank String wallet_name, long amount, long owner_id) {
        entityManager.createQuery('UPDATE Wallet w SET wallet_name = :wallet_name, amount = :amount, owner_id = :owner_id where wallet_id = :wallet_id')
            .setParameter('wallet_id', wallet_id)
            .setParameter('wallet_name', wallet_name)
            .setParameter('amount', amount)
            .setParameter('owner_id', owner_id)

            .executeUpdate()
    }
}