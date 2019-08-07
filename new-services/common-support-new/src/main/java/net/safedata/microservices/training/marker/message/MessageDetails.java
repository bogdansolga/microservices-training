package net.safedata.microservices.training.marker.message;

public @interface MessageDetails {
    Service publisher();

    Service[] subscribers();

    Channel channel();
}
