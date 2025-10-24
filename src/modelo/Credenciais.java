package modelo;

/**
 * Classe que representa credenciais de autenticação
 */
public class Credenciais {
    private String usuario;
    private String apiKey;
    private String apiSecret;
    private String accessToken;
    private String refreshToken;

    public Credenciais(String usuario, String apiKey, String apiSecret, 
                      String accessToken, String refreshToken) {
        this.usuario = usuario;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters e Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Credenciais{" +
                "usuario='" + usuario + '\'' +
                ", apiKey='***" + '\'' +
                ", apiSecret='***" + '\'' +
                ", accessToken='***" + '\'' +
                '}';
    }
}
