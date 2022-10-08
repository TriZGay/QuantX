package io.futakotome.quantx.collect.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_plate")
@NamedQuery(name = "Plate.findAll", query = "select p from Plate p")
public class Plate {
    public static final String FIND_ALL = "Plate.findAll";
    @Id
    private Long id;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "code", length = 20)
    private String code;
    @Column(name = "plate_id", length = 20)
    private String plateId;
    @Column(name = "create_date")
    private Long createDate;
    @Column(name = "modify_date")
    private Long modifyDate;

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

    public String getPlateId() {
        return plateId;
    }

    public void setPlateId(String plateId) {
        this.plateId = plateId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Long modifyDate) {
        this.modifyDate = modifyDate;
    }
}
