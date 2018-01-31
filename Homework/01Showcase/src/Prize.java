class Prize {
  final String name;
  final long price;

  Prize(String name, long price) {
    this.name = name;
    this.price = price >= 0 ? price : 0; // set price to zero if invalid
  }

  public String toString() { return String.format("%s\t%s", name, price); }
}
