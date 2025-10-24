package modelo;

import java.time.LocalDateTime;

/**
 * Classe que representa uma publicação realizada
 */
public class Publicacao {
    private String id;
    private String plataforma;
    private Conteudo conteudo;
    private LocalDateTime dataPublicacao;
    private StatusPublicacao status;
    private String url;

    public Publicacao(String id, String plataforma, Conteudo conteudo, 
                      LocalDateTime dataPublicacao, StatusPublicacao status, String url) {
        this.id = id;
        this.plataforma = plataforma;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.status = status;
        this.url = url;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }

    public void setConteudo(Conteudo conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusPublicacao getStatus() {
        return status;
    }

    public void setStatus(StatusPublicacao status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Publicacao{" +
                "id='" + id + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", status=" + status +
                ", url='" + url + '\'' +
                '}';
    }
}
