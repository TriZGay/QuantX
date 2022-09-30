package io.futakotome.quantx.collect.domain;

import javax.persistence.*;

@Entity
@Table(name = "known_fruits")
@NamedQuery(name = "Fruits.findAll", query = "select f from Fruit f order by f.name")
@NamedQuery(name = "Fruits.findByName", query = "select f from Fruit f where f.name=:name")
public class Fruit {
    @Id
    @SequenceGenerator(name = "fruitsSequence", sequenceName = "known_fruits_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "fruitsSequence")
    private Integer id;

    @Column(length = 40, unique = true)
    private String name;

    public Fruit() {
    }

    public Fruit(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
