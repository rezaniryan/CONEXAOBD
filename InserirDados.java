package aulaconecao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InserirDados {

    public static void inserirAluno(Scanner scanner) {
        Connection conexao = ConexaoBD.conectar();
        if (conexao != null) {
            String sql = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";
            try {
                System.out.print("Digite o nome do aluno: ");
                String nome = scanner.nextLine();

                System.out.print("Digite a idade do aluno: ");
                int idade = scanner.nextInt();

                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.executeUpdate();

                System.out.println("Dados inseridos com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao inserir dados: " + e.getMessage());
            } finally {
                try {
                    if (conexao != null) conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}
