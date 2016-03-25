package com.example.ameet.blackjack;


import java.util.Random;

/**
 * Created by Ameet on 6/1/2015.
 */
public class IdGenerator {

    int[] IdArray = new int[40];
    public int arrayCount=0;

    public IdGenerator()
    {

    }

    public int newId()
    {
        int newId;

        while (true) {
            Random rand = new Random();
            newId = rand.nextInt(10000);
            if(checkId(newId )==true)
            {
                break;
            }

        }

        IdArray [arrayCount]= newId;
        arrayCount+=1;
        return newId;
    }

    public boolean checkId(int id)
    {
        boolean b = true;
        for(int i =0;i<arrayCount;i++)
        {
            if(IdArray[arrayCount]==id)
            {
                b=false;
            }
        }

        return b;
    }

}
