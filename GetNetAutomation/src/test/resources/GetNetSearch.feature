#Author: mabvelho@gmail.com

@Website
Feature: GetNet Search
  This feature verifies the search popup

  @Search
  Scenario Outline: Website Search For Popup Title
    Given I access the GetNet homepage at "<url>"
     When I search for "<query>"
      And I select the "<result>" from the search result
     Then a popup with the title "<popupTitle>" is displayed

    Examples: 
      | url 												| query			| result 															| popupTitle 													|
      | https://site.getnet.com.br/	| superget	| Como acesso a minha conta SuperGet?	| Como acesso a minha conta SuperGet?	|