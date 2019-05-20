# Foods R Us

An e-commerce web application for food items. The project is separated into three components: eFoods, Auth, middleware (MW).

## eFoods (B2C)
The client-facing website that users visit and interact with. Displays a catalog of items fetched from a database of categorized food items. Creates a collection of items (purchase order) that a user wants to order. An OAuth service is used to verify their identity on checkout. The server side of the application keeps a record of the orders that clients make and then ships a single purchase order.

![Animated workflow of the eFoods application](https://i.imgur.com/gcF06Ah.gif)

## Auth
The authentication service used to sign users in to the website (requires an account from York University's Department of Electrical Engineering and Computer Science).

## Middleware (MW)
A non-interactive web application that runs asynchronously to the eFoods (business-to-client, B2C) application. It is responsible for reading the purchase orders created by the B2C application in order to send them to a possible business-to-business (B2B) procurement application (not implemented for this project).

## Note
This project will not be maintained at the moment.

