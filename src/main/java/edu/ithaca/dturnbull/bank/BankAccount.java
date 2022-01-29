package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) throws IllegalArgumentException{
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @post throw an exception if the amount is negative, or greater than the balance
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException{
        if(amount < 0){
            throw new IllegalArgumentException("amount can't be negative");
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @param amount amount in bank account balance
     * @return true if amount is non-negative and has two decimal places or less, or false otherwise
     */
    public static boolean isAmountValid(double amount){
        if (amount <= 0){
            return false;
        }

        String amountStr = Double.toString(amount);
        if (amountStr.substring(amountStr.indexOf('.'), amountStr.length() - 1).length() > 2){
            return false;
        }
        else{
            return true;
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        

        int size = email.length();
        
        boolean flag = true;
        for(int i = 0; i < size; i++){
            if ((email.charAt(i)=='.')||(email.charAt(i)=='-')||(email.charAt(i)=='_')||(email.charAt(i)=='@')){
                if(flag){
                    return false;
                }
                flag = true;
            }
            else{
                flag = false;
            }
            if(email.charAt(i)=='#'){
                return false;
            }
        }
        if (email.charAt(size-1)=='.' || email.charAt(size-2)== '.'){
            return false;
        }
        for(int i = email.indexOf('@'); i < size; i++){
            if(email.charAt(i)=='.'){
                break;
            }
            else if(i == (size-1)){
                return false;
            }
        }
        return true;

    }
}