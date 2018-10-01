package uk.me.lwood.solitaire.model;

public enum Suit {
    HEARTS(Colour.RED),
    DIAMONDS(Colour.RED),
    SPADES(Colour.BLACK),
    CLUBS(Colour.BLACK);

    private final Colour colour;

    Suit(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }
}
