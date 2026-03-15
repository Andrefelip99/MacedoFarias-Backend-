package com.example.confeitariaMacedoFarias.config;

public class SecurityConstants {

    // Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    // Permissões por endpoint
    // Abertos (público)
    public static final String PUBLIC = "permitAll()";

    // Usuário
    public static final String USER_ONLY = "hasRole('USER') or hasRole('ADMIN')";
    public static final String USER_READ = "hasRole('USER') or hasRole('ADMIN')";

    // Administrador
    public static final String ADMIN_ONLY = "hasRole('ADMIN')";

    // Combinado: Administrador ou dono do recurso
    public static final String ADMIN_OR_OWNER = "hasRole('ADMIN')";

    private SecurityConstants() {
    }
}
