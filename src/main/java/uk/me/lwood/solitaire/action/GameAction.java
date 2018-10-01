package uk.me.lwood.solitaire.action;

import uk.me.lwood.solitaire.Game;

public interface GameAction {
    public boolean canBePlayedIn(Game game);
    public void alterState(Game game);
}
