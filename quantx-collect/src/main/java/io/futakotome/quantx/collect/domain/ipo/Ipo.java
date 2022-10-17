package io.futakotome.quantx.collect.domain.ipo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_ipo")
@NamedQuery(name = "Ipo.findAll", query = "select i from Ipo i join i.ipoExHk exhk")
public class Ipo {
    public static final String FIND_ALL = "Ipo.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_ipo_id_seq")
    @Column(name = "id")
    private Long id;
    //股票市场
    @Column(name = "security", length = 20)
    private String security;
    //股票名称
    @Column(name = "name", length = 20)
    private String name;
    //上市日期
    @Column(name = "list_time")
    private String listTime;
    //上市日期时间戳
    @Column(name = "list_timestamp")
    private LocalDateTime listTimeStamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ipo_ex_hk_id", referencedColumnName = "id")
    private IpoExHk ipoExHk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public LocalDateTime getListTimeStamp() {
        return listTimeStamp;
    }

    public void setListTimeStamp(LocalDateTime listTimeStamp) {
        this.listTimeStamp = listTimeStamp;
    }

    public IpoExHk getIpoExHk() {
        return ipoExHk;
    }

    public void setIpoExHk(IpoExHk ipoExHk) {
        this.ipoExHk = ipoExHk;
    }
}
