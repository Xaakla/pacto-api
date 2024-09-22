package com.app.pactoapi;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Routes {

    public static final String PATH_FIELD = "path";
    public static final String AUTHZ_FIELD = "authz";
    public static final String root = "/api";

    public static final class Public {

        public static final class Auth {
            public static final String root = Routes.root + "/auth";

            public static final class Register {
                public static final String path = Auth.root + "/signup";
            }

            public static final class Login {
                public static final String path = Auth.root + "/signin";
            }

            public static final class Logout {
                public static final String path = Auth.root + "/logout";
            }
        }
    }

    public static final class Admin {
        public static final String root = Routes.root + "/admin";

        public static final class Sales {
            public static final String path = Routes.root + "/sales";
            public static final Map<HttpMethod, List<String>> authz = Map.of(
                    HttpMethod.POST, List.of(),
                    HttpMethod.GET, List.of(),
                    HttpMethod.DELETE, List.of());

            public static final class ById {
                public static final String path = Sales.path + "/{id}";
                public static final Map<HttpMethod, List<String>> authz = Map.of(
                        HttpMethod.GET, List.of());

                public static final class Full {
                    public static final String path = ById.path + "/full";
                    public static final Map<HttpMethod, List<String>> authz = Map.of(HttpMethod.GET, List.of());
                }
            }
        }

        public static final class Payment {
            public static final String path = Routes.root + "/payment";
            public static final Map<HttpMethod, List<String>> authz = Map.of(
                    HttpMethod.POST, List.of(),
                    HttpMethod.GET, List.of());

            public static final class ById {
                public static final String path = Payment.path + "/{id}";
                public static final Map<HttpMethod, List<String>> authz = Map.of(
                        HttpMethod.PUT, List.of());
            }
        }
    }

    public static List<String> routeNames(Class<?> clazz) {
        return routeNames(clazz, new ArrayList<>());
    }

    private static List<String> routeNames(Class<?> clazz, List<String> list) {

        var nestedClasses = Arrays.asList(clazz.getDeclaredClasses());
        var fields = Arrays.asList(clazz.getDeclaredFields());

        fields.forEach(field -> {
            try {
                if (field.getName().equals(PATH_FIELD))
                    list.add(field.get(null).toString());

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        if (nestedClasses.isEmpty()) return list;

        nestedClasses.forEach(it -> routeNames(it, list));
        return list;
    }

    public static List<AuthEndpoint> all(Class<?> clazz) {
        return all(clazz, new ArrayList<>());
    }

    private static List<AuthEndpoint> all(Class<?> clazz, List<AuthEndpoint> list) {

        var nestedClasses = Arrays.asList(clazz.getDeclaredClasses());
        var fields = Arrays.asList(clazz.getDeclaredFields());
        var path = new AtomicReference<>("");
        var authz = new AtomicReference<>(Map.<HttpMethod, List<String>>of());

        fields.forEach(field -> {
            try {
                if (field.getName().equals(PATH_FIELD))
                    path.set(field.get(null).toString());

                if (field.getName().equals(AUTHZ_FIELD))
                    authz.set((Map<HttpMethod, List<String>>) field.get(null));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        if (!path.get().isBlank()) {
            list.add(new AuthEndpoint(path.get(), authz.get()));
        }
        nestedClasses.forEach(it -> all(it, list));
        return list;
    }

    public static class AuthEndpoint {

        public final String path;
        public final Map<HttpMethod, List<String>> authz;

        public AuthEndpoint(String path, Map<HttpMethod, List<String>> authz) {
            this.path = path;
            this.authz = authz;
        }
    }
}
