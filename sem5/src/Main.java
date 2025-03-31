import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    /**
     * Необходимо разделить на горизонтальные уровни "Редактор 3D графики"
     * Один пользователь. Программа работает на одном компьютере без выхода в сеть
     * <p>
     * Что видит пользователь, как взаимодействует? (Панель загрузки, блок редактирования, блок просмотра)
     * Какие задачи можно делать - функции системы? (Загрузить 3D модель, рассмотреть 3D модель, создать новую,
     * редактировать вершины, текстуры, сделать рендер, сохранить рендер)
     * Какие и где хранятся данные? (файлы 3D моделей, рендеры, анимация ..., в файловой системе компьютера)
     * <p>
     * Предложить варианты связывания всех уровней - сценарии использования 3-4 сценария
     * Сквозная функция - создать новую 3D модель, сделать рендер для печати на принтере
     *
     * @param args параметры запуска
     */
    public static void main(String[] args) {
        Editor3D editor3D = new Editor3D();
        boolean f = true;
        while (f) {
            System.out.println("*** МОЙ 3D РЕДАКТОР ***");
            System.out.println("=======================");
            System.out.println("1. Открыть проект");
            System.out.println("2. Сохранить проект");
            System.out.println("3. Отобразить параметры проекта");
            System.out.println("4. Отобразить все модели проекта");
            System.out.println("5. Отобразить все текстуры проекта");
            System.out.println("6. Выполнить рендер всех моделей");
            System.out.println("7. Выполнить рендер модели");
            System.out.println("0. ЗАВЕРШЕНИЕ РАБОТЫ ПРИЛОЖЕНИЯ");
            System.out.println("Пожалуйста выберите пункт меню");
            if (scanner.hasNextInt()) {
                int no = scanner.nextInt();
                scanner.nextLine();
                try {
                    switch (no) {
                        case 0:
                            System.out.println("Завершение работы приложения");
                            f = false;
                            break;
                        case 1:
                            System.out.println("Укажите наименование файла проекта: ");
                            String fileName = scanner.nextLine();
                            editor3D.openProject(fileName);
                            System.out.println("Проект успешно открыт");
                            break;
                        case 3:
                            editor3D.showProjectSettings();
                            break;
                        case 4:
                            editor3D.printAllModels();
                            break;
                        case 5:
                            editor3D.printAllTextures();
                            break;
                        case 6:
                            editor3D.renderAll();
                            break;
                        case 7:
                            System.out.println("Укажите номер модели: ");
                            if (scanner.hasNextInt()) {
                                int modelNo = scanner.nextInt();
                                scanner.nextLine();
                                editor3D.renderModel(modelNo);
                            } else {
                                System.out.println("Номер модели указан не корректно");
                            }
                            break;
                        default:
                            System.out.println("Укажите корректный пункт меню");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}

/**
 * UI (User Interface)
 */
class Editor3D implements UILayer {
    private ProjectFile projectFile;
    private BusinessLogicalLayer businessLogicalLayer;
    private DatabaseAccess databaseAccess;
    private Database database;

    private void  initialize() {
        database = new EditorDatabase(projectFile);
        databaseAccess = new EditorDatabaseAccess(database);
        businessLogicalLayer = new EditorBusinessLogicalLayer(databaseAccess);
    }

    @Override
    public void openProject(String fileName) {
        projectFile = new ProjectFile(fileName);
        initialize();
    }

    @Override
    public void showProjectSettings() {
        // Предусловие
        checkProjectFile();

        System.out.println("*** Project ver1 ***");
        System.out.println("********************");
        System.out.printf("fileName: %s\n", projectFile.getFileName());
        System.out.printf("setting1: %d\n", projectFile.getSetting1());
        System.out.printf("setting2: %s\n", projectFile.getSetting2());
        System.out.printf("setting3: %s\n", projectFile.getSetting3());
        System.out.println("********************");
    }

    private void checkProjectFile() {
        if (projectFile == null)
            throw new RuntimeException("Файл проекта не определён");
    }

    @Override
    public void saveProject() {
        checkProjectFile();
        database.save();
        System.out.println("Изменения успешно сохранены");
    }

    @Override
    public void printAllModels() {
        checkProjectFile();

        ArrayList<Model3D> model3DS = (ArrayList<Model3D>)businessLogicalLayer.getAllModels();
        for (int i = 0; i < model3DS.size(); i++) {
            System.out.printf("===%d===\n", i);
            System.out.println(model3DS.get(i));
            for (Texture texture: model3DS.get(i).getTextures()) {
                System.out.printf("\t%s\n", texture);
            }
        }
    }

    @Override
    public void printAllTextures() {
        checkProjectFile();

        ArrayList<Texture> textures = (ArrayList<Texture>)businessLogicalLayer.getAllTextures();
        for (int i = 0; i < textures.size(); i++) {
            System.out.printf("===%d===\n", i);
            System.out.println(textures.get(i));
        }
    }

    @Override
    public void renderAll() {
        checkProjectFile();

        System.out.println("Подождите ...");
        long startTime = System.currentTimeMillis();
        businessLogicalLayer.renderAllModels();
        long endTime = (System.currentTimeMillis() - startTime);
        System.out.printf("Операция выполнена за %d мс. \n", endTime);
    }

    @Override
    public void renderModel(int i) {
        checkProjectFile();

        ArrayList<Model3D> model3DS = (ArrayList<Model3D>) businessLogicalLayer.getAllModels();
        if (i < 0 || i > model3DS.size() - 1)
            throw new RuntimeException("Номер модели указан некорректно");
        System.out.println("Подождите");
        long startTime = System.currentTimeMillis();
        businessLogicalLayer.renderModel(model3DS.get(i));
        long endTime = (System.currentTimeMillis() - startTime);
        System.out.printf("Операция выполнена за %d мс. \n", endTime);
    }
}

/**
 * Интерфейс UI
 */
interface UILayer {
    void openProject(String fileName);
    void showProjectSettings();
    void saveProject();
    void printAllModels();
    void printAllTextures();
    void renderAll();
    void renderModel(int i);
}

/**
 * Реализация Business Logical Layer
 */
class EditorBusinessLogicalLayer implements BusinessLogicalLayer {
    private Random random = new Random();
    private DatabaseAccess databaseAccess;

    public EditorBusinessLogicalLayer(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public Collection<Model3D> getAllModels() {
        return databaseAccess.getAllModels();
    }

    @Override
    public Collection<Texture> getAllTextures() {
        return databaseAccess.getAllTextures();
    }

    @Override
    public void renderModel(Model3D model3D) {
        processRender(model3D);
    }

    @Override
    public void renderAllModels() {
        for (Model3D model3D: getAllModels()) {
            processRender(model3D);
        }
    }

    private void processRender(Model3D model3D) {
        try {
            Thread.sleep(2500 - random.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Интерфейс BLL (Business Logical Layer)
 */
interface BusinessLogicalLayer {
    Collection<Model3D> getAllModels();
    Collection<Texture> getAllTextures();
    void renderModel(Model3D model3D);
    void renderAllModels();
}

/**
 * Реализация DAC
 */
class EditorDatabaseAccess implements DatabaseAccess {
    private final Database editorDatabase;

    public EditorDatabaseAccess(Database editorDatabase) {
        this.editorDatabase = editorDatabase;
    }

    @Override
    public void addEntity(Entity entity) {
        editorDatabase.getAll().add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        editorDatabase.getAll().remove(entity);
    }

    @Override
    public Collection<Texture> getAllTextures() {
        Collection<Texture> textures = new ArrayList<>();
        for (Entity entity: editorDatabase.getAll()) {
            if (entity instanceof Model3D) {
                textures.add((Texture) entity);
            }
        }
        return textures;
    }

    @Override
    public Collection<Model3D> getAllModels() {
        Collection<Model3D> model3DS = new ArrayList<>();
        for (Entity entity: editorDatabase.getAll()) {
            if (entity instanceof Model3D) {
                model3DS.add((Model3D)entity);
            }
        }
        return model3DS;
    }
}

/**
 * Интерфейс DAC
 */
interface DatabaseAccess {
    void addEntity(Entity entity);
    void removeEntity(Entity entity);
    Collection<Texture> getAllTextures();
    Collection<Model3D> getAllModels();
}

/**
 * Database
 */
class EditorDatabase implements Database {
    private Random random = new Random();
    private final ProjectFile projectFile;
    private Collection<Entity> entities;

    public EditorDatabase(ProjectFile projectFile) {
        this.projectFile = projectFile;
        load();
    }

    @Override
    public void load() {
        // TODO: Загрузка всех сущностей проекта (модели, текстуры и т.д.)
    }

    @Override
    public void save() {
        // TODO: Сохранение текущего состояния всех сущностей в проекте
    }

    public Collection<Entity> getAll() {
        if (entities == null) {
            entities = new ArrayList<>();
            int entCount = random.nextInt(5,11);
            for (int i = 0; i < entCount; i++) {

            }
        }
        return entities;
    }

    private void generateModelAndTextures() {
        Model3D model3D = new Model3D();
        int txCount = random.nextInt(3);
        for (int i = 0; i < txCount; i++) {
            Texture texture = new Texture();
            model3D.getTextures().add(texture);
            entities.add(texture);
        }
        entities.add(model3D);
    }
}

/**
 * Интерфейс БД
 */
interface Database {
    void load();

    void save();

    Collection<Entity> getAll();
}

/**
 * 3D Модель
 */
class Model3D implements Entity {
    private static int counter = 10000;
    private int id;
    private Collection<Texture> textures = new ArrayList<>();

    @Override
    public int getId() {
        return id;
    }

    {
        id = ++counter;
    }

    public Model3D() {
    }

    public Model3D(Collection<Texture> textures) {
        this.textures = textures;
    }

    public Collection<Texture> getTextures() {
        return textures;
    }

    @Override
    public String toString() {
        return String.format("Model #%s", id);
    }
}

/**
 * Текстура
 */
class Texture implements Entity {
    private static int counter = 50000;
    private int id;

    {
        id = ++counter;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Texture #%s", id);
    }
}

/**
 * Сущность
 */
interface Entity {
    int getId();
}

class ProjectFile {
    private String fileName;
    private int setting1;
    private String setting2;
    private boolean setting3;

    public ProjectFile(String fileName) {
        this.fileName = fileName;
        //TODO: Загрузка настроек проекта из файла
        setting1 = 1;
        setting2 = "...";
        setting3 = false;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSetting1() {
        return setting1;
    }

    public String getSetting2() {
        return setting2;
    }

    public boolean getSetting3() {
        return setting3;
    }
}