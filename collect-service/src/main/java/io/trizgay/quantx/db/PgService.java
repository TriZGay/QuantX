package io.trizgay.quantx.db;

import io.trizgay.quantx.db.pojo.Plate;
import io.trizgay.quantx.db.pojo.PlateParametersMapper;
import io.trizgay.quantx.db.pojo.Security;
import io.trizgay.quantx.db.pojo.SecurityParametersMapper;
import io.trizgay.quantx.utils.Log;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.List;
import java.util.Map;

public class PgService implements DataFetcher {
    private final PgPool pool;
    private final Map<String, String> sqlMap;
    private static final String BATCH_INSERT_PLATES = "insert into t_plate(name, code, market) " +
            "values (#{name},#{code},#{market}) on conflict (code) do update set name=excluded.name,code=excluded.code,market=excluded.market " +
            "where t_plate.name is distinct from excluded.name or t_plate.code is distinct from excluded.code or t_plate.market is distinct from excluded.market";

    private static final String BATCH_INSERT_STOCK = "insert into t_security(name, lot_size, sec_type, list_time, de_listing,exchange_type, identity, market, code) " +
            "values (#{name},#{lotSize},#{secType},#{listTime},#{deListing},#{exchangeType},#{identity},#{market},#{code})";

    public PgService(PgPool pool, Map<String, String> sqlMap) {
        this.pool = pool;
        this.sqlMap = sqlMap;
    }

    @Override
    public Future<Integer> insertPlateBatch(List<Plate> plates) {
        Promise<Integer> promise = Promise.promise();
        SqlTemplate.forUpdate(pool, BATCH_INSERT_PLATES)
                .mapFrom(PlateParametersMapper.INSTANCE)
                .executeBatch(plates)
                .onSuccess(sqlResult -> promise.complete(plates.size()))
                .onFailure(promise::fail);
        return promise.future();
    }

    @Override
    public Future<Integer> insertSecurityBatch(List<Security> securities) {
        Promise<Integer> promise = Promise.promise();
        SqlTemplate.forUpdate(pool, BATCH_INSERT_STOCK)
                .mapFrom(SecurityParametersMapper.INSTANCE)
                .executeBatch(securities)
                .onSuccess(sqlResult -> promise.complete(securities.size()))
                .onFailure(promise::fail);
        return promise.future();
    }
}
