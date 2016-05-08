package enums;

/**
 *
 * @author Daniel Szabo
 */
public enum TransactionType {
    TRANSFER("Átutalás"),
    INPAYMENT("Befizetés"),
    WITHDRAWAL_IN_BANK("Kifizetés"),
    WITHDRAWAL_FROM_ATM("Készpénzfelvétel ATM");
    
    private String type;
    
    TransactionType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
