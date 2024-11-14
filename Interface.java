package aulaconecao;

import java.sql.*;
import java.util.Scanner;

public class Interface {
    private String nome;
    private int idade;

    // Configuração do banco de dados
    private final String URL = "jdbc:mysql://localhost:3306/aula";
    private final String USUARIO = "root";
    private final String SENHA = "sua_senha";

    public Interface() {
        this.nome = null;
        this.idade = 0;
    }

    // Conecta ao banco de dados e retorna a conexão
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    // Lê os dados de um aluno específico do banco de dados
    public void lerDados(int alunoId) {
        try (Connection conexao = conectar()) {
            String sql = "SELECT nome, idade FROM alunos WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, alunoId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.nome = rs.getString("nome");
                this.idade = rs.getInt("idade");
                System.out.println("Dados do aluno lidos com sucesso!");
            } else {
                System.out.println("Aluno não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao ler dados: " + e.getMessage());
        }
    }

    // Insere um novo aluno no banco de dados
    public void inserirAluno() {
        try (Connection conexao = conectar()) {
            String sql = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o nome do aluno: ");
            this.nome = scanner.nextLine();
            System.out.print("Digite a idade do aluno: ");
            this.idade = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline
            
            stmt.setString(1, this.nome);
            stmt.setInt(2, this.idade);
            stmt.executeUpdate();
            
            System.out.println("Aluno inserido com sucesso no banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir aluno: " + e.getMessage());
        }
    }

    // Atualiza o nome do aluno no banco de dados
    public void atualizarNome(int alunoId) {
        try (Connection conexao = conectar()) {
            String sql = "UPDATE alunos SET nome = ? WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o novo nome do aluno: ");
            this.nome = scanner.nextLine();
            
            stmt.setString(1, this.nome);
            stmt.setInt(2, alunoId);
            stmt.executeUpdate();
            
            System.out.println("Nome do aluno atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o nome: " + e.getMessage());
        }
    }

    // Deleta um aluno do banco de dados
    public void deletarAluno(int alunoId) {
        try (Connection conexao = conectar()) {
            String sql = "DELETE FROM alunos WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setInt(1, alunoId);
            stmt.executeUpdate();
            
            System.out.println("Aluno deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar aluno: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Interface aluno = new Interface();
        int opcao;
        int alunoId = 1; // Exemplo de ID de aluno. Pode ser solicitado ao usuário.

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Inserir novo aluno");
            System.out.println("2. Atualizar nome do aluno");
            System.out.println("3. Deletar aluno");
            System.out.println("4. Ler dados do aluno");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            switch (opcao) {
                case 1:
                    aluno.inserirAluno();
                    break;
                case 2:
                    aluno.atualizarNome(alunoId);
                    break;
                case 3:
                    aluno.deletarAluno(alunoId);
                    break;
                case 4:
                    aluno.lerDados(alunoId);
                    aluno.exibirInfo();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);

        scanner.close();
    }

    // Exibe as informações do aluno em memória
    public void exibirInfo() {
        if (nome != null && idade != 0) {
            System.out.println("Nome do aluno: " + nome);
            System.out.println("Idade do aluno: " + idade);
        } else {
            System.out.println("Nenhum aluno registrado.");
        }
    }
}
