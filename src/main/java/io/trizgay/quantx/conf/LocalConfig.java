package io.trizgay.quantx.conf;

public class LocalConfig {
    private Server server;

    private FT ft;

    public FT getFt() {
        return ft;
    }

    public void setFt(FT ft) {
        this.ft = ft;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public static class FT {
        private String url;
        private Integer port;
        private boolean isEnableEncrypt;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public boolean getIsEnableEncrypt() {
            return isEnableEncrypt;
        }

        public void setEnableEncrypt(boolean enableEncrypt) {
            isEnableEncrypt = enableEncrypt;
        }
    }

    public static class Server {
        private int port;

        private String path;

        private String host;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

    }

    private Pg readWrite;

    public static class Pg {
        private Pool pool;
        private String host;
        private Integer port;
        private String db;
        private String user;
        private String password;

        public Pool getPool() {
            return pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getDb() {
            return db;
        }

        public void setDb(String db) {
            this.db = db;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Pool {
        private Integer maxSize = 4;
        private Integer maxWaitQueueSize = -1;
        private Integer idleTimeout = 0;
        private Integer poolCleanerPeriod = 1000;
        private Integer connectionTimeout = 30;
        private String poolName = "shardPool";

        public Integer getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(Integer maxSize) {
            this.maxSize = maxSize;
        }

        public Integer getMaxWaitQueueSize() {
            return maxWaitQueueSize;
        }

        public void setMaxWaitQueueSize(Integer maxWaitQueueSize) {
            this.maxWaitQueueSize = maxWaitQueueSize;
        }

        public Integer getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(Integer idleTimeout) {
            this.idleTimeout = idleTimeout;
        }

        public Integer getPoolCleanerPeriod() {
            return poolCleanerPeriod;
        }

        public void setPoolCleanerPeriod(Integer poolCleanerPeriod) {
            this.poolCleanerPeriod = poolCleanerPeriod;
        }

        public Integer getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(Integer connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public String getPoolName() {
            return poolName;
        }

        public void setPoolName(String poolName) {
            this.poolName = poolName;
        }
    }

    public Pg getReadWrite() {
        return readWrite;
    }

    public void setReadWrite(Pg readWrite) {
        this.readWrite = readWrite;
    }

    @Override
    public String toString() {
        return "LocalConfig{" +
                "server=" + server +
                ", ft=" + ft +
                ", readWrite=" + readWrite +
                '}';
    }
}
