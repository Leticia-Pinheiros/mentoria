package mentoria;

import java.util.ArrayList;
import java.util.List;

public class Mentor extends Pessoa {
    private List<String> areasEspecializacao;
    private List<String> disciplinas;

    public Mentor(String nome, String email) {
        super(nome, email);
        this.areasEspecializacao = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
    }

    public List<String> getAreasEspecializacao() {
        return areasEspecializacao;
    }

    public void adicionarAreaEspecializacao(String areaEspecializacao) {
        this.areasEspecializacao.add(areaEspecializacao);
    }

    public List<String> getDisciplinas() {
        return disciplinas;
    }

    public void adicionarDisciplina(String disciplina) {
        this.disciplinas.add(disciplina);
    }
}
