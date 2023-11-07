package futurewomen;

import java.util.*;
import java.util.concurrent.*;

public class HRSystem {
    public static List<JobPosition> jobPositions = new ArrayList<>(Arrays.asList(
            JobPosition.EXECUTIVE,
            JobPosition.QA_TESTER,
            JobPosition.TEAM_LEAD,
            JobPosition.SOFTWARE_DEVELOPER,
            JobPosition.MANAGER));
    public static List<Recruiter> recruiters = new ArrayList<>();
    private int maxCapacity;
    private ArrayBlockingQueue<Applicant> applicants;
    public boolean quotaReached = false;


    public HRSystem(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        applicants = new ArrayBlockingQueue<>(maxCapacity);
    }



    public void addRecruiter(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public synchronized void addApplicant(Applicant applicant) {
        while (applicants.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (applicants.offer(applicant)) {
            System.out.println("Added applicant to queue. " + applicant);
        } else System.out.println("Queue is full.");
        notifyAll();

    }

    public boolean hasApplicants() {
        return !applicants.isEmpty();
    }

    public synchronized boolean reviewApplicant(Recruiter recruiter) {
        while (applicants.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (recruiter.isSpecializedFor(applicants.element().getAppliedPosition())) {
            recruiter.reviewApplicant(Objects.requireNonNull(applicants.poll()));
            notifyAll();
            return true;
        } else{
            System.out.printf("%s is not specialized in position %s\n", recruiter.getName(), applicants.element().getAppliedPosition().getLabel());
            notifyAll();
            return false;
        }


    }
}
