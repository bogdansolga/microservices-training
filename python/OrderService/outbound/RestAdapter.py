import requests

from python.RestaurantService.domain import Menu


class RestAdapter:
    def __init__(self, base_url):
        self.base_url = base_url

    def get_menu(self, restaurant_id):
        response = requests.get(f'{self.base_url}/menu/{restaurant_id}')
        return Menu.from_dict(response.json())