package com.example.swimmingcompetition.repository;
import com.example.swimmingcompetition.domain.Task;
import com.example.swimmingcompetition.domain.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class RepoBDTask implements RepoTask{


    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    /**
     * constructor
     * @param props = properties
     */
    public RepoBDTask(Properties props) {
        logger.info("I initialize RepoBDTask with properties: " + props);
        dbUtils=new JdbcUtils(props);
    }


    /**
     * convert a distance in enum
     * @param distance - the distance that will be modified
     * @return - modified distance
     */
    private Utility.enumDistance convertDistanceToEnum (int distance){

        return switch (distance) {
            case 50 -> Utility.enumDistance.A;
            case 200 -> Utility.enumDistance.B;
            case 800 -> Utility.enumDistance.C;
            default -> Utility.enumDistance.D;
        };

    }


    /**
     * convert a style in enum
     * @param style - the style that will be modified
     * @return - modified style
     */
    private Utility.enumStyle convertStyleToEnum (String style){

        return switch (style) {
            case "MIXT" -> Utility.enumStyle.MIXT;
            case "BUTTERFLY" -> Utility.enumStyle.BUTTERFLY;
            case "BACKSTROKE" -> Utility.enumStyle.BACKSTROKE;
            default -> Utility.enumStyle.FREESTYLE;
        };
    }


    /**
     * convert a style in string
     * @param style - the style that will be modified
     * @return - modified style
     */
    private String convertEnumToStyle (Utility.enumStyle style){
        return switch (style){
            case FREESTYLE -> "FREESTYLE";
            case BACKSTROKE -> "BACKSTROKE";
            case BUTTERFLY -> "BUTTERFLY";
            default -> "MIXT";
        };
    }


    /**
     * verify if this task is in database or not
     * @param t - verified task
     * @return - TRUE if there exist, else FALSE
     */
    @Override
    public boolean ifExistTask(Task t) {
        String sql = "SELECT * FROM tasks WHERE distance = " + t.getDistance().getDist() + " AND style = '" + convertEnumToStyle(t.getStyle()) + "'";
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
     * Search a task by ID in database
     * @param idTask - searched task's ID
     * @return - the task if there exist, else null
     */
    @Override
    public Task findOne(Integer idTask) {
        String sql = "SELECT * FROM tasks WHERE id = " + idTask;
        Task t = null;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while(resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                int distance = Integer.parseInt(resultSet.getString("distance"));
                String style = resultSet.getString("style");
                t = new Task(convertDistanceToEnum(distance), convertStyleToEnum(style));
                t.setId(id);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("findOne - I found: " + t);
        return t;
    }


    /**
     * @return - an array with all tasks from the database
     */
    @Override
    public Vector<Task> findAll() {
        Vector<Task> lista = new Vector<Task>();
        String sql = "SELECT * FROM tasks";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()){
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                int distance = Integer.parseInt(resultSet.getString("distance"));
                String style = resultSet.getString("style");
                Task t = new Task(convertDistanceToEnum(distance), convertStyleToEnum(style));
                t.setId(id);
                lista.add(t);
            }
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("findAll - I found: " + lista);
        return lista;
    }


    /**
     * add task in database
     * @param t - the task that will be added in database
     */
    @Override
    public void add(Task t) {
        String sql = "INSERT INTO tasks (id, distance, style) VALUES (?, ?, ?)";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, t.getId());
            ps.setInt(2, t.getDistance().getDist());
            ps.setString(3, String.valueOf(convertEnumToStyle(t.getStyle())));
            ps.executeUpdate();
        }catch (SQLException e){
            logger.error(e);
        }
        logger.info("add - I added: " + t);
    }


    /**
     * delete a task from database.
     * @param idTask - task's ID that will be deleted it
     */
    @Override
    public void delete(Integer idTask) {
        String sql = "DELETE FROM tasks WHERE id = " + idTask;
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
        }
        logger.info("delete - I deleted: " + idTask);
    }

}
