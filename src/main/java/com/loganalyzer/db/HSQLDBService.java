package com.loganalyzer.db;

import com.loganalyzer.model.LogAnalyzedRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class HSQLDBService {

    private Connection connection;

    public void writeDetails(LogAnalyzedRecord record) {
        connection = HSQLDBConnection.getConnection();
        if (connection != null) {
            createTableDetails();
            if (isRecordExists(record.getId())) {
                updateRecord(record);
            } else {
                insertRecord(record);
            }
        }
    }

    public List<String> readDetails(){
        connection = HSQLDBConnection.getConnection();
        if (connection != null) {
            return readRecords();
        }
        return null;
    }

    private boolean isRecordExists(String id) {
        try (PreparedStatement statement =
                     connection.prepareStatement("select count(id) from EVENT_DETAILS where id = ?")) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void insertRecord(LogAnalyzedRecord record) {
        try (PreparedStatement statement =
                     connection.prepareStatement("insert into PUBLIC.EVENT_DETAILS (ID, DURATION, HOST, TYPE, ALERT) values (?, ?, ?, ?, ?)")) {
            statement.setString(1, record.getId());
            statement.setLong(2, record.getDuration());
            statement.setString(3, record.getHost());
            statement.setString(4, record.getType());
            statement.setBoolean(5, record.getAlert());
            statement.execute();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void updateRecord(LogAnalyzedRecord record) {
        try (PreparedStatement statement =
                     connection.prepareStatement("update PUBLIC.EVENT_DETAILS set DURATION = ?, HOST = ?, TYPE = ?, ALERT = ? where ID = ?")) {
            statement.setLong(1, record.getDuration());
            statement.setString(2, record.getHost());
            statement.setString(3, record.getType());
            statement.setBoolean(4, record.getAlert());
            statement.setString(5, record.getId());
            statement.execute();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private List<String> readRecords() {
        List<String> result = new LinkedList<>();
        try (Statement statement =
                     connection.createStatement()) {
            ResultSet rs =  statement.executeQuery("select * from PUBLIC.EVENT_DETAILS");
            while(rs.next()){
                result.add(String.format("{'id':'%s','duration':'%d','host':'%s','type':'%s','alert':'%b'}",
                        rs.getString("id"), rs.getInt("duration"), rs.getString("host"),
                        rs.getString("type"), rs.getBoolean("alert")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void createTableDetails() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    "create table if not exists PUBLIC.EVENT_DETAILS " +
                            "(ID varchar(255) not null constraint EVENT_DETAILS_pk primary key, " +
                            "DURATION bigint not null," +
                            "HOST varchar(255), TYPE varchar(255), ALERT BOOLEAN)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
