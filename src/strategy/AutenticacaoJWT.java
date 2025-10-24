package strategy;

import modelo.Credenciais;

/**
 * Estratégia de autenticação via JWT
 */
public class AutenticacaoJWT implements EstrategiaAutenticacao {
    
    private boolean autenticado = false;
    private String jwtToken;
    
    @Override
    public boolean autenticar(Credenciais credenciais) {
        System.out.println("Autenticando via JWT...");
        System.out.println("Usuário: " + credenciais.getUsuario());
        
        // Simulação de autenticação JWT
        if (credenciais.getAccessToken() != null && !credenciais.getAccessToken().isEmpty()) {
            this.jwtToken = credenciais.getAccessToken();
            this.autenticado = true;
            System.out.println("Autenticação JWT bem-sucedida!");
            return true;
        }
        
        System.out.println("Falha na autenticação JWT");
        return false;
    }
    
    @Override
    public String renovarToken(String refreshToken) {
        System.out.println("Renovando JWT Token...");
        // Simulação de renovação de JWT
        String novoToken = "jwt_" + System.currentTimeMillis();
        this.jwtToken = novoToken;
        System.out.println("JWT Token renovado com sucesso!");
        return novoToken;
    }
    
    @Override
    public boolean estaAutenticado() {
        return autenticado && jwtToken != null;
    }
}
