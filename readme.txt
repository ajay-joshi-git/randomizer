Execute  PrimeClientRunner (This is Socket client) ; you have to run Server App(RandomizerRunner) first and then run this.
Server app also starts distributed queue.

Flow :
- An randomizer thread generate prime and put random number to Input blocking queue
- Queue thread take number from in-queue - [process it]* - and put result to out-queue
- Printer thread print payload to console
*Process it - means send to client and get result 