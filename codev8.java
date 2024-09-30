import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


// Abstract NetflixTitle class
abstract class NetflixTitle {
    protected String show_id;
    protected String title;
    protected String type;
    protected String director;
    protected String country;
    protected int release_year;
    protected String rating;
    protected String duration;
    protected String genre;

    public NetflixTitle(String show_id, String title, String type, String director, String country, int release_year, String rating, String duration, String genre) {
        this.show_id = show_id;
        this.title = title;
        this.type = type;
        this.director = director;
        this.country = country;
        this.release_year = release_year;
        this.rating = rating;
        this.duration = duration;
        this.genre = genre;
    }
    
    // Getter and Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    // Other getters and setters
    public String getShowId() { return show_id; }
    public void setShowId(String show_id) { this.show_id = show_id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getReleaseYear() { return release_year; }
    public void setReleaseYear(int release_year) { this.release_year = release_year; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public String getAttributeValue(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "title":
                return getTitle();
            case "director":
                return getDirector();
            case "country":
                return getCountry();
            case "release_year":
                return String.valueOf(getReleaseYear());
            case "rating":
                return getRating();
            case "duration":
                return getDuration();
            case "genre":
                return getGenre();
            default:
                return "Attribute not found";
        }
    }
    
    public void setAttributeValue(String attributeName, String newValue) {
        switch (attributeName.toLowerCase()) {
            case "title":
                setTitle(newValue);
                break;
            case "director":
                setDirector(newValue);
                break;
            case "country":
                setCountry(newValue);
                break;
            case "release_year":
                setReleaseYear(Integer.parseInt(newValue));
                break;
            case "rating":
                setRating(newValue);
                break;
            case "duration":
                setDuration(newValue);
                break;
            case "genre":
                setGenre(newValue);
                break;
            default:
                System.out.println("Attribute not found");
        }
    }
    @Override
    public String toString() {
        return "Type: " + getType() + ", Show ID: " + getShowId() + ", Title: " + getTitle() 
        + ", Director: " + getDirector() + ", Country: " + getCountry() + ", Release Year: " 
        + getReleaseYear() + ", Rating: " + getRating() + ", Duration: " + getDuration() + ", Genre: " + getGenre();
    }
}

// Movie class
class Movie extends NetflixTitle {
    public Movie(String show_id, String title, String director, String country, int release_year, String rating, String duration, String genre) {
        super(show_id, title, "Movie", director, country, release_year, rating, duration, genre);
    }
}

// TVShow class
class TVShow extends NetflixTitle {
    public TVShow(String show_id, String title, String director, String country, int release_year, String rating, String duration, String genre) {
        super(show_id, title, "TV Show", director, country, release_year, rating, duration, genre);
    }
}

class Utility {

    public static void saveDatabaseToFile(NetflixDatabase db, String fileName) {
        List<NetflixTitle> titles = db.getTitles();

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("show_id,type,title,director,country,release_year,rating,duration,listed_in");
            for (NetflixTitle title : titles) {
                writer.println(String.join(",",
                        "\"" + title.getShowId() + "\"",
                        "\"" + title.getType() + "\"",
                        "\"" + title.getTitle() + "\"",
                        "\"" + (title.getDirector() == null ? "" : title.getDirector()) + "\"",
                        "\"" + (title.getCountry() == null ? "" : title.getCountry()) + "\"",
                        "\"" + title.getReleaseYear() + "\"",
                        "\"" + title.getRating() + "\"",
                        "\"" + title.getDuration() + "\"",
                        "\"" + title.getGenre() + "\""
                ));
            }
            System.out.println("Database saved to file " + fileName + " successfully.");
        } catch (IOException e) {
            System.out.println("Error saving database to file " + fileName + ": " + e.getMessage());
        }
    }
}




// NetflixDatabase, TestDriver classes
// NetflixDatabase class
class NetflixDatabase {
    
    
    private ArrayList<NetflixTitle> titles;

    public NetflixDatabase() {
        titles = new ArrayList<>();
    }

