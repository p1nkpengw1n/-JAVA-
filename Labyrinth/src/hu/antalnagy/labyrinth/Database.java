package hu.antalnagy.labyrinth;

import java.sql.*;
import java.util.ArrayList;

class Database {

    ArrayList<Score> loadFromDb() {
        ArrayList<Score> topTen = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/highscores", "root", ""); Statement statement = connection.createStatement()) {
//            statement.execute("CREATE TABLE IF NOT EXISTS scores (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL, score integer NOT NULL)");
            ResultSet scores = statement.executeQuery("SELECT name, level, time FROM highscores order by level desc, time asc LIMIT 10");
            if(scores != null) {
                while (scores.next()) {
                    topTen.add(new Score(scores.getInt(2), scores.getLong(3), scores.getString(1)));
                }
            }
            return topTen;
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
        return topTen;
    }

    void flushToDb(Score sc) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/highscores", "root", ""); Statement statement = connection.createStatement()) {
            //statement.execute("DROP TABLE IF EXISTS highscores");
            statement.execute("CREATE TABLE IF NOT EXISTS highscores (name text NOT NULL, level integer NOT NULL, time double NOT NULL)");
            String sql = "INSERT INTO highscores (name, level, time) VALUES " + "(\'" + sc.getName() + "\', \'" + sc.getLevel() + "\', '" + sc.getTime() + "\');";
            //sql += topTen.stream().map((score) ->  score.toSqlValue() + ',').reduce("", (acc, score) -> acc + score);
            //sql = sql.substring(0, sql.length() - 1) + ";";
            statement.execute(sql);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
