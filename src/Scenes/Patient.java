package Scenes;

record Patient (
    String name,
    String lastName,
    String address,
    String birthDate,
    String PESEL,
    String phoneNumber,
    String email,
    String documentation
){
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
                """.formatted(name,lastName,address,birthDate,PESEL,phoneNumber, email);
    }
}
