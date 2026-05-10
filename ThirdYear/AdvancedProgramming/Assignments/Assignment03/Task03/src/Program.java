import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.Scanner;


public class Program {

    // fields needed to access database
    // actual connection to db
    private Connection con = null;
    // object used to issue SQL commands
    private Statement stmt = null;


    static void main() {
        new Program();
    }























    public Program() {

        connectToDB();
        processMenu();
        disconnect();

    }

    void processMenu() {
        Menu menu = new Menu("Main menu");

        menu.addChoice(new MenuChoice() {
            public String getText() { return "View All Albums"; }
            public void run() { try { displayAlbums(); } catch (SQLException e) { e.printStackTrace(); } }
        });


        menu.addChoice(new MenuChoice() {
            public String getText() { return "Add New Album"; }
            public void run() { addAlbum(); }
        });



        menu.addChoice(new MenuChoice() {
            public String getText() { return "Edit Album"; }
            public void run() { editAlbum(); }
        });
        menu.addChoice(new MenuChoice() {
            public String getText() {
                return "Add Song"; }
            public void run() { addSong(); }
        });

        menu.addChoice(new MenuChoice() {
            public String getText() { return "Edit Song"; }
            public void run() { editSong(); }
        });

        menu.addChoice(new MenuChoice() {
            public String getText() { return "Delete Song"; }
            public void run() { deleteSong(); }
        });


        menu.addChoice(new MenuChoice() {
            public String getText() { return "Query Songs by Artist"; }
            public void run() { querySongsByArtist(); }
        });
        menu.addChoice(new MenuChoice() {
            public String getText() { return "Query Songs by Title Phrase"; }
            public void run() { querySongsByTitlePhrase(); }
        });

        menu.addChoice(new MenuChoice() {
            public String getText() {
                return "Query Albums by Title Phrase";
            }
            public void run() {
                queryAlbumsByTitlePhrase();
            }
        });

        menu.run();
    }


    void displayAlbums() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Album");

        // Get metadata so we know how many columns there are
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        while (rs.next()) {
            // Print each column in the row
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i);
                String value = rs.getString(i);
                System.out.print(columnName + ": " + value + "  ");
            }
            System.out.println(); // new line after each album
        }
    }

    void addAlbum() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Album Title: ");
            String title = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO Album (Title) VALUES (?)");
            ps.setString(1, title);
            ps.executeUpdate();
            System.out.println("Album added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void editAlbum() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Album ID to edit: ");
            int aid = sc.nextInt(); sc.nextLine();
            System.out.print("Enter new Album Title: ");
            String newTitle = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("UPDATE Album SET Title=? WHERE AID=?");
            ps.setString(1, newTitle);
            ps.setInt(2, aid);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Album updated." : "Album not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addSong() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Album ID: ");
            int aid = sc.nextInt(); sc.nextLine();
            System.out.print("Enter Song Title: ");
            String title = sc.nextLine();
            System.out.print("Enter Artist: ");
            String artist = sc.nextLine();
            System.out.print("Enter Length: ");
            String length = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Song (AID, Title, Artist, Length) VALUES (?, ?, ?, ?)");
            ps.setInt(1, aid);
            ps.setString(2, title);
            ps.setString(3, artist);
            ps.setString(4, length);
            ps.executeUpdate();
            System.out.println("Song added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void editSong() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Song ID to edit: ");
            int sid = sc.nextInt(); sc.nextLine();
            System.out.print("Enter new Song Title: ");
            String newTitle = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("UPDATE Song SET Title=? WHERE SID=?");
            ps.setString(1, newTitle);
            ps.setInt(2, sid);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? " Song updated." : "Song not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteSong() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Song ID to delete: ");
            int sid = sc.nextInt();

            PreparedStatement ps = con.prepareStatement("DELETE FROM Song WHERE SID=?");
            ps.setInt(1, sid);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Song deleted." : " Song not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void querySongsByArtist() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Artist name: ");
            String artist = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT s.Title, a.Title AS AlbumTitle " +
                            "FROM Song s JOIN Album a ON s.AID = a.AID " +
                            "WHERE s.Artist LIKE ?");
            ps.setString(1, "%" + artist + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Song: " + rs.getString("Title") +
                        " | Album: " + rs.getString("AlbumTitle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void querySongsByTitlePhrase() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter phrase in Song title: ");
            String phrase = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Song WHERE Title LIKE ?");
            ps.setString(1, "%" + phrase + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("SID: " + rs.getInt("SID") +
                        " | Title: " + rs.getString("Title") +
                        " | Artist: " + rs.getString("Artist"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void queryAlbumsByTitlePhrase() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter phrase in Album title: ");
            String phrase = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Album WHERE Title LIKE ?");
            ps.setString(1, "%" + phrase + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("AID: " + rs.getInt("AID") +
                        " | Title: " + rs.getString("Title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void disconnect() {
        try {
            if (con != null) {
                con.close();
                System.out.println("🔌 Disconnected from database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void connectToDB(){

        Scanner scanner = new Scanner(System.in);
        //You may want to remove this
        System.out.println("Establishing connection to database...");

        //You may want to remove this
        System.out.println("   Loading JDBC driver for MS SQL Server database...");
        try {

            //This goes to the class of the new driver
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (Exception e) {
            System.out.printf("   Unable to load JDBC driver... '%s'\n", e.getMessage());
            return;
        }

        //You may want to remove this
        System.out.println("   Use driver to connect to MS SQL Server (OPENBOX\\WRR)...");
        if (true) {
            try {
                System.out.println("   Locate database to open (using connection string)...");

                String connectionString = "jdbc:jtds:sqlserver://postsql.mandela.ac.za/WRAP301Music;instance=WRR";
                System.out.println("      Connection string = " + connectionString);

                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();

                con = DriverManager.getConnection(connectionString, username, password);

                // create statement object for manipulating DB
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                System.out.println("Connected to database...");
            } catch (Exception e) {
                System.out.printf("   Unable to connect to DB... '%s'\n", e.getMessage());
            }

            //I don't think we'll use this part
        } else {
            try {
                // another way of creating a connection to a database
                System.out.println("   Locate database to open (using server data source)...");

                // specify details of connection to be made
                SQLServerDataSource ds = new SQLServerDataSource();
                ds.setUser("WRAP301User");
                ds.setPassword("WRAP301");
                ds.setServerName("postsql.mandela.ac.za");
                ds.setInstanceName("WRR");
                ds.setDatabaseName("WRAP301music");

                // create the connection to the DB
                con = ds.getConnection();

                // create statement object for manipulating DB
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}