    public void addTitle(NetflixTitle title) {
        titles.add(title);
    }
    public ArrayList<NetflixTitle> getTitles() {
        return this.titles;
    }
    public void deleteTitle(NetflixTitle title) {
        titles.remove(title);
    }
    public void modifyTitle(int index, NetflixTitle modifiedTitle) {
        titles.set(index, modifiedTitle);
    }    
    public NetflixTitle getTitle(String show_id) {
        for (NetflixTitle title : titles) {
            if (title.getShowId().equals(show_id)) {
                return title;
            }
        }
        return null;
    }
    //to get the stored data
    public HashSet<String> getUniqueAttributeValues(String attribute, String type) {
        HashSet<String> uniqueValues = new HashSet<>();
        for (NetflixTitle title : titles) {
            if (title.getType().equalsIgnoreCase(type)) {
                String attributeValue = title.getAttributeValue(attribute);
                //error handler
                if (attributeValue != null && !attributeValue.equalsIgnoreCase("Attribute not found")) {
                    uniqueValues.add(attributeValue);
                }
            }
        }
        return uniqueValues;
    }
    
    public static void readTitlesFromFile(String filename, NetflixDatabase db) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Skip the first line (header)
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                String show_id = fields[0];
                String type = fields[1];
                String title = fields[2];
                String director = fields.length > 3 ? fields[3] : "";
                String country = fields.length > 4 ? fields[4] : "";
                int release_year = Integer.parseInt(fields[6]);
                String rating = fields[7];
                String duration = fields[8];
                String genre = fields[9];

                if (type.equalsIgnoreCase("movie")) {
                    db.addTitle(new Movie(show_id, title, director, country, release_year, rating, duration, genre));
                } else if (type.equalsIgnoreCase("tv show")) {
                    db.addTitle(new TVShow(show_id, title, director, country, release_year, rating, duration, genre));
                }
            }
            scanner.close();

            System.out.println("Titles added from file " + filename + " successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename + ", " + e.getMessage());
        }
    }
    
    
}
class AddTitleUseCase {
    private NetflixDatabase db;
    private Scanner scanner;
    private String fileName;

    public AddTitleUseCase(NetflixDatabase db, Scanner scanner, String fileName) {
        this.db = db;
        this.scanner = scanner;
        this.fileName = fileName;
    }

    public void execute() {
        System.out.println("Do you want to build a TV show or a movie? (Enter 'TV Show' or 'Movie')");
        String choice = scanner.nextLine();

        System.out.println("Enter the show_id:");
        String show_id = scanner.nextLine();
        System.out.println("Enter the title:");
        String title = scanner.nextLine();
        System.out.println("Enter the director:");
        String director = scanner.nextLine();
        System.out.println("Enter the country:");
        String country = scanner.nextLine();
        System.out.println("Enter the release_year:");
        int release_year = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.println("Enter the rating:");
        String rating = scanner.nextLine();
        System.out.println("Enter the duration(min if Movie & if TV Show then Seasons):");
        String duration = scanner.nextLine();
        System.out.println("Enter the genre:");
        String genre = scanner.nextLine();

        if (choice.equalsIgnoreCase("movie")) {
            db.addTitle(new Movie(show_id, title, director, country, release_year, rating, duration, genre));
            Utility.saveDatabaseToFile(db, fileName);
        } else if (choice.equalsIgnoreCase("tv show")) {
            db.addTitle(new TVShow(show_id, title, director, country, release_year, rating, duration, genre));
            Utility.saveDatabaseToFile(db, fileName);
        } else {
            System.out.println("Invalid choice, title not added.");
        }
    }
}
    // Other methods to manage collection of title
//System.out.println(fields[0]);
class SearchTitleUseCase {
    private NetflixDatabase db;
    private Scanner scanner;

    public SearchTitleUseCase(NetflixDatabase db, Scanner scanner) {
        this.db = db;
        this.scanner = scanner;
    }
    //main handler for search
    private List<String> getUniqueAttributeValues(List<NetflixTitle> titles, String attribute, String type) {
        Set<String> uniqueValues = new HashSet<>();
        for (NetflixTitle title : titles) {
            if (title.getType().equalsIgnoreCase(type)) {
                String attributeValue = title.getAttributeValue(attribute);
                if (attributeValue != null) {
                    String[] values = attributeValue.split(", ");
                    for (String value : values) {
                        uniqueValues.add(value.trim());
                    }
                }
            }
        }
    
        List<String> sortedUniqueValues;
        if ("duration".equalsIgnoreCase(attribute) && "Movie".equalsIgnoreCase(type)) {
            // Calculate the maximum duration for movies
            int maxDuration = 0;
            for (String value : uniqueValues) {
                int duration = Integer.parseInt(value.split(" ")[0]);
                if (duration > maxDuration) {
                    maxDuration = duration;
                }
            }
    
            // Generate the duration ranges
            List<String> durationRanges = new ArrayList<>();
            for (int i = 0; i <= maxDuration; i += 30) {
                int rangeStart = i;
                int rangeEnd = i + 30;
                String range = rangeStart + "-" + rangeEnd + " min";
                durationRanges.add(range);
            }
            sortedUniqueValues = durationRanges;
        } else {
            sortedUniqueValues = new ArrayList<>(uniqueValues);
            Collections.sort(sortedUniqueValues);
        }
        return sortedUniqueValues;
    }
    
