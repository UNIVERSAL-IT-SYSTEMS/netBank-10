package enums;

/**
 *
 * @author Daniel Szabo
 */
public enum EmailType {
    
    REGISTRATION("Üdvözli önt az SZDT Bank" , "Sikeresen regisztrált az SZDT Bank rendszerébe, "
            + "amint ügyintézőnk jóváhagyja a regisztrációt, azonnal beléphet és használhatja bankunk rendszerét!\n"),
    
    ACTIVATION("Regisztrációja jóváhagyásra került" ,"Regisztrációja jóváhagyásra került, "
            + "most már beléphet és használhatja bankunk rendszerét!\n"),
            
    REFUSE("Regisztrációja visszautasításra került" ,    "Regisztrációja visszautasításra került, "
            + "ennek oka: "
            + "\n");

	
        private String subject;
	private String message;
	
	
	EmailType(String subject, String message){
                this.subject = subject;
		this.message = message;

	}

	public String getMessage() {
		return message;
	}

    public String getSubject() {
        return subject;
    }

        
}
