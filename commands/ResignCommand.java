package commands;

import logic.GameState;
import logic.PlaySide;

import java.util.Optional;

public class ResignCommand implements Action{
    @Override
    public Optional<String> execute() {
      if (GameState.getPlayerColor() == PlaySide.WHITE) {
          GameState.setPlayerColor(PlaySide.BLACK);
      } else {
          GameState.setPlayerColor(PlaySide.WHITE);
      }

      return Optional.empty();
    }
}