    public void execute() {
        System.out.println("Do you want to search for a TV show or a movie? (Enter 'TV Show' or 'Movie')");
        String choice = scanner.nextLine();
    
        // Prompt user to select the attribute for search
        System.out.println("Select the attribute you want to search for: (By associated number) ");
        System.out.println("1. Rating");
        System.out.println("2. Director");
        System.out.println("3. Genre");
        System.out.println("4. Duration");
        System.out.println("5. Country");
        System.out.println("6. Year\n");

        int attributeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String attribute = "";
        switch (attributeChoice) {
            case 1:
                attribute = "rating";
                break;
            case 2:
                attribute = "director";
                break;
            case 3:
                attribute = "genre";
                break;
            case 4:
                attribute = "duration";
                break;
            case 5:
                attribute = "country";
                break;
            case 6:
                attribute = "release_year";
                break;
            default:
                System.out.println("Invalid choice, attribute not found.");
                return;
        }

        /* 
        // Filter titles based on the user's choice
        List<NetflixTitle> filteredTitles = db.getTitles().stream()
        .filter(title -> title.getType().equalsIgnoreCase(choice))
        .collect(Collectors.toList());
        */
        // Get the unique attribute values for the selected attribute and type
        List<String> uniqueValues = getUniqueAttributeValues(db.getTitles(), attribute, choice);
    
        // Display the unique attribute values
        if (uniqueValues.size() == 0) {
            System.out.println("No unique values found.");
        } else {
            System.out.println("Select a value to search for:");
            int i = 1;
            for (String value : uniqueValues) {
                System.out.println(i + ". " + value);
                i++;
            }
            int choiceValue = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
    
            // Search for the title based on user input
            ArrayList<NetflixTitle> result = new ArrayList<>();
            for (NetflixTitle title : db.getTitles()) {
                if (title.getType().equalsIgnoreCase(choice)) {
                    String attributeValue = title.getAttributeValue(attribute);
                    if (attributeValue != null) {
                        //add in duration interval
                        if (attribute.equalsIgnoreCase("duration") && choice.equalsIgnoreCase("Movie")) {
                            String selectedRange = uniqueValues.toArray()[choiceValue - 1].toString();
                            int rangeStart = Integer.parseInt(selectedRange.split("-")[0].trim());
                            int rangeEnd = Integer.parseInt(selectedRange.split("-")[1].split(" ")[0].trim());
                            int duration = Integer.parseInt(attributeValue.split(" ")[0]);
                            if (duration >= rangeStart && duration <= rangeEnd) {
                                result.add(title);
                            }
                        } else {
                            String[] values = attributeValue.split(", ");
                            for (String value : values) {
                                if (value.equalsIgnoreCase(uniqueValues.toArray()[choiceValue - 1].toString())) {
                                    result.add(title);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // Display search results
            if (result.size() == 0) {
                System.out.println("No titles found.");
            } else {
                for (NetflixTitle title : result) {
                    System.out.println(title.toString());
                    System.out.println("");
                }
            }
        }
    }
    
}
class DeleteTitleUseCase {
    private NetflixDatabase db;
    private Scanner scanner;
    private String fileName;

    public DeleteTitleUseCase(NetflixDatabase db, Scanner scanner, String fileName) {
        this.db = db;
        this.scanner = scanner;
        this.fileName = fileName;
    }

    public void execute() {
        System.out.println("Do you want to delete a TV show or a movie? (Enter 'TV Show' or 'Movie')");
        String choice = scanner.nextLine();

        List<NetflixTitle> filteredTitles = db.getTitles().stream()
            .filter(title -> title.getType().equalsIgnoreCase(choice))
            .collect(Collectors.toList());

        if (filteredTitles.isEmpty()) {
            System.out.println("No titles found.");
            return;
        }

        System.out.println("Select the index number of the title you want to delete:");
        int index = 1;
        for (NetflixTitle title : filteredTitles) {
            System.out.println(index + ". " + title);
            index++;
        }

        int selectedIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (selectedIndex >= 1 && selectedIndex <= filteredTitles.size()) {
            NetflixTitle selectedTitle = filteredTitles.get(selectedIndex - 1);
            db.deleteTitle(selectedTitle);
            Utility.saveDatabaseToFile(db, fileName);
            System.out.println("Title deleted: " + selectedTitle);
        } else {
            System.out.println("Invalid selection. No title deleted.");
        }
    }
}

class TestDriver {
    public static void readTitlesFromFile(String filename, NetflixDatabase db) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
    
            // Skip the first line (header)
            scanner.nextLine();
            //to read string
            Pattern pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            while (scanner.hasNextLine()) {
                String[] fields = pattern.split(scanner.nextLine());
                //to read string
                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].replaceAll("^\"|\"$", "");
                }

                String show_id = fields[0];
                String type = fields[1];
                String title = fields[2];
                String director = fields[3].isEmpty() ? null : fields[3];
                String country = fields[4].isEmpty() ? null : fields[4];
                int release_year = fields[5].isEmpty() ? 0 : Integer.parseInt(fields[5]);
                String rating = fields[6];
                String duration = fields[7];
                String genre = fields[8];
    
                if (type.equalsIgnoreCase("movie")) {
                    db.addTitle(new Movie(show_id, title, director, country, release_year, rating, duration, genre));
                } else if (type.equalsIgnoreCase("tv show")) {
                    db.addTitle(new TVShow(show_id, title, director, country, release_year, rating, duration, genre));
                }
            }
            scanner.close();
    
            System.out.println("Titles added from file " + filename + " successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename + ", " + e.getMessage());
        }
    }
    public static void modifyRating(NetflixDatabase db, Scanner scanner, String fileName) {
        System.out.println("Do you want to modify the rating of a TV show or a movie? (Enter 'TV Show' or 'Movie')");
        String type = scanner.nextLine().trim();
    
        List<NetflixTitle> titlesOfType = db.getTitles().stream()
            .filter(t -> t.getType().equalsIgnoreCase(type))
            .collect(Collectors.toList());
    
        if (titlesOfType.isEmpty()) {
            System.out.println("No titles found.");
            return;
        }
    
        System.out.println("Select the title you want to modify by entering its index number:");
        for (int i = 0; i < titlesOfType.size(); i++) {
            System.out.println((i + 1) + ". " + titlesOfType.get(i).getTitle());
        }
    
        int titleIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        NetflixTitle titleToModify = titlesOfType.get(titleIndex - 1);
    
        System.out.println("Enter the new rating for the title (current: " + titleToModify.getRating() + "):");
        String newRating = scanner.nextLine().trim();
    
        titleToModify.setRating(newRating);
        Utility.saveDatabaseToFile(db, fileName);
        System.out.println("Rating modified successfully.");
    }
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        NetflixDatabase db = new NetflixDatabase();
    
        //System.out.println("Enter the name of the file to read from:");
        //String fileName = scanner.nextLine();
        String fileName = "netflix.csv";
    
        try {
            readTitlesFromFile(fileName, db);
        } catch (Exception e) {
            System.out.println("File not found.");
        }
    
        boolean exit = false;
        while (!exit) {
            System.out.println("Please choose an option:");
            System.out.println("1. Add a title");
            System.out.println("2. Delete a title");
            System.out.println("3. Search for titles");
            System.out.println("4. Modify a title");
            System.out.println("5. Exit\n");
    
            int choice2 = scanner.nextInt();
            scanner.nextLine(); // Consume newline left over
    
            switch (choice2) {
                case 1:
                    AddTitleUseCase addTitleUseCase = new AddTitleUseCase(db, scanner, fileName);
                    addTitleUseCase.execute();
                    break;
                case 2:
                    // Implement Delete a title use case
                    DeleteTitleUseCase deleteTitleUseCase = new DeleteTitleUseCase(db, scanner, fileName);
                    deleteTitleUseCase.execute();
                    break;
                case 3:
                    // Implement Search for titles use case
                    SearchTitleUseCase searchTitleUseCase = new SearchTitleUseCase(db, scanner);
                    searchTitleUseCase.execute();
                    break;
                case 4:
                    // Implement Modify a title use case
                    modifyRating(db, scanner, fileName);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }
} 