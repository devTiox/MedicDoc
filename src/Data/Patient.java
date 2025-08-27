package Data;

import java.text.Collator;
import java.util.Locale;

public class Patient implements Comparable<Patient>{
    public final String name;
    public final String lastName;
    public final String address;
    public final String birthDate;
    public final String PESEL;
    public final String phoneNumber;
    public final String email;
    public String documentation;
    public String patientSceneTitle;

    public Patient(String name, String lastName, String address, String birthDate, String PESEL, String phoneNumber, String email) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.PESEL = PESEL;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.documentation = "";
        patientSceneTitle = "PATIENT-" + PESEL + "-SCENE";
    }

    private static final Collator POLISH_COLLATOR =
            Collator.getInstance(new Locale("pl", "PL"));

    static {
        POLISH_COLLATOR.setStrength(Collator.PRIMARY);
    }

    @Override
    public int compareTo(Patient o) {
        int result = POLISH_COLLATOR.compare(this.lastName, o.lastName);
        if (result == 0) {
            result = POLISH_COLLATOR.compare(this.name, o.name);
        }
        return result;
    }

    @Override
    public String toString() {
        return """
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                -------------------------------------
                %s
                """.formatted(name,lastName,address,birthDate,PESEL,phoneNumber, email, documentation);
    }
}
