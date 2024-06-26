package me.whiteship.section0;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    private String name;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        this.limit = limit;
        if(limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야 한다.");
        }
    }

    public StudyStatus getStatus() {
        return status;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
