import java.util.*;
import java.util.stream.*;

// By using specification Design pattern, we are keeping filters open for extention
// and closed for modification. At any point of time, with product increasing in attributes
// and the numbers of filters increasing exponentially, we aren't modifying existing code at all!


enum Color {
    RED, GREEN, BLACK;
}

enum Size {
    SMALL, LARGE, HUGE;
}

class Product {
    String name;
    Color color;
    Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Product Name: " + this.name + ", Color: " + this.color + ", Size: " + this.size;
    }
}

class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.color == color);
    }
}

interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filter(List<T> items);
}

class ColorSpecification implements Specification<Product> {
    private Color color;
    public ColorSpecification(Color color) {
        this.color = color;
    }

    public boolean isSatisfied(Product p) {
        return p.color == color;
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;
    public SizeSpecification(Size size) {
        this.size = size;
    }

    public boolean isSatisfied(Product p) {
        return p.size == size;
    }
}

class AndSpecification implements Specification<Product> {
    List<Specification<Product>> specs;
    public AndSpecification(List<Specification<Product>> specs) {
        this.specs = specs;
    }
    
    public boolean isSatisfied(Product p) {
        for (Specification<Product> spec : specs) {
            if (!spec.isSatisfied(p)) return false;
        }

        return true;
    }
}

class OrSpecification implements Specification<Product> {
    List<Specification<Product>> specs;
    public OrSpecification(List<Specification<Product>> specs) {
        this.specs = specs;
    }
    
    public boolean isSatisfied(Product p) {
        for (Specification<Product> spec : specs) {
            if (spec.isSatisfied(p)) return true; 
        }

        return false;
    }
}

class AndFilter implements Filter<Product> {
    Specification<Product> spec;
    public AndFilter(Specification<Product> spec) {
        this.spec = spec;
    }

    public Stream<Product> filter(List<Product> products) {
        return products.stream().filter(p -> spec.isSatisfied(p));
    }
}

class OrFilter implements Filter<Product> {
    Specification<Product> spec;
    public OrFilter(Specification<Product> spec) {
        this.spec = spec;
    }

    public Stream<Product> filter(List<Product> products) {
        return products.stream().filter(p -> spec.isSatisfied(p));
    }
}

class ColorFilter implements Filter<Product> {
    Specification<Product> spec;

    public ColorFilter(Specification<Product> spec) {
        this.spec = spec;
    }

    public Stream<Product> filter(List<Product> products) {
        return products.stream().filter(p -> spec.isSatisfied(p));
    }
}

class Driver {
    public static void main(String[] args) {
        Product p1 = new Product("Speakers", Color.RED, Size.LARGE);
        Product p2 = new Product("Camera", Color.BLACK, Size.SMALL);
        Product p3 = new Product("Christmas Tree", Color.GREEN, Size.HUGE);

        List<Product> products = List.of(p1, p2, p3);
        ProductFilter pf = new ProductFilter();
        pf.filterByColor(products, Color.GREEN)
            .forEach((p) -> { System.out.println(p); });

        // OOP + Specification
        ColorSpecification cs = new ColorSpecification(Color.GREEN);
        SizeSpecification ss = new SizeSpecification(Size.LARGE);

        ColorFilter cf = new ColorFilter(cs);
        cf.filter(products).forEach((p) -> { System.out.println(p); });

        // And specification
        AndFilter af = new AndFilter(new AndSpecification(List.of(cs, ss)));
        af.filter(products).forEach((p) -> { System.out.println(p); });

        // Or specification
        OrFilter of = new OrFilter(new OrSpecification(List.of(cs, ss)));
        of.filter(products).forEach((p) -> { System.out.println(p); });
    }
}