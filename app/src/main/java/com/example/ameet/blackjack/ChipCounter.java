package com.example.ameet.blackjack;

/**
 * Created by Ameet on 6/2/2015.
 */
public class ChipCounter {

    int bankroll;
    int bet=0;

    public ChipCounter(int initialBankroll )
    {
       bankroll= initialBankroll;
    }

    public void addToBet(int amt)
    {
       bankroll-=amt;
        bet+=amt;
    }
    public int getBet()
    {
        return bet;
    }
    public int getBankroll()
    {
        return bankroll;
    }
    public void setBet(int newBet){bet=newBet;}
    public void setBankroll(int newBankroll){bankroll=newBankroll;}

}
