package com.example.ameet.blackjack;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Random;

/**
 * Created by Ameet on 5/9/2015.
 */
public class Game extends Activity {

    Deck gameDeck;
    int numDecks=2;
    Card playerCard1;
    Card playerCard2;
    Card dealerCard1;
    Card dealerCard2;
    Hand playerHand;
    Hand dealerHand;
    IdGenerator idGenerator=new IdGenerator();
    ChipCounter counter = new ChipCounter(100);



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        Game();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setNumDecks (int x)
    {
        numDecks = x;

    }

    public void Game()
    {

        gameDeck= new Deck(numDecks);
        dealCards();
        playerHand = new Hand(playerCard1,playerCard2);
        dealerHand = new Hand(dealerCard1,dealerCard2);
        TextView bankroll = (TextView)(findViewById(R.id.bankroll));
        bankroll.setText(String.valueOf(counter.getBankroll()));
        TextView totalBet = (TextView)(findViewById(R.id.totalBet));
        totalBet.setText(String.valueOf(counter.getBet()));



    }

    public Card getRandomCard()
    {
        Random rand = new Random();
        int n = rand.nextInt(gameDeck.getSize()-1);
        return gameDeck.getCardFromDeck(n);
    }

    public void dealCards()
    {

        playerCard1= getRandomCard();
        playerCard2 = getRandomCard();
        dealerCard1 = getRandomCard();
        dealerCard2 = getRandomCard();
       setCardImage(playerCard1, "pc1");
       setCardImage(playerCard2, "pc2");
       setCardImage(dealerCard1, "dc1");
       setCardImage(dealerCard2, "dc2");
    }

    public void addToBet(View view ){
        counter.addToBet(10);
        TextView bankroll = (TextView)(findViewById(R.id.bankroll));
        bankroll.setText(String.valueOf(counter.getBankroll()));
        TextView bet=(TextView)(findViewById(R.id.totalBet));
        bet.setText(String.valueOf(counter.getBet()));
    }
    public void saveBet(View view){
        Button b = (Button)(findViewById(R.id.betButton));
        b.setBackgroundColor(Color.GRAY);
        b.setEnabled(false);
        Button c= (Button) view;
        c.setBackgroundColor(Color.GRAY);
        c.setEnabled(false);
    }
    public void hit(View view)
    {
        Button saveButton= (Button)(findViewById(R.id.saveBetButton));
        if(saveButton.isEnabled())
        {
            TextView tv = (TextView)(findViewById(R.id.totalBet));
            tv.setText("You must save your bet first");
        }
        else {
            if (playerHand.getTotal() < 22) {

//        TextView tv = (TextView) findViewById(R.id.textView);
                Card card = getRandomCard();
                Card positionCard = playerHand.getLastCard();
                playerHand.addCard(card);
//        tv.setText(String.valueOf(positionCard.getCardImageViewId()));
                if (playerHand.getLastCardPos() > 16) {
//          stand();
                } else if (playerHand.getLastCardPos() != 8) {
                    addPlayerCardToLayout((ImageView) findViewById(positionCard.getCardImageViewId()), card);
                } else if (playerHand.getLastCardPos() == 8) {
                    addNewPlayerRowToLayout(card);
                }
            } else {
                Button b = (Button) (findViewById(R.id.hitButton));
                b.setBackgroundColor(Color.BLUE);
                b.setText("Cannot Hit");
                b.setEnabled(false);
            }
        }

    }

