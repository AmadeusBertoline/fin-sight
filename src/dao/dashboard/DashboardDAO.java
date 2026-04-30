package dao.dashboard;

import model.Dashboard;
import model.TipoInvestimento;
import model.Ativo;

import java.sql.SQLException;
import java.util.List;

public interface DashboardDAO {
    
    Dashboard getResumoCarteira() throws SQLException;
    List<String> getTopAtivos() throws SQLException;
    List<TipoInvestimento> getDistribuicaoPorTipo() throws SQLException;
   
}