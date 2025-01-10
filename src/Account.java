import java.util.UUID;

public class Account {
    private String accountNum;
    private double balance_amount;
    private String accountType;

    private String generateAccountID(){
        return UUID.randomUUID().toString().substring(0,10);
    }

    public boolean addToAccount(double amount){
        if(amount > 0){
            balance_amount += amount;
            return true;
        }
        return false;
    }

    public boolean removeFromAccount(double amount){
        if(amount <= balance_amount && amount >0){
            balance_amount -= amount;
            return true;
        }
        return false;
    }

    public Account(String accountType){
        this.balance_amount = 1000;
        this.accountNum = generateAccountID();

    }

    public String getAccountNum() {
        return accountNum;
    }

    public double getBalance_amount() {
        return balance_amount;
    }
}
