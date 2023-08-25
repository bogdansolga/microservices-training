class MenuRepository:
    def __init__(self):
        # In-memory storage of menus by restaurant ID
        self.menus = {
            'restaurant_1': {
                'items': [
                    {'name': 'Pasta', 'price': 10},
                    {'name': 'Salad', 'price': 5},
                ]
            },
            'restaurant_2': {
                'items': [
                    {'name': 'Burger', 'price': 8},
                    {'name': 'Fries', 'price': 3},
                ]
            }
        }

    def find_by_restaurant_id(self, restaurant_id):
        # Return the menu for the given restaurant ID
        return self.menus.get(restaurant_id, None)