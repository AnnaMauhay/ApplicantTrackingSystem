package futurewomen;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

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

    public HRSystem(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        applicants = new ArrayBlockingQueue<>(maxCapacity);
    }

    public void addRecruiter(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public synchronized void addApplicant(Applicant applicant) {
        while (applicants.size()>=maxCapacity){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            if (applicants.offer(applicant, 1, TimeUnit.SECONDS)) System.out.println("Added applicant to queue. " + applicant);
            else System.out.println("Queue is full.");
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void reviewApplicant(Recruiter recruiter) {
        while (applicants.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (recruiter.isSpecializedFor(applicants.element().getAppliedPosition())) {
            try {
                recruiter.reviewApplicant(Objects.requireNonNull(applicants.poll(1, TimeUnit.SECONDS)));

            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " is interrupted." + e.getMessage());
            }
        } else
            System.out.printf("Recruiter %s is not specialized in position %s\n", recruiter.getName(), applicants.element().getAppliedPosition().getLabel());
        notifyAll();
    }
}
