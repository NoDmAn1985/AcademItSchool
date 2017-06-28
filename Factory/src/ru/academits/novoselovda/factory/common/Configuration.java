package ru.academits.novoselovda.factory.common;

public enum Configuration {
    BODY_PROVIDERS_COUNT ("BodyProvidersCount", 2),
    ENGINE_PROVIDERS_COUNT ("EngineProvidersCount", 2),
    ACCESSORIES_PROVIDERS_COUNT ("AccessoriesProvidersCount", 1),
    PRODUCERS_COUNT ("ProducersCount", 3),
    DEALERS_COUNT ("DealersCount", 2),
    CONTROLLERS_COUNT ("ControllersCount", 1),

    BODY_STORAGE_SIZE ("BodyStorageSize", 30),
    ENGINE_STORAGE_SIZE ("EngineStorageSize", 30),
    ACCESSORIES_STORAGE_SIZE ("AccessoriesStorageSize", 30),
    CAR_STORAGE_SIZE ("CarStorageSize", 10),

    BODY_PROVIDERS_SPEED ("BodyProvidersSpeed", 3),
    ENGINE_PROVIDERS_SPEED ("EngineProvidersSpeed", 4),
    ACCESSORIES_PROVIDERS_SPEED ("AccessoriesProvidersSpeed", 5),
    PRODUCERS_SPEED ("ProducersSpeed", 5),
    DEALERS_SPEED ("DealersSpeed", 3),
    CONTROLLERS_SPEED ("ControllersSpeed", 8),

    TARGET_VALUE ("TargetValue", 30),

    LOG ("Log", 1);

    private String name;
    private int defaultValue;

    Configuration(String name, int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public int getDefaultValue() {
        return defaultValue;
    }
}
