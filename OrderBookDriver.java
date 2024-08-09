import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OrderBookDriver {
    public static void main(String[] args){
        OrderBook book = new OrderBook(10.00);
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        if(choice.equals("bots")){
            Queue<OrderBookBot> bots = new LinkedList<>();
            System.out.println("Bot Count: ");
            int count = sc.nextInt();
            long id = 1;
            for(int i = 0; i < count; i++){
                bots.add(new OrderBookBot(id));
                id++;
            }
            int ct = 0;
            while(ct < 1000000){
                OrderBookBot bot = bots.poll();
                bot.execute();
                bots.add(bot);
                ct++;
                if(ct % 10000 == 0){
                    System.out.println(OrderBookBot.positions);
                    System.out.println(OrderBookBot.profits);
                }

            }
            System.out.println(OrderBookBot.positions);
            System.out.println(OrderBookBot.profits);

        }else{
            //System.out.println("Trading sim begin: ");
            long id = 10000000;
            while(true){
                String line = sc.nextLine();
                String[] h = line.split("\\s+");
                if(line.equals("Done")){
                    break;
                }
                else if(line.equals("view")){
                    System.out.println(book.buyersBook);
                    System.out.println(book.sellersBook);
                    System.out.println(book.availableBuys);
                    System.out.println(book.availableSells);
                    System.out.println("bidPrice: " + book.bidPrice);
                    System.out.println("askPrice: " + book.askPrice);
                }
                else if(h[0].equals("marketOrder")){
                    if(h[1].equals("sell")){
                        book.addMarketOrder(false, id);
                    }
                    else{
                        book.addMarketOrder(true, id);
                    }
                    id++;
                }
                else if(h[0].equals("limit")){
                    Double price = Double.parseDouble(h[2]);
                    if(h[1].equals("buy")){
                        book.addLimitOrder(true, price, id);
                    }
                    else{
                        book.addLimitOrder(false, price, id);
                    }
                    id++;
                }
                else{
                    System.out.println("invalid trade token used");
                }
            }
            System.out.println("Trading sim exited");
        }

    }
}

