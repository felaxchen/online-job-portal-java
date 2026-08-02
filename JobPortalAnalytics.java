import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobPortalAnalytics {

    public static void printSummary(List<Job> jobs, List<Candidate> candidates, List<Application> applications) {
        System.out.println("\n===== ANALYTICS DASHBOARD =====");
        System.out.println("Total Registered Candidates : " + candidates.size());
        System.out.println("Total Jobs Posted           : " + jobs.size());
        System.out.println("Total Applications Submitted: " + applications.size());

        System.out.println("Most In-Demand Category     : " + mostPopularCategory(jobs));
        System.out.println("Most Applied Job             : " + mostAppliedJob(jobs));
        printApplicationsPerCategory(jobs);
        System.out.println("================================\n");
    }

    private static String mostPopularCategory(List<Job> jobs) {
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Job job : jobs) {
            categoryCount.merge(job.getCategory(), 1, Integer::sum);
        }
        String topCategory = "N/A";
        int max = -1;
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topCategory = entry.getKey();
            }
        }
        return topCategory + (max > 0 ? " (" + max + " jobs)" : "");
    }

    private static String mostAppliedJob(List<Job> jobs) {
        Job topJob = null;
        int max = -1;
        for (Job job : jobs) {
            if (job.getApplications().size() > max) {
                max = job.getApplications().size();
                topJob = job;
            }
        }
        if (topJob == null) return "N/A";
        return topJob.getTitle() + " (" + max + " applicants)";
    }

    private static void printApplicationsPerCategory(List<Job> jobs) {
        Map<String, Integer> categoryApplications = new HashMap<>();
        for (Job job : jobs) {
            categoryApplications.merge(job.getCategory(), job.getApplications().size(), Integer::sum);
        }
        System.out.println("Applications by Category:");
        if (categoryApplications.isEmpty()) {
            System.out.println("  No data yet.");
        }
        for (Map.Entry<String, Integer> entry : categoryApplications.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue() + " applications");
        }
    }
}
