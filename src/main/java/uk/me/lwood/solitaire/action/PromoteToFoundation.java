package uk.me.lwood.solitaire.action;

import uk.me.lwood.solitaire.Game;
import uk.me.lwood.solitaire.model.Card;

import java.util.Stack;

/**
 * Promotes the top card from a column in the tableau to the appropriate foundation pile
 */
public class PromoteToFoundation implements GameAction {
    private final int column;

    public PromoteToFoundation(int column) {
        this.column = column;
    }

    @Override
    public boolean canBePlayedIn(Game game) {
        if (game.getTableau().size() < column + 1 || game.getTableau().get(column).empty()) {
            return false;
        }

        Card card = game.getTableau().get(column).peek();

        Stack<Card> suitFoundation = game.getFoundation().get(card.getSuit());
        if (suitFoundation.empty() && card.getValue().ordinal() == 0) {
            return true;
        } else if (suitFoundation.empty()) {
            return false;
        }
        return card.getValue().ordinal() == suitFoundation.peek().getValue().ordinal() + 1;
    }

    @Override
    public void alterState(Game game) {
        Card card = game.getTableau().get(column).pop();

        Stack<Card> suitFoundation = game.getFoundation().get(card.getSuit());
        suitFoundation.push(card);
    }
}
