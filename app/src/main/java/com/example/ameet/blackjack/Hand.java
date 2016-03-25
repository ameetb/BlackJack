package com.example.ameet.blackjack;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ameet on 5/9/2015.
 */
public class Hand {

    Card firstCard;
    Card secondCard;
    private ArrayList<Card> CardList=new ArrayList<Card>();



    public Hand(Card firstCard,Card secondCard)
    {
        CardList.add( firstCard);
        CardList.add( secondCard);

    }

    public void addCard(Card additionalCard)
    {

        CardList.add(additionalCard);

    }

    public int getNumCards()
    {

        return CardList.size();

    }

    public int getTotal()
    {
        int aceEleven=0;
        int aceOne=0;

        for(int i=0; i<CardList.size();i++)
        {
            int x= CardList.get(i).cardNumber();
            if(x==14)
            {
                aceEleven+=11;
            }
            if(x>9&&x!=14)
            {
                aceEleven+=10;
            }
            else if(x!=14&&!(x>11))
            {
                aceEleven+=x;
            }
        }

        for(int i=0; i<CardList.size();i++)
        {
            int x= CardList.get(i).cardNumber();
            if(x==14)
            {
                aceOne+=1;
            }
            if(x>9&&x!=14)
            {
                aceOne+=10;
            }
            else if(x!=14&&!(x>11))
            {
                aceOne+=x;
            }
        }

        if(aceEleven>21)
        {
            return aceOne;
        }

        else
        {
            return aceEleven;
        }







    }

    public Card getLastCard()
    {
        return CardList.get(CardList.size()-1);

    }
    public int getLastCardPos()
    {
        return CardList.size()-1;

    }


}
