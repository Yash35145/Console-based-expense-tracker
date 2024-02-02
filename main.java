import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

class expense{
    int exp_id;
    int amount;
    String category;
    String description;
    String date;

    expense(){}

    expense(int exp_id, int amount, String category , String description , String date){
        this.exp_id = exp_id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }
    
    public int getExp_id() {
        return exp_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getString(){
        return String.format("%d,%d,%s,%s,%s", exp_id,amount,category,description,date);
    }

    public String getFormattedString(){
        return String.format("ID : %d , Amount : %d , Category : %s , Description : %s , Date : %s", exp_id,amount,category,description,date);
    }
}


// storing expense
class expenseStorage{

    static void storeExpense(ArrayList<expense> expObjects){
        File myFile = new File("expense.txt");
        // checking if file exist or not
        if(myFile.exists()){}
        else{
            try {
                myFile.createNewFile();
            } catch (Exception e) {
                System.out.println("Cannot create File! " + e.getMessage());
            }
        }
        
       try {
        FileWriter myFileWriter = new FileWriter("expense.txt");
        for (expense expense : expObjects) {
            myFileWriter.write(expense.getString() + "\n");
        }
        myFileWriter.close();
    } catch (Exception e) {
        System.out.println("Cannot store the data");
        e.printStackTrace();
    }
    }


    static ArrayList<expense> readExpense(){
        File myFile = new File("expense.txt");
        ArrayList<expense> expObjects = new ArrayList<expense>();
        if(myFile.exists()){
        try {
            Scanner sc = new Scanner(myFile);
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                String[] str_arr = str.split("\n");
                try {                    
                    for (String string : str_arr) {
                        String[] expInfo = string.split(",");
                        expense exp = new expense(Integer.valueOf(expInfo[0]),Integer.valueOf(expInfo[1]),expInfo[2],expInfo[3],expInfo[4]);
                        expObjects.add(exp);
                    }
                } catch (Exception e) {
                    System.out.println("Cannot return expense");
                }
            }
            sc.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
        return expObjects;
    }

}

public class main{

    static void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void main(String[] args) {

        // code to clear screen
        clearScreen();
        // arraylist to store object of class expense
        ArrayList<expense> expObjects = expenseStorage.readExpense();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while(option !=7){
            // code to display name of project
        System.out.println("\t********************************");
        System.out.println(" \t\tExpense Tracker");
        System.out.println("\t********************************\n");

        // options displayed to user
            System.out.println("1)Add expense");
            System.out.println("2)Delete expense");
            System.out.println("3)Manage categories");
            System.out.println("4)View expense");
            System.out.println("5)Modify expense");
            System.out.println("6)Generate Report");
            System.out.println("7)Exit\n");

            // taking input
            System.out.print("Enter your option :");
            option = sc.nextInt();

            switch (option) {
                case 1:
                System.out.print("\nEnter expense ID :");
                int exp_id = sc.nextInt();
                System.out.print("Enter Amount :");
                int amount = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Category :");
                String category = sc.nextLine();
                System.out.print("Enter Description :");
                String description = sc.nextLine();
                System.out.print("Enter Date(dd/mm/yyyy) :");
                String date = sc.nextLine();
                expense exp = new expense(exp_id, amount, category, description, date);
                try {
                    expObjects.add(exp);
                    expenseStorage.storeExpense(expObjects);
                    System.out.println("\n\nInformation Added successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

                case 2:
                System.out.println("\n\n***************");
                System.out.println("Delete Expense");
                System.out.println("***************\n");
                System.out.print("Enter Expense ID to delete expense :");
                int deleteExpId = sc.nextInt();
                boolean expenseDeleted = false;
                try {
                    for (expense seperateExpense : expObjects) {
                        if(seperateExpense.getExp_id() == deleteExpId){
                           expObjects.remove(expObjects.indexOf(seperateExpense));
                           System.out.println("Expense Deleted successfully");
                           expenseDeleted = true;
                        }
                    }
                } catch (Exception e) {
                    // System.out.println("Error while deleting expense.");
                }
                if(expenseDeleted == false) System.out.println("Element not found");
                expenseStorage.storeExpense(expObjects);
                break;

                case 4:
                System.out.println("\n\n***************");
                System.out.println("View Expense");
                System.out.println("***************\n");
                int i = 1;
                for (expense expense : expObjects) {
                    System.out.println(i+") " + expense.getFormattedString());
                    i++;
                }
                System.out.println("\n");
                break;

                case 7:
                    System.out.println("Thank you for using our expense tracker, Goodbye...");
                    break;
            
                default:
                System.out.println("Invalid Choice. Try Again");
                    break;
            }

        }
        
        
        sc.close();
    }
}