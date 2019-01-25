package com.estados.br.model;

import javax.persistence.*;

@Entity
public class Cidades {

    @Id
    @SequenceGenerator(name = "id", sequenceName = "id_cid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Integer id_cid;
    private String nome;
    private String sigla;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cidades_estados",
            joinColumns = {@JoinColumn(name = "id_cidade", referencedColumnName = "id_cid")},
            inverseJoinColumns = {@JoinColumn(name = "id_estado", referencedColumnName = "id")}
    )
    private Estados estados;

    public Integer getId_cid() {
        return id_cid;
    }

    public void setId_cid(Integer id_cid) {
        this.id_cid = id_cid;
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

    public Estados getEstados() {
        return estados;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }
}
