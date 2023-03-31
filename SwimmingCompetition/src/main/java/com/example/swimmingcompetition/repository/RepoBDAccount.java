package com.example.swimmingcompetition.repository;
import com.example.swimmingcompetition.domain.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class RepoBDAccount implements RepoAccount{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();


    /**
     * constructor
     * @param props = properties
     */
    public RepoBDAccount(Properties props) {
        logger.info("I initialize RepoBDAccount with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }


    /**
     * verify if this account is in database or not
     * @param ac - verified account
     * @return - account's ID if there is one, else -1
     */
    @Override
    public int ifExistAccount(Account ac) {
        String sql = "SELECT * FROM accountTable WHERE username = '" + ac.getUsername() + "' AND password = '" + ac.getPassword() + "'";
        int id = -1;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("id"));
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("ifExist - I found: " + id);
        return id;
    }


    /**
     * Search an account by ID in database
     * @param idAccount - searched account's ID
     * @return - the account if there exist, else null
     */
    @Override
    public Account findOne(Integer idAccount) {
        String sql = "SELECT * FROM accountTable WHERE id = " + idAccount;
        Account a = null;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                a = new Account(username, password);
                a.setId(id);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("findOne - I found: " + a);
        return a;
    }


    /**
     * @return - an array with all accounts from the database
     */
    @Override
    public Vector<Account> findAll() {
        Vector<Account> lista = new Vector<Account>();
        String sql = "SELECT * FROM accountTable";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Account a = new Account(username, password);
                a.setId(id);
                lista.add(a);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("findAll - I found: " + lista);
        return lista;
    }


    /**
     * add an account in database
     * @param ac - the account that will be added in database
     */
    @Override
    public void add(Account ac) {
        String sql = "INSERT INTO accountTable (id, username, password) VALUES (?, ?, ?)";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, ac.getId());
            ps.setString(2, String.valueOf(ac.getUsername()));
            ps.setString(3, String.valueOf(ac.getPassword()));
            ps.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("add - I added: " + ac);
    }


    /**
     * delete an account from database
     * @param idAccount - account's ID that will be deleted it
     */
    @Override
    public void delete(Integer idAccount) {
        String sql = "DELETE FROM accountTable WHERE id = " + idAccount;
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
        }
        logger.info("delete - I deleted: " + idAccount);
    }
}
