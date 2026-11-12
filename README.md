# Murder Mystery Game

A Java-based, console-driven interactive murder mystery game developed for the **Object-Oriented Programming** course at the University of Messina.

## About the Game

The player takes on the role of a detective investigating the murder of Edward Harlow, who has been found dead in the Study of a Victorian mansion.

Four suspects are present in the house, and one of them is the killer. The detective must explore five rooms, examine physical evidence, interrogate suspects, uncover hidden information, and identify the murderer before running out of accusations.

## Game Features

- **Room Exploration:** Navigate through five locations: Entrance, Study, Kitchen, Garden, and Library.
- **Evidence Investigation:** Examine clues to reveal hidden forensic information.
- **Suspect Interrogation:** Question suspects and pressure them to obtain additional statements.
- **Accusation System:** Identify the killer while managing a limited number of accusations.
- **Difficulty Levels:** Choose between Easy, Medium, and Hard modes.
- **Investigation Rules:** Interview all suspects before making an accusation.

## Technologies

- Java
- Object-Oriented Programming
- Java Collections Framework

## Object-Oriented Programming Concepts

The project demonstrates the following principles and techniques:

- **Information Hiding:** Private fields and controlled access to internal state.
- **Encapsulation:** Bundling data and related operations within classes.
- **Inheritance:** A class hierarchy based on the abstract `Character` and `Suspect` classes.
- **Abstraction:** Abstract classes and interfaces defining common contracts.
- **Polymorphism:** Method overriding, overloading, dynamic dispatch, and generics.
- **Composition:** Relationships between game components using HAS-A structures.
- **Subtyping:** Using concrete objects through their parent classes and interfaces.
- **Code Reuse:** Reusing shared behaviour through inheritance and interfaces.
- **Exception Handling:** Custom checked exceptions for game-specific errors.
- **Extensibility:** Supporting new suspects, clues, rooms, and difficulty configurations.

## Project Structure

```text
src/
├── Main.java
├── engine/
│   ├── GameEngine.java
│   ├── GameWorld.java
│   └── DifficultyConfig.java
├── model/
│   ├── entities/
│   ├── interfaces/
│   └── world/
└── exception/
```

## How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Locate `Main.java` inside the `src` directory.
4. Run the `main()` method to launch the game.

## Academic Information

- **Course:** Object-Oriented Programming
- **University:** University of Messina
- **Programming Language:** Java
- **Project Type:** Academic Project
