package tal.leaders.ttt;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tal.leaders.ttt.resources.MatchResource;

public class TTTApplication extends Application<TTTConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TTTApplication().run(args);
    }

    @Override
    public String getName() {
        return "TTT";
    }

    @Override
    public void initialize(final Bootstrap<TTTConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TTTConfiguration configuration, final Environment environment) {
        final MatchResource resource = new MatchResource();
        environment.jersey().register(resource);
    }

}
