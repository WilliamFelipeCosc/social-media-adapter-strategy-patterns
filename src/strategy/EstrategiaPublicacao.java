package strategy;

import modelo.Conteudo;

/**
 * Interface Strategy para diferentes estratégias de publicação
 */
public interface EstrategiaPublicacao {
    
    /**
     * Processa o conteúdo antes de publicar
     * @param conteudo Conteúdo a ser processado
     * @return Conteúdo processado
     */
    Conteudo processarConteudo(Conteudo conteudo);
    
    /**
     * Valida o conteúdo para a plataforma
     * @param conteudo Conteúdo a ser validado
     * @return true se válido
     */
    boolean validar(Conteudo conteudo);
    
    /**
     * Obtém descrição da estratégia
     * @return Descrição
     */
    String getDescricao();
}
