package ru.dst.analyze.realtime.response;

import java.util.List;

public class Result {

    public Result() {
    }

    public Result(String error) {
        System.out.print("ERROR, reason is: ");
    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                '}';
    }
}
