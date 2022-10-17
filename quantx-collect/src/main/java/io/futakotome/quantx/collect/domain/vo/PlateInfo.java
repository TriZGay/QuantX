package io.futakotome.quantx.collect.domain.vo;

public class PlateInfo {
    private String name;
    private Plate plate;

    public static class Plate {
        private Integer market;
        private String code;

        public Integer getMarket() {
            return market;
        }

        public void setMarket(Integer market) {
            this.market = market;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "Plate{" +
                    "market=" + market +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plate getPlate() {
        return plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    @Override
    public String toString() {
        return "PlateInfo{" +
                "name='" + name + '\'' +
                ", plate=" + plate +
                '}';
    }
}
