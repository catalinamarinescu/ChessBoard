package commands;

import java.util.Optional;
import logic.GameState;

public class QuitCommand implements  Action{
    @Override
    public Optional<String> execute() {

        GameState.setQuitMode(true);
        return Optional.empty();
    }
}
