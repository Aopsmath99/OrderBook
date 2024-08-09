import java.util.*;
import java.io.*;

class Customer{
    long id;
    boolean buy;
    boolean isMarketOrder;
    double price;

    public Customer(long id, boolean buy, boolean isMarketOrder, double price){
        this.id = id;
        this.buy = buy;
        this.isMarketOrder = isMarketOrder;
        this.price = price;
    }
}

public class OrderBook {
    HashMap<Double, LinkedList<Long>> buyersBook;
    HashMap<Double, LinkedList<Long>> sellersBook;
    TreeSet<Double> availableSells;
    TreeSet<Double> availableBuys;
    Double bidPrice;
    Double askPrice;
    Double marketPrice;
    ArrayList<Customer> ordersToProcess;

    public OrderBook(Double marketPrice){
        buyersBook = new HashMap<Double, LinkedList<Long>>();
        sellersBook = new HashMap<Double, LinkedList<Long>>();
        availableSells = new TreeSet<Double>();
        availableBuys = new TreeSet<Double>((a,b) -> b-a > 0 ? 1 : -1);
        this.marketPrice = marketPrice;
        this.bidPrice = -1.0;
        this.askPrice = Double.MAX_VALUE;
    }

    //Buy or sell at the best available price

    public DoubleLongPair addMarketOrder(boolean buy, long id){
        DoubleLongPair p = new DoubleLongPair(-1.0, -1);
        if(buy){
            //System.out.println("Order " + id + " created as a Market Order Buy");
            if(availableSells.size() > 0){
                Double buyPrice = availableSells.first();
                long sellerID = sellersBook.get(buyPrice).get(0);
                //System.out.println("Order " + sellerID + " executed at the price " + buyPrice);
                sellersBook.get(buyPrice).remove(0);
                if(sellersBook.get(buyPrice).size() == 0){
                    sellersBook.remove(buyPrice);
                    availableSells.pollFirst();
                }
                //System.out.println("Order " + id + " executed at the price " + buyPrice);
                p.d = buyPrice;
                p.l = sellerID;
            }
            else{
                /*
                availableBuys.add(marketPrice);
                if(!buyersBook.containsKey(marketPrice)){
                    buyersBook.put(marketPrice, new ArrayList<Long>());
                }
                buyersBook.get(marketPrice).add(id);
                //we still need to process this order
                ordersToProcess.add(new Customer(id, buy, true, 10.00));
                */
                //System.out.println("Not enough liquidity to fill order " + id + ", killing order");
            }
        }
        else{
            //System.out.println("Order " + id + " created as a Market Order Sell");
            if(availableBuys.size() > 0){
                Double sellPrice = availableBuys.last();
                long buyerID = buyersBook.get(sellPrice).get(0);
                //System.out.println("Order " + buyerID + " executed at the price " + sellPrice);
                buyersBook.get(sellPrice).remove(0);
                if(buyersBook.get(sellPrice).size() == 0){
                    buyersBook.remove(sellPrice);
                    availableBuys.pollLast();
                }
                //System.out.println("Order " + id + " executed at the price " + sellPrice);
                p.d = sellPrice;
                p.l = buyerID;
            }
            else{
                /*
                availableSells.add(marketPrice);
                if(!sellersBook.containsKey(marketPrice)){
                    sellersBook.put(marketPrice, new ArrayList<Long>());
                }
                sellersBook.get(marketPrice).add(id);
                */

                //System.out.println("Not enough liquidity to fill order " + id + ", killing order");
            }
        }

        updateMarketPrices();
        return p;
    }

    //Buy at "price" price or better

    public DoubleLongPair addLimitOrder(boolean buy, Double price, long id){
        DoubleLongPair p = new DoubleLongPair(-1.0, -1);
        if(buy){
            //System.out.println("Order " + id + " created as a Limit Order Buy");
            if(price > askPrice){
                //execute the trade
                long sellerID = sellersBook.get(askPrice).get(0);
                //System.out.println("Order " + sellerID + " executed at the price " + askPrice);
                sellersBook.get(askPrice).remove(0);
                if(sellersBook.get(askPrice).size() == 0){
                    sellersBook.remove(askPrice);
                    availableSells.pollFirst();
                }
                //System.out.println("Order " + id + " executed at the price " + askPrice);
                p.d = askPrice;
                p.l = sellerID;
            }
            else{
                //add it to our ask book
                availableBuys.add(price);
                if(!buyersBook.containsKey(price)){
                    buyersBook.put(price, new LinkedList<>());
                }
                buyersBook.get(price).add(id);
            }
        }
        else{
            //System.out.println("Order " + id + " created as a Limit Order Sell");
            if(price < bidPrice){
                long buyerID = buyersBook.get(bidPrice).get(0);
                //System.out.println("Order " + buyerID + " executed at the price " + bidPrice);
                buyersBook.get(bidPrice).remove(0);
                if(buyersBook.get(bidPrice).size() == 0){
                    buyersBook.remove(bidPrice);
                    availableBuys.pollLast();
                }
                //System.out.println("Order " + id + " executed at the price " + bidPrice);
                p.d = bidPrice;
                p.l = buyerID;
            }
            else{
                availableSells.add(price);
                if(!sellersBook.containsKey(price)){
                    sellersBook.put(price, new LinkedList<>());
                }
                sellersBook.get(price).add(id);
            }
        }

        updateMarketPrices();
        return p;
    }

    public void updateMarketPrices(){
        if(availableBuys.size() > 0)
            bidPrice = availableBuys.last();
        else
            bidPrice = -1.0;

        if(availableSells.size() > 0)
            askPrice = availableSells.first();
        else
            askPrice = Double.MAX_VALUE;
    }

    //becomes a market order whenver stop price is reached

    public void addStopOrder(Double stopPrice, long id){

    }

    //becomes a limit order whenever market price becomes stop price

    public void addStopLimitOrder(Double stopLimitPrice, long id){

    }

    public void performTrades(){
        for(Double d : availableBuys){
            Double price = d;
            //ArrayList<Long> clientsWaiting = buyersBook.get(price);
        }
    }
}
