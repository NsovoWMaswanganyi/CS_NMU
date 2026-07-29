package nmmu.wrap301.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;

public class DBExample {
    // fields needed to access database
    // actual connection to db
    private Connection con = null;
    // object used to issue SQL commands
    private Statement stmt = null;

    public static void main(String[] args) {
        new DBExample();
    }

    public DBExample() {
        // establish a connection to the database
        connectToDB();

        // use the database if a connection was established
        if (con != null) {
            // add a new record
            addRecord();

            // use the database
            useDB();

            // get meta data
            getMetaData();

            // close the database when finished
            disconnectDB();
        }
    }

    /**
     * Establish a connection to the database. Cannot do <b>anything</b> until a connection
     * is established.
     */
    public void connectToDB() {
        System.out.println("Establishing connection to database...");

        System.out.println("   Loading JDBC driver for MS SQL Server database...");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            System.out.printf("   Unable to load JDBC driver... '%s'\n", e.getMessage());
            return;
        }

        System.out.println("   Use driver to connect to MS SQL Server (OPENBOX\\WRR)...");
        if (true) {
            try {
                System.out.println("   Locate database to open (using connection string)...");

                String connectionString = "jdbc:sqlserver://postsql.mandela.ac.za\\WRR;databaseName=WRPV301";
                System.out.println("      Connection string = " + connectionString);

                // create connection to DB, including username & password
                // NEVER, EVER, include a username and password in your code!!!!
                con = DriverManager.getConnection(connectionString, "WRPV301User", "WRPV301");

                // create statement object for manipulating DB
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (Exception e) {
                System.out.printf("   Unable to connect to DB... '%s'\n", e.getMessage());
            }
        } else {
            try {
                // another way of creating a connection to a database
                System.out.println("   Locate database to open (using server data source)...");

                // specify details of connection to be made
                SQLServerDataSource ds = new SQLServerDataSource();
                ds.setUser("WRPV301User");
                ds.setPassword("WRPV301");
                ds.setServerName("postsql.mandela.ac.za");
                ds.setInstanceName("WRR");
                ds.setDatabaseName("WRPV301");

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

    /**
     * Performs a <b>query</b> on the database and processes the result set
     * that was returned.
     */
    public void useDB() {
        System.out.println("Using database...");

        try {
            // perform query on database and retrieve results
            String sql = "SELECT * FROM Person";
            System.out.println("   Performing query, sql = " + sql);
            ResultSet result = stmt.executeQuery(sql);

            System.out.println();
            System.out.println("   Displaying Query Result");
            System.out.println("----------------------------------------------------------------------");

            // while there are tuples in the result set, display them
            while (result.next()) {
                // get values from current tuple
                String surname = result.getString("surname");
                String name = result.getString("name");
                String phone = result.getString("phone");
                String cell = result.getString("cell");

                // use info
                System.out.println("   " + surname + ", " + name + ", (p) " + phone + ", (c) " + cell);
            }

            System.out.println("----------------------------------------------------------------------");

            // close result set when done with it
            System.out.println("   Closing query result set...");
            result.close();

            System.out.println();
        } catch (Exception e) {
            System.out.println("   Was not able to query database...");
        }
    }

    /**
     * Performs an instruction that does <i>not</i> return something, but instead
     * modifies the database. SQL commands that fall in this category as inserts,
     * deletes, updates, creating tables, etc.
     */
    public void addRecord() {
        System.out.println("Inserting new records...");
        try {
            String sql = "INSERT INTO Person VALUES ('Somesurnameelse', 'Aname', '1234', '5678')";
            stmt.execute(sql);
            System.out.println("\tDone!");
        } catch (Exception e) {
            System.out.println("Could not insert new record... " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * Obtains information about the data being returned. Very useful in some
     * circumstances.
     */
    public void getMetaData() {
        System.out.println("Examining Meta Data...");

        try {
            // perform query on database and retrieve results
            String sql = "SELECT * FROM Person";
            System.out.println("   Performing query, sql = " + sql);
            ResultSet result = stmt.executeQuery(sql);

            // get meta data of result set
            ResultSetMetaData meta = result.getMetaData();

            int columns = meta.getColumnCount();
            System.out.println("\tColumns = " + columns);
            for (int i = 1; i <= columns; i++) {
                String colName = meta.getColumnLabel(i);
                String colType = meta.getColumnTypeName(i);
                System.out.println("\tcol[" + i + "]: name = " + colName + ", type = " + colType);
            }

            System.out.println();
            System.out.println("\tDisplay by index");
            // while there are tuples in the result set, display them... using indices
            int row = 0;
            while (result.next()) {
                // get values from current tuple
                row++;
                String line = "\tRow[" + row + "]=";
                for (int i = 1; i <= columns; i++) {
                    line = line.concat(result.getString(i) + " ");
                }

                // use info
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Could not query database... " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * All done. Be polite and close the connection with the database.
     */
    public void disconnectDB() {
        System.out.println("Disconnecting from database...");

        try {
            //Important to close connection (same as with files)
            con.close();
        } catch (Exception ex) {
            System.out.println("   Unable to disconnect from database");
        }
    }
}