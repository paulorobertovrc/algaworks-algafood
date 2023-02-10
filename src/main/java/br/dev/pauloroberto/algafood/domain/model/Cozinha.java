package br.dev.pauloroberto.algafood.domain.model;

import javax.persistence.*;

@Entity
public class Cozinha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cozinha cozinha)) return false;

        return getId().equals(cozinha.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Cozinha{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
