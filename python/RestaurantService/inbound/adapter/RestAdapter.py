from flask import Flask
from python.RestaurantService.service import RestaurantService

app = Flask(__name__)
restaurant_service = RestaurantService(menu_repository)

@app.route('/menu/<restaurant_id>', methods=['GET'])
def get_menu(restaurant_id):
    menu = restaurant_service.get_menu(restaurant_id)
    return menu.to_dict(), 200
