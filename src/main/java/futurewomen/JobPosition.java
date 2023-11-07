package futurewomen;

public enum JobPosition {
    SOFTWARE_DEVELOPER("Software Developer"),
    TEAM_LEAD("Team Lead"),
    QA_TESTER("QA Tester"),
    EXECUTIVE("Executive"),
    MANAGER("Manager"),
    ADMIN("Administrator");
    private final String label;
    JobPosition(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
