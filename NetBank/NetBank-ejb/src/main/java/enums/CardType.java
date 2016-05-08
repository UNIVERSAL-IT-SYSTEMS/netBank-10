package enums;

/**
 *
 * @author Daniel Szabo
 */
public enum CardType {
    VISA("Visa"),
    MASTER_CARD("Master Card"),
    MAESTRO("Maestro");

    private String type;

    CardType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
