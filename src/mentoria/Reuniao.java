package mentoria;

import java.time.LocalDateTime;

public class Reuniao {
    private Aluno aluno;
    private Mentor mentor;
    private LocalDateTime dataHora;
    private String motivo;
    
    public Reuniao(Aluno aluno, Mentor mentor, LocalDateTime dataHora, String motivo) {
        this.aluno = aluno;
        this.mentor = mentor;
        this.dataHora = dataHora;
        this.motivo = motivo;
    }
    
    public Aluno getAluno() {
        return aluno;
    }
    
    public Mentor getMentor() {
        return mentor;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public String getMotivo() {
        return motivo;
    }

	public void setDataHora(LocalDateTime novaDataHora) {
		this.dataHora = novaDataHora;
		
	}

	public void setMotivo(String novoMotivo) {
		this.motivo = novoMotivo;
		
	}
}