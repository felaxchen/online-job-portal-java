public class Application {
    public enum Status { APPLIED, SHORTLISTED, REJECTED }

    private final Candidate candidate;
    private final Job job;
    private Status status;

    public Application(Candidate candidate, Job job) {
        this.candidate = candidate;
        this.job = job;
        this.status = Status.APPLIED;
    }

    public Candidate getCandidate() { return candidate; }
    public Job getJob() { return job; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return candidate.getFullName() + " -> " + job.getTitle() + " [" + status + "]";
    }
}
