package api;

import java.time.LocalDateTime;
import java.util.*;

/**
 * API simulada do Twitter (X)
 * Adaptee - possui interface própria incompatível com a interface unificada
 */
public class TwitterAPI {
    
    private String apiKey;
    private boolean conectado = false;
    private Map<String, TweetData> tweets = new HashMap<>();
    
    /**
     * Conecta à API do Twitter
     */
    public boolean conectar(String apiKey, String apiSecret) {
        System.out.println("Conectando à API do Twitter...");
        this.apiKey = apiKey;
        this.conectado = true;
        System.out.println("Conectado ao Twitter com sucesso!");
        return true;
    }
    
    /**
     * Cria um tweet
     */
    public TweetData criarTweet(String texto, List<String> midias, List<String> hashtags) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado à API do Twitter");
        }
        
        String tweetId = "tw_" + UUID.randomUUID().toString().substring(0, 8);
        TweetData tweet = new TweetData(tweetId, texto, midias, hashtags, LocalDateTime.now());
        tweets.put(tweetId, tweet);
        
        System.out.println("Tweet criado: " + tweetId);
        return tweet;
    }
    
    /**
     * Remove um tweet
     */
    public boolean removerTweet(String tweetId) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado à API do Twitter");
        }
        
        boolean removido = tweets.remove(tweetId) != null;
        if (removido) {
            System.out.println("🗑️ Tweet removido: " + tweetId);
        }
        return removido;
    }
    
    /**
     * Obtém métricas de um tweet
     */
    public TweetMetrics obterMetricas(String tweetId) {
        if (!conectado) {
            throw new IllegalStateException("Não conectado à API do Twitter");
        }
        
        // Simulação de métricas
        Random random = new Random();
        return new TweetMetrics(
            tweetId,
            random.nextInt(10000),  // impressions
            random.nextInt(500),    // likes
            random.nextInt(100),    // retweets
            random.nextInt(50)      // replies
        );
    }
    
    /**
     * Lista todos os tweets
     */
    public List<TweetData> listarTweets() {
        return new ArrayList<>(tweets.values());
    }
    
    /**
     * Verifica se está conectado
     */
    public boolean estaConectado() {
        return conectado;
    }
    
    // Classes internas para representar dados específicos do Twitter
    public static class TweetData {
        private String id;
        private String texto;
        private List<String> midias;
        private List<String> hashtags;
        private LocalDateTime dataCriacao;
        
        public TweetData(String id, String texto, List<String> midias, 
                        List<String> hashtags, LocalDateTime dataCriacao) {
            this.id = id;
            this.texto = texto;
            this.midias = midias;
            this.hashtags = hashtags;
            this.dataCriacao = dataCriacao;
        }
        
        // Getters
        public String getId() { return id; }
        public String getTexto() { return texto; }
        public List<String> getMidias() { return midias; }
        public List<String> getHashtags() { return hashtags; }
        public LocalDateTime getDataCriacao() { return dataCriacao; }
    }
    
    public static class TweetMetrics {
        private String tweetId;
        private int impressions;
        private int likes;
        private int retweets;
        private int replies;
        
        public TweetMetrics(String tweetId, int impressions, int likes, 
                           int retweets, int replies) {
            this.tweetId = tweetId;
            this.impressions = impressions;
            this.likes = likes;
            this.retweets = retweets;
            this.replies = replies;
        }
        
        // Getters
        public String getTweetId() { return tweetId; }
        public int getImpressions() { return impressions; }
        public int getLikes() { return likes; }
        public int getRetweets() { return retweets; }
        public int getReplies() { return replies; }
    }
}
