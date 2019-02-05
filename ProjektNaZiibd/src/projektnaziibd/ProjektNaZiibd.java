
package projektnaziibd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import oracle.jdbc.pool.OracleDataSource;


public class ProjektNaZiibd {

    public static void main(String[] args) {

        Connection myConnection = null;
        Statement myStatement = null;
  
        try {
            
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            myConnection = DriverManager.getConnection("jdbc:oracle:thin:@155.158.112.45:1521:oltpstud" , "ziibd22", "haslo2018");
            myConnection.setAutoCommit(false);
            myStatement = myConnection.createStatement();

            ProjektNaZiibd p = new ProjektNaZiibd();
            int x = 1;
            Scanner odczyt = new Scanner(System.in);
            
            while(x != 0) {
                
                System.out.print("\nWybierz co chcesz zrobić:\n 1: Sprawdz tabelę dept\n 2: Wrzuc cos do tabeli dept( w formacie [numer, string] )\n 3: Usun cos z tabeli dept podajac id [numer]\n 0: Wyjdz z programu\n");              
                
                x = odczyt.nextInt();
                
                if (x == 0) {
                    break;
                }
                if (x == 1) {
                    p.checkTable(myStatement);
                }
                if (x == 2) {
                    p.insertTable(myStatement);
                }
                if (x == 3) {
                    p.deleteTable(myStatement);
                }
            }
        }
        catch(SQLException e){
            System.out.println("Error code = " + e.getErrorCode());
            System.out.println("Error message = " + e.getMessage());
            System.out.println("SQL state = " + e.getSQLState());
            e.printStackTrace();
        }
        finally {
            try {
                if(myStatement != null ){
                    myStatement.close();
                }
                if(myConnection != null) {
                    myConnection.close();
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    private Boolean checkTable(Statement myStatement) throws SQLException {
        
        int id;
        String name;
        
        ResultSet customerResultSet = myStatement.executeQuery(
            "SELECT id, name " +
            "FROM dept");
        
            while(customerResultSet.next()){

                id = customerResultSet.getInt("id");
                name = customerResultSet.getString("name");

                System.out.println("id= " + id);
                System.out.println("name= " + name); 
        }
        customerResultSet.close();
        return true;
    }
    
    private void insertTable(Statement myStatement) throws SQLException {
        
        int id;
        String name;
        
        Scanner odczyt = new Scanner(System.in);
        
        id = odczyt.nextInt();
        name = odczyt.nextLine();
        
        myStatement.executeUpdate(
        "INSERT INTO dept VALUES (" + id + ",'" + name + "')");
        myStatement.executeUpdate(
        "COMMIT");
    }
    
    private void deleteTable(Statement myStatement) throws SQLException {
        
        int id;
        
        Scanner odczyt = new Scanner(System.in);
        id = odczyt.nextInt();
        
        myStatement.executeUpdate(
        "DELETE FROM dept WHERE id = '" + id + "'");
        myStatement.executeUpdate(
        "COMMIT");
    }
}