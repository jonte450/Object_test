public class User {
    private String pin;
    private String personalNumber;
    private Account salary;
    private Account saving;


    public User(String personalNumber, String pin){
        this.personalNumber = personalNumber;
        this.pin = pin;
        this.salary = new Account("Löne-konto");
        this.saving = new Account("Spar-konto");
    }

    public boolean verifyPin(String pin){
        return this.pin.equals(pin);
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public Account getSalary() {
        return salary;
    }

    public Account getSaving() {
        return saving;
    }
}
