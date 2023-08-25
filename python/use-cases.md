#### Short summary
This small sample project includes two minimal services:
- a minimal _OrderService_
- a minimal _RestaurantService_
---
#### Implemented use-cases
There are two service-to-service communication scenarios implemented:
* a synchronous (REST) communication - the _OrderService_ is making a REST call to the _RestaurantService_ to retrieve the menu for a certain restaurant_id
* an asynchronous (messaging based) communication - the _OrderService_ is publishing an async message to the _RestaurantService_, to notify a restaurant when an order has been placed
---
**For the first use-case** (the synchronous REST communication between the _OrderService_ and the _RestaurantService_):
* for the _OrderService_:
  * the _OrderService_ class makes the REST call using the outbound _RestAdapter_
* for the _RestaurantService_:
  * the REST endpoint is exposed on the inbound _RestAdapter_
  * the inbound _RestAdapter_ invokes the _RestaurantService_, to retrieve the menu for the specified restaurant ID

**For the second use-case** (the asynchronous communication between the _OrderService_ and the _RestaurantService_):
* for the _OrderService_:
    * the _OrderService_ class publishes an async message using the outbound _MessagingAdapter_
* for the _RestaurantService_:
    * the message listening endpoint is exposed on the inbound _MessagingAdapter_
    * the inbound _MessagingAdapter_ invokes the _RestaurantService_ to process the details about a certain order
  