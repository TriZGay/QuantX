package io.trizgay.quantx.db;


import io.trizgay.quantx.db.pojo.Plate;
import io.trizgay.quantx.db.pojo.Security;
import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;

import java.util.List;
import java.util.Map;

public interface DataFetcher {

    static DataFetcher create(PgPool pool, Map<String, String> sqlMap) {
        return new PgService(pool, sqlMap);
    }

    Future<Integer> insertPlateBatch(List<Plate> plate);

    Future<Integer> insertSecurityBatch(List<Security> securities);
}
