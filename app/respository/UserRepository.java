package respository;

import io.ebean.*;
import models.User;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class UserRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public UserRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<Optional<User>> lookup(Long id) {
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(User.class).setId(id).findOne()), executionContext);
    }

    public CompletionStage<Long> insert(User user) {
        return supplyAsync(() -> {
            user.id = System.currentTimeMillis(); // not ideal, but it works
            ebeanServer.insert(user);
            return user.id;
        }, executionContext);
    }

    public CompletionStage<Integer> editPuntos(User user, Integer puntos) {
        return supplyAsync(() -> {
            user.puntos=puntos;
            ebeanServer.update(user);
            return user.puntos;
        }, executionContext);
    }

}
