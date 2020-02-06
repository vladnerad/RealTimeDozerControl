package ru.dst.analyze.realtime.response;

public class Message {

    private Result result;
    private String description;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data=" + result +
                '}';
    }
}
