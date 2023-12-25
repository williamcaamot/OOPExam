import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    JDBCOps jdbcOps = new JDBCOps();
    public void run(){


        System.out.println();

        boolean running = true;

        while(running == true){ // INITIAL MENU
            System.out.println("Welcome to even registry for the ceremony of 2023! Please select an option below:");
            System.out.println("0 - Exit program");
            System.out.println("1 - Sign in");
            System.out.println("2 - See the program");

            String option = scanner.nextLine();

            if(option.equalsIgnoreCase("0")){ // CLOSES PROGRAM
                running = false;
            } else if (option.equalsIgnoreCase("1")) { // OPENS THE USER MENU AND LOGS USER IN
                signInMenu();
            } else if (option.equalsIgnoreCase("2")) { // SAME CODE AS BELOW, COULD BE IT'S OWN FUNCTION / METHOD
                System.out.println("+----------------------+----------------------+----------------------+");
                System.out.printf("| %-20s | %-20s | %-20s |%n", "What presentation","Person talking","Time");
                System.out.println("+----------------------+----------------------+----------------------+");
                System.out.printf("| %-20s | %-20s | %-20s |%n", "Introduction","Headmaster","30 minutes");

                for(StudyProgramInfo studyProgramInfo : jdbcOps.infoAboutAllPrograms()){
                    System.out.printf("| %-20s | %-20s | %-20s |%n", studyProgramInfo.getName(),studyProgramInfo.getResponsiblePerson(),((studyProgramInfo.getAmountOfStudents()/5)+1) + " minutes");
                    System.out.printf("| %-20s | %-20s | %-20s |%n", "Break","N/A","5 minutes");
                }
                System.out.printf("| %-20s | %-20s | %-20s |%n", "Closing remarks","Headmaster","15 minutes");
                System.out.println("+----------------------+----------------------+----------------------+");
            }else{
                System.out.println("You didn't write any of the options correctly, please write '0' , '1' or '2'");
            }
        }
    }

    public void signInMenu(){ // USER MENU
        System.out.println("What's your name?:");
        Student user = jdbcOps.getStudent(scanner.nextLine());
        if(user.getName() != null){ // CHECKING IF USER ALREADY REGISTERED OR NOT
            System.out.println("Welcome " + user.getName());
            boolean running = true;
            while(running == true){ // MENU WITH ALL THE NEW OPTIONS FOR THE USER, BELOW THEY ARE CODED
                System.out.println("Select one of the options below;");
                System.out.println("0 - Sign out and exit to the main menu");
                System.out.println("1 - Register for the event");
                System.out.println("2 - See all participants for the event");
                System.out.println("3 - See participants from a specific program");
                System.out.println("4 - Search for a particular student in the event"); // One place in the task it said student, another place students and guests, here it is only students
                System.out.println("5 - See all students registered for the event");
                System.out.println("6 - See the overall program");
                System.out.println("7 - Delete my registrering (along with any guests  you have invited)");
                System.out.println("8 - Redo my register with new guests");

                String option = scanner.nextLine();
                if(option.equalsIgnoreCase("0")){
                    running = false;
                } else if (option.equalsIgnoreCase("1")) { // OPTION 1 - REGISTER USER FOR THE EVENT ALONG WITH HIS/HER GUESTS

                    if(jdbcOps.checkForAttendee(user.getName())){
                        System.out.println("You are already registered.");
                    }else {
                        System.out.println("How many guests do you want to bring? (Type in 0 - 4)");
                        int howManyGuests = 0;
                        try {
                            howManyGuests = Integer.parseInt(scanner.nextLine()); // INSTEAD OF THROWING OR PRINTING ERROR SIMPLY HANDLING IT BY ASKING USER TO REWRITE THE INPUT, COULD BE USEFUL TO LOG
                        }catch (NumberFormatException n){
                            System.out.println("Something went wrong... Please write a number between 0-4");
                        }
                        if(howManyGuests < 0 ||howManyGuests > 4){ // CHECKING FOR AMOUNT OF GUESTS TO BE WITHIN DEISRED RANGE
                            System.out.println("Something went wrong... Please write a number between 0-4");
                        }else{
                            for(int i = 1; i < howManyGuests + 1; i++){
                                System.out.println("What is the name of your guest number " + i + "?");

                                if(jdbcOps.addAttendee(new Attendee(scanner.nextLine(), "Guest",user.getName(),"N/A"))){ // CHECKING IF GUEST ALREADY EXSITS
                                    System.out.println("First guest added...");
                                }else{
                                    System.out.println("This guest has already been added");
                                }
                            }
                        }
                        jdbcOps.addAttendee(new Attendee(user.getName(), "Student", "N/A", user.getProgram()));
                    }


                } else if (option.equalsIgnoreCase("2")) {
                    showAttendees("%%", "%%", "%%"); // %% is used to show all records within a parameter
                } else if (option.equalsIgnoreCase("3")) {
                    System.out.println("Type in which program you want to see students registered for the ceremony:");
                    showAttendees(scanner.nextLine(), "%%", "%%");

                }else if(option.equalsIgnoreCase("4")){
                    System.out.println("Type in the student name you are looking for:");
                    showAttendees("%%","student",scanner.nextLine());
                }else if(option.equalsIgnoreCase("5")){
                    showAttendees("%%","student","%%");
                } else if (option.equalsIgnoreCase("6")) {

                    System.out.println("+----------------------+----------------------+----------------------+");
                    System.out.printf("| %-20s | %-20s | %-20s |%n", "What presentation","Person talking","Time");
                    System.out.println("+----------------------+----------------------+----------------------+");
                    System.out.printf("| %-20s | %-20s | %-20s |%n", "Introduction","Headmaster","30 minutes");

                    for(StudyProgramInfo studyProgramInfo : jdbcOps.infoAboutAllPrograms()){
                        System.out.printf("| %-20s | %-20s | %-20s |%n", studyProgramInfo.getName(),studyProgramInfo.getResponsiblePerson(),((studyProgramInfo.getAmountOfStudents()/5)+1) + " minutes");
                        System.out.printf("| %-20s | %-20s | %-20s |%n", "Break","N/A","5 minutes");
                    }
                    System.out.printf("| %-20s | %-20s | %-20s |%n", "Closing remarks","Headmaster","15 minutes");
                    System.out.println("+----------------------+----------------------+----------------------+");


                } else if (option.equalsIgnoreCase("7")){
                    System.out.println("Are you sure you want to delete your registration along with your guests? ('YES' or 'NO')");
                    String answer = scanner.nextLine();
                    if(answer.equalsIgnoreCase("YES")){
                        if(jdbcOps.removeAttendant(user.getName())){
                            System.out.println("You and guests associated with you were successfully removed");
                        }else{
                            System.out.println("There was a problem removing you.. You may not have been registered to begin with");
                        }


                    }else{
                        System.out.println("Returning to menu...");
                    }
                }else if(option.equalsIgnoreCase("8")){
                    jdbcOps.removeAttendant(user.getName());
                    System.out.println("Your details have been removed, please re-do your registration;");
                    System.out.println("How many guests do you want to bring? (Type in 0 - 4)");    // This is the same code as option 1, so it would probably be better to make it a seperate method/function instead of having it like this
                    int howManyGuests = 0;
                    try {
                        howManyGuests = Integer.parseInt(scanner.nextLine());
                    }catch (NumberFormatException n){
                        System.out.println("Something went wrong... Please write a number between 0-4");
                    }
                    if(howManyGuests < 0 ||howManyGuests > 4){
                        System.out.println("Something went wrong... Please write a number between 0-4");
                    }else{
                        for(int i = 1; i < howManyGuests + 1; i++){
                            System.out.println("What is the name of your guest number " + i + "?");

                            if(jdbcOps.addAttendee(new Attendee(scanner.nextLine(), "Guest",user.getName(),"N/A"))){
                                System.out.println("First guest added...");
                            }else{
                                System.out.println("This guest has already been added");
                            }
                        }
                    }
                    jdbcOps.addAttendee(new Attendee(user.getName(), "Student", "N/A", user.getProgram()));
                }


            }
        }else{
            System.out.println("Ehm! Seems like either you are not a student, or you wrote your name wrong!");
        }
    }

    public void showAttendees(String program, String role, String name){
        ArrayList<Attendee> attendees = jdbcOps.getAllAttendess(program, role, name);
        System.out.println("+----------------------+----------------------+----------------------+------------------------------------+");
        System.out.printf("| %-20s | %-20s | %-20s | %-34s |%n", "Name","Role","Guest of","Study program");
        System.out.println("+----------------------+----------------------+----------------------+------------------------------------+");
        for(Attendee attendee : attendees){
            System.out.printf("| %-20s | %-20s | %-20s | %-34s |%n", attendee.getName(),attendee.getRole(),attendee.getGuestOf(),attendee.getProgram());
        }
        System.out.println("+----------------------+----------------------+----------------------+------------------------------------+");
    }




}
