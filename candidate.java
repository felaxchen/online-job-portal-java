public class Candidate {
    private String username;
    private String password;
    private String fullName;
    private String resumeSummary;

    public Candidate(String username, String password, String fullName, String resumeSummary) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.resumeSummary = resumeSummary;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getResumeSummary() { return resumeSummary; }

    public void setResumeSummary(String resumeSummary) { this.resumeSummary = resumeSummary; }

    @Override
    public String toString() {
        return "Candidate: " + fullName + " (" + username + ")";
    }
}
