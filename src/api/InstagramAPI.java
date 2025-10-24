package api;

import java.time.LocalDateTime;
import java.util.*;

/**
 * API simulada do Instagram
 * Adaptee - possui interface própria incompatível com a interface unificada
 */
public class InstagramAPI {
    
    private String accessToken;
    private boolean autenticado = false;
    private Map<String, PostData> posts = new HashMap<>();
    
    /**
     * Autentica na API do Instagram
     */
    public boolean autenticar(String username, String accessToken) {
        System.out.println("Autenticando no Instagram...");
        this.accessToken = accessToken;
        this.autenticado = true;
        System.out.println("Autenticado no Instagram: @" + username);
        return true;
    }
    
    /**
     * Publica uma foto/vídeo
     */
    public PostData publicarMidia(String caption, String mediaUrl, List<String> tags) {
        if (!autenticado) {
            throw new IllegalStateException("Não autenticado no Instagram");
        }
        
        String postId = "ig_" + UUID.randomUUID().toString().substring(0, 8);
        PostData post = new PostData(postId, caption, mediaUrl, tags, 
                                     LocalDateTime.now(), "POST");
        posts.put(postId, post);
        
        System.out.println("Post publicado no Instagram: " + postId);
        return post;
    }
    
    /**
     * Publica um Story
     */
    public PostData publicarStory(String mediaUrl, String sticker) {
        if (!autenticado) {
            throw new IllegalStateException("Não autenticado no Instagram");
        }
        
        String postId = "story_" + UUID.randomUUID().toString().substring(0, 8);
        PostData post = new PostData(postId, "", mediaUrl, new ArrayList<>(), 
                                     LocalDateTime.now(), "STORY");
        posts.put(postId, post);
        
        System.out.println("Story publicado no Instagram: " + postId);
        return post;
    }
    
    /**
     * Remove um post
     */
    public boolean deletarPost(String postId) {
        if (!autenticado) {
            throw new IllegalStateException("Não autenticado no Instagram");
        }
        
        boolean removido = posts.remove(postId) != null;
        if (removido) {
            System.out.println("Post deletado: " + postId);
        }
        return removido;
    }
    
    /**
     * Obtém insights de um post
     */
    public PostInsights obterInsights(String postId) {
        if (!autenticado) {
            throw new IllegalStateException("Não autenticado no Instagram");
        }
        
        // Simulação de insights
        Random random = new Random();
        return new PostInsights(
            postId,
            random.nextInt(5000),   // reach
            random.nextInt(1000),   // likes
            random.nextInt(200),    // comments
            random.nextInt(150)     // saves
        );
    }
    
    /**
     * Lista todos os posts
     */
    public List<PostData> listarPosts() {
        return new ArrayList<>(posts.values());
    }
    
    /**
     * Verifica se está autenticado
     */
    public boolean estaAutenticado() {
        return autenticado;
    }
    
    // Classes internas para representar dados específicos do Instagram
    public static class PostData {
        private String id;
        private String caption;
        private String mediaUrl;
        private List<String> tags;
        private LocalDateTime timestamp;
        private String tipo;
        
        public PostData(String id, String caption, String mediaUrl, List<String> tags,
                       LocalDateTime timestamp, String tipo) {
            this.id = id;
            this.caption = caption;
            this.mediaUrl = mediaUrl;
            this.tags = tags;
            this.timestamp = timestamp;
            this.tipo = tipo;
        }
        
        // Getters
        public String getId() { return id; }
        public String getCaption() { return caption; }
        public String getMediaUrl() { return mediaUrl; }
        public List<String> getTags() { return tags; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getTipo() { return tipo; }
    }
    
    public static class PostInsights {
        private String postId;
        private int reach;
        private int likes;
        private int comments;
        private int saves;
        
        public PostInsights(String postId, int reach, int likes, int comments, int saves) {
            this.postId = postId;
            this.reach = reach;
            this.likes = likes;
            this.comments = comments;
            this.saves = saves;
        }
        
        // Getters
        public String getPostId() { return postId; }
        public int getReach() { return reach; }
        public int getLikes() { return likes; }
        public int getComments() { return comments; }
        public int getSaves() { return saves; }
    }
}
