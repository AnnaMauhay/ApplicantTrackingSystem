package futurewomen;

public class Applicant {
    private String name;
    private JobPosition appliedPosition;
    private Status status = Status.PENDING;

    public Applicant(String name, JobPosition appliedPosition) {
        this.name = name;
        this.appliedPosition = appliedPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobPosition getAppliedPosition() {
        return appliedPosition;
    }

    public void setAppliedPosition(JobPosition appliedPosition) {
        this.appliedPosition = appliedPosition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Name: "+name + ", AppliedPosition: " + appliedPosition.getLabel();
    }
}
