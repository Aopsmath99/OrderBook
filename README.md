# OrderBook

Welcome to my OrderBook, written entirely in Java. Currently,
I support only market orders and limit orders, but they work exactly
as expected. I support most basic OrderBook functionalities, with more
to come.

There are two modes for the book:
1) Manual - You can use the book to test strategies by creating your
own scenarios and seeing how other people's decisions affect your profit
2) Bots - There are few different types of bots in the OrderBookBots folder
but the most developed is the random behavior bot. These bots can help you
automate strategy testing. Feel free to add your own through the OrderBook
interface.

To run, simply compile and run the OrderBookDriver java file, and enter 
"bots" to run your bots, or enter anything else to run manually. If you
choose to run the bots, you will be followed up by a prompt for bot count.