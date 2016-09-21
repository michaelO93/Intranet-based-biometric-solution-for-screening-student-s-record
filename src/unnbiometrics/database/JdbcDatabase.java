/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author michael-prime
 */
public class JdbcDatabase extends Database {
    static final Logger LOGGER = Logger.getLogger(JdbcDatabase.class.getName());
    String url = "jdbc:mysql://localhost/unnBiometrics";
    String dbname = "root";
    String dbpass = "holyspirit93";

    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dbname, dbpass);
            System.out.println("Connected to DB!");
            return connection;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    }

    private static List<Map> maps(ResultSet set) throws SQLException, IOException {
        ResultSetMetaData rsmd = set.getMetaData();
        final int columns = set.getMetaData().getColumnCount();
        final List<Map> maps = new ArrayList<>();

        int size = 0;
        while (set.next()) {
            size++;
            Map map = new Map();
            for (int i = 1; i <= columns; i++) {
                String key = rsmd.getColumnLabel(i);
                Object val = set.getObject(i);
                if (val instanceof Blob) {
                    System.out.println(key + " is Blob");
                    InputStream is = set.getBinaryStream(i);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    
                    int b;
                    while ((b = is.read()) != -1) {
                        baos.write(b);
                    }
                    val = baos.toByteArray();
                }
                map.put(key, val);
            }
            maps.add(map);
        }
        
        System.out.println(size + " rows");
        return maps;
    }
    
    private void execute(String sql, Collection<?>... multiArgs) throws IOException {
        System.err.println(sql);
        if (DEBUGGING) {
            int index = 1;
            for (Collection<?> args : multiArgs) {
                for (Object o : args) System.err.println((index++) + " " + o);
            }
        } else {
            try (PreparedStatement ps = connect().prepareStatement(sql)) {
                int index = 1;
                for (Collection<?> args : multiArgs) {
                    for (Object arg : args) {
                        if (arg instanceof byte[]) {
                            ps.setBytes(index++, (byte[]) arg);
                        } else {
                            ps.setObject(index++, arg);
                        }
                    }
                }
                ps.execute();
            } catch (SQLException ex) {
                throw new IOException(ex);
            }
        }
    }
    
    private List<Map> executeQuery(String sql, Collection<?>... multiArgs) throws IOException {
        System.err.println(sql);
        if (DEBUGGING) {
            int index = 1;
            for (Collection<?> args : multiArgs) {
                for (Object o : args) System.err.println((index++) + " " + o);
            }
            return Collections.emptyList();
        }
        
        try (PreparedStatement ps = connect().prepareStatement(sql)) {
            int index = 1;
            for (Collection<?> args : multiArgs) {
                for (Object arg : args) {
                    if (arg instanceof byte[]) {
                        ps.setBytes(index++, (byte[]) arg);
                    } else {
                        ps.setObject(index++, arg);
                    }
                }
            }
            try (ResultSet result = ps.executeQuery()) {
                return maps(result);
            }
        } catch (SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    protected List<Map> getAll(String table) throws IOException {
        return executeQuery("SELECT * FROM " + table);
    }

    @Override
    protected List<Map> select(String table, Map filter) throws IOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(table);
        String prefix = "WHERE";
        
        for (String key : filter.keySet()) {
            sql.append(' ').append(prefix);
            sql.append(' ').append(key); 
            sql.append("=?");
            prefix = "AND";
        }
        
        return executeQuery(sql.toString(), filter.values());
    }

    @Override
    protected void delete(String table, Map filter) throws IOException {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(table);
        String prefix = "WHERE";
        
        for (String key : filter.keySet()) {
            sql.append(' ').append(prefix);
            sql.append(' ').append(key); 
            sql.append("=?");
            prefix = "AND";
        }
        
        execute(sql.toString(), filter.values());
    }

    @Override
    protected void update(String table, Map filter, Map object) throws IOException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(table);
        sql.append(  " SET ").append(object.toString(", ", "?"));
        sql.append(" WHERE ").append(filter.toString(" AND ", "?"));
        
        execute(sql.toString(), object.values(), filter.values());
    }

    @Override
    protected void insert(String table, Map object) throws IOException {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(table);
        sql.append(" (").append(String.join(",", object.keySet())).append(')');
        sql.append(" VALUES (").append(repeat("?", object.size())).append(')');
        
        execute(sql.toString(), object.values());
    }
    
    private static String repeat(String text, int count) {
        StringBuilder sb = new StringBuilder();
        if (count > 0) sb.append(text);
        for (int i = 0; i < count - 1; i++) {
            sb.append(",").append(text);
        }
        return sb.toString();
    }
}
