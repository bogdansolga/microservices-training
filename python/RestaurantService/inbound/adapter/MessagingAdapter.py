import pika

from python.RestaurantService.domain import OrderDetails


class MessagingAdapter:
    def __init__(self, connection_parameters, restaurant_service):
        self.connection_parameters = connection_parameters
        self.restaurant_service = restaurant_service

    def start_listening(self):
        connection = pika.BlockingConnection(self.connection_parameters)
        channel = connection.channel()
        channel.queue_declare(queue = 'order_queue')

        def callback(channel, method, properties, body):
            order_details = OrderDetails.from_json(body)
            self.restaurant_service.process_order(order_details)

        channel.basic_consume(queue = 'order_queue', on_message_callback = callback, auto_ack = True)
        channel.start_consuming()