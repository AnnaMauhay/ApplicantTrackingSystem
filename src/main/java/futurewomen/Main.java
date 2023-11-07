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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            hr.quotaReached = true;
        });
        applyThread.start();


        Thread reviewThread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (hr.hasApplicants()) hr.reviewApplicant(recruiters.get(0));
                else if (hr.quotaReached) break;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Thread reviewThread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (hr.hasApplicants()) hr.reviewApplicant(recruiters.get(1));
                else if (hr.quotaReached) break;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        reviewThread1.start();
        reviewThread2.start();


        try {
            applyThread.join();
            System.out.println("Application thread joined.\n");
            reviewThread1.join();
            reviewThread2.join();
            System.out.println("\nHR has remaining applicants? " + hr.hasApplicants());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}