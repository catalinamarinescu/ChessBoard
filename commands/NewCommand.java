package commands;

import logic.GameState;
import  logic.PlaySide;

import java.util.Optional;

public class NewCommand implements Action{
    @Override
    public Optional<String> execute() {
        GameState.reset();

        GameState.setPlayerColor(PlaySide.WHITE);
        GameState.setBotColor(PlaySide.BLACK);

        return Optional.empty();
    }
}


