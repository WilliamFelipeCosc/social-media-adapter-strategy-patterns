package adapter;

import api.TwitterAPI;
import interfaces.GerenciadorMidiaSocial;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import strategy.EstrategiaAutenticacao;
import strategy.EstrategiaPublicacao;

/**
 * Adapter para a API do Twitter
 * Adapta a interface específica do Twitter para a interface unificada
 */
public class TwitterAdapter implements GerenciadorMidiaSocial {
    
    private final TwitterAPI twitterAPI;
    private final EstrategiaAutenticacao estrategiaAuth;
    private final EstrategiaPublicacao estrategiaPublicacao;
    
    public TwitterAdapter(EstrategiaAutenticacao estrategiaAuth, 
                         EstrategiaPublicacao estrategiaPublicacao) {
        this.twitterAPI = new TwitterAPI();
        this.estrategiaAuth = estrategiaAuth;
        this.estrategiaPublicacao = estrategiaPublicacao;
    }
    
    @Override
    public boolean autenticar(Credenciais credenciais) {
        System.out.println("\n=== TWITTER ADAPTER - Autenticação ===");
        
        // Usa a estratégia de autenticação
        boolean authSuccess = estrategiaAuth.autenticar(credenciais);
        
        if (authSuccess) {
            // Adapta para a API específica do Twitter
            return twitterAPI.conectar(credenciais.getApiKey(), credenciais.getApiSecret());
        }
        return false;
    }
    
    @Override
    public Publicacao publicar(Conteudo conteudo) {
        System.out.println("\n=== TWITTER ADAPTER - Publicação ===");
        
        if (!twitterAPI.estaConectado()) {
            throw new IllegalStateException("Não autenticado no Twitter");
        }
        
        // Usa a estratégia de publicação
        if (!estrategiaPublicacao.validar(conteudo)) {
            throw new IllegalArgumentException("Conteúdo inválido para publicação");
        }
        
        Conteudo processado = estrategiaPublicacao.processarConteudo(conteudo);
        
        // Adapta o conteúdo unificado para o formato do Twitter
        TwitterAPI.TweetData tweet = twitterAPI.criarTweet(
            processado.getTexto(),
            processado.getMidias(),
            processado.getHashtags()
        );
        
        // Converte o resultado do Twitter para o formato unificado
        return new Publicacao(
            tweet.getId(),
            "Twitter",
            conteudo,
            tweet.getDataCriacao(),
            StatusPublicacao.PUBLICADA,
            "https://twitter.com/post/" + tweet.getId()
        );
    }
    
    @Override
    public Publicacao agendar(Conteudo conteudo) {
        System.out.println("\n=== TWITTER ADAPTER - Agendamento ===");
        System.out.println("Agendando tweet para: " + conteudo.getDataAgendamento());
        
        // Simula agendamento (em produção, usaria scheduler real)
        return new Publicacao(
            "scheduled_tw_" + System.currentTimeMillis(),
            "Twitter",
            conteudo,
            conteudo.getDataAgendamento(),
            StatusPublicacao.AGENDADA,
            "https://twitter.com/scheduled"
        );
    }
    
    @Override
    public boolean remover(String idPublicacao) {
        System.out.println("\n=== TWITTER ADAPTER - Remoção ===");
        return twitterAPI.removerTweet(idPublicacao);
    }
    
    @Override
    public Estatisticas obterEstatisticas(String idPublicacao) {
        System.out.println("\n=== TWITTER ADAPTER - Estatísticas ===");
        
        // Adapta as métricas do Twitter para o formato unificado
        TwitterAPI.TweetMetrics metrics = twitterAPI.obterMetricas(idPublicacao);
        
        return new Estatisticas(
            metrics.getTweetId(),
            metrics.getImpressions(),
            metrics.getLikes(),
            metrics.getRetweets(),
            metrics.getReplies()
        );
    }
    
    @Override
    public List<Publicacao> listarPublicacoes() {
        System.out.println("\n=== TWITTER ADAPTER - Listagem ===");
        
        List<Publicacao> publicacoes = new ArrayList<>();
        List<TwitterAPI.TweetData> tweets = twitterAPI.listarTweets();
        
        for (TwitterAPI.TweetData tweet : tweets) {
            Conteudo conteudo = new Conteudo(
                tweet.getTexto(),
                tweet.getMidias(),
                tweet.getHashtags(),
                tweet.getDataCriacao(),
                TipoConteudo.TEXTO
            );
            
            publicacoes.add(new Publicacao(
                tweet.getId(),
                "Twitter",
                conteudo,
                tweet.getDataCriacao(),
                StatusPublicacao.PUBLICADA,
                "https://twitter.com/post/" + tweet.getId()
            ));
        }
        
        return publicacoes;
    }
    
    @Override
    public String getNomePlataforma() {
        return "Twitter";
    }
    
    @Override
    public boolean validarConteudo(Conteudo conteudo) {
        // Twitter tem limite de 280 caracteres
        if (conteudo.getTexto() == null || conteudo.getTexto().length() > 280) {
            System.out.println("Texto inválido para Twitter (máx 280 caracteres)");
            return false;
        }
        
        // Máximo de 4 imagens por tweet
        if (conteudo.getMidias() != null && conteudo.getMidias().size() > 4) {
            System.out.println("Máximo de 4 imagens por tweet");
            return false;
        }
        
        System.out.println("Conteúdo válido para Twitter");
        return true;
    }
}
