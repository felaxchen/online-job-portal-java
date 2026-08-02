public class HR {
    private String username;
    private String password;
    private String fullName;
    private String companyName;

    public HR(String username, String password, String fullName, String companyName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.companyName = companyName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getCompanyName() { return companyName; }

    @Override
    public String toString() {
        return "HR: " + fullName + " (" + companyName + ")";
    }
}
