Feature: Operaciones de tickets
  Revisión de comportamiento del servicio de tickets


  Scenario: El usuario crea un ticket correctamente
    Given Creación correcta de un ticket vacío
    When Se le asigna todo el contenido a dicho ticket para registrar un problema o tarea
    Then El ticket queda cargado

  Scenario: El usuario crea un ticket correctamente para reportar un error
    Given Creación correcta de un ticket vacío
    When Se le asigna todo el contenido a dicho ticket para registrar un problema
    Then El ticket queda cargado con el error


  Scenario: El usuario modifica el estado de un ticket
    Given A un ticket ya creado con sus atributos
    When Cuando se usa edita el estado del ticket
    Then Se verá reflejado el cambio en el ticket


  Scenario: El usuario modifica el severidad de un ticket
    Given A un ticket ya creado con sus atributos
    When Cuando se usa edita la severidad del ticket
    Then Se verá reflejado el cambio en su severidad


  Scenario: El usuario modifica el prioridad de un ticket
    Given A un ticket ya creado con sus atributos
    When Cuando se usa edita la prioridad del ticket
    Then Se verá reflejado el cambio en su prioridad


#