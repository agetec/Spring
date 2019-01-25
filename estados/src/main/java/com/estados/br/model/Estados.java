package com.estados.br.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estados {
    @Id
    @SequenceGenerator(name = "id", sequenceName = "id_est")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Integer id;
    private String nome;
    private String sigla;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cidades_estados",
            joinColumns = {@JoinColumn(name = "id_estado", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_cidade", referencedColumnName = "id_cid")}
    )
    private List<Cidades> cidades;

    public Estados() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }


}
