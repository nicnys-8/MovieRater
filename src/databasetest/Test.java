package databasetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void compare(String[] args) {
        
        long before, after;
        int numIterations = 500;
        
        if (args.length > 0) {
            try {
                numIterations = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument " + args[0] + " must be an integer.");
                System.exit(1);
            }
        }
     
        System.out.println("Number of iterations: " + numIterations);
        
        before = System.currentTimeMillis();
        notPrepared(numIterations);
        after = System.currentTimeMillis();
        System.out.println("notPrepared runtime: " + (after - before));
        
        before = System.currentTimeMillis();
        prepared(numIterations);
        after = System.currentTimeMillis();
        System.out.println("prepared runtime: " + (after - before));

    }

    private static void notPrepared(int numIterations) {
        

        Connection con = null;
        Statement st = null;

        String cs = "jdbc:mysql://localhost:3307/testdb";
        String user = "testuser";
        String password = "test623";

        try {

            con = DriverManager.getConnection(cs, user, password);

            st = con.createStatement();

            for (int i = 1; i <= numIterations; i++) {
                String query = "INSERT INTO Testing(Id) VALUES(" + 2 * i + ")";
                st.executeUpdate(query);
            }

            System.out.println("Doop");

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Test.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Test.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    private static void prepared(int numIterations) {

        Connection con = null;
        PreparedStatement pst = null;

        String cs = "jdbc:mysql://localhost:3307/testdb";
        String user = "testuser";
        String password = "test623";

        try {

            con = DriverManager.getConnection(cs, user, password);

            pst = con.prepareStatement("INSERT INTO Testing(Id) VALUES(?)");

            for (int i = 1; i <= numIterations; i++) {
                pst.setInt(1, i * 2);
                pst.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Test.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Test.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
