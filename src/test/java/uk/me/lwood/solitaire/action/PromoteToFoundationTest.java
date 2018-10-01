package uk.me.lwood.solitaire.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import uk.me.lwood.solitaire.Game;
import uk.me.lwood.solitaire.model.Card;
import uk.me.lwood.solitaire.model.Suit;
import uk.me.lwood.solitaire.model.Value;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PromoteToFoundationTest {
    @Mock
    private Game game;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @ParameterizedTest(name = "{index} ==> canPromote[Column={1}, Foundation={2}, Tableau={3}] => {0}")
    @MethodSource("argumentsForCanPromoteToFoundation")
    public void canPromoteToFoundation(boolean outcome, int column, Map<Suit, Stack<Card>> foundation, List<Stack<Card>> tableau) {
        when(game.getFoundation()).thenReturn(foundation);
        when(game.getTableau()).thenReturn(tableau);

        PromoteToFoundation promoteToFoundation = new PromoteToFoundation(column);

        assertEquals(outcome, promoteToFoundation.canBePlayedIn(game));
    }

    public static Stream<Arguments> argumentsForCanPromoteToFoundation() {
        return Stream.of(
                // Column is outside tableau range
                arguments(false, 1, foundation(new Card(Suit.CLUBS, Value.ACE)), emptyList()),
                // Tableau column is empty
                arguments(false, 0, foundation(new Card(Suit.CLUBS, Value.ACE)), singletonList(new Stack<>())),
                // Card in tableau is not the next value
                arguments(false, 0, foundation(new Card(Suit.CLUBS, Value.ACE)), tableau(new Card(Suit.CLUBS, Value.THREE))),
                // Card in tableau is not same suit
                arguments(false, 0, foundation(new Card(Suit.CLUBS, Value.ACE)), tableau(new Card(Suit.HEARTS, Value.TWO))),
                // Aces can always be promoted to an empty foundation slot
                arguments(true, 0, foundation(new Card(Suit.CLUBS, Value.ACE)), tableau(new Card(Suit.HEARTS, Value.ACE))),
                // Next card can always be promoted to the same foundation slot
                arguments(true, 0, foundation(new Card(Suit.CLUBS, Value.ACE)), tableau(new Card(Suit.CLUBS, Value.TWO)))
        );
    }

    public static Map<Suit, Stack<Card>> foundation(Card card) {
        Map<Suit, Stack<Card>> cards = new EnumMap<>(Suit.class);
        for (Suit suit : Suit.values()) {
            cards.put(suit, new Stack<>());
        }
        cards.get(card.getSuit()).push(card);
        return cards;
    }

    public static List<Stack<Card>> tableau(Card card) {
        List<Stack<Card>> cards = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            cards.add(new Stack<>());
        }
        cards.get(0).push(card);
        return cards;
    }
}
