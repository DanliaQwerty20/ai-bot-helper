package by.system.aibothelper;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AiBotHelperApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AiBotHelperApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
