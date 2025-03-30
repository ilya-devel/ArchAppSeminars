package task2;

import java.util.ArrayList;

/**
 * Информация о детали
 */
public class ComponentInfo {
    private int id;
    private String description;
    public int getId() {return id;}
    public String getDescription() {return description;}

    public ComponentInfo(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("#%d = %s", id, description);
    }
}

/**
 * Завод по производству деталей
 */
class FactoryProvider {
    private ArrayList<ComponentInfo> components = new ArrayList<>();
    {
        for (int i = 0; i < 10; i++) {
            components.add(new ComponentInfo(900000 + i, String.format("component description %d", 900000 + i)));
        }
        for (int i = 0; i < 5; i++) {
            components.add(new ComponentInfo(1000 + i, String.format("component description %d", 1000 + i)));
        }
    }

    /**
     * Получить информацию о детали по её идентификатору
     * @param id Идентификатор детали
     * @throws RuntimeException исключения при обработки операции поиска
     * @return Информация о детали
     */
    public ComponentInfo getComponentInfo(int id) {
        if (id < 0) throw new RuntimeException("Некорректный номер детали");
        if (String.valueOf(id).length() < 6) throw new RuntimeException("Некорректный номер детали. Она существует, но устарела и более не производится");

        // Выполнение основного кода подпрограммы
        ComponentInfo searchComponent = null;

        for (ComponentInfo componentInfo: components) {
            if (componentInfo.getId() == id) {
                searchComponent = componentInfo;
                break;
            }
        }
        // Постусловие
        if (searchComponent == null)
            throw new RuntimeException("Деталь не найдена");

        return searchComponent;
    }
}

/**
 * Диллер
 */
class DealerProvider {
    private final FactoryProvider factoryProvider;

    public DealerProvider(FactoryProvider factoryProvider) {
        this.factoryProvider = factoryProvider;
    }

    public ComponentInfo getComponent(int id) {
        // Предусловия ...

//        if (id < 0 || String.valueOf(id).length() < 6) {
//            throw new RuntimeException("Некорректный номер детали");
//        }

        // Выполнение основного кода подпрограммы

        ComponentInfo componentInfo = factoryProvider.getComponentInfo(id);


        // Постусловия...
        if (componentInfo == null) throw new RuntimeException("Деталь не найдена");

//        return factoryProvider.getComponentInfo(id);
        return  componentInfo;
    }
}