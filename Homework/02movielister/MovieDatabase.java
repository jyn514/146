import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllLines;

public class MovieDatabase extends DoublyLinkedList<Movie> {

  void print() { System.out.println(this); }

  String removeMovieTitle(String title) {
    goToStart();
    StringBuilder message = new StringBuilder();
    for (Movie m : this) if (m.name.contains(title)) {
      message.append(String.format("Deleted movie %s.", m.name));
      deleteCurrent();
    }
    return (message.length() == 0 ? String.format("No movies of title %s found.", title) : message.toString());
  }

  void write(Path p) throws IOException {
    File f = p.toFile();
    if (!f.exists() && !f.createNewFile()) {
      System.err.printf("WARNING: File %s created by different program between check and creation.", f);
    }
    if (!(f.isFile() && f.canRead() && f.canWrite())) {
      throw new IOException("Cannot write to file.");
    }
    PrintWriter writer = new PrintWriter(f);
    writer.print(this);
    writer.close();
  }

  MovieDatabase read(Path p) throws IOException {
    MovieDatabase result = new MovieDatabase();
    File f = p.toFile();
    if (!(f.exists() && f.isFile() && f.canRead())) {
      throw new IOException("Cannot read file.");
    }
    List<String> movies = readAllLines(p);
    for (String s : movies) {
      result.append(parse(s));
    }
    return result;
  }

  private Movie parse(String movie) {
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
    HashSet<Movie> movieTitles = new HashSet<>();
    for (Movie m : this) {
      if (m.name.contains(s) || m.name.equalsIgnoreCase(s)) {
        movieTitles.add(m);
      }
    }
    return movieTitles;
  }

  Set<Movie> searchByDirector(String s) {
    HashSet<Movie> movieDirectors = new HashSet<>();
    for (Movie m : this) {
      if (m.director.contains(s) || m.director.equalsIgnoreCase(s)) {
        movieDirectors.add(m);
      }
    }
    return movieDirectors;
  }

  Set<Movie> searchByYear(int n) {
    HashSet<Movie> movieYears = new HashSet<>();
    for (Movie m : this) if (m.year == n) movieYears.add(m);
    return movieYears;
  }

  Set<Movie> searchByRating(int n) {
    HashSet<Movie> movieRatings = new HashSet<>();
    for (Movie m : this) if (m.rating >= n) movieRatings.add(m);
    return movieRatings;
  }

}
