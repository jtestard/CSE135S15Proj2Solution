package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryWithCount {

    private int id;

    private String name;

    private String description;

    private int count;

    /**
     * @param id
     * @param name
     * @param description
     * @param count
     */
    public CategoryWithCount(int id, String name, String description, int count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.count = count;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    public static ArrayList<CategoryWithCount> getCategoriesWithCount() throws SQLException {
        ArrayList<CategoryWithCount> categories = new ArrayList<CategoryWithCount>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Internal Server Error. This shouldn't happen.");
            return new ArrayList<CategoryWithCount>();
        }
        String url = "jdbc:postgresql://127.0.0.1:5432/cse135";
        String user = "postgres";
        String password = "postgres";
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        String query = "SELECT c.id, c.name, c.description, COUNT(p.id) as count FROM Categories c LEFT OUTER JOIN Products p ON c.id=p.cid GROUP BY c.id, c.name, c.description";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            Integer count = rs.getInt(4);
            categories.add(new CategoryWithCount(id, name, description, count));
        }
        return categories;
    }
}