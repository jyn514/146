public class Movie {
  final String name;
  final String director;
  final long grossIncome;
  final int year; // greater than 1874
  final short rating; // 1 - 5 stars

  Movie(String n, String d, long g, int y, short r) {
    if (y < 1874) throw new IllegalArgumentException("Cameras did not exist before 1874.");
    if (r < 1 || r > 5) throw new IllegalArgumentException("Rating must be between 1 and 5 stars.");
    name = n;
    director = d;
    grossIncome = g;
    year = y;
    rating = r;
  }

  public boolean equals(Movie m) {
    return name.equals(m.name) && director.equals(m.director) && year == m.year
            && grossIncome == m.grossIncome && rating == m.rating;
  }

  public int hashCode(Movie m) {
    if (m == null) return 0;
    int sum = 0;
    
    sum += hammingDistance(name, m.name);
    sum += hammingDistance(director, m.director);
    sum += Math.pow(4, rating - m.rating);
    sum += year - m.year;
    sum += Math.log10(grossIncome - m.grossIncome);

    return sum;

  }
  
  public String toString() {
    return String.format("Title: %s; Director: %s; Gross income: %d; Year released: %d; Rating: %d",
            name, director, grossIncome, year, rating);
  }

  private int hammingDistance(String original, String modified) {
    int sum = 0;
    for (int i = 0; i < original.length(); i++) if (original.charAt(i) != modified.charAt(i)) sum++;
    return sum;
  }
}
