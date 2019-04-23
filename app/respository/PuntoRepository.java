package respository;

import io.ebean.*;
import models.Punto;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class PuntoRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public PuntoRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Punto>> lookupByUserId(Long idUsuario) {
        return supplyAsync(() -> ebeanServer.find(Punto.class).where().eq("id_usuario",idUsuario).findList(), executionContext);
    }

    public CompletionStage<Long> insert(Punto punto) {
        return supplyAsync(() -> {
            punto.id = System.currentTimeMillis(); // not ideal, but it works
            ebeanServer.insert(punto);
            return punto.id;
        }, executionContext);
    }

}
