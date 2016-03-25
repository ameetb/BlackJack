package com.example.ameet.blackjack;

/**
 * Created by Ameet on 5/8/2015.
 */
public class Card {

    int suit;
    int number;
    int cardImageViewId;

    public Card(int x, int y)
    {
        suit=x;
        // 1-spades,2-clubs,3-diamond,4-hearts
        number=y;

    }
    public String getSuit()
    {
        if(suit==1)
        {
            return "s";
        }
        if(suit==2)
        {
            return "c";
        }
        if(suit==3)
        {
            return "d";
        }
        if(suit==4)
        {
            return "h";
        }
        else
            return "suit error";
    }
    public String getNumber()
    {
        if(1<number && number<11)
        {
            return number +"";
        }
        if(number==11)
        {
            return "j";
        }
        if(number==12)
        {
            return "q";
        }
        if(number==13)
        {
            return "k";
        }
        if(number==14)
        {
            return "a";
        }
        else
            return "error";
    }

    public String getCardImageName()
    {
          return(getSuit()+getNumber());
    }

    public int cardNumber()
    {
        return number;
    }

    public void saveCardImageViewId(int id)
    {
        cardImageViewId= id;
    }

    public int getCardImageViewId()
    {
       return cardImageViewId;
    }
}
