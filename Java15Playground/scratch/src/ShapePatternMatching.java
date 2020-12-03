import java.util.Arrays;
import java.util.List;

public abstract class ShapePatternMatching {
    protected int numberOfSides = 0;
    protected void go(){
        System.out.println("I am a shape");
    }

    public static void main(String[] args){
        ShapePatternMatching rectangle = new Rectangle();
        ShapePatternMatching square = new Square();
        ShapePatternMatching triangle = new Triangle();
        List<ShapePatternMatching> shapePatternMatchings = Arrays.asList(rectangle, square, triangle);
        shapePatternMatchings.forEach(shapePatternMatching -> getInstanceOf(shapePatternMatching));
        System.out.println("Polymorphism");
        shapePatternMatchings.forEach(ShapePatternMatching::go);
    }

    private static void getInstanceOf(ShapePatternMatching shapePatternMatching) {
        if (shapePatternMatching instanceof ShapePatternMatching){
            shapePatternMatching.go();
        }
        if (shapePatternMatching instanceof Rectangle){
            shapePatternMatching.go();
        }
        if (shapePatternMatching instanceof Square){
            shapePatternMatching.go();
        }
    }
}

class Rectangle extends ShapePatternMatching {
    protected int numberOfSides = 4;
    public void go(){
        System.out.println("I am a Rectangle");
    }
}

class Triangle extends ShapePatternMatching {
    protected int numberOfSides = 3;
    protected void go(){
        System.out.println("I am a Triangle");
    }
}

class Square extends Rectangle {
    public void go(){
        System.out.println("I am a Square");
    }
}
