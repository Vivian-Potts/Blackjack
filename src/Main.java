import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int credits = 80;

        for (int gameNo = 1; gameNo<6; gameNo++) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Game: "+gameNo);
            System.out.println("You have "+ credits+" credits");
            credits = play(credits);
            if (credits == 0) {
                System.out.println("GAME OVER");
                break;
            }
        }
        System.out.println("Game finished! Score: " + credits);

    }

    public static int play(int credits) throws InterruptedException {

        //Make variables
        Scanner scan = new Scanner(System.in);
        CardDeck deck = new CardDeck();
        int dealerHand = 0;
        int playerHand = 0;
        int hiddenCard = 0;
        int cardHolder = 0;
        int bet = 0;
        boolean dealerBust = false;
        boolean bust = false;
        boolean firstTurn = true;
        String decision;

        //Take bet
        while (true) {
            System.out.print("What is your bet?: ");
            bet = scan.nextInt();
            if (bet <= credits && bet > 0) {
                break;
            } else {
                System.out.println("Not enough credits");
            }

        }


        //Deal starting cards
        dealerHand = deck.deal(false);
        if (dealerHand == 123456789) {
            dealerHand = 11;
        }
        System.out.println("Dealer drew: " + dealerHand);
        hiddenCard = deck.deal(false);


        playerHand += deck.deal(true);
        System.out.println("You drew: " + playerHand);
        cardHolder = deck.deal(true);
        System.out.println("You drew " + cardHolder);
        playerHand += cardHolder;
        System.out.println("Your hand totals: " + playerHand);

        //Game Loop

        while (true) {
            cardHolder = 0;
            if (firstTurn){
                System.out.println("Would you like to hit, stand, or double down? [H/S/DD]");
            }else {
                System.out.println("Would you like to hit or stand?");
            }
             //IDK WHY WE NEED THIS BUT WE DO
            decision = scan.next();

            decision = decision.toLowerCase(Locale.ROOT);
            //System.out.println("After Scan");
            if (decision.equals("h")) {
                firstTurn = false;
                cardHolder = deck.deal(true);
                System.out.println("You drew: " + cardHolder);
                playerHand += cardHolder;
                if (playerHand > 21) {
                    System.out.println("BUST");
                    System.out.println("Game Lost, " + bet + " credits subtracted");
                    return credits - bet;
                }
                else {
                    System.out.println("Your hand is now: " + playerHand);
                }
            }
            if (decision.equals("s")) {
                break;
            }
            if (decision.equals("dd")) {
                if (!firstTurn){
                    System.out.println("You can only double down on your first turn");
                }else {
                    if (bet <= credits / 2) {
                        bet *= 2;

                        cardHolder += deck.deal(true);
                        playerHand += cardHolder;
                        System.out.println("You drew: " + cardHolder);
                        System.out.println("Your hand is now: " + playerHand);
                        if (playerHand > 21) {
                            System.out.println("BUST");
                            System.out.println("Game Lost, " + bet + " credits subtracted");
                            return credits - bet;
                        }
                        break;
                    } else {System.out.println("You do not have enough credits to double down");}
                }
            }

        }



        //Dealer Time



        if(hiddenCard == 123456789){
            System.out.println("Dealer's facedown card is an ace!");
            if (dealerHand >= 11){
                System.out.println("Adding 1");
                dealerHand++;
            }else {
                System.out.println("Adding 11");
                dealerHand += 11;
            }
        }else{
            System.out.println("Dealers facedown card is: "+ hiddenCard);
            dealerHand += hiddenCard;
        }


        while (true){
            cardHolder = 0;
            System.out.println("The dealer has: "+ dealerHand);
            Thread.sleep(1500);

            if (dealerHand < 17){
                cardHolder += deck.deal(false);
                if (cardHolder == 123456789){
                    if (dealerHand + 11 > 21){
                        dealerHand++;
                    }else {
                        dealerHand += 11;
                    }

                }
                else{
                    dealerHand += cardHolder;
                }
            }else{
                System.out.println("You have: " + playerHand);
                if(dealerHand > 21){
                    dealerBust = true;
                }
                break;
            }


        }

        //Check who won
        if (playerHand == dealerHand) {
            System.out.println("Push, Money returned without change");
            return credits;
        } else if(bust || playerHand <= dealerHand && !dealerBust){
            System.out.println("Game Lost, " + bet + " credits subtracted");
            return credits - bet;
        }else {
            System.out.println("Game Won, paying out " + bet + " credits");
            return credits + bet;
        }
    }
}

//Handle dealer
//Handle double down turns