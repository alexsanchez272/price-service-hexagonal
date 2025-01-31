Feature: Price Consultation
  As a user of the e-commerce system
  I want to consult product prices
  So that I can know the applicable price for a specific date and product

  Scenario Outline: Consult price for product 35455 of brand ZARA at different times
    Given the price service is running
    When I request the price for product 35455 of brand 1
    And the consultation date is "<consultationDate>"
    Then the response status code should be 200
    And the price list should be <priceList>
    And the final price should be <finalPrice>
    And the brand id should be 1

    Examples:
      | consultationDate      | priceList | finalPrice |
      | 2020-06-14 10:00:00   | 1         | 35.50      |
      | 2020-06-14 16:00:00   | 2         | 25.45      |
      | 2020-06-14 21:00:00   | 1         | 35.50      |
      | 2020-06-15 10:00:00   | 3         | 30.50      |
      | 2020-06-16 21:00:00   | 4         | 38.95      |