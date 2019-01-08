package com.example.demo.bean.jira;

public class Board {
    private Integer boardId;
    private String currentSprintName;
    private Integer currentSprintId;

    public Board(Integer boardId, String currentSprintName, Integer currentSprintId) {
        this.boardId = boardId;
        this.currentSprintId = currentSprintId;
        this.currentSprintName = currentSprintName;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getCurrentSprintName() {
        return currentSprintName;
    }

    public void setCurrentSprintName(String currentSprintName) {
        this.currentSprintName = currentSprintName;
    }

    public Integer getCurrentSprintId() {
        return currentSprintId;
    }

    public void setCurrentSprintId(Integer currentSprintId) {
        this.currentSprintId = currentSprintId;
    }
}
