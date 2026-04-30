package dao.dashboard;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.connection.ConnectionFactory;
import model.Dashboard;
import model.TipoInvestimento;

public class DashboarDAOImpl implements DashboardDAO {

    private final ConnectionFactory conexao = new ConnectionFactory();

    @Override
    public Dashboard getResumoCarteira() throws SQLException {
        String sql = "{call resumoCarteira()}";

        // O try-with-resources garante o fechamento da conexão, statement e resultset
        try (Connection con = conexao.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            if (rs.next()) {
                return new Dashboard(
                    rs.getBigDecimal("total_investido"), 
                    rs.getBigDecimal("valorizacao"),
                    rs.getBigDecimal("desvalorizacao")
                );
            }
        } catch (SQLException e) {
            // Logamos o erro técnico e lançamos para que o Controller saiba que falhou
            System.err.println("Erro ao buscar resumo da carteira: " + e.getMessage());
            throw e; 
        }
        return null;
    }

    @Override
    public List<String> getTopAtivos() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "{call totalPorTicker()}";

        try (Connection con = conexao.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                String ticker = rs.getString("ticker");
                int total = rs.getInt("total_cotas");
                lista.add(ticker + " - " + total + " cotas");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao chamar procedure totalPorTicker: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    @Override
    public List<TipoInvestimento> getDistribuicaoPorTipo() throws SQLException {
        List<TipoInvestimento> lista = new ArrayList<>();
        String sql = "SELECT tipo, SUM(valor_unitario) as total FROM ativos GROUP BY tipo";

        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new TipoInvestimento(rs.getString("tipo"), rs.getBigDecimal("total")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar distribuição por tipo: " + e.getMessage());
            throw e;
        }
        return lista;
    }
}