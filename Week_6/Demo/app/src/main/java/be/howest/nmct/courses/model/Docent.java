package be.howest.nmct.courses.model;

/**
 * Created by Stijn on 23/03/2015.
 */
public class Docent {

    private String lastName;
    private String firstname;
    private String email;

    public Docent(String lastName, String firstname, String email) {
        this.lastName = lastName;
        this.firstname = firstname;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return getLastName().toUpperCase() + ", " + getFirstname();
    }
}
