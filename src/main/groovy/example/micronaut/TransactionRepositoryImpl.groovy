package example.micronaut

import example.micronaut.domain.Transaction
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceException
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotNull

@Singleton
class TransactionRepositoryImpl implements TransactionRepository {

    private static final List<String> VALID_PROPERTY_NAMES = ['trx_id', 'wallet_source', 'wallet_destination', 'quantity']

    private final EntityManager entityManager
    private final ApplicationConfiguration applicationConfiguration

    TransactionRepositoryImpl(EntityManager entityManager,
                        ApplicationConfiguration applicationConfiguration) { 
        this.entityManager = entityManager
        this.applicationConfiguration = applicationConfiguration
    }

    @Override
    @ReadOnly
    Optional<Transaction> findById(long trx_id) {
        Optional.ofNullable(entityManager.find(Transaction, trx_id))
    }

    @Override
    @Transactional 
    Transaction save(long wallet_source, long wallet_destination, long quantity) {
        Transaction transaction1 = new Transaction(wallet_source, wallet_destination, quantity)
        entityManager.persist(transaction1)
                entityManager.createQuery('UPDATE Wallet w SET amount = :quantity + amount where wallet_id = :wallet_destination')

                .setParameter('quantity', quantity)
                .setParameter('wallet_destination', wallet_destination)
                .executeUpdate()

            transaction1

         Transaction transaction2 = new Transaction(wallet_source, wallet_destination, quantity)
                entityManager.createQuery('UPDATE Wallet w SET amount = amount - :quantity where wallet_id = :wallet_source')

                .setParameter('quantity', quantity)
                .setParameter('wallet_source', wallet_source)
                .executeUpdate()

            transaction2
            
    }

    @ReadOnly 
    List<Transaction> findAll(@NotNull TransactionSortingAndOrderArguments args) {
        String qlString = 'SELECT w FROM Transaction as w'
        if (args.order && args.sort && VALID_PROPERTY_NAMES.contains(args.sort)) {
            qlString += ' ORDER BY w.' + args.sort + ' ' + args.order.toLowerCase()
        }
        TypedQuery<Transaction> query = entityManager.createQuery(qlString, Transaction)
        query.maxResults = args.max != null ? args.max : applicationConfiguration.max
        if (args.offset) {
            query.firstResult = args.offset
        }
        query.resultList
    }

    @ReadOnly 
    @Transactional 
    List<Transaction> discover(@NotNull long wallet_id){
        String qlString = 'SELECT w FROM Transaction as w'
        
        qlString += ' WHERE wallet_source = ' + wallet_id + ' OR wallet_destination = ' + wallet_id

        TypedQuery<Transaction> query = entityManager.createQuery(qlString, Transaction)

        query.resultList
    }
}
