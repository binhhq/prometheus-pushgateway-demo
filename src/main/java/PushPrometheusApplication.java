import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
public class PushPrometheusApplication {


    public static void readFile() throws IOException {
        String file ="C:\\Users\\daisu\\Downloads\\queue_size.log";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        Stream<String> lines = reader.lines();
        lines.forEach(System.out::println);
        reader.close();
    }

    public static void pushPrometheus() throws IOException {
        CollectorRegistry registry = new CollectorRegistry();
        Gauge duration = Gauge.build()
                .name("my_batch_job_duration_seconds").help("Duration of my batch job in seconds.").register(registry);
        Gauge.Timer durationTimer = duration.startTimer();
        try {
            // Your code here.

            // This is only added to the registry after success,
            // so that a previous success in the Pushgateway isn't overwritten on failure.
            Gauge lastSuccess = Gauge.build()
                    .name("my_batch_job_last_success").help("Last time my batch job succeeded, in unixtime.").register(registry);
            lastSuccess.setToCurrentTime();
        } finally {
            durationTimer.setDuration();
            PushGateway pg = new PushGateway("127.0.0.1:9091");
            pg.pushAdd(registry, "my_batch_job");
        }

    }

    public static void main(String[] args) throws IOException {
        readFile();
        pushPrometheus();
    }
}
