package strategy;

import modelo.Credenciais;

/**
 * Interface Strategy para diferentes estratégias de autenticação
 */
public interface EstrategiaAutenticacao {
    
    /**
     * Autentica usando as credenciais fornecidas
     * @param credenciais Credenciais para autenticação
     * @return true se autenticação for bem-sucedida
     */
    boolean autenticar(Credenciais credenciais);
    
    /**
     * Renova o token de acesso
     * @param refreshToken Token de renovação
     * @return Novo token de acesso
     */
    String renovarToken(String refreshToken);
    
    /**
     * Verifica se está autenticado
     * @return true se autenticado
     */
    boolean estaAutenticado();
}
