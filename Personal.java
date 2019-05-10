package addressbook;

public class Personal {
    private String name;
    private String address;
    private String telephoneNumber;
    private String emailAddress;

    public Personal(String name, String address, String telephoneNumber, String emailAddress){
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
    }
    
    public Personal(Personal source) {
        this.name = source.name;
        this.address = source.address;
        this.telephoneNumber = source.telephoneNumber;
        this.emailAddress   = source.emailAddress;
    }
    
    public boolean IsEqual(Personal other){
        boolean ret = false;
        
        String sourceName = other.getName();
        String sourceAddress = other.getAddress();
        String sourceTelephoneNumber = other.getTelephoneNumber();
        String sourceEmailAddress = other.getEmailAddress();
        
        if(this.name.compareTo(sourceName) == 0 && this.address.compareTo(sourceAddress) == 0
                && this.telephoneNumber.compareTo(sourceTelephoneNumber) == 0 && this.emailAddress.compareTo(sourceEmailAddress) == 0) {
            ret = true;
        }
        
        return ret;
    }
    
    public boolean IsNotEqual(Personal other) {
        boolean ret = false;
        
        String sourceName = other.getName();
        String sourceAddress = other.getAddress();
        String sourceTelephoneNumber = other.getTelephoneNumber();
        String sourceEmailAddress = other.getEmailAddress();
        
        if(this.name.compareTo(sourceName) != 0 || this.address.compareTo(sourceAddress) != 0 
                || this.telephoneNumber.compareTo(telephoneNumber) != 0 || this.emailAddress.compareTo(sourceEmailAddress) != 0) {
            ret = true;
        }
        
        return ret;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
}
