package homework.config;

import homework.appcontainer.api.AppComponent;
import homework.appcontainer.api.AppComponentsContainerConfig;
import homework.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig1 {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer(){
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

}
