package com.co.common.repository;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import com.co.common.models.PrestamoDTO;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class PrestamoRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public PrestamoRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<Long> insert(PrestamoDTO prestamo) {
        return supplyAsync(() -> {
            prestamo.id = System.currentTimeMillis(); // not ideal, but it works
            ebeanServer.insert(prestamo);
            return prestamo.id;
        }, executionContext);
    }
}
