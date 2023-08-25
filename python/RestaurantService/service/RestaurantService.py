class RestaurantService:
    def __init__(self, menu_repository):
        self.menu_repository = menu_repository

    def get_menu(self, restaurant_id):
        return self.menu_repository.find_by_restaurant_id(restaurant_id)

    @classmethod
    def process_order(self, order_details):
        print("Processing the order " + order_details)