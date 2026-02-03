package com.example.ass1.model;

public class Settings {
    private String button1Name;
    private String button2Name;
    private String button3Name;
    private int maxEvents;

    public Settings() {}

    public Settings(String b1, String b2, String b3, int maxEvents) {
        this.button1Name = b1;
        this.button2Name = b2;
        this.button3Name = b3;
        this.maxEvents = maxEvents;
    }

    public String getButton1Name() { return button1Name; }
    public void setButton1Name(String button1Name) { this.button1Name = button1Name; }

    public String getButton2Name() { return button2Name; }
    public void setButton2Name(String button2Name) { this.button2Name = button2Name; }

    public String getButton3Name() { return button3Name; }
    public void setButton3Name(String button3Name) { this.button3Name = button3Name; }

    public int getMaxEvents() { return maxEvents; }
    public void setMaxEvents(int maxEvents) { this.maxEvents = maxEvents; }

    public boolean isComplete() {
        return button1Name != null && !button1Name.isBlank()
                && button2Name != null && !button2Name.isBlank()
                && button3Name != null && !button3Name.isBlank()
                && maxEvents >= 5 && maxEvents <= 200;
    }
}
