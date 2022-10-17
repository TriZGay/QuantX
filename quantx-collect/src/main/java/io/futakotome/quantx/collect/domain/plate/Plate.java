package io.futakotome.quantx.collect.domain.plate;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "t_plate")
@NamedQuery(name = "Plate.findAll", query = "select p from Plate p")
public class Plate {
    public static final String FIND_ALL = "Plate.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_plate_id_seq")
    @Column(name = "id")
    private Long id;
    //板块名称
    @Audited
    @Column(name = "name", length = 20)
    private String name;
    //板块代码
    @Column(name = "code", length = 20)
    private String code;
    //板块市场
    @Column(name = "plate_id", length = 20)
    private Integer market;

    public Plate() {
    }

    public Plate(String name, String code, Integer market) {
        this.name = name;
        this.code = code;
        this.market = market;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }
}
