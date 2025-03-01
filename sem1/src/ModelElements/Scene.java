package ModelElements;

import SomeClasses.Type;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public int id;
    public List<PoligonalModel> models;
    public List<Flash> flashes = new ArrayList<>();

    public Scene(List<PoligonalModel> models) {
        this.models = models;
    }

    public Scene(List<PoligonalModel> models, List<Flash> flashes) {
        this.models = models;
        this.flashes = flashes;
    }

    public Type method1 (Type type) {return type;}
    public Type method2 (Type typeA, Type typeB) {return typeA;}
}
