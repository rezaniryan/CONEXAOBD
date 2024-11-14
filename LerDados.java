package aulaconecao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LerDados {

    // Tornando o método estático para ser chamado diretamente do menu
    public static void lerAlunos() {
        Connection conexao = ConexaoBD.conectar(); // A conexão com o banco de dados

        if (conexao != null) {
            String sql = "SELECT * FROM alunos"; // Consulta para ler os alunos
            try {
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(); // Executa a consulta e armazena os resultados

                // Exibindo os alunos encontrados no banco de dados
                System.out.println("\n===== Lista de Alunos =====");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");

                    // Exibe o id, nome e idade de cada aluno
                    System.out.println("ID: " + id + " | Nome: " + nome + " | Idade: " + idade);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao ler dados: " + e.getMessage());
            } finally {
                try {
                    if (conexao != null) {
                        conexao.close(); // Fecha a conexão após o uso
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}