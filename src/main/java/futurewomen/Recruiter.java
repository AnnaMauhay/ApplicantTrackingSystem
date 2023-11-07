package futurewomen;

import java.util.HashSet;
import java.util.Set;

public class Recruiter {
    private String name;
    private Set<JobPosition> jobPositionsManaged = new HashSet<>();

    public void assignJobPosition(JobPosition jobPosition) {
        jobPositionsManaged.add(jobPosition);
    }

    public Set<JobPosition> getJobPositionsManaged() {
        return jobPositionsManaged;
    }

    public Recruiter(String name, Set<JobPosition> jobPositionsManaged) {
        this.name = name;
        this.jobPositionsManaged = jobPositionsManaged;
    }

    public synchronized void reviewApplicant(Applicant applicant) {
        applicant.setStatus(Status.REVIEWED);
        System.out.printf("%s finished reviewing %s's application.\n",name,applicant.getName());
    }

    public boolean isSpecializedFor(JobPosition jobPosition) {
        return jobPositionsManaged.contains(jobPosition);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
