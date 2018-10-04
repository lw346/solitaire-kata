package uk.me.lwood.solitaire.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import uk.me.lwood.solitaire.Game;
import uk.me.lwood.solitaire.model.Card;
import uk.me.lwood.solitaire.model.Suit;
import uk.me.lwood.solitaire.model.Value;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PromoteToFoundationTest {
    @Mock
    private Game game;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void columnIsOutsideTableauRange() {
        when(game.getFoundation()).thenReturn(foundation(new Card(Suit.CLUBS, Value.ACE)));
        when(game.getTableau()).thenReturn(emptyList());

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(1);

        assertFalse(promoteToFoundation.canBePlayedIn(game));
    }

    @Test
    void tableauColumnIsEmpty() {
        when(game.getFoundation()).thenReturn(foundation(new Card(Suit.CLUBS, Value.ACE)));
        when(game.getTableau()).thenReturn(singletonList(new Stack<>()));

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(0);

        assertFalse(promoteToFoundation.canBePlayedIn(game));
    }

    @Test
    void cardInTableauIsNotNextValue() {
        when(game.getFoundation()).thenReturn(foundation(new Card(Suit.CLUBS, Value.ACE)));
        when(game.getTableau()).thenReturn(tableau(new Card(Suit.CLUBS, Value.THREE)));

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(0);

        assertFalse(promoteToFoundation.canBePlayedIn(game));
    }

    @Test
    void cardInTableauIsNotTheSameSuit() {
        when(game.getFoundation()).thenReturn(foundation(new Card(Suit.CLUBS, Value.ACE)));
        when(game.getTableau()).thenReturn(tableau(new Card(Suit.HEARTS, Value.TWO)));

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(0);

        assertFalse(promoteToFoundation.canBePlayedIn(game));
    }

    @Test
    void acesCanAlwaysBePromotedToEmptyFoundationSlot() {
        when(game.getFoundation()).thenReturn(foundation(new Card(Suit.CLUBS, Value.ACE)));
        when(game.getTableau()).thenReturn(tableau(new Card(Suit.HEARTS, Value.ACE)));

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(0);

        assertTrue(promoteToFoundation.canBePlayedIn(game));
    }

    @Test
    void nextCardCanBePromoted() {
        when(game.getFoundation()).thenReturn(foundation(new Card(Suit.CLUBS, Value.ACE)));
        when(game.getTableau()).thenReturn(tableau(new Card(Suit.CLUBS, Value.TWO)));

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(0);

        assertTrue(promoteToFoundation.canBePlayedIn(game));
    }

    private static Map<Suit, Stack<Card>> foundation(Card card) {
        Map<Suit, Stack<Card>> cards = new EnumMap<>(Suit.class);
        for (Suit suit : Suit.values()) {
            cards.put(suit, new Stack<>());
        }
        cards.get(card.getSuit()).push(card);
        return cards;
    }

    private static List<Stack<Card>> tableau(Card card) {
        List<Stack<Card>> cards = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            cards.add(new Stack<>());
        }
        cards.get(0).push(card);
        return cards;
    }
}
