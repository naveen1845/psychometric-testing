package com.careercompass.model;

/**
 * Enum representing the five career-interest dimensions assessed by Career Compass.
 * Each dimension has three predefined questions assigned to it.
 */
public enum Dimension {
    ANALYTICAL_THINKING("Analytical Thinking", "Measures interest in logic, pattern identification, and structured problem-solving."),
    CREATIVITY("Creativity", "Measures interest in original thinking, visual/aesthetic design, and innovative solutions."),
    SOCIAL_ORIENTATION("Social Orientation", "Measures interest in helping others, teaching, collaboration, and empathetic communication."),
    LEADERSHIP_AND_INITIATIVE("Leadership and Initiative", "Measures comfort with taking ownership, decision-making, and directing group activities."),
    PRACTICAL_HANDS_ON("Practical and Hands-on Orientation", "Measures enthusiasm for building, experimentation, technical mechanics, and practical implementation.");

    private final String displayName;
    private final String description;

    Dimension(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public static Dimension fromString(String text) {
        if (text == null) return null;
        for (Dimension d : Dimension.values()) {
            if (d.name().equalsIgnoreCase(text.trim()) || d.displayName.equalsIgnoreCase(text.trim())) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown dimension: " + text);
    }
}
