package com.marocair.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    private Statement stmt;
    private Connection cnx;
    public String URL;
    public String NAME;
    public String USER;
    public String PASS;

    public Database(){
        try {
            Dotenv dotEnv = Dotenv.load();
            URL = dotEnv.get("URL");
            NAME = dotEnv.get("DB_NAME");
            USER = dotEnv.get("USER");
            PASS = dotEnv.get("PASSWORD");
            Class.forName("org.postgresql.Driver");
            cnx = DriverManager.getConnection(URL+"\"" + NAME, USER, PASS);
            System.out.println("Connection successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public void query(String request){
        try {
            stmt = cnx.createStatement();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean execute(String rq) {
        this.query(rq);
        try {
            stmt.executeUpdate(rq);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ResultSet resultSet(String s){
        this.query(s);
        try {
            return stmt.executeQuery(s);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void closeCnx(){
        try {
            stmt.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
