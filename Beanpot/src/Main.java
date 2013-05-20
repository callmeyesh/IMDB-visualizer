import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		Job job = new Job(conf, "Extract movie names and city");
		String inputPath = "/Users/yeshwanthvenkatesh/Documents/MyProjects/Beanpot/inputs/2010s.txt";
		String outputPath = "/Users/yeshwanthvenkatesh/Documents/MyProjects/Output/2010s";
		job.setJarByClass(Main.class);
		job.setMapperClass(movieMapper.class);
		job.setReducerClass(movieReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.waitForCompletion(true);
	}

	public static class movieMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		private final Text CITY = new Text();
		private final IntWritable COUNT = new IntWritable();

		public void map(LongWritable key, Text value, Context context) {
			int count = 0;
			try {
				int startIndex = value.find("\t\t\t");
				String cityName = value.toString().substring(startIndex,
						value.toString().indexOf(","));
				String cityTrim = cityName.trim();
				CITY.set(cityTrim);
				COUNT.set(++count);
			} catch (Exception e) {
				CITY.set("UNKOWN");
				COUNT.set(++count);
			}
			try {
				context.write(CITY, COUNT);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static class movieReduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		private final static IntWritable COUNT = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			COUNT.set(sum);
			context.write(key, COUNT);
		}
	}
}
