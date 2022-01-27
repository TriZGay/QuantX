package io.trizgay.quantx.db;

import io.trizgay.quantx.db.pojo.Plate;
import io.trizgay.quantx.db.pojo.PlateParametersMapper;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.Map;

public class PgService implements DataFetcher {
    private final PgPool pool;
    private final Map<String, String> sqlMap;

    public PgService(PgPool pool, Map<String, String> sqlMap) {
        this.pool = pool;
        this.sqlMap = sqlMap;
    }

    @Override
    public Future<Integer> insetOnePlate(Plate plate) {
        Promise<Integer> promise = Promise.promise();
        SqlTemplate.forUpdate(pool, sqlMap.get("INSET_ONE_PLATE"))
                .mapFrom(PlateParametersMapper.INSTANCE)
                .execute(plate)
                .onSuccess(sqlResult -> promise.complete(1))
                .onFailure(promise::fail);
        return promise.future();
    }
}
