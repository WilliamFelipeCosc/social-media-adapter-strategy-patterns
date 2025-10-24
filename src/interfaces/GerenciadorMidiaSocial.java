package interfaces;

import java.util.List;
import modelo.Conteudo;
import modelo.Credenciais;
import modelo.Estatisticas;
import modelo.Publicacao;

/**
 * Interface unificada para gerenciamento de mídias sociais
 * Padrão Target do Adapter
 */
public interface GerenciadorMidiaSocial {
    
    /**
     * Autentica na plataforma de mídia social
     * @param credenciais Credenciais de autenticação
     * @return true se autenticação for bem-sucedida
     */
    boolean autenticar(Credenciais credenciais);
    
    /**
     * Publica conteúdo na plataforma
     * @param conteudo Conteúdo a ser publicado
     * @return Publicação criada
     */
    Publicacao publicar(Conteudo conteudo);
    
    /**
     * Agenda uma publicação futura
     * @param conteudo Conteúdo a ser agendado
     * @return Publicação agendada
     */
    Publicacao agendar(Conteudo conteudo);
    
    /**
     * Remove uma publicação
     * @param idPublicacao ID da publicação
     * @return true se removida com sucesso
     */
    boolean remover(String idPublicacao);
    
    /**
     * Obtém estatísticas de uma publicação
     * @param idPublicacao ID da publicação
     * @return Estatísticas da publicação
     */
    Estatisticas obterEstatisticas(String idPublicacao);
    
    /**
     * Lista todas as publicações
     * @return Lista de publicações
     */
    List<Publicacao> listarPublicacoes();
    
    /**
     * Obtém o nome da plataforma
     * @return Nome da plataforma
     */
    String getNomePlataforma();
    
    /**
     * Valida se o conteúdo é compatível com a plataforma
     * @param conteudo Conteúdo a ser validado
     * @return true se válido
     */
    boolean validarConteudo(Conteudo conteudo);
}
