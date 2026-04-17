package by.system.aibothelper.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.prompt")
@Getter
@Setter
public class PromptProperties {

    private String legendReview;
}
