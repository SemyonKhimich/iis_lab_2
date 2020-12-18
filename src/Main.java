import logic.Algorithm;
import logic.Rice;
import view.MyFrame;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Rice> data;
        try {
            data = Rice.readData("data.txt");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return;
        }
        Collections.shuffle(data, new Random(1));
        double coefficientFirst = 0.4;
        double coefficientSecond = 0.8;
        List<Rice> train = data.subList(0, (int) (data.size() * coefficientFirst));
        List<Rice> parametric = data.subList((int) (data.size() * coefficientFirst),
                (int) (data.size() * coefficientSecond));
        List<Rice> test = data.subList((int) (data.size() * coefficientSecond), data.size());
        Algorithm algorithm = new Algorithm();
        algorithm.fit(train, parametric);
        System.out.println("Train accuracy: " + algorithm.getTrainAccuracy());
        System.out.println("Test accuracy: " + algorithm.calculateAccuracy(test));
        new MyFrame(algorithm);
    }
}
