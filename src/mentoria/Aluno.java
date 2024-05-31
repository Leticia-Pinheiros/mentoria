package mentoria;

public class Aluno extends Pessoa {
	private String matricula;
	private Mentor mentor;
	
	public Aluno(String nome, String email, String matricula) {
		super (nome, email);
		this.matricula = matricula;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public Mentor getMentor() {
		return mentor;
	}
	
	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}
	
}

