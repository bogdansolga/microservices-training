from python.OrderService.outbound import RestAdapter

class OrderService:
    def __init__(self, rest_adapter, messaging_adapter):
        self.rest_adapter = rest_adapter
        self.messaging_adapter = messaging_adapter

    def fetch_menu(self, restaurant_id):
        menu = self.rest_adapter.get_menu(restaurant_id)
        return menu

    def place_order(self, order_details):
        self.messaging_adapter.notify_order(order_details)
