package com.example.swimmingcompetition.repository;
import com.example.swimmingcompetition.domain.ParticipantTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class RepoBDParticipantTask implements RepoParticipantTask{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();


    /**
     * constructor
     * @param props = properties
     */
    public RepoBDParticipantTask(Properties props) {
        logger.info("I initialize RepoBDParticipantTask with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }


    /**
     * It will give the array of ParticipantTasks who follows the SQL instruction
     * (va da lista de ParticipantTask uri care respecta instructiunea SQL)
     * @param sql - SQL string
     * @return - an array of ParticipantTasks
     */
    private Vector<ParticipantTask> getParticipantTasks(String sql) {
        Vector<ParticipantTask> vect = new Vector<ParticipantTask>();
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                int idParticipant = Integer.parseInt(resultSet.getString("idParticipant"));
                int idTask = Integer.parseInt(resultSet.getString("idTask"));
                ParticipantTask pt = new ParticipantTask(idParticipant, idTask);
                pt.setId(id);
                vect.add(pt);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("getPArticipantTask - I found:" + vect);
        return vect;
    }


    /**
     * verify if this participantTask is in database or not
     * @param pt - verified participantTask
     * @return - TRUE if there exist, else FALSE
     */
    @Override
    public boolean ifExist(ParticipantTask pt) {
        String sql = "SELECT * FROM participantTask WHERE idParticipant = " + pt.getIdParticipant() + " AND idTask = " + pt.getIdTask();
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
     * Search all participantTasks that have a task's ID
     * used to get the IDs of all participants participating in a task
     * (folosita pentru a lua id urile tuturor participantilor care participa la un task)
     * @param idTask - task's ID
     * @return - an array with all participantTasks from the database
     */
    @Override
    public Vector<ParticipantTask> findAllParticipants(int idTask) {
        String sql = "SELECT * FROM participantTask WHERE idTask = " + idTask;
        return getParticipantTasks(sql);
    }


    /**
     * Search all participantTasks that have a participant's ID
     * used to get the IDs of all the tasks in which a participant participates
     * (folosita pentru a lua id urile tuturor taskurile la care participa un participant)
     * @param idParticipant - participant's ID
     * @return - an array with all participantTasks from the database
     */
    @Override
    public Vector<ParticipantTask> findAllTasks(int idParticipant) {
        String sql = "SELECT * FROM participantTask WHERE idParticipant = " + idParticipant;
        return getParticipantTasks(sql);
    }


    /**
     * -
     * @param id -
     * @return -
     */
    @Override
    public ParticipantTask findOne(Integer id) {
        return null;
    }


    /**
     * @return - an array with all participantTasks from the database
     */
    @Override
    public Vector<ParticipantTask> findAll() {
        String sql = "SELECT * FROM participantTask";
        return getParticipantTasks(sql);
    }


    /**
     * add participantTask in database
     * @param pt - the participantTask that will be added in database
     */
    @Override
    public void add(ParticipantTask pt) {
        String sql = "INSERT INTO participantTask (id, idParticipant, idTask) VALUES (?, ?, ?)";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, pt.getId());
            ps.setInt(2, pt.getIdParticipant());
            ps.setInt(3, pt.getIdTask());
            ps.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("add - I added: " + pt);
    }


    /**
     * delete a participantTask from database
     * @param id - participantTask's ID that will be deleted it
     */
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM participantTask WHERE id = " + id;
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
        }
        logger.info("delete - I deleted: " + id);
    }

}
