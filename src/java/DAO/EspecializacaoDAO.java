/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.EspecializacaoVO;
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
@WebService(serviceName = "EspecializacaoDAO")
public class EspecializacaoDAO {

   ArrayList<EspecializacaoVO> especVO = new ArrayList<EspecializacaoVO>();
    EspecializacaoVO espec = new EspecializacaoVO();
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
  @WebMethod(operationName = "existeRegistroPorIdEspecializacao")
    public boolean existeRegistroPorIdEspecializacao(@WebParam(name = "espeCod")int espeCod) {
        String query = "SELECT * FROM especializacao WHERE espeCod = '" + espeCod + "'";
        Connection conn = Banco.getConnection();
	Statement stmt = Banco.getStatement(conn);
	ResultSet resultado = null;
      		
	try {
             resultado = stmt.executeQuery(query);
              if (resultado.next()) {
		return true;
		}
          
	}catch (SQLException e) {
		System.out.println("Erro ao executar Query que verifica a existência de Especialização pela ID. Cause: \n" + e.getMessage());
		
	}finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
	}
	return false;
}
 
     @WebMethod(operationName = "inserirEspecializacao")
    public int inserirEspecializacao(@WebParam(name = "espCod")int espCod, @WebParam(name = "medCod") int medCod, @WebParam(name = "espAnoEspecializacao")String espAnoEspecializacao) {
       int novoId = -1;

       String query = "INSERT INTO especializacao (espCod, medCod, espAnoEspecializacao) " + " VALUES (?, ?, ?)";

       Connection conn = Banco.getConnection();
       PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);
       
       try{
           prepStmt.setInt(1, espec.getEspCod());
           prepStmt.setInt(2, espec.getMedCod());
           prepStmt.setString(3, espec.getEspAnoEspecializacao());

           prepStmt.executeUpdate();

           ResultSet generatedKeys = prepStmt.getGeneratedKeys();
           if(generatedKeys.next()){
               novoId = generatedKeys.getInt(1);
           }
       }catch(SQLException e){
           System.out.println("Erro ao executar Query de Cadastro da Especialização! Causa: \n: " + e.getMessage());
       }finally {
          Banco.closePreparedStatement(prepStmt);
          Banco.closeConnection(conn);
       }
       return novoId;
   }

    @WebMethod(operationName = "deleteEspecializacao")
    public boolean deleteEspecializacao(@WebParam(name = "espeCod")int espeCod) {
        boolean sucessoDelete = false;
        
        String query = "DELETE from especializacao where espeCod = ? ";
        
        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
        
        try{
            prepStmt.setInt(1, espeCod);
            int codigoRetorno = prepStmt.executeUpdate();
                if (codigoRetorno == 1){
                    sucessoDelete = true;
                }
        }catch(SQLException e){
           System.out.println("Erro ao executar Query de Exclusão da Especialização! Causa: \n: " + e.getMessage()); 
        }finally{
            Banco.closePreparedStatement(prepStmt);
            Banco.closeConnection(conn);
        }
        return sucessoDelete;
    }

    @WebMethod(operationName = "atualizarEspecializacao")
    public boolean atualizarEspecializacao(@WebParam(name = "espCod")int espCod, @WebParam(name = "medCod")int medCod, @WebParam(name = "espAnoEspecializacao")String espAnoEspecializacao, @WebParam(name = "espeCod")int espeCod) {
        boolean sucessoAtualizar = false;
           
         String query = "UPDATE especializacao SET espCod=?, espMed=?, espAnoEspecializacao=? " + "where espeCod = ?"; 
         Connection conn = Banco.getConnection();
         PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
         
        try {
           prepStmt.setInt(1, espec.getEspCod());
           prepStmt.setInt(2, espec.getMedCod());
           prepStmt.setString(3, espec.getEspAnoEspecializacao());
           prepStmt.setInt(4, espec.getEspeCod());
            
            int codigoRetorno = prepStmt.executeUpdate();
            
            if(codigoRetorno == 1){
                sucessoAtualizar = true;
            }
         } catch (SQLException ex) {
            System.out.println("Erro ao executar Query de Atualização do Especializacao!Causa: \n: " + ex.getMessage());
         }finally {
            Banco.closePreparedStatement(prepStmt);
            Banco.closeConnection(conn);
	}
        return sucessoAtualizar;
    }

    @WebMethod(operationName = "consultarEspecializacaoPorID")
    public ArrayList<EspecializacaoVO> consultarEspecializacaoPorID(@WebParam(name = "espeCod")int espeCod){
    
        String query = "SELECT *from especializacao " + " where espeCod = ?";
        
        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
        EspecializacaoVO espec = null;
         try { 
            prepStmt.setInt(1, espeCod);
            ResultSet resultado = prepStmt.executeQuery();
          
            while(resultado.next()){
             espec =  new EspecializacaoVO();
             espec.setEspeCod(resultado.getInt(1));
             espec.setEspCod(resultado.getInt(2));
             espec.setMedCod(resultado.getInt(3));
             espec.setEspAnoEspecializacao(resultado.getString(4));
                  
             especVO.add(espec);
            }
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }finally {
          Banco.closeStatement(prepStmt);
          Banco.closeConnection(conn);
	}
         return especVO;
       }
}