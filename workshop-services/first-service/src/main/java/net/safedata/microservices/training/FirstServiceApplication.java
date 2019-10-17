package net.safedata.microservices.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

import java.text.DecimalFormat;
import java.text.NumberFormat;

//@SpringBootApplication
public class FirstServiceApplication {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##");

	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(FirstServiceApplication.class, args);

		DecimalFormat decimalFormat = new DecimalFormat("##");

		StopWatch stopWatch = new StopWatch("method name");
		stopWatch.start("first step");
		Thread.sleep(205);
		stopWatch.stop();

		stopWatch.start("the second");
		Thread.sleep(325);
		stopWatch.stop();

		stopWatch.start("the third");
		Thread.sleep(453);
		stopWatch.stop();

		//System.out.println(stopWatch.prettyPrint());

		final double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
		System.out.println("Total time in millis: " + stopWatch.getTotalTimeMillis());
		System.out.println("Total time in seconds: " + stopWatch.getTotalTimeSeconds());
		for (StopWatch.TaskInfo taskInfo : stopWatch.getTaskInfo()) {
			System.out.println(taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis() + ", " +
					decimalFormat.format(taskInfo.getTimeSeconds() * 100 / totalTimeSeconds) + "%");
			setPercentage(taskInfo.getTimeMillis(), totalTimeSeconds);
		}
	}

	private static void setPercentage(final long timeInMillis, final double totalTimeInSeconds) {
		String percentage = DECIMAL_FORMAT.format(timeInMillis / (totalTimeInSeconds * 10)) + "%";
		System.out.println(percentage + " / " + timeInMillis + " & " + totalTimeInSeconds);
		System.out.println("---------------------------------------");
	}
}
