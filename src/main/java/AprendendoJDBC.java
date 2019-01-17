
// Importa��o do Pacote java.sql para acesso a bancos de dados relacionais
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AprendendoJDBC {
	
	public static void main(String[] args) {
					
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< CONEX�O COM O BANCO DE DADOS  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); 
				
		/* As classes Connection, Statement, PreparedStatement e ResultSet
		 * fazem parte do pacote java.sql e servem para acessar
		 * a persistencia de dados (banco de dados)
		 */						
						
	    // Dados necess�rios para CONECTAR AO BANCO DE DADOS
		String servidor = "localhost";
		String driver = "com.mysql.cj.jdbc.Driver";	
		String usuario = "root";
		int port = 3306;	
		String banco = "dbcurso";	
		String senha = "1234"; 
		//String urlConexao = driver + "//" + servidor + "/" + banco;
		String urlConexao = "jdbc:mysql://localhost:3306/dbcurso?useTimezone=true&serverTimezone=UTC";
	    
    		
        // <<<<<< CONEX�O COM O BD >>>>>>
     	Connection conexao = null;             
		try {        
	        conexao = DriverManager.getConnection(urlConexao,usuario, senha);
	        conexao.setAutoCommit(true);
	        
	        System.out.println(urlConexao);
            System.out.println("Conex�o Realizada com sucesso !"); 
	        
	    }		
        catch(SQLException e ) {
        	//e.printStackTrace();
        	System.out.println("Erro de Conex�o:" + e.getErrorCode() + " " + e.getMessage().toString());
        	return;
        }	  
		// <<<<<< FIM DA CONEX�O  COM O BD >>>>>>> 

		
		
		/* Banco mysql
	    String driver = "jdbc:mysql:";
	    String urlConexao = driver + "//" + servidor +  "/" + banco;
	    String usuario = "root";
	    */
		
		/*                
           jdbc:oracle:thin:@(description=(address=(host=HOSTNAME)(protocol=tcp)(port=PORT))(connect_data=(service_name=SERVICENAME)(server=SHARED)));              
           jdbc:oracle:thin:@(description=(address=(host=localhost)(protocol=tcp)(port=1521))(connect_data=(service_name=XE)(server=SHARED)));        
        */
		
		
		
		
		// <<<<<< INSTRU��ES DML  >>>>>>> 

		
		

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< execu��o de c�digo SQL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"); 
        
		
		// executa um comando DML (update ou insert)
		try {	
			// instru��o SQL a ser enviada ao BD
			Statement statement = conexao.createStatement();
			
			statement.execute("insert into tb_aluno (nome,cpf,matricula,idade) values ('Jo�o da Silva','00100003004','abdefg',18)");
						
		} catch (SQLException e) {
			System.out.println("Erro de insert:" + e.getMessage().toString());
		}
				
		
		
		// executa um comando DML (update ou insert)
		/* Mais otimizado (rapida) para execu��es repetidas 
		 * e pr�tica com m�todos SET 
		 */
		try {
			String sql = "";		
			sql = "insert into tb_aluno (nome,cpf,matricula,idade,sexo)";
			sql += " values (?,?,?,?,?)";					
			PreparedStatement prepStatement = conexao.prepareStatement(sql);	
			
			prepStatement.setString(1, "Zezinho");
			prepStatement.setString(2, "1267");
			prepStatement.setString(3, "aau");
			prepStatement.setInt(4, 15);
			prepStatement.setString(5, "M");
			
			prepStatement.execute(); 
			
		} catch (SQLException e) {
			System.out.println("Erro de insert Prepared:" + e.getMessage().toString());
		}
			
		
		
		
		// <<<<<< INSTRU��ES SELECT  >>>>>>> 
				
		
		
		System.out.println("");			
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<< execu��o de CONSULTA SQL (com retorno de dados)  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>") ;

		
		System.out.println("Consultando disciplinas...");		
		
		try {						
			Statement statement = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			/* a classe ResultSet respresenta uma matriz 
               contendo as colunas e linhas resultantes 
               da consulta select */
			ResultSet dbResultado = statement.executeQuery("Select id, titulo from tb_disciplina");
			
			// metodo que posiciona no primeiro registro da consulta
			dbResultado.first();
			System.out.println("Disciplina: " + dbResultado.getString("id") + " " + dbResultado.getString("titulo"));
			
			// metodo que posiciona no ultimo registro da consulta
			dbResultado.last();
			System.out.println("Disciplina: " + dbResultado.getString("id") + " " + dbResultado.getString("titulo"));
			
			
		} catch (SQLException e) {

			System.out.println("Erro :" + e.getMessage().toString());
		}
		
		
		
		System.out.println("");
		
		
		
		try {
			
			Statement statement = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet dbResultado = statement.executeQuery("Select nome, cpf from tb_aluno");
			
			
			System.out.println("Lista de Alunos");			
			
		    /*  o metodo first() posiciona no primeiro registro da consulta
			 *  e retorna false, se o resultado for vazio 
			 */			
			if (dbResultado.first()) { 
				
				do {					
					System.out.println("Nome: " + dbResultado.getString("nome") + " CPF: " + dbResultado.getString("cpf"));					
				}
				while (dbResultado.next()); 
				
			    /*  o metodo next() posiciona no pr�ximo registro
			     *  da consulta.  Retorna false quando n�o houver
			     *  mais registros.
			     */				
			}
				
			
		} catch (SQLException e) {

			System.out.println("Erro :" + e.getMessage().toString());
		}
    		
		
	}
	  
			
		
}
