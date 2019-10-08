package spark.ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.junit.Test;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-3</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class Clustering {
    JavaSparkContext jsc = getSparkSc();

    private JavaSparkContext getSparkSc() {
        SparkConf conf = new SparkConf().setAppName("Clustering").setMaster("local[2]");
        //创建sparkContext
        return new JavaSparkContext(conf);

    }


    @Test
    public void test() {
/**
 * bin/spark-submit    --class org.apache.spark.examples.mllib.JavaKMeansExample  --master spark://node0:7077
 * ~/spark/examples/jars/spark-examples_2.11-2.3.1.jar
 */
// Load and parse data
        String path = "learnJava/mrdata/spark/ml/data/kmeans_data.txt";
        JavaRDD<String> data = jsc.textFile(path);
        JavaRDD<Vector> parsedData = data.map(s -> {
            String[] sarray = s.split(" ");
            double[] values = new double[sarray.length];
            for (int i = 0; i < sarray.length; i++) {
                values[i] = Double.parseDouble(sarray[i]);
            }
            return Vectors.dense(values);
        });
        parsedData.cache();

// Cluster the data into two classes using KMeans
        int numClusters = 2;
        int numIterations = 20;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        System.out.println("Cluster centers:");
        for (Vector center : clusters.clusterCenters()) {
            System.out.println(" " + center);
        }
        double cost = clusters.computeCost(parsedData.rdd());
        System.out.println("Cost: " + cost);

// Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

// Save and load model
        clusters.save(jsc.sc(), "target/org/apache/spark/JavaKMeansExample/KMeansModel");
        KMeansModel sameModel = KMeansModel.load(jsc.sc(),
                "target/org/apache/spark/JavaKMeansExample/KMeansModel");
    }
}
