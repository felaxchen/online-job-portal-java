import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Online Job Portal and Analytics - Core Java Console Application
 *
 * Simulates a simplified job portal where Candidates can register,
 * search and apply for jobs, and HR users can post jobs and review
 * applications. Includes a basic analytics dashboard.
 */
public class JobPortalSystem {

    private static final List<Candidate> candidates = new ArrayList<>();
    private static final List<HR> hrUsers = new ArrayList<>();
    private static final List<Job> jobs = new ArrayList<>();
    private static final List<Application> applications = new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedDemoData();

        boolean running = true;
        while (running) {
            System.out.println("========== ONLINE JOB PORTAL AND ANALYTICS ==========");
            System.out.println("1. Candidate Register");
            System.out.println("2. Candidate Login");
            System.out.println("3. HR Register");
            System.out.println("4. HR Login");
            System.out.println("5. View Analytics Dashboard");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": candidateRegister(); break;
                case "2": candidateLogin(); break;
                case "3": hrRegister(); break;
                case "4": hrLogin(); break;
                case "5": JobPortalAnalytics.printSummary(jobs, candidates, applications); break;
                case "6": running = false; break;
                default: System.out.println("Invalid option. Try again.\n");
            }
        }
        System.out.println("Thank you for using the Online Job Portal. Goodbye!");
    }

    // ---------------- Candidate flows ----------------

    private static void candidateRegister() {
        System.out.print("Choose username: ");
        String username = sc.nextLine().trim();
        System.out.print("Choose password: ");
        String password = sc.nextLine().trim();
        System.out.print("Full name: ");
        String name = sc.nextLine().trim();
        System.out.print("Short resume summary/skills: ");
        String resume = sc.nextLine().trim();

        candidates.add(new Candidate(username, password, name, resume));
        System.out.println("Registration successful! You can now log in.\n");
    }

    private static void candidateLogin() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        Candidate candidate = findCandidate(username, password);
        if (candidate == null) {
            System.out.println("Invalid credentials.\n");
            return;
        }

        System.out.println("Welcome, " + candidate.getFullName() + "!");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Candidate Dashboard ---");
            System.out.println("1. View All Jobs");
            System.out.println("2. Apply for a Job");
            System.out.println("3. View My Applications");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": viewAllJobs(); break;
                case "2": applyForJob(candidate); break;
                case "3": viewMyApplications(candidate); break;
                case "4": loggedIn = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void viewAllJobs() {
        if (jobs.isEmpty()) {
            System.out.println("No jobs posted yet.");
            return;
        }
        System.out.println("\n--- Available Jobs ---");
        for (Job job : jobs) {
            System.out.println(job);
        }
    }

    private static void applyForJob(Candidate candidate) {
        viewAllJobs();
        if (jobs.isEmpty()) return;

        System.out.print("Enter Job ID to apply: ");
        int jobId = parseIntSafe(sc.nextLine().trim());
        Job job = findJobById(jobId);
        if (job == null) {
            System.out.println("Job not found.\n");
            return;
        }

        boolean alreadyApplied = applications.stream()
                .anyMatch(a -> a.getCandidate() == candidate && a.getJob() == job);
        if (alreadyApplied) {
            System.out.println("You have already applied for this job.\n");
            return;
        }

        Application application = new Application(candidate, job);
        applications.add(application);
        job.addApplication(application);
        System.out.println("Application submitted successfully!\n");
    }

    private static void viewMyApplications(Candidate candidate) {
        System.out.println("\n--- My Applications ---");
        boolean found = false;
        for (Application app : applications) {
            if (app.getCandidate() == candidate) {
                System.out.println(app);
                found = true;
            }
        }
        if (!found) System.out.println("You haven't applied to any jobs yet.");
    }

    // ---------------- HR flows ----------------

    private static void hrRegister() {
        System.out.print("Choose username: ");
        String username = sc.nextLine().trim();
        System.out.print("Choose password: ");
        String password = sc.nextLine().trim();
        System.out.print("Full name: ");
        String name = sc.nextLine().trim();
        System.out.print("Company name: ");
        String company = sc.nextLine().trim();

        hrUsers.add(new HR(username, password, name, company));
        System.out.println("HR registration successful! You can now log in.\n");
    }

    private static void hrLogin() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        HR hr = findHR(username, password);
        if (hr == null) {
            System.out.println("Invalid credentials.\n");
            return;
        }

        System.out.println("Welcome, " + hr.getFullName() + "!");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- HR Dashboard ---");
            System.out.println("1. Post a New Job");
            System.out.println("2. View My Posted Jobs");
            System.out.println("3. View Applicants for a Job");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": postJob(hr); break;
                case "2": viewMyJobs(hr); break;
                case "3": viewApplicants(); break;
                case "4": loggedIn = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void postJob(HR hr) {
        System.out.print("Job title: ");
        String title = sc.nextLine().trim();
        System.out.print("Category (e.g., IT, Marketing, Finance): ");
        String category = sc.nextLine().trim();
        System.out.print("Location: ");
        String location = sc.nextLine().trim();
        System.out.print("Salary offered: ");
        double salary = parseDoubleSafe(sc.nextLine().trim());

        Job job = new Job(title, hr.getCompanyName(), category, location, salary, hr.getUsername());
        jobs.add(job);
        System.out.println("Job posted successfully!\n");
    }

    private static void viewMyJobs(HR hr) {
        System.out.println("\n--- Your Posted Jobs ---");
        boolean found = false;
        for (Job job : jobs) {
            if (job.getPostedBy().equals(hr.getUsername())) {
                System.out.println(job);
                found = true;
            }
        }
        if (!found) System.out.println("You haven't posted any jobs yet.");
    }

    private static void viewApplicants() {
        System.out.print("Enter Job ID to view applicants: ");
        int jobId = parseIntSafe(sc.nextLine().trim());
        Job job = findJobById(jobId);
        if (job == null) {
            System.out.println("Job not found.\n");
            return;
        }
        if (job.getApplications().isEmpty()) {
            System.out.println("No applicants yet for this job.\n");
            return;
        }
        System.out.println("\n--- Applicants for " + job.getTitle() + " ---");
        for (Application app : job.getApplications()) {
            System.out.println(app);
        }
    }

    // ---------------- Helper / lookup methods ----------------

    private static Candidate findCandidate(String username, String password) {
        for (Candidate c : candidates) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) return c;
        }
        return null;
    }

    private static HR findHR(String username, String password) {
        for (HR hr : hrUsers) {
            if (hr.getUsername().equals(username) && hr.getPassword().equals(password)) return hr;
        }
        return null;
    }

    private static Job findJobById(int id) {
        for (Job job : jobs) {
            if (job.getJobId() == id) return job;
        }
        return null;
    }

    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return -1; }
    }

    private static double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return 0.0; }
    }

    // ---------------- Demo seed data (so the app is not empty on first run) ----------------

    private static void seedDemoData() {
        HR hr1 = new HR("hr_infosys", "hr123", "Anita Rao", "Infosys");
        HR hr2 = new HR("hr_tcs", "hr123", "Karan Mehta", "TCS");
        hrUsers.add(hr1);
        hrUsers.add(hr2);

        jobs.add(new Job("Java Developer", "Infosys", "IT", "Bengaluru", 650000, hr1.getUsername()));
        jobs.add(new Job("Business Analyst", "TCS", "Finance", "Pune", 550000, hr2.getUsername()));
        jobs.add(new Job("Frontend Developer", "Infosys", "IT", "Hyderabad", 600000, hr1.getUsername()));

        Candidate demoCandidate = new Candidate("demo", "demo123", "Demo Candidate", "Java, SQL, Problem Solving");
        candidates.add(demoCandidate);
    }
}
