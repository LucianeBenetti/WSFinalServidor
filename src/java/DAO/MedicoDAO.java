/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.Medico;
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
@WebService(serviceName = "MedicoDAO")
public class MedicoDAO {
   
  private static ArrayList<Medico> medico = new ArrayList<Medico>();
  Medico med = new Medico();

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    @WebMethod(operationName = "existeRegistroPorCpf")
    public boolean existeRegistroPorCpf(@WebParam(name = "cpf")String cpf) {
	
        String query = "SELECT * FROM medico WHERE cpf = '" + cpf + "'";
	Connection conn = Banco.getConnection();
	Statement stmt = Banco.getStatement(conn);
	ResultSet resultado = null;
	try {
            resultado = stmt.executeQuery(query);
		if (resultado.next()) {
		return true;
				}
	}catch (SQLException e) {
		System.out.println("Erro ao executar Query que verifica a existência de Medico peloCPF. Cause: \n" + e.getMessage());
	}finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
	}
	return false;
    }
    
    @WebMethod(operationName = "inserirMedico")
    public int inserirMedico(@WebParam(name = "medNome")String medNome, @WebParam(name = "crm")String crm, @WebParam(name = "celMen")String celMen, @WebParam(name = "cel")String cel,@WebParam(name = "email") String email, @WebParam(name = "cpf")String cpf, @WebParam(name = "cnpj")String cnpj, @WebParam(name = "logradouro")String logradouro, @WebParam(name = "numLog")String numLog, @WebParam(name = "complemento")String complemento,@WebParam(name = "bairro") String bairro,  @WebParam(name = "cidade")String cidade,@WebParam(name = "uf") String uf,@WebParam(name = "cep") String cep) {
        int novoId = -1;

        String query = "INSERT INTO medico (medNome, crm, celMen, cel, email, cpf, cnpj, logradouro, numLog, complemento, bairro, cidade, uf, cep)" 
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query, Statement.RETURN_GENERATED_KEYS);
       
        try{
            prepStmt.setString(1, med.getMedNome());
            prepStmt.setString(2, med.getCrm());
            prepStmt.setString(3, med.getCelMensagem());
            prepStmt.setString(4, med.getCel());
            prepStmt.setString(5, med.getEmail());
            prepStmt.setString(6, med.getCpf());
            prepStmt.setString(7, med.getCnpj());
            prepStmt.setString(8, med.getLogradouro());
            prepStmt.setString(9, med.getNumLog());
            prepStmt.setString(10, med.getComplemento());
            prepStmt.setString(11, med.getBairro());
            prepStmt.setString(12, med.getCidade());
            prepStmt.setString(13, med.getUf());
            prepStmt.setString(14, med.getCep());

            prepStmt.executeUpdate();
            
            ResultSet generatedKeys = prepStmt.getGeneratedKeys();
            if(generatedKeys.next()){
                novoId = generatedKeys.getInt(1);
            }
        }catch(SQLException e){
            System.out.println("Erro ao executar Query de Cadastro de Médico! Causa: \n: " + e.getMessage());
        }finally {
           Banco.closePreparedStatement(prepStmt);
           Banco.closeConnection(conn);
        }
        return novoId;
    }
    @WebMethod(operationName = "deleteMedico")
        public boolean deleteMedico(@WebParam(name = "cpf")String cpf) {
            boolean sucessoDelete = false;
        String query = "DELETE from medico where cpf = ? ";
           Connection conn = Banco.getConnection();
            PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);

            try{
                prepStmt.setString(1, cpf);
                int codigoRetorno = prepStmt.executeUpdate();
                    if (codigoRetorno == 1){
                        sucessoDelete = true;
                    }
            }catch(SQLException e){
               System.out.println("Erro ao executar Query de Exclusão do Médico! Causa: \n: " + e.getMessage()); 
            }finally{
                Banco.closePreparedStatement(prepStmt);
                Banco.closeConnection(conn);
            }
            return sucessoDelete;
        }

    @WebMethod(operationName = "atualizarMedico")
    public boolean atualizarMedico(@WebParam(name = "medNome")String medNome, @WebParam(name = "crm")String crm, @WebParam(name = "celMen")String celMen, @WebParam(name = "cel")String cel,@WebParam(name = "email") String email, @WebParam(name = "cpf")String cpf, @WebParam(name = "cnpj")String cnpj, @WebParam(name = "logradouro")String logradouro, @WebParam(name = "numLog")String numLog, @WebParam(name = "complemento")String complemento,@WebParam(name = "bairro") String bairro,  @WebParam(name = "cidade")String cidade,@WebParam(name = "uf") String uf,@WebParam(name = "cep") String cep, @WebParam(name = "cpfAtual")String cpfAtual) {
       boolean sucessoAtualizar = false;
           
        String query = "UPDATE medico SET medNome=?, crm=?, celMen=?, cel=?, email=?, cpf=?, cnpj=?, logradouro=?, numLog=?, complemento=?, bairro=?, cidade=?, uf=?, cep=?" + " where cpf = ?"; 

         Connection conn = Banco.getConnection();
         PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
               
        try{
            prepStmt.setString(1, med.getMedNome());
            prepStmt.setString(2, med.getCrm());
            prepStmt.setString(3, med.getCelMensagem());
            prepStmt.setString(4, med.getCel());
            prepStmt.setString(5, med.getEmail());
            prepStmt.setString(6, med.getCpf());
            prepStmt.setString(7, med.getCnpj());
            prepStmt.setString(8, med.getLogradouro());
            prepStmt.setString(9, med.getNumLog());
            prepStmt.setString(10, med.getComplemento());
            prepStmt.setString(11, med.getBairro());
            prepStmt.setString(12, med.getCidade());
            prepStmt.setString(13, med.getUf());
            prepStmt.setString(14, med.getCep());
            prepStmt.setString(15, med.getCpf());
                    
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
      
    @WebMethod(operationName = "consultarMedicoPorCRM")
    public ArrayList<Medico> consultarMedicoPorCRM(@WebParam(name = "crm")String crm) {
        
        String query = "SELECT *from medico " + " where crm = ?";
        
        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, query);
        Medico med = null;
         try { 
            prepStmt.setString(1, crm);
            ResultSet result = prepStmt.executeQuery();
          
            while(result.next()){
             med = new Medico();
             med.setMedicoCod(result.getInt(1));
             med.setMedNome(result.getString(2));
             med.setCrm(result.getString(3));
             med.setCelMensagem(result.getString(4));
             med.setCel(result.getString(5));
             med.setEmail(result.getString(6));
             med.setCpf(result.getString(7));
             med.setCnpj(result.getString(8));
             med.setLogradouro(result.getString(9));
             med.setNumLog(result.getString(10));
             med.setComplemento(result.getString(11));
             med.setBairro(result.getString(12));
             med.setCidade(result.getString(13));
             med.setUf(result.getString(14));
             med.setCep(result.getString(15));
             
             medico.add(med);
              
            }
        } catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }finally {
          Banco.closeStatement(prepStmt);
          Banco.closeConnection(conn);
	}
    return medico;
    }
   
    
}
