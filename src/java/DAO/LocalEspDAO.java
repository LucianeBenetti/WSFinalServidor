/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.LocalEsp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author luciane
 */
@WebService(serviceName = "LocalEspDAO")
public class LocalEspDAO {
ArrayList<LocalEsp> localespecializacao = new ArrayList<LocalEsp>();

    LocalEsp localEsp =new LocalEsp();
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    @WebMethod(operationName = "existeRegistroPorIdLocalEsp")
    public boolean existeRegistroPorIdLocalEsp(@WebParam(name = "espCod") int espCod) {
        
        String query = "SELECT * FROM especialidade WHERE espCod = '" + espCod + "'";
        
        Connection conn = Banco.getConnection();
	Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
	
	try {
            resultado = stmt.executeQuery(query);
           if (resultado.next()) {
                return true;
		}
	}catch (SQLException e) {
		System.out.println("Erro ao executar Query que verifica a existência de Especialidade pela ID. Cause: \n" + e.getMessage());
		
	}finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
	}
	return false;
}
 
     @WebMethod(operationName = "inserirLocalEsp")
    public int inserirLocalEsp(@WebParam(name = "espNome")String espNome, @WebParam(name = "espInstituicao")String espInstituicao) {
       int novoId = -1;

       String query = "INSERT INTO especialidade (espNome, espInstituicao)" + " VALUES (?, ?)";
       Connection conn = Banco.getConnection();
       PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);

       try{
           prepStmt.setString(1, localEsp.getEspNome());
           prepStmt.setString(2, localEsp.getEspInstituicao());

           prepStmt.executeUpdate();

           ResultSet generatedKeys = prepStmt.getGeneratedKeys();
           if(generatedKeys.next()){
               novoId = generatedKeys.getInt(1);
           }
       }catch(SQLException e){
           System.out.println("Erro ao executar Query de Cadastro da Especialidade! Causa: \n: " + e.getMessage());
       }finally {
          Banco.closePreparedStatement(prepStmt);
          Banco.closeConnection(conn);
       }
       return novoId;
   }

    @WebMethod(operationName = "deleteLocalEsp")
    public boolean deleteLocalEsp(@WebParam(name = "espCod")int espCod) {
        boolean sucessoDelete = false;
        
        String query = "DELETE from especialidade where EspCod = ? ";
        
        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
        
        try{
            prepStmt.setInt(1, espCod);
            int codigoRetorno = prepStmt.executeUpdate();
                if (codigoRetorno == 1){
                    sucessoDelete = true;
                }
        }catch(SQLException e){
           System.out.println("Erro ao executar Query de Exclusão da Especialidade! Causa: \n: " + e.getMessage()); 
        }finally{
            Banco.closePreparedStatement(prepStmt);
            Banco.closeConnection(conn);
        }
        return sucessoDelete;
    }

    @WebMethod(operationName = "atualizarLocalEsp")
    public boolean atualizarLocalEsp(@WebParam(name = "espNome")String espNome, @WebParam(name = "espInstituicao")String espInstituicao, @WebParam(name = "espCod")int especialidadeCod) {
        boolean sucessoAtualizar = false;
           
        String query = "UPDATE especialidade SET espNome=?, espInstituicao=? " + "where espCod = ?"; 

         Connection conn = Banco.getConnection();
         PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
         
        try {
           prepStmt.setString(1, localEsp.getEspNome());
           prepStmt.setString(2, localEsp.getEspInstituicao());
           prepStmt.setInt(3, localEsp.getEspecialidadeCod());
                        
            int codigoRetorno = prepStmt.executeUpdate();
            
            if(codigoRetorno == 1){
                sucessoAtualizar = true;
            }
         } catch (SQLException ex) {
            System.out.println("Erro ao executar Query de Atualização do Especialidade!Causa: \n: " + ex.getMessage());
         }finally {
            Banco.closePreparedStatement(prepStmt);
            Banco.closeConnection(conn);
	}
        return sucessoAtualizar;
    }

    @WebMethod(operationName = "consultarEspecialidadePorID")
    public  ArrayList<LocalEsp> consultarEspecialidadePorID(@WebParam(name = "espCod")int espCod){
    
        String query = "SELECT *from especialidade " + " where espCod = ?";
        
        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
        LocalEsp localEsp = null;
         try { 
            prepStmt.setInt(1, espCod);
            ResultSet resultado = prepStmt.executeQuery();
          
            while(resultado.next()){
                localEsp = new LocalEsp();
                localEsp.setEspecialidadeCod(resultado.getInt(1));
                localEsp.setEspNome(resultado.getString(2));
                localEsp.setEspInstituicao(resultado.getString(3));
                
                localespecializacao.add(localEsp);
            }
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }finally {
          Banco.closeStatement(prepStmt);
          Banco.closeConnection(conn);
	}
         return localespecializacao;
       }
   
}
