package commands;

import logic.GameState;

import java.util.Optional;

public class GoCommand implements Action {
    @Override
    public Optional<String> execute() {

        GameState.setForceMode(false);
        return Optional.empty();
    }
}