    public void addNewPlayerRowToLayout(Card newCard)
    {
        ImageView iv = new ImageView(Game.this);
//        iv.setId(View.generateViewId());
        iv.setId(idGenerator.newId());
        newCard.saveCardImageViewId(iv.getId());
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_RIGHT, playerCard1.getCardImageViewId());
        params.addRule(RelativeLayout.BELOW, playerCard1.getCardImageViewId());
        rl.addView(iv, params);
        setImage(iv,newCard);

    }

    public void addPlayerCardToLayout(ImageView positionCardImage,Card newCard)
    {
        ImageView iv = new ImageView(Game.this);
//        iv.setId(View.generateViewId());
        iv.setId(idGenerator.newId());
        newCard.saveCardImageViewId(iv.getId());
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_TOP, positionCardImage.getId());
        params.addRule(RelativeLayout.RIGHT_OF, positionCardImage.getId());
        params.addRule(RelativeLayout.ALIGN_BOTTOM, positionCardImage.getId());
        rl.addView(iv, params);
        setImage(iv,newCard);

    }

    public void stand(View view) {
        Button saveButton= (Button)(findViewById(R.id.saveBetButton));
        if(saveButton.isEnabled()) {
            TextView tv = (TextView) (findViewById(R.id.textView));
            Button standButton = (Button) (findViewById(R.id.StandButton));
            standButton.setEnabled(false);
            standButton.setBackgroundColor(Color.GRAY);
            setImage((ImageView) (findViewById(R.id.dealerCard2)), dealerCard2);

            if (playerHand.getTotal() > 21) {
                tv.setText("Dealer Wins");
                counter.setBet(0);
                TextView bankroll = (TextView) (findViewById(R.id.bankroll));
                bankroll.setText(String.valueOf(counter.getBankroll()));
                TextView bet = (TextView) (findViewById(R.id.totalBet));
                bet.setText(String.valueOf(counter.getBet()));

            } else {

                while (dealerHand.getTotal() < 17) {
                    dealerTurn();
                }

                if (dealerHand.getTotal() > 21) {
                    tv.setText("Player Wins");
                    counter.setBankroll(2 * counter.getBet() + counter.getBankroll());
                    counter.setBet(0);

                } else if (playerHand.getTotal() == dealerHand.getTotal()) {
                    tv.setText("Push");
                    counter.setBankroll(counter.getBankroll() + counter.getBet());
                    counter.setBet(0);

                } else if (playerHand.getTotal() > dealerHand.getTotal()) {
                    tv.setText("Player Wins");
                    counter.setBankroll(2 * counter.getBet() + counter.getBankroll());
                    counter.setBet(0);

                } else if (dealerHand.getTotal() > playerHand.getTotal()) {
                    counter.setBet(0);


                }
                TextView bankroll = (TextView) (findViewById(R.id.bankroll));
                bankroll.setText(String.valueOf(counter.getBankroll()));
                TextView bet = (TextView) (findViewById(R.id.totalBet));
                bet.setText(String.valueOf(counter.getBet()));

            }
        }


    }
    public void dealerTurn()
    {

        Card card = getRandomCard();
        Card positionCard = dealerHand.getLastCard();
        dealerHand.addCard(card);

        if(dealerHand.getLastCardPos()>16)
        {
//          stand();
        }
        else if(dealerHand.getLastCardPos()!=8)
        {
            addDealerCardToLayout((ImageView) findViewById(positionCard.getCardImageViewId()), card);
        }
        else if (dealerHand.getLastCardPos()==8)
        {
            addNewDealerRowToLayout(card);
        }

    }

    public void addNewDealerRowToLayout(Card newCard)
    {
        ImageView iv = new ImageView(Game.this);
//        iv.setId(View.generateViewId());
        iv.setId(idGenerator.newId());
        newCard.saveCardImageViewId(iv.getId());
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_RIGHT, playerCard1.getCardImageViewId());
        params.addRule(RelativeLayout.ABOVE, dealerCard1.getCardImageViewId());
        rl.addView(iv, params);
        setImage(iv,newCard);

    }

    public void addDealerCardToLayout(ImageView positionCardImage,Card newCard)
    {
        ImageView iv = new ImageView(Game.this);
//        iv.setId(View.generateViewId());
        iv.setId(idGenerator.newId());
        newCard.saveCardImageViewId(iv.getId());
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_TOP, positionCardImage.getId());
        params.addRule(RelativeLayout.RIGHT_OF, positionCardImage.getId());
        params.addRule(RelativeLayout.ALIGN_BOTTOM, positionCardImage.getId());
        rl.addView(iv, params);
        setImage(iv,newCard);

    }

    public void setCardImage(Card card,String cardType ) {
        ImageView playCard1 = (ImageView) (findViewById(R.id.playerCard1));
        ImageView playCard2 = (ImageView) (findViewById(R.id.playerCard2));
        ImageView dealCard1 = (ImageView) (findViewById(R.id.dealerCard1));
        ImageView dealCard2 = (ImageView) (findViewById(R.id.dealerCard2));


        if (cardType.equals("pc1")) {
            setImage(playCard1, card);
        }
        if (cardType.equals("pc2")) {
            setImage(playCard2, card);
        }
        if (cardType.equals("dc1")) {
            setImage(dealCard1, card);
        }
        if (cardType.equals("dc2")) {
           dealCard2.setImageResource(R.drawable.b1fv);
           card.saveCardImageViewId(dealCard2.getId());
        }
    }

        public void setImage(ImageView I, Card c)
        {
            String card = c.getCardImageName();
            c.saveCardImageViewId(I.getId());

            switch(card)
            {
                case "c2":
                    I.setImageResource(R.drawable.c2);break;
                case "c3":
                    I.setImageResource(R.drawable.c3);break;
                case "c4":
                    I.setImageResource(R.drawable.c4);break;
                case "c5":
                    I.setImageResource(R.drawable.c5);break;
                case "c6":
                    I.setImageResource(R.drawable.c6);break;
                case "c7":
                    I.setImageResource(R.drawable.c7);break;
                case "c8":
                    I.setImageResource(R.drawable.c8);break;
                case "c9":
                    I.setImageResource(R.drawable.c9);break;
                case "c10":
                    I.setImageResource(R.drawable.c10);break;
                case "ca":
                    I.setImageResource(R.drawable.ca);break;
                case "cj":
                    I.setImageResource(R.drawable.cj);break;
                case "ck":
                    I.setImageResource(R.drawable.ck);break;
                case "cq":
                    I.setImageResource(R.drawable.cq);break;
                case "d2":
                    I.setImageResource(R.drawable.d2);break;
                case "d3":
                    I.setImageResource(R.drawable.d3);break;
                case "d4":
                    I.setImageResource(R.drawable.d4);break;
                case "d5":
                    I.setImageResource(R.drawable.d5);break;
                case "d6":
                    I.setImageResource(R.drawable.d6);break;
                case "d7":
                    I.setImageResource(R.drawable.d7);break;
                case "d8":
                    I.setImageResource(R.drawable.d8);break;
                case "d9":
                    I.setImageResource(R.drawable.d9);break;
                case "d10":
                    I.setImageResource(R.drawable.d10);break;
                case "da":
                    I.setImageResource(R.drawable.da);break;
                case "dj":
                    I.setImageResource(R.drawable.cj);break;
                case "dk":
                    I.setImageResource(R.drawable.dk);break;
                case "dq":
                    I.setImageResource(R.drawable.dq);break;
                case "s2":
                    I.setImageResource(R.drawable.s2);break;
                case "s3":
                    I.setImageResource(R.drawable.s3);break;
                case "s4":
                    I.setImageResource(R.drawable.s4);break;
                case "s5":
                    I.setImageResource(R.drawable.s5);break;
                case "s6":
                    I.setImageResource(R.drawable.s6);break;
                case "s7":
                    I.setImageResource(R.drawable.s7);break;
                case "s8":
                    I.setImageResource(R.drawable.s8);break;
                case "s9":
                    I.setImageResource(R.drawable.s9);break;
                case "s10":
                    I.setImageResource(R.drawable.s10);break;
                case "sa":
                    I.setImageResource(R.drawable.sa);break;
                case "sj":
                    I.setImageResource(R.drawable.sj);break;
                case "sk":
                    I.setImageResource(R.drawable.sk);break;
                case "sq":
                    I.setImageResource(R.drawable.sq);break;
                case "h2":
                    I.setImageResource(R.drawable.h2);break;
                case "h3":
                    I.setImageResource(R.drawable.h3);break;
                case "h4":
                    I.setImageResource(R.drawable.h4);break;
                case "h5":
                    I.setImageResource(R.drawable.h5);break;
                case "h6":
                    I.setImageResource(R.drawable.h6);break;
                case "h7":
                    I.setImageResource(R.drawable.h7);break;
                case "h8":
                    I.setImageResource(R.drawable.h8);break;
                case "h9":
                    I.setImageResource(R.drawable.h9);break;
                case "h10":
                    I.setImageResource(R.drawable.h10);break;
                case "ha":
                    I.setImageResource(R.drawable.ha);break;
                case "hj":
                    I.setImageResource(R.drawable.hj);break;
                case "hk":
                    I.setImageResource(R.drawable.hk);break;
                case "hq":
                    I.setImageResource(R.drawable.hq);break;

            }

        }

    public void startNewGame(View view) {
        Button standButton = (Button) (findViewById(R.id.StandButton));
        TextView tv = (TextView) (findViewById(R.id.totalBet));
        if (standButton.isEnabled() == true) {
            tv.setText("Finish this game first");
        } else {
            standButton.setEnabled(true);
            standButton.setBackgroundColor(0);
            Button a = (Button) (findViewById(R.id.hitButton));
            a.setEnabled(true);
            a.setBackgroundColor(0);
            Button b = (Button) (findViewById(R.id.saveBetButton));
            b.setEnabled(true);
            b.setBackgroundColor(0);
            Button c = (Button) (findViewById(R.id.betButton));
            c.setEnabled(true);
            c.setBackgroundColor(0);
            Button d = (Button) view;
            d.setEnabled(false);
            d.setBackgroundColor(0);


        }
    }


    }

