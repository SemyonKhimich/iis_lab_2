package logic;

import tools.MyException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

//class BFunctionClass implements logic.BFunction {
//    List<Double> coefficients;
//
//    public BFunctionClass(Random random, int numFeatures) {
//        coefficients = new ArrayList<>();
//        for (int i = 0; i < numFeatures; i++) {
//            coefficients.add(random.nextDouble());
//        }
//        double sum = coefficients.stream().reduce(0d, Double::sum);
//        for (int i = 0; i < numFeatures; i++) {
//            coefficients.set(i, coefficients.get(i) / sum);
//        }
//    }
//
//    @Override
//    public String toString() {
//        return coefficients.toString();
//    }
//
//    @Override
//    public double calculate(List<Double> values) {
//        if (values.size() != coefficients.size()) {
//            throw new RuntimeException();
//        }
//        double value = 0;
//        for (int i = 0; i < coefficients.size(); i++) {
//            value += coefficients.get(i) * values.get(i);
//        }
//        return value;
//    }
//}

public class Rice {
    public static int FEATURES_NUM = 7;
    private final List<Double> features;
    private final int classNum;

    public static List<Rice> readData(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<Rice> data = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            try {
                data.add(Rice.parseLine(line));
            } catch (MyException exception) {
                System.out.println("Skip object");
            }
            line = reader.readLine();
        }
        return data;
    }

    public static Rice parseLine(String line) throws MyException {
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        if (tokenizer.countTokens() - 1 != Rice.FEATURES_NUM) {
            throw new MyException("");
        }
        List<Double> features = new ArrayList<>();
        for (int i = 0; i < Rice.FEATURES_NUM; i++) {
            features.add(Double.parseDouble(tokenizer.nextToken()));
        }
        return new Rice(features, Integer.parseInt(tokenizer.nextToken()));
    }

    public Rice(List<Double> features, int classNum) {
        this.features = features;
        this.classNum = classNum;
    }

    public List<Double> getFeatures() {
        return features;
    }

    public int getClassNum() {
        return classNum;
    }
}
