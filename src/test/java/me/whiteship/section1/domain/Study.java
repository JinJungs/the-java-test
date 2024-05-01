package me.whiteship.section1.domain;

import me.whiteship.section0.StudyStatus;

import java.time.LocalDateTime;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private Member owner;

    private String name;

    private LocalDateTime openedDateTime;

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

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Member getOwner() {
        return owner;
    }

    public LocalDateTime getOpenedDateTime() {
        return openedDateTime;
    }



    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }
}
