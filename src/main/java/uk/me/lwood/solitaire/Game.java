package uk.me.lwood.solitaire;

import uk.me.lwood.solitaire.model.Card;
import uk.me.lwood.solitaire.model.Suit;

import java.util.*;

public class Game {
    private final Queue<Card> stock = new LinkedList<>();
    private final Stack<Card> discardPile = new Stack<>();
    private final Map<Suit, Stack<Card>> foundation = new EnumMap<>(Suit.class);
    private final List<Stack<Card>> tableau = new Stack<>();

    public Game() {

    }

    public Queue<Card> getStock() {
        return stock;
    }

    public Stack<Card> getDiscardPile() {
        return discardPile;
    }

    public Map<Suit, Stack<Card>> getFoundation() {
        return foundation;
    }

    public List<Stack<Card>> getTableau() {
        return tableau;
    }
}
