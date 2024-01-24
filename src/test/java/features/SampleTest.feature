Feature: Sample Screen functionality

  @Regression
  Scenario Outline: Verify About Us Link
    When click on "<link>" menu
    Then verify the user redirect to the "<link_text>" page

    Examples:
      | link     | link_text    |
      | About Us | about.htm    |
      | Services | services.htm |

  @SampleData_Test001:SampleAppData
  Scenario: Verify About Us Link with JSON data
    When click on "link" menu with Data Driven
    Then verify the user redirect to the "link_text" page with Data Driven
