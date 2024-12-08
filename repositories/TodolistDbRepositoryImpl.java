package repositories;

import config.Database;
import entities.Todolist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TodolistDbRepositoryImpl implements TodolistRepository {
    private final Database database;

    public TodolistDbRepositoryImpl(final Database database) {
        this.database = database;
    }

    @Override
    public Todolist[] getAll() {
        Connection connection = database.getConnection();
        String query = "SELECT * FROM todo";
        List<Todolist> todolists = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String todo = resultSet.getString(2);
                Todolist newTodo = new Todolist();
                newTodo.setId(id);
                newTodo.setTodo(todo);
                todolists.add(newTodo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return todolists.toArray(Todolist[]::new);
    }

        @Override
        public void add (final Todolist todolist){
            Connection connection = database.getConnection();
            String query = "INSERT INTO todo(todo) VALUES(?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, todolist.getTodo());
                Integer rowsEffected = preparedStatement.executeUpdate();
                if (rowsEffected > 0){
                    System.out.println("insert record successfully 1");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public Boolean remove (final Integer number,final Boolean isNumberId ){
            Connection connection = database.getConnection();
            String query = "DELETE FROM todo WHERE id = ?";

            Integer dbId;
            if(isNumberId) {
                Todolist[] todolists = getAll();
                Long countElement = Arrays.stream(todolists).filter(Objects::nonNull).count();
                if (countElement == 0) {
                    return false;
                }
                dbId = todolists[number - 1].getId();
            } else {
                dbId = number;
            }
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, dbId);

                Integer rowsEffected = preparedStatement.executeUpdate();
                if (rowsEffected > 0){
                    System.out.println("Delete successful !");
                    return true;
                }else {
                    return false;
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
    }
