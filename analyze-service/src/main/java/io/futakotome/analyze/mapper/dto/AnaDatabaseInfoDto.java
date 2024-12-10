package io.futakotome.analyze.mapper.dto;

public class AnaDatabaseInfoDto {
    private String database;
    private String table;
    private String size;
    private String bytesOnDisk;
    private String dataUncompressedBytes;
    private String dataCompressedBytes;
    private Double compressedRate;
    private String rows;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBytesOnDisk() {
        return bytesOnDisk;
    }

    public void setBytesOnDisk(String bytesOnDisk) {
        this.bytesOnDisk = bytesOnDisk;
    }

    public String getDataUncompressedBytes() {
        return dataUncompressedBytes;
    }

    public void setDataUncompressedBytes(String dataUncompressedBytes) {
        this.dataUncompressedBytes = dataUncompressedBytes;
    }

    public String getDataCompressedBytes() {
        return dataCompressedBytes;
    }

    public void setDataCompressedBytes(String dataCompressedBytes) {
        this.dataCompressedBytes = dataCompressedBytes;
    }

    public Double getCompressedRate() {
        return compressedRate;
    }

    public void setCompressedRate(Double compressedRate) {
        this.compressedRate = compressedRate;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
}
