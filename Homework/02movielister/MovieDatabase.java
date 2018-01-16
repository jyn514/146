import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class MovieDatabase extends GenLinkedList implements Iterable<Movie> {

  MovieDatabase() {
    super();
  }

  MovieDatabase(Movie m) {
    super(m);
  }

  MovieDatabase(Movie... movies) {
    super((Object[]) movies);
  }

  @Override
  String getCurrent() {
    return get(current);
  }

  @Override
  String getAll() {
    goToStart();
    if (current == null || current.data == null) {
      return "Empty database.";
    }

    StringBuilder all = new StringBuilder();
    while (current != null && current.data != null && current.link != null) { // got to be a better
                                                                              // way
      all.append(getCurrent());
      all.append('\n');
      goToNext();
    }
    all.append(getCurrent());
    return all.toString();
  }

  String get(ListNode node) {
    if (node.data == null) {
      return "No data at this node.";
    } else {
      return node.data.toString();
    }
  }

  @Override
  void print() {
    System.out.println(getAll());
  }

  void append(Movie m) {
    goToEnd();
    if (current == null) { // database created with new MovieDatabase()
      current = new ListNode(m);
    } else if (current.data == null) {
      current.data = m;
    } else {
      current.link = new ListNode(m);
    }
  }

  String removeMovieTitle(String title) {
    goToStart();
    String message = "";
    Movie c;

    while (current.link != null) {
      c = (Movie) current.data;
      if (c.name.contains(title)) {
        message += String.format("Deleted movie %s.", c.name);
        deleteCurrent();
      }
      goToNext();
    }
    
    // Do it all again
    // DRY , stupid!
    c = (Movie) current.data;
    if (c.name.contains(title)) {
      message += String.format("Deleted movie %s.", c.name);
      deleteCurrent();
    }

    if (message.isEmpty()) {
      return String.format("No movies of title %s found.", title);
    }

    return message;
  }

  void write(Path p) throws IOException {
    File f = p.toFile();
    if (!f.exists()) {
      f.createNewFile();
    }
    if (!(f.isFile() && f.canRead() && f.canWrite())) {
      throw new IOException("Cannot write to file.");
    }
    PrintWriter writer = new PrintWriter(f);
    writer.print(getAll());
    writer.close();
  }

  MovieDatabase read(Path p) throws IOException {
    MovieDatabase result = new MovieDatabase();
    File f = p.toFile();
    if (!(f.exists() && f.isFile() && f.canRead())) {
      throw new IOException("Cannot read file.");
    }
    List<String> movies = Files.readAllLines(p);
    for (String s : movies) {
      result.append(parse(s));
    }
    return result;
  }

  Movie parse(String movie) {
    String[] dataPoints = movie.split("; ");
    String title = null, director = null;
    long income = -1;
    int year = 0;
    short rating = 0;

    for (String s : dataPoints) {
      String[] finalSplit = s.split(": ");
      switch (finalSplit[0]) {
      case "Title":
        title = finalSplit[1];
        break;
      case "Director":
        director = finalSplit[1];
        break;
      case "Gross income":
        income = Long.parseLong(finalSplit[1]);
        break;
      case "Year released":
        year = Integer.parseInt(finalSplit[1]);
        break;
      case "Rating":
        rating = Short.parseShort(finalSplit[1]);
        break;
      }
    }
    return new Movie(title, director, income, year, rating);
  }

  Set<Movie> searchByTitle(String s) {
    HashSet<Movie> movieTitles = new HashSet<Movie>();
    for (Movie m : this) {
      if (m.name.contains(s) || m.name.equalsIgnoreCase(s)) {
        movieTitles.add(m);
      }
    }
    return movieTitles;
  }

  Set<Movie> searchByDirector(String s) {
    HashSet<Movie> movieDirectors = new HashSet<Movie>();
    for (Movie m : this) {
      if (m.director.contains(s) || m.director.equalsIgnoreCase(s)) {
        movieDirectors.add(m);
      }
    }
    return movieDirectors;
  }

  Set<Movie> searchByYear(int n) {
    HashSet<Movie> movieYears = new HashSet<Movie>();
    for (Movie m : this) {
      if (m.year == n) {
        movieYears.add(m);
      }
    }
    return movieYears;
  }

  Set<Movie> searchByRating(int n) {
    HashSet<Movie> movieRatings = new HashSet<Movie>();
    for (Movie m : this) {
      if (m.rating >= n) {
        movieRatings.add(m);
      }
    }
    return movieRatings;
  }

  @Override
  public Iterator<Movie> iterator() {

    goToStart();
    
    return new Iterator<Movie>() {

      @Override
      public boolean hasNext() {
        return current != null && current.data != null;
      }

      @Override
      public Movie next() {
        goToNext();
        return (Movie) previous.data; // TODO: Fix unsafe casting
      }

      public void remove() {
        deleteCurrent();
      }

    };
  }
}
