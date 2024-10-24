import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int credits = 80;

        for (int gameNo = 1; gameNo<6; gameNo++) {
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

    public static int play(int credits) {

        //Make variables
        Scanner scan = new Scanner(System.in);
        CardDeck deck = new CardDeck();
        int dealerHand = 0;
        int playerHand = 0;
        int hiddenCard = 0;
        int cardHolder = 0;
        int bet = 0;
        boolean bust = false;
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
            System.out.println("Would you like to hit, stand, or double down? [H/S/DD]");
             //IDK WHY WE NEED THIS BUT WE DO
            decision = scan.next();

            decision = decision.toLowerCase(Locale.ROOT);
            System.out.println("After Scan");
            if (decision.equals("h")) {
                cardHolder = deck.deal(true);
                System.out.println("You drew: " + cardHolder);
                playerHand += cardHolder;
                if (playerHand > 21) {
                    System.out.println("BUST");
                    bust = true;
                    break;
                }
                else {
                    System.out.println("Your hand is now: " + playerHand);
                }
            }
            if (decision.equals("s")) {
                break;
            }
            if (decision.equals("dd")) {
                //DoubleDown
            }
        }



        //Dealer Time

        //Check who won
        if (playerHand > dealerHand && !bust){
            System.out.println("Game Won, paying out " + bet + " credits");
            return credits + bet;
        }else {
            System.out.println("Game Lost, " + bet + " credits subtracted");
            return credits - bet;
        }
    }
}
