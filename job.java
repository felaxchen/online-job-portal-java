import java.util.ArrayList;
import java.util.List;

public class Job {
    private static int counter = 1;

    private final int jobId;
    private String title;
    private String company;
    private String category;
    private String location;
    private double salary;
    private String postedBy; // HR username
    private final List<Application> applications;

    public Job(String title, String company, String category, String location, double salary, String postedBy) {
        this.jobId = counter++;
        this.title = title;
        this.company = company;
        this.category = category;
        this.location = location;
        this.salary = salary;
        this.postedBy = postedBy;
        this.applications = new ArrayList<>();
    }

    public int getJobId() { return jobId; }
    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public double getSalary() { return salary; }
    public String getPostedBy() { return postedBy; }
    public List<Application> getApplications() { return applications; }

    public void addApplication(Application app) {
        applications.add(app);
    }

    @Override
    public String toString() {
        return String.format("[ID:%d] %s at %s | Category: %s | Location: %s | Salary: %.2f | Applicants: %d",
                jobId, title, company, category, location, salary, applications.size());
    }
}
