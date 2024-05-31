package mentoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private List<Aluno> alunos;
    private List<Mentor> mentores;
    private List<Reuniao> reunioes;
    private DateTimeFormatter formatter;


    public Menu() {
        this.alunos = new ArrayList<>();
        this.mentores = new ArrayList<>();
        this.reunioes = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    public void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n=====================================");
            System.out.println("                MENU PRINCIPAL                ");
            System.out.println("=====================================");
            System.out.println("1. Adicionar Alunos");
            System.out.println("2. Adicionar Mentores");
            System.out.println("3. Menu Agendamentos");
            System.out.println("4. Escolher Mentor");
            System.out.println("5. Ver Reuniões Marcadas");
            System.out.println("6. Editar Reunião do Aluno");
            System.out.println("7. Cancelar Reunião do Aluno");
            System.out.println("0. Sair\n");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir nova linha

            switch (opcao) {
                case 1:
                    exibirMenuAlunos(scanner);
                    break;
                case 2:
                    exibirMenuMentores(scanner);
                    break;
                case 3:
                    exibirMenuAgendamento(scanner);
                    break;
                case 4:
                    exibirMenuEscolherMentor(scanner);
                    break;
                case 5:
                    exibirReunioesMarcadas();
                    break;
                case 6:
                    editarReuniaoAluno(scanner);
                    break;
                case 7:
                    cancelarReuniaoAluno(scanner);
                    break;
                case 0:
                    System.out.println("\nSaindo...");
                    break;
                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void exibirMenuAlunos(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("        ADICIONAR NOVO ALUNO          ");
        System.out.println("-------------------------------------");
        System.out.print("Nome do Aluno: ");
        String nome = scanner.nextLine();
        System.out.print("Email do Aluno: ");
        String email = scanner.nextLine();
        System.out.print("Matrícula do Aluno: ");
        String matricula = scanner.nextLine();
        Aluno aluno = new Aluno(nome, email, matricula);
        alunos.add(aluno);
        System.out.println("\nAluno adicionado com sucesso.");
    }

    private void exibirMenuMentores(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("        ADICIONAR NOVO MENTOR         ");
        System.out.println("-------------------------------------");
        System.out.print("Nome do Mentor: ");
        String nome = scanner.nextLine();
        System.out.print("Email do Mentor: ");
        String email = scanner.nextLine();
        Mentor mentor = new Mentor(nome, email);

       
        String areaEspecializacao;
        do {
            System.out.print("Área de Especialização do Mentor (ou 'sair' para terminar): ");
            areaEspecializacao = scanner.nextLine();
            if (!areaEspecializacao.equalsIgnoreCase("sair")) {
                mentor.adicionarAreaEspecializacao(areaEspecializacao);
            }
        } while (!areaEspecializacao.equalsIgnoreCase("sair"));
        System.out.println ("\n");
        
        String disciplina;
        do {
            System.out.print("Disciplina do Mentor (ou 'sair' para terminar): ");
            disciplina = scanner.nextLine();
            if (!disciplina.equalsIgnoreCase("sair")) {
                mentor.adicionarDisciplina(disciplina);
            }
        } while (!disciplina.equalsIgnoreCase("sair"));

        mentores.add(mentor);
        System.out.println("\nMentor adicionado com sucesso.");
    }

    private void exibirMenuAgendamento(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("          AGENDAR REUNIÃO            ");
        System.out.println("-------------------------------------");
        System.out.print("Nome do Aluno: ");
        String nomeAluno = scanner.nextLine();
        Aluno aluno = buscarAlunoPorNome(nomeAluno);
        if (aluno == null) {
            System.out.println("\nAluno não encontrado.");
            return;
        }

        System.out.print("Nome do Mentor: ");
        String nomeMentor = scanner.nextLine();
        Mentor mentor = buscarMentorPorNome(nomeMentor);
        if (mentor == null) {
            System.out.println("\nMentor não encontrado.");
            return;
        }

        System.out.print("Data e Hora (dd/MM/yyyy HH:mm): ");
        String dataHoraStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        System.out.print("Motivo da Reunião: ");
        String motivo = scanner.nextLine();
        
        Reuniao reuniao = new Reuniao(aluno, mentor, dataHora, motivo);
        reunioes.add(reuniao);
        System.out.println("\nReunião agendada com sucesso entre " + aluno.getNome() + " e " + mentor.getNome() + " em " + dataHora.format(formatter));
    }

    private void exibirMenuEscolherMentor(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("          ESCOLHER MENTOR            ");
        System.out.println("-------------------------------------");
        System.out.print("Nome do Aluno: ");
        String nomeAluno = scanner.nextLine();
        Aluno aluno = buscarAlunoPorNome(nomeAluno);
        if (aluno == null) {
            System.out.println("\nAluno não encontrado.");
            return;
        }

        System.out.print("Disciplina do Mentor: ");
        String disciplina = scanner.nextLine();
        System.out.print("Especialização do Mentor: ");
        String especializacao = scanner.nextLine();
        List<Mentor> mentoresDisponiveis = buscarMentoresPorDisciplinaEEspecializacao(disciplina, especializacao);
        if (mentoresDisponiveis.isEmpty()) {
            System.out.println("\nNenhum mentor encontrado com essa especialização e disciplina.");
            return;
        }

        System.out.println("\nMentores disponíveis:\n");
        for (int i = 0; i < mentoresDisponiveis.size(); i++) {
            Mentor mentor = mentoresDisponiveis.get(i);
            System.out.println((i + 1) + ". " + mentor.getNome() + " (" + mentor.getAreasEspecializacao() + ")");
        }
        
        System.out.print("\nEscolha um mentor (número): ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); 

        if (escolha > 0 && escolha <= mentoresDisponiveis.size()) {
            Mentor mentorEscolhido = mentoresDisponiveis.get(escolha - 1);
            aluno.setMentor(mentorEscolhido);
            System.out.println("\nMentor " + mentorEscolhido.getNome() + " atribuído ao aluno " + aluno.getNome() + " com sucesso.");
        } else {
            System.out.println("\nEscolha inválida.");
        }
    }

    private void editarReuniaoAluno(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("        EDITAR REUNIÃO             ");
        System.out.println("-------------------------------------");
        System.out.print("Nome do Aluno: ");
        String nomeAluno = scanner.nextLine();
        Aluno aluno = buscarAlunoPorNome(nomeAluno);
        if (aluno == null) {
            System.out.println("\nAluno não encontrado.");
            return;
        }

        List<Reuniao> reunioesAluno = buscarReunioesPorAluno(aluno);
        if (reunioesAluno.isEmpty()) {
            System.out.println("\nO aluno não tem nenhuma reunião marcada.");
            return;
        }

        System.out.println("\nReuniões marcadas para " + aluno.getNome() + ":\n");
        for (int i = 0; i < reunioesAluno.size(); i++) {
            Reuniao reuniao = reunioesAluno.get(i);
            System.out.println((i + 1) + ". " + "Com " + reuniao.getMentor().getNome() + " em " +
                    reuniao.getDataHora().format(formatter) + " - Motivo: " + reuniao.getMotivo());
        }

        System.out.print("\nEscolha uma reunião para editar (número): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();  

        if (escolha > 0 && escolha <= reunioesAluno.size()) {
            Reuniao reuniao = reunioesAluno.get(escolha - 1);

            System.out.print("Nova Data e Hora (dd/MM/yyyy HH:mm): ");
            String novaDataHoraStr = scanner.nextLine();
            LocalDateTime novaDataHora = LocalDateTime.parse(novaDataHoraStr, formatter);
            reuniao.setDataHora(novaDataHora);

            System.out.print("Novo Motivo: ");
            String novoMotivo = scanner.nextLine();
            reuniao.setMotivo(novoMotivo);

            System.out.println("\nReunião editada com sucesso.");
        } else {
            System.out.println("\nEscolha inválida .");
        }
    }

    private void cancelarReuniaoAluno(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("    CANCELAR REUNIÃO DO ALUNO       ");
        System.out.println("-------------------------------------");
        System.out.print("Nome do Aluno: ");
        String nomeAluno = scanner.nextLine();
        Aluno aluno = buscarAlunoPorNome(nomeAluno);
        if (aluno == null) {
            System.out.println("\nAluno não encontrado.");
            return;
        }

        System.out.println("Reuniões marcadas para " + aluno.getNome() + ":\n");
        List<Reuniao> reunioesAluno = buscarReunioesPorAluno(aluno);
        if (reunioesAluno.isEmpty()) {
            System.out.println("\nNenhuma reunião encontrada para este aluno.");
            return;
        }

        for (int i = 0; i < reunioesAluno.size(); i++) {
            Reuniao reuniao = reunioesAluno.get(i);
            System.out.println((i + 1) + ". Mentor: " + reuniao.getMentor().getNome() + ", Data: " +
                    reuniao.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                    ", Motivo: " + reuniao.getMotivo());
        }

        System.out.print("\nEscolha o número da reunião que deseja cancelar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();  

        if (escolha > 0 && escolha <= reunioesAluno.size()) {
            Reuniao reuniaoCancelada = reunioesAluno.remove(escolha - 1);
            reunioes.remove(reuniaoCancelada);
            System.out.println("\nReunião com o mentor " + reuniaoCancelada.getMentor().getNome() +
                    " no dia " + reuniaoCancelada.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                    " cancelada com sucesso.");
        } else {
            System.out.println("\nEscolha inválida.");
        }
    }

    private void exibirReunioesMarcadas() {
        System.out.println("\n-------------------------------------");
        System.out.println("          REUNIÕES MARCADAS          ");
        System.out.println("-------------------------------------");
        if (reunioes.isEmpty()) {
            System.out.println("\nNenhuma reunião marcada.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            for (Reuniao reuniao : reunioes) {
                System.out.println("Reunião entre " + reuniao.getAluno().getNome() + " e " + reuniao.getMentor().getNome() +
                        " em " + reuniao.getDataHora().format(formatter) + " - Motivo: " + reuniao.getMotivo());
            }
        }
    }

    private Aluno buscarAlunoPorNome(String nome) {
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                return aluno;
            }
        }
        return null;
    }

    private Mentor buscarMentorPorNome(String nome) {
        for (Mentor mentor : mentores) {
            if (mentor.getNome().equalsIgnoreCase(nome)) {
                return mentor;
            }
        }
        return null;
    }

    private List<Mentor> buscarMentoresPorDisciplinaEEspecializacao(String disciplina, String especializacao) {
        return mentores.stream()
                .filter(mentor -> mentor.getDisciplinas().contains(disciplina) &&
                        mentor.getAreasEspecializacao().contains(especializacao))
                .collect(Collectors.toList());
    }

    private List<Reuniao> buscarReunioesPorAluno(Aluno aluno) {
        return reunioes.stream()
                .filter(reuniao -> reuniao.getAluno().equals(aluno))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenuPrincipal();
    }
}