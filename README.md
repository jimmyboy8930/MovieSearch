
# Netflix Database Project

This project is a Java-based Netflix title database manager that allows users to add, delete, modify, and search for movie and TV show titles. The application leverages object-oriented programming principles and uses a local file for storing information about various Netflix titles.

## Features

- **Add Titles**: Allows the user to add movies or TV shows to the database.
- **Delete Titles**: Supports deleting specific titles from the database.
- **Modify Titles**: Enables modification of existing titles in the database.
- **Search Titles**: Users can search for movies and TV shows based on attributes like rating, genre, duration, etc.
- **Store and Retrieve Data**: Data is stored persistently in a file that can be loaded and queried.

## Classes

- `AddTitleUseCase.class`: Handles the logic for adding new titles (either movies or TV shows) to the Netflix database.
- `DeleteTitleUseCase.class`: Implements functionality to remove a title from the database.
- `SearchTitleUseCase.class`: Provides the logic to search for titles based on attributes such as rating, genre, director, and year.
- `Movie.class`: Represents the Movie entity, containing attributes like title, director, duration, etc.
- `TVShow.class`: Represents the TV Show entity with relevant attributes such as title, seasons, and episodes.
- `NetflixDatabase.class`: Manages the list of Netflix titles, providing methods to add, delete, search, and modify titles.
- `NetflixTitle.class`: An abstract class or interface used by both movies and TV shows.
- `Utility.class`: Provides utility functions to support the operations within the application.
- `TestDriver.class`: A driver class used to run and test the Netflix database application.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher.
- A terminal or IDE (such as VS Code, IntelliJ, or Eclipse) capable of running Java applications.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/yourrepository.git
   ```

2. **Compile the Java files**:
   Open a terminal in the project directory and run:
   ```bash
   javac *.java
   ```

3. **Run the application**:
   After compiling, run the `TestDriver` class:
   ```bash
   java TestDriver
   ```

### Usage

1. Upon running the application, you will be prompted with a menu to add, delete, modify, or search titles.
2. Follow the on-screen prompts to interact with the database.

### Example

To add a movie:
1. Choose the "Add a title" option.
2. Enter the required information such as title, rating, genre, etc.

To search for a movie:
1. Choose the "Search for titles" option.
2. Select the attribute you want to search by (e.g., rating, genre, etc.).

### Data Storage

All data is stored in a local file (likely a CSV or serialized file) that is automatically updated with any changes made by the user.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
