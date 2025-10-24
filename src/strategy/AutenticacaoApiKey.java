package strategy;

import modelo.Credenciais;

/**
 * Estratégia de autenticação via API Key
 */
public class AutenticacaoApiKey implements EstrategiaAutenticacao {
    
    private boolean autenticado = false;
    private String apiKeyAtual;
    
    @Override
    public boolean autenticar(Credenciais credenciais) {
        System.out.println("Autenticando via API Key...");
        System.out.println("Usuário: " + credenciais.getUsuario());
        
        // Simulação de autenticação via API Key
        if (credenciais.getApiKey() != null && !credenciais.getApiKey().isEmpty()) {
            this.apiKeyAtual = credenciais.getApiKey();
            this.autenticado = true;
            System.out.println("Autenticação via API Key bem-sucedida!");
            return true;
        }
        
        System.out.println("Falha na autenticação via API Key");
        return false;
    }
    
    @Override
    public String renovarToken(String refreshToken) {
        System.out.println("API Key não requer renovação");
        return apiKeyAtual;
    }
    
    @Override
    public boolean estaAutenticado() {
        return autenticado && apiKeyAtual != null;
    }
}
