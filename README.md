# eFoods

## B2C
This is the web application that the user(client) communicates with. all it is supposed to do is collect information from the client(things that they want to order). When they want to check out, they use OAuth to verify their identities. The application(server side) will keep track of all the orders that the clients make and every now and then ships a P/O, ordering all the things the clients want in one shot.

![alt text](https://i.imgur.com/pO7KAkD.gif)

## MW
This is a headles web application that runs asynchronously from the B2C application. It reads the P/O that B2C creates and sends an order to B2B( we aren.t implementing B2B).
