package by.ksu.training.entity;

import java.util.List;
import java.util.Objects;

public class Exercise extends Entity {
    public static List<String> types;
    private String title;
    private String adjusting;
    private String mistakes;
    private String picturePath;
    private String audioPath;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdjusting() {
        return adjusting;
    }

    public void setAdjusting(String adjusting) {
        this.adjusting = adjusting;
    }

    public String getMistakes() {
        return mistakes;
    }

    public void setMistakes(String mistakes) {
        this.mistakes = mistakes;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getType() {
        return type;
    }
    public int getTypeId() {
        return types.indexOf(type) +1;
    }

    public void setType(int i) {
        this.type = types.get(i-1);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(title, exercise.title) &&
                Objects.equals(adjusting, exercise.adjusting) &&
                Objects.equals(mistakes, exercise.mistakes) &&
                Objects.equals(picturePath, exercise.picturePath) &&
                Objects.equals(audioPath, exercise.audioPath) &&
                Objects.equals(type, exercise.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, adjusting, mistakes, picturePath, audioPath, type);
    }

    @Override
    public String toString() {
        return "Exercise{" + super.toString() +
                ", title=" + title +
                ", adjusting=" + adjusting +
                ", mistakes=" + mistakes +
                ", picturePath=" + picturePath +
                ", audioPath=" + audioPath +
                ", type=" + type +
                "} ";
    }
}
