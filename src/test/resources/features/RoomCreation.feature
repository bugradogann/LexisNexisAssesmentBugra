@reg
Feature: Room Creation


  Scenario Outline: Multiple type of Room Creation
    Given the user is logged in thru admin panel
    When the user creates rooms with following options
      | roomNumber        | <number>        |
      | roomType          | <type>          |
      | roomAccessibility | <accessibility> |
      | roomPrice         | <price>         |
      | roomDetails       | <details>       |
    And the user navigates back to home page
    Then user the should be able to see newly created room details in the main listing
      | expectedRoomType          | <type>          |
      | expectedRoomAccessibility | <accessibility> |
      | expectedRoomPrice         | <price>         |
      | expectedRoomDetails       | <details>       |
    Examples:
      | number | type   | accessibility | price | details |
      | 102    | Twin   | true          | 150   | WiFi    |
      | 103    | Double | false         | 200   | TV      |
      | 104    | Family | true          | 250   | Safe    |
      | 105    | Suite  | false         | 300   | Radio   |