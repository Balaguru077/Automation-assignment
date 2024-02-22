Feature: Add new item to ToDO list

  Scenario: Open 'SEE ALL INTEGRATIONS' link in a new tab and verify URL
    Given User navigates to "https://www.lambdatest.com"
    When User performs an explicit wait until all elements are available
    And User scrolls to the WebElement 'SEE ALL INTEGRATIONS' using scrollIntoView method
    And User clicks on the link
    Then User saves the window handles in a List
    And User verifies the URL in the new tab
    And I close the current browser window
