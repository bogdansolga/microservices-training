Feature: Place Order
  As a consumer of the Order Service
  I should be able to place an order
  Scenario: Order authorized
    Given a valid consumer
    Given using a valid card
    Given the restaurant is accepting orders
    When I place an order for a sandwich at SF
    Then the order should be APPROVED
    And an OrderAuthorized event should be published

  Scenario: Order rejected due to expired card
    Given a valid consumer
    Given using an expired card
    Given the restaurant is accepting orders
    When I place an order for a sandwich at SF
    Then the order should be REJECTED
    And an OrderRejected event should be published
