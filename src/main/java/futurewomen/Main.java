package futurewomen;

import java.util.*;

import static futurewomen.HRSystem.recruiters;

public class Main {
    public static void main(String[] args) {
        HRSystem hr = new HRSystem(5);
        Random random = new Random();

        hr.addRecruiter(new Recruiter("Recruiter 1", Set.of(JobPosition.ADMIN, JobPosition.MANAGER, JobPosition.EXECUTIVE)));
        hr.addRecruiter(new Recruiter("Recruiter 2", Set.of(JobPosition.SOFTWARE_DEVELOPER, JobPosition.QA_TESTER, JobPosition.TEAM_LEAD)));

        Thread applyThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                hr.addApplicant(new Applicant("Applicant " + i, JobPosition.values()[random.nextInt(0, 5)]));
            }
        });
        applyThread.start();


        Thread reviewThread1 = new Thread(() -> {
            hr.reviewApplicant(recruiters.get(0));
        });
        reviewThread1.start();

        Thread reviewThread2 = new Thread(() -> {
            hr.reviewApplicant(recruiters.get(0));
        });
        reviewThread2.start();





    }
}