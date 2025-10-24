package strategy;

import java.time.LocalDateTime;
import modelo.Conteudo;

/**
 * Estratégia de publicação agendada
 */
public class PublicacaoAgendada implements EstrategiaPublicacao {
    
    @Override
    public Conteudo processarConteudo(Conteudo conteudo) {
        System.out.println("Processando para publicação agendada...");
        System.out.println("Data: " + conteudo.getDataAgendamento());
        // Processa o conteúdo para publicação agendada
        return conteudo;
    }
    
    @Override
    public boolean validar(Conteudo conteudo) {
        System.out.println("Validando conteúdo para publicação agendada...");
        // Validações para agendamento
        if (conteudo.getDataAgendamento() == null) {
            System.out.println("Data de agendamento não especificada");
            return false;
        }
        
        if (conteudo.getDataAgendamento().isBefore(LocalDateTime.now())) {
            System.out.println("Data de agendamento está no passado");
            return false;
        }
        
        return conteudo.getTexto() != null && !conteudo.getTexto().isEmpty();
    }
    
    @Override
    public String getDescricao() {
        return "Publicação Agendada";
    }
}
