package com.co.common.repository;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import com.co.common.models.Bicicleta;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class BicicletaRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public BicicletaRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<Optional<Bicicleta>> lookupByCodigo(String codigo) {
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(Bicicleta.class).where().eq("codigo", codigo).findOne()), executionContext);
    }

}
