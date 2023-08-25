import pika

class MessagingAdapter:
    def __init__(self, connection_parameters):
        self.connection_parameters = connection_parameters

    def notify_order(self, order_details):
        connection = pika.BlockingConnection(self.connection_parameters)
        channel = connection.channel()
        channel.basic_publish(exchange='', routing_key='order_queue', body=order_details.to_json())
        connection.close()