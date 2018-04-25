package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static java.io.FileDescriptor.in;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

public class Main {
    private static final Path DEFAULT = get("shapeFile.txt");
    private static final BufferedReader stdin = new BufferedReader(new FileReader(in));

    public static void main(String[] args) {
        BufferedReader reader = null;
        if (args.length != 0) {
            try {
                reader = newBufferedReader(get(args[0]));
            } catch (IOException ignored) {} // probably bad path
        }

        if (reader == null) reader = getFile();
        if (reader == null) return; // given EOF

        String line;
        String[] parts;
        ShapeTree tree = new ShapeTree();
        try {
            while (null != (line = reader.readLine())) {
                parts = line.split("\t");
                switch (parts[0]) {
                    case "Rectangle":
                        double length = Double.parseDouble(parts[1]);
                        double width = Double.parseDouble(parts[2]);
                        tree.add(new Shape.Rectangle(length, width));
                        break;
                    case "Right Triangle":
                        double base = Double.parseDouble(parts[1]);
                        double height = Double.parseDouble(parts[2]);
                        tree.add(new Shape.Triangle(base, height));
                        break;
                    case "Circle":
                        tree.add(new Shape.Circle(Double.parseDouble(parts[1])));
                    // default: do nothing
                }
            }
        } catch (IOException ignored) {}

        tree.printPreorder();
        System.out.println(tree);
        tree.printPostorder();
        System.out.println(tree.maxArea());

        tree.delete(new Shape.Rectangle(7, 2));
        System.out.println(tree);

        tree.deleteGreaterThan(30);
        System.out.printf("%n%s%n", tree);
    }

    private static BufferedReader getFile() {
        String input;
        System.out.println("enter a file to read (default: '"
                           + DEFAULT.toString() + "')");
        try {
            input = stdin.readLine();
            if (input == null) return null;
            Path path = input.trim().equals("") ? DEFAULT : get(input);
            return newBufferedReader(path);
        } catch (IOException ignored) { return getFile(); }
    }

    private static class ShapeTree extends LinkedBinaryTree<Shape> {

        double maxArea() { return greatest().area; }

        void deleteGreaterThan(double d) {
            deleteGreaterThan(d, root);
        }

        private void deleteGreaterThan(double d, Node start) {
            if (start == null) return;
            if (start.data.area >= d) {
                start.right = null;
                deleteGreaterThan(d, start.left);
                delete(start);
            } else {
                deleteGreaterThan(d, start.right);
            }
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            forEach(t -> {
                result.append(t);
                result.append('\n');
            });
            return result.toString();
        }
    }

}
