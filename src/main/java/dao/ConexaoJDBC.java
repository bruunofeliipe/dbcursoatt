package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexaoJDBC {

	// <<<<<< CONEXÃO COM O BD >>>>>>
	private Connection conexao;
			
	public boolean conecta() {
		
		try {

			String servidor = "localhost";
			String driver = "com.mysql.cj.jdbc.Driver";	
			String usuario = "root";
			int port = 3306;	
			String banco = "dbcurso";	
			String senha = "1234"; 
			//String urlConexao = driver + "//" + servidor + "/" + banco;
			Class.forName("com.mysql.cj.jdbc.Driver");
			String urlConexao = "jdbc:mysql://localhost:3306/dbcurso?useTimezone=true&serverTimezone=UTC";
		    
		    // registra o Driver
		    Class.forName(servidor);
     	      		    
		    // cria conexão no banco
		    this.conexao = DriverManager.getConnection(urlConexao,usuario, senha);
		    this.conexao.setAutoCommit(true);
	    
            return true;            	        
	    } 

        catch(SQLException e ) {         	            
	    	return false;
        }  catch (ClassNotFoundException e) {
			return false;
		}	
		
	}
	
		
	public PreparedStatement preparaDeclaracao(String sql) {
		
		PreparedStatement declaracaoPrep = null;
      
        // executa um comando DML (update ou insert)
		try {
			declaracaoPrep = this.conexao.prepareStatement(sql);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return declaracaoPrep;
		
	}
	
	

	
		
		
	public ResultSet consulta(String sql) {
			
		ResultSet dbResultado = null;
		
		try {
			
			Statement declaracao = this.conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			
			dbResultado = declaracao.executeQuery(sql);	
			
			if (dbResultado != null)
			    dbResultado.first();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return dbResultado;
     
	}
	
	
}
