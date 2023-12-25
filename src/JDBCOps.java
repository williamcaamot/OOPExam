import java.sql.*;
import java.util.ArrayList;

public class JDBCOps {

    public JDBCOps() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public Connection getConnection(){
        try{
            return DriverManager
                    .getConnection(
                            "jdbc:mysql://localhost:3306/universityDB?" + // CAN CHANGE NAME OF DB HERE
                                    "useSSL=false&" +
                                    "allowPublicKeyRetrieval=false&" +
                                    "allowMultiQueries=true",
                            "root", // CHANGE USERNAME HERE
                            "Passord1" // CHANGE PASSWORD HERE
                    );

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }

    public Connection getConnectionEvent(){
        try{
            return DriverManager
                    .getConnection(
                            "jdbc:mysql://localhost:3306/eventDB?" + // CAN CHANGE NAME OF DB HERE
                                    "useSSL=false&" +
                                    "allowPublicKeyRetrieval=false&" +
                                    "allowMultiQueries=true",
                            "root", // CHANGE USERNAME HERE
                            "Passord1" //CHANGE PASSWORD HERE
                    );

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }


    public Student getStudent(String name){
        Student returnVariable = new Student();

        try(Connection con = getConnection()){
            String query = "SELECT * FROM students WHERE name = ?;";
            PreparedStatement checkStatement = con.prepareStatement(query);
            checkStatement.setString(1, name);
            ResultSet resultSet = checkStatement.executeQuery();
            if(resultSet.next()){
                returnVariable.setId(resultSet.getInt("id"));
                returnVariable.setName(resultSet.getString("name"));
                returnVariable.setProgram(resultSet.getString("studyProgram"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnVariable;
    }

    public ArrayList<Attendee> getAllAttendess(String program, String role, String name){
        ArrayList<Attendee> returnVariable = new ArrayList<>();

        try(Connection con = getConnectionEvent()){
            String query = "SELECT * FROM attendees WHERE studyProgram LIKE ? AND ROLE LIKE ? AND NAME LIKE ?;";
            PreparedStatement checkStatement = con.prepareStatement(query);
            checkStatement.setString(1,program);
            checkStatement.setString(2,role);
            checkStatement.setString(3, name);
            ResultSet resultSet = checkStatement.executeQuery();
            while(resultSet.next()){
                Attendee temp = new Attendee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("role"),
                        resultSet.getString("guestOf"),
                        resultSet.getString("studyProgram")
                );
                returnVariable.add(temp);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnVariable;
    }


    public boolean checkForAttendee(String name){ // Using name instead of object because when having name I can check guests as well
        boolean returnVariable = false; // return true if student or guest already exists, false if not

        try(Connection con = getConnectionEvent()){
            String query = "SELECT * FROM attendees WHERE name=?;";
            PreparedStatement checkStatement = con.prepareStatement(query);
            checkStatement.setString(1,name);
            ResultSet resultSet = checkStatement.executeQuery();
            if(resultSet.next()){
                returnVariable = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnVariable;
    }



    public boolean addAttendee(Attendee attendee){
        boolean returnVariable = false;
        try(Connection con = getConnectionEvent()){
            String query = "INSERT INTO attendees (name, role, guestOf, studyProgram) VALUES(?,?,?,?);";
            PreparedStatement checkStatement = con.prepareStatement(query);
            checkStatement.setString(1,attendee.getName());
            checkStatement.setString(2,attendee.getRole());
            checkStatement.setString(3,attendee.getGuestOf());
            checkStatement.setString(4,attendee.getProgram());
            if(checkStatement.executeUpdate() == 1){
                returnVariable = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnVariable;
    }

    public boolean removeAttendant(String name){
        boolean returnVariable = false;
        try(Connection con = getConnectionEvent()){
            String query1 = "DELETE FROM attendees WHERE name LIKE ?;";
            String query2 = "DELETE FROM attendees WHERE guestOf LIKE ?";
            PreparedStatement checkStatement = con.prepareStatement(query1);
            PreparedStatement checkStatement2 = con.prepareStatement(query2);
            checkStatement.setString(1,name);
            checkStatement2.setString(1,name);
            if(checkStatement.executeUpdate() >= 1){
                returnVariable = true;
            }
            checkStatement2.executeUpdate();
        }catch (SQLException e){
            //e.printStackTrace();
        }
        return returnVariable;
    }

    public ArrayList<StudyProgramInfo> infoAboutAllPrograms(){
        ArrayList<StudyProgramInfo> returnVariable = new ArrayList<>();

        try(Connection con = getConnectionEvent()){
            String query = "SELECT studyProgram, " +
                    "MAX(CASE WHEN role = 'Program Responsible' THEN name END) as responsiblePerson, " +
                    "SUM(CASE WHEN role = 'Student' THEN 1 ELSE 0 END) as howManyStudents " +
                    "FROM attendees " +
                    "WHERE role = 'Program Responsible' OR role = 'Student'" +
                    "GROUP BY studyProgram;";
            PreparedStatement checkStatement = con.prepareStatement(query);
            ResultSet resultSet = checkStatement.executeQuery();
            while(resultSet.next()){
                StudyProgramInfo temp = new StudyProgramInfo();
                temp.setName(resultSet.getString("studyProgram"));
                temp.setResponsiblePerson(resultSet.getString("responsiblePerson"));
                temp.setAmountOfStudents(resultSet.getInt("howManyStudents"));
                returnVariable.add(temp);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnVariable;
    }






}
