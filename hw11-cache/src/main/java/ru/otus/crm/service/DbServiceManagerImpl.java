package ru.otus.crm.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Manager;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionRunner transactionRunner;
    private final MyCache<Long, Manager> managerCache;

    public DbServiceManagerImpl(TransactionRunner transactionRunner, DataTemplate<Manager> managerDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
        this.managerCache = new MyCache<>();
    }

    public DbServiceManagerImpl(
            TransactionRunner transactionRunner,
            DataTemplate<Manager> managerDataTemplate,
            MyCache<Long, Manager> managerCache) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
        this.managerCache = managerCache;
    }

    @Override
    public Manager saveManager(Manager manager) {
        Manager savedManager = transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = managerDataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel(), manager.getParam1());
                log.info("created manager: {}", createdManager);
                return createdManager;
            }
            managerDataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            return manager;
        });
        managerCache.put(savedManager.getNo(), savedManager);
        return savedManager;
    }

    @Override
    public Optional<Manager> getManager(long no) {
        return Optional.ofNullable(managerCache.get(no))
                .or(() -> transactionRunner.doInTransaction(connection -> {
                    var clientOptional = managerDataTemplate.findById(connection, no);
                    log.info("client: {}", clientOptional);
                    return clientOptional;
                }));
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> managers = transactionRunner.doInTransaction(connection -> {
            var managerList = managerDataTemplate.findAll(connection);
            log.info("managerList:{}", managerList);
            return managerList;
        });
        managers.forEach(manager -> managerCache.put(manager.getNo(), manager));
        return managers;
    }
}
