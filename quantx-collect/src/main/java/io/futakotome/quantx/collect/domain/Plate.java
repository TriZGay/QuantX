package io.futakotome.quantx.collect.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_plate")
@NamedQuery(name = "Plate.findAll", query = "select p from Plate p")
@NamedNativeQuery(name = "Plate.insertOne", query = "insert into t_plate(name, code, plate_id) values (?1,?2,?3)")
public class Plate {
    public static final String FIND_ALL = "Plate.findAll";
    public static final String INSERT_ONE = "Plate.insertOne";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_plate_id_seq")
    @Column(name = "id")
    private Long id;
    //板块名称
    @Column(name = "name", length = 20)
    private String name;
    //板块代码
    @Column(name = "code", length = 20)
    private String code;
    //板块id
    @Column(name = "plate_id", length = 20)
    private String plateId;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}
