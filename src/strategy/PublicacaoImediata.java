package strategy;

import modelo.Conteudo;

/**
 * Estratégia de publicação imediata
 */
public class PublicacaoImediata implements EstrategiaPublicacao {
    
    @Override
    public Conteudo processarConteudo(Conteudo conteudo) {
        System.out.println("Processando para publicação imediata...");
        // Processa o conteúdo para publicação imediata
        return conteudo;
    }
    
    @Override
    public boolean validar(Conteudo conteudo) {
        System.out.println("Validando conteúdo para publicação imediata...");
        // Validações básicas
        return conteudo.getTexto() != null && !conteudo.getTexto().isEmpty();
    }
    
    @Override
    public String getDescricao() {
        return "Publicação Imediata";
    }
}
