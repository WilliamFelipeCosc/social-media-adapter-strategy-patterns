package adapter;

import api.LinkedInAPI;
import interfaces.GerenciadorMidiaSocial;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import strategy.EstrategiaAutenticacao;
import strategy.EstrategiaPublicacao;

/**
 * Adapter para a API do LinkedIn
 * Adapta a interface específica do LinkedIn para a interface unificada
 */
public class LinkedInAdapter implements GerenciadorMidiaSocial {
    
    private final LinkedInAPI linkedInAPI;
    private final EstrategiaAutenticacao estrategiaAuth;
    private final EstrategiaPublicacao estrategiaPublicacao;
    
    public LinkedInAdapter(EstrategiaAutenticacao estrategiaAuth,
                          EstrategiaPublicacao estrategiaPublicacao) {
        this.linkedInAPI = new LinkedInAPI();
        this.estrategiaAuth = estrategiaAuth;
        this.estrategiaPublicacao = estrategiaPublicacao;
    }
    
    @Override
    public boolean autenticar(Credenciais credenciais) {
        System.out.println("\n=== LINKEDIN ADAPTER - Autenticação ===");
        
        // Usa a estratégia de autenticação
        boolean authSuccess = estrategiaAuth.autenticar(credenciais);
        
        if (authSuccess) {
            // Adapta para a API específica do LinkedIn
            return linkedInAPI.estabelecerConexao(
                credenciais.getApiKey(),
                credenciais.getApiSecret(),
                credenciais.getAccessToken()
            );
        }
        return false;
    }
    
    @Override
    public Publicacao publicar(Conteudo conteudo) {
        System.out.println("\n=== LINKEDIN ADAPTER - Publicação ===");
        
        if (!linkedInAPI.estaConectado()) {
            throw new IllegalStateException("Não conectado ao LinkedIn");
        }
        
        // Usa a estratégia de publicação
        if (!estrategiaPublicacao.validar(conteudo)) {
            throw new IllegalArgumentException("Conteúdo inválido para publicação");
        }
        
        Conteudo processado = estrategiaPublicacao.processarConteudo(conteudo);
        
        // Adapta o conteúdo unificado para o formato do LinkedIn
        LinkedInAPI.ShareData share;
        
        if (conteudo.getTipo() == TipoConteudo.ARTIGO) {
            // Publica como artigo
            share = linkedInAPI.publicarArtigo(
                "Artigo",
                processado.getTexto(),
                processado.getMidias() != null && !processado.getMidias().isEmpty() 
                    ? processado.getMidias().get(0) : ""
            );
        } else {
            // Publica como compartilhamento normal
            String imagemUrl = processado.getMidias() != null && !processado.getMidias().isEmpty() 
                ? processado.getMidias().get(0) : "";
            share = linkedInAPI.compartilharConteudo(
                processado.getTexto(),
                "",
                imagemUrl
            );
        }
        
        // Converte o resultado do LinkedIn para o formato unificado
        return new Publicacao(
            share.getId(),
            "LinkedIn",
            conteudo,
            share.getDataPublicacao(),
            StatusPublicacao.PUBLICADA,
            "https://linkedin.com/posts/" + share.getId()
        );
    }
    
    @Override
    public Publicacao agendar(Conteudo conteudo) {
        System.out.println("\n=== LINKEDIN ADAPTER - Agendamento ===");
        System.out.println("Agendando post para: " + conteudo.getDataAgendamento());
        
        // Simula agendamento
        return new Publicacao(
            "scheduled_li_" + System.currentTimeMillis(),
            "LinkedIn",
            conteudo,
            conteudo.getDataAgendamento(),
            StatusPublicacao.AGENDADA,
            "https://linkedin.com/scheduled"
        );
    }
    
    @Override
    public boolean remover(String idPublicacao) {
        System.out.println("\n=== LINKEDIN ADAPTER - Remoção ===");
        return linkedInAPI.removerShare(idPublicacao);
    }
    
    @Override
    public Estatisticas obterEstatisticas(String idPublicacao) {
        System.out.println("\n=== LINKEDIN ADAPTER - Estatísticas ===");
        
        // Adapta os analytics do LinkedIn para o formato unificado
        LinkedInAPI.ShareAnalytics analytics = linkedInAPI.obterAnalytics(idPublicacao);
        
        return new Estatisticas(
            analytics.getShareId(),
            analytics.getViews(),
            analytics.getReactions(),
            analytics.getShares(),
            analytics.getComments()
        );
    }
    
    @Override
    public List<Publicacao> listarPublicacoes() {
        System.out.println("\n=== LINKEDIN ADAPTER - Listagem ===");
        
        List<Publicacao> publicacoes = new ArrayList<>();
        List<LinkedInAPI.ShareData> shares = linkedInAPI.listarShares();
        
        for (LinkedInAPI.ShareData share : shares) {
            List<String> midias = new ArrayList<>();
            if (share.getImagemUrl() != null && !share.getImagemUrl().isEmpty()) {
                midias.add(share.getImagemUrl());
            }
            
            TipoConteudo tipo = share.getTipo().equals("ARTICLE") 
                ? TipoConteudo.ARTIGO : TipoConteudo.TEXTO;
            
            Conteudo conteudo = new Conteudo(
                share.getTexto(),
                midias,
                new ArrayList<>(),
                share.getDataPublicacao(),
                tipo
            );
            
            publicacoes.add(new Publicacao(
                share.getId(),
                "LinkedIn",
                conteudo,
                share.getDataPublicacao(),
                StatusPublicacao.PUBLICADA,
                "https://linkedin.com/posts/" + share.getId()
            ));
        }
        
        return publicacoes;
    }
    
    @Override
    public String getNomePlataforma() {
        return "LinkedIn";
    }
    
    @Override
    public boolean validarConteudo(Conteudo conteudo) {
        // LinkedIn tem limite de 3000 caracteres para posts
        if (conteudo.getTexto() == null || conteudo.getTexto().isEmpty()) {
            System.out.println("Texto não pode estar vazio no LinkedIn");
            return false;
        }
        
        if (conteudo.getTexto().length() > 3000) {
            System.out.println("Texto muito longo para LinkedIn (máx 3000 caracteres)");
            return false;
        }
        
        // Conteúdo profissional - evitar muitas hashtags
        if (conteudo.getHashtags() != null && conteudo.getHashtags().size() > 5) {
            System.out.println("Recomendado máximo de 5 hashtags no LinkedIn");
        }
        
        System.out.println("Conteúdo válido para LinkedIn");
        return true;
    }
}
