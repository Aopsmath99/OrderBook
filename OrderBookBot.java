import java.util.*;

class Pair{
    double price;
    long id;

    public Pair(double p, long i){
        this.price = p;
        this.id = i;
    }
}

public class OrderBookBot {
    static HashMap<Long, Double> profits = new HashMap<>();
    static HashMap<Long, Integer> positions = new HashMap<>();
    static HashMap<Long, Long> orderToID = new HashMap<>();
    static OrderBook book = new OrderBook(10.00);
    static long tradeID = 10000000;
    long id;
    int position = 0;
    Double profit = 0.0;
    public OrderBookBot(long id){
        profits.put(id, 0.0);
        positions.put(id, 0);
        this.id = id;
    }

    public void execute(){
        /*
        try{
            TimeUnit.SECONDS.sleep(2);
        }
        catch(Exception e){
            //System.out.println("Couldn't sleep lmao");
        }
            */

        //System.out.println(this.id + " executing");
        Random rand = new Random();
        int n = rand.nextInt(6);
        if(n == 0){
            //System.out.println("view");
            //System.out.println(book.buyersBook);
            //System.out.println(book.sellersBook);
            //System.out.println(book.availableBuys);
            //System.out.println(book.availableSells);
            //System.out.println("bidPrice: " + book.bidPrice);
            //System.out.println("askPrice: " + book.askPrice);
        }
        else if(n == 1){
            //System.out.println("placing market order buy");
            DoubleLongPair p = book.addMarketOrder(true, tradeID);
            orderToID.put(tradeID, this.id);
            if(p.d != -1){
                profits.put(this.id, profits.get(this.id) - p.d);
                positions.put(this.id, positions.get(this.id) + 1);
                profits.put(orderToID.get(p.l), profits.get(orderToID.get(p.l)) + p.d);
                positions.put(orderToID.get(p.l), positions.get(orderToID.get(p.l)) - 1);
            }
            tradeID++;
        }
        else if(n == 2){
            //System.out.println("placing market order sell");
            DoubleLongPair p = book.addMarketOrder(false, tradeID);
            orderToID.put(tradeID, this.id);
            if(p.d != -1){
                profits.put(this.id, profits.get(this.id) + p.d);
                positions.put(this.id, positions.get(this.id) - 1);
                profits.put(orderToID.get(p.l), profits.get(orderToID.get(p.l)) - p.d);
                positions.put(orderToID.get(p.l), positions.get(orderToID.get(p.l)) + 1);
            }
            tradeID++;
        }
        else if(n == 3){
            double limitOrder = rand.nextDouble()*10000;
            //System.out.println("placing limit order buy at " + limitOrder);
            DoubleLongPair p = book.addLimitOrder(true, limitOrder, tradeID);
            orderToID.put(tradeID, this.id);
            if(p.d != -1){
                profit -= p.d;
                position++;
                profits.put(this.id, profits.get(this.id) - p.d);
                positions.put(this.id, positions.get(this.id) + 1);
                profits.put(orderToID.get(p.l), profits.get(orderToID.get(p.l)) + p.d);
                positions.put(orderToID.get(p.l), positions.get(orderToID.get(p.l)) - 1);
            }
            tradeID++;
        }
        else if(n == 4){
            double limitOrder = rand.nextDouble()*10000;
            //System.out.println("placing limit order sell at " + limitOrder);
            DoubleLongPair p = book.addLimitOrder(false, limitOrder, tradeID);
            orderToID.put(tradeID, this.id);
            if(p.d != -1){
                profits.put(this.id, profits.get(this.id) + p.d);
                positions.put(this.id, positions.get(this.id) - 1);
                profits.put(orderToID.get(p.l), profits.get(orderToID.get(p.l)) - p.d);
                positions.put(orderToID.get(p.l), positions.get(orderToID.get(p.l)) + 1);
            }
            tradeID++;
        }
    }
}
