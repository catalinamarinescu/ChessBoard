package commands;



import java.util.Optional;
import logic.GameState;

public class ForceCommand  implements Action {

    @Override
    public Optional<String> execute() {

        GameState.setForceMode(true);
        return Optional.empty();
    }
}
