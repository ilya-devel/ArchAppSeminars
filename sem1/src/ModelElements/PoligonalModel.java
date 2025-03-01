package ModelElements;

import java.util.ArrayList;
import java.util.List;

public class PoligonalModel {
    public List<Poligon> poligons;
    public List<Texture> textures = new ArrayList<>();

    public PoligonalModel(List<Poligon> poligons) {
        this.poligons = poligons;
    }

    public PoligonalModel(List<Poligon> poligons, List<Texture> textures) {
        this.poligons = poligons;
        this.textures = textures;
    }
}
