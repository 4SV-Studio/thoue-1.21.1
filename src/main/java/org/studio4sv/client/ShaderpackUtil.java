package org.studio4sv.client;

import java.lang.reflect.Method;

public final class ShaderpackUtil {

    private static final long CACHE_TTL_NANOS = 500_000_000L;

    private static final String[] IRIS_API_CLASSES = {
            "net.irisshaders.iris.api.v0.IrisApi",
            "net.irisshaders.iris.api.IrisApi",
            "net.coderbot.iris.api.v0.IrisApi",
            "net.coderbot.iris.api.IrisApi",
    };

    private static Method irisShaderPackInUse;
    private static Object irisApiInstance;

    private static long lastCheckNanos;
    private static boolean cachedActive;

    private ShaderpackUtil() {
    }

    public static boolean isShaderpackActive() {
        long now = System.nanoTime();
        if (now - lastCheckNanos > CACHE_TTL_NANOS) {
            cachedActive = isIrisShaderPackInUse() || isOptifineShadersActive();
            lastCheckNanos = now;
        }
        return cachedActive;
    }

    private static boolean isIrisShaderPackInUse() {
        if (irisApiInstance == null) {
            resolveIrisApi();
        }
        if (irisShaderPackInUse == null) {
            return false;
        }
        try {
            return Boolean.TRUE.equals(irisShaderPackInUse.invoke(irisApiInstance));
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static void resolveIrisApi() {
        for (String className : IRIS_API_CLASSES) {
            try {
                Class<?> apiClass = Class.forName(className);
                Method getInstance = apiClass.getMethod("getInstance");
                Object instance = getInstance.invoke(null);
                if (instance == null) {
                    continue;
                }
                irisApiInstance = instance;
                irisShaderPackInUse = apiClass.getMethod("isShaderPackInUse");
                return;
            } catch (Throwable ignored) {
                // try next candidate
            }
        }
    }

    private static boolean isOptifineShadersActive() {
        try {
            Class<?> shadersClass = Class.forName("net.optifine.shaders.Shaders");
            for (String methodName : new String[]{"isShadersActive", "isActive"}) {
                try {
                    Method method = shadersClass.getMethod(methodName);
                    if (Boolean.TRUE.equals(method.invoke(null))) {
                        return true;
                    }
                } catch (NoSuchMethodException ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            Class<?> configClass = Class.forName("net.optifine.Config");
            return Boolean.TRUE.equals(configClass.getMethod("isShaders").invoke(null));
        } catch (Throwable ignored) {
            return false;
        }
    }
}