package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Algorithm {
    private final DecisionRule decisionRule;
    private final DistanceFunction distanceFunction;
    private List<BFunction> bFunctions;
    private List<Rice> train;
    private List<Rice> parametric;
    int numClasses;
    private final double minAccuracy = 0.80;

    public Algorithm() {
        decisionRule = values -> {
            int indMin = 0;
            for (int i = 1; i < values.size(); i++) {
                if (values.get(i) < values.get(indMin)) {
                    indMin = i;
                }
            }
            return indMin;
        };
        distanceFunction = (riceFirst, riceSecond) -> {
            double distance = 0;
            for (int i = 0; i < riceFirst.getFeatures().size(); i++) {
                distance += Math.pow(riceFirst.getFeatures().get(i) - riceSecond.getFeatures().get(i), 2);
            }
            return Math.sqrt(distance);
        };
    }

    public double getTrainAccuracy() {
        return calculateAccuracy(train);
    }

    public double calculateAccuracy(List<Rice> riceList) {
        int numCorrect = 0;
        for (Rice rice : riceList) {
            if (rice.getClassNum() == predict(rice)) {
                numCorrect += 1;
            }
        }
        return 1. * numCorrect / riceList.size();
    }

    public void fit(List<Rice> train, List<Rice> parametric) {
        this.train = train;
        this.parametric = parametric;
        numClasses = Stream.concat(train.stream(), parametric.stream()).mapToInt(Rice::getClassNum)
                .max().orElseThrow(() -> new RuntimeException("empty samples")) + 1;
        double accuracy = 0;
        while (accuracy < minAccuracy) {
            bFunctions = new ArrayList<>();
            bFunctions.add(values -> values.stream()
                    .reduce(0d, Double::sum) / values.size());
            bFunctions.add(values -> values.stream()
                    .reduce(0d, Double::sum) / values.size());
            accuracy = getTrainAccuracy();
        }
    }

    public int predict(Rice rice) {
        List<Double> d = new ArrayList<>();
        for (Rice value : parametric) {
            d.add(distanceFunction.distance(rice, value));
        }
        List<Double> b = new ArrayList<>();
        for (int k = 0; k < numClasses; k++) {
            List<Double> values = new ArrayList<>();
            for (int j = 0; j < parametric.size(); j++) {
                if (parametric.get(j).getClassNum() == k) {
                    values.add(d.get(j));
                }
            }
            b.add(bFunctions.get(k).calculate(values));
        }
        return decisionRule.calculate(b);
    }
}
