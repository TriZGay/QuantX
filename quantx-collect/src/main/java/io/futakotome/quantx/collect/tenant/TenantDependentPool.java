package io.futakotome.quantx.collect.tenant;

import io.vertx.core.*;
import io.vertx.sqlclient.*;
import org.hibernate.reactive.pool.impl.DefaultSqlClientPool;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class TenantDependentPool extends DefaultSqlClientPool {
    private static final Logger LOGGER = Logger.getLogger(TenantDependentPool.class.getName());

    private MultiplePools pools;

    @Override
    protected Pool getTenantPool(String tenantId) {
        return pools.getTenantPool(MyCurrentTenantIdentifierResolver.Tenant.valueOf(tenantId));
    }

    @Override
    protected Pool createPool(URI uri, SqlConnectOptions connectOptions, PoolOptions poolOptions, Vertx vertx) {
        Map<MyCurrentTenantIdentifierResolver.Tenant, Pool> poolMap = stream(
                MyCurrentTenantIdentifierResolver.Tenant.values()
        ).collect(Collectors.toMap(
                tenant -> tenant,
                tenant -> createPool(uri, connectOptions, poolOptions, vertx, tenant)
        ));
        pools = new MultiplePools(poolMap);
        return pools;
    }

    private Pool createPool(URI uri, SqlConnectOptions connectOptions, PoolOptions poolOptions, Vertx vertx, MyCurrentTenantIdentifierResolver.Tenant tenant) {
        return super.createPool(changeDbName(uri, tenant), changeDbName(connectOptions, tenant), poolOptions, vertx);
    }

    private static URI changeDbName(URI uri, MyCurrentTenantIdentifierResolver.Tenant tenant) {
        String uriAsString = uri.toString()
                .replaceAll("/[\\w\\d]+\\?", "/" + tenant.getName() + "?");
        LOGGER.infov("real sql uri : ${0}", uriAsString);
        return URI.create(uriAsString);
    }

    private SqlConnectOptions changeDbName(SqlConnectOptions sqlConnectOptions, MyCurrentTenantIdentifierResolver.Tenant tenantId) {
        SqlConnectOptions newOptions = new SqlConnectOptions(sqlConnectOptions);
        newOptions.setDatabase(tenantId.getName());
        return newOptions;
    }

    private static class MultiplePools implements Pool {
        private final Map<MyCurrentTenantIdentifierResolver.Tenant, Pool> poolMap;
        private final MyCurrentTenantIdentifierResolver.Tenant defaultTenantId;

        public MultiplePools(Map<MyCurrentTenantIdentifierResolver.Tenant, Pool> poolMap) {
            this.poolMap = poolMap;
            this.defaultTenantId = MyCurrentTenantIdentifierResolver.Tenant.DEFAULT;
        }

        public Pool getTenantPool(MyCurrentTenantIdentifierResolver.Tenant tenantId) {
            return poolMap.get(tenantId);
        }

        @Override
        public void getConnection(Handler<AsyncResult<SqlConnection>> handler) {
            poolMap.get(defaultTenantId).getConnection(handler);
        }

        @Override
        public Future<SqlConnection> getConnection() {
            return poolMap.get(defaultTenantId).getConnection();
        }

        @Override
        public Query<RowSet<Row>> query(String sql) {
            return poolMap.get(defaultTenantId).preparedQuery(sql);
        }

        @Override
        public PreparedQuery<RowSet<Row>> preparedQuery(String sql) {
            return poolMap.get(defaultTenantId).preparedQuery(sql);
        }

        @Override
        public PreparedQuery<RowSet<Row>> preparedQuery(String s, PrepareOptions prepareOptions) {
            return poolMap.get(defaultTenantId).preparedQuery(s, prepareOptions);
        }

        @Override
        public void close(Handler<AsyncResult<Void>> handler) {
            poolMap.forEach((tenant, pool) -> pool.close(handler));
        }

        @Override
        public Future<Void> close() {
            Future<Void> close = Future.succeededFuture();
            for (Pool pool : poolMap.values()) {
                close = close.compose(unused -> pool.close());
            }
            return close;
        }

        @Override
        public Pool connectHandler(Handler<SqlConnection> handler) {
            return poolMap.get(defaultTenantId).connectHandler(handler);
        }

        @Override
        public Pool connectionProvider(Function<Context, Future<SqlConnection>> function) {
            return poolMap.get(defaultTenantId).connectionProvider(function);
        }

        @Override
        public int size() {
            return 8;
        }
    }
}
