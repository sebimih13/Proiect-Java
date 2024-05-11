package model;

public class Client {
    private static Integer maxIDClient;

    static {
        maxIDClient = 0;
    }

    private Integer ID;
    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String nrTelefon;
    private String email;

    public Integer getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public String getEmail() {
        return email;
    }
}

