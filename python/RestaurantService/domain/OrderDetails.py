import json


class OrderDetails:
    def __init__(self, order_id, restaurant_id, items, customer_info):
        self.order_id = order_id
        self.restaurant_id = restaurant_id
        self.items = items  # List of ordered items, e.g., [{'name': 'Pasta', 'quantity': 2}, {'name': 'Salad', 'quantity': 1}]
        self.customer_info = customer_info  # Dictionary containing customer information, e.g., {'name': 'John', 'address': '123 Street'}

    @classmethod
    # allows the creation of an OrderDetails object from a JSON string
    def from_json(cls, json_str):
        data = json.loads(json_str)
        return cls(
            order_id=data['order_id'],
            restaurant_id=data['restaurant_id'],
            items=data['items'],
            customer_info=data['customer_info']
        )

    # serializes the object to a JSON string
    def to_json(self):
        return json.dumps({
            'order_id': self.order_id,
            'restaurant_id': self.restaurant_id,
            'items': self.items,
            'customer_info': self.customer_info
        })
