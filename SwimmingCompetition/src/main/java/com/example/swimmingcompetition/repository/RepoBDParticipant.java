package com.example.swimmingcompetition.repository;
import com.example.swimmingcompetition.domain.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class RepoBDParticipant implements RepoParticipant {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();


    /**
     * constructor
     * @param props = properties
     */
    public RepoBDParticipant(Properties props) {
        logger.info("I initialize RepoBDParticipant with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }


    /**
     * verify if this participant is in database or not
     * @param p - verified participant
     * @return - TRUE if there exist, else FALSE
     */
    @Override
    public boolean ifExistParticipant(Participant p) {
        String sql = "SELECT * FROM participants WHERE name = '" + p.getName() + "' AND age = " + p.getAge();
        boolean find = false;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()) {
                find = true;
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("ifExist - I found: " + find);
        return find;
    }


    /**
     * Search a participant by ID in database
     * @param idParticipant - searched participant's ID
     * @return - the participant if there exist, else null
     */
    @Override
    public Participant findOne(Integer idParticipant) {
        String sql = "SELECT * FROM participants WHERE id = " + idParticipant;
        Participant p = null;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                int age = Integer.parseInt(resultSet.getString("age"));
                p = new Participant(name, age);
                p.setId(id);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("findOne - I found: " + p);
        return p;
    }


    /**
     * @return - an array with all participants from the database
     */
    @Override
    public Vector<Participant> findAll() {
        Vector<Participant> lista = new Vector<Participant>();
        String sql = "SELECT * FROM participants";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                int age = Integer.parseInt(resultSet.getString("age"));
                Participant p = new Participant(name, age);
                p.setId(id);
                lista.add(p);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("findAll - I found: " + lista);
        return lista;
    }


    /**
     * add a participant in database
     * @param p - the participant that will be added in database
     */
    @Override
    public void add(Participant p) {
        String sql = "INSERT INTO participants (id, name, age) VALUES (?, ?, ?)";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, p.getId());
            ps.setString(2, String.valueOf(p.getName()));
            ps.setInt(3, p.getAge());
            ps.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("add - I added: " + p);
    }


    /**
     * delete a participant from database.
     * @param idParticipant - participant's ID that will be deleted it
     */
    @Override
    public void delete(Integer idParticipant) {
        String sql = "DELETE FROM participants WHERE id = " + idParticipant;
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
        }
        sql = "DELETE FROM participantTask WHERE idParticipant = " + idParticipant;
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
        }
        logger.info("delete - I deleted: " + idParticipant);
    }
}
