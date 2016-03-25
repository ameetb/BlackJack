package com.example.ameet.blackjack;

import java.util.ArrayList;
import java.lang.String;

/**
 * Created by Ameet on 5/8/2015.
 */
public class Deck {

    int numDecks;
    private ArrayList<Card> CardList=new ArrayList<Card>();


    public Deck(int decks)
    {
        numDecks=decks;

        for(int i = 0;i<numDecks;i=i+1) {

            for (int suit = 1; suit < 5; suit = suit + 1) {
                for (int num = 2; num < 15; num = num + 1) {
                    CardList.add(new Card(suit, num));
                }


            }
        }
    }

    public Card getCardFromDeck(int position)
    {
        Card c = CardList.get(position);
        CardList.remove(position);
        return c;
    }

    public int getSize()
    {
        return CardList.size();
    }


}
