package modelo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe que representa o conteúdo a ser publicado nas redes sociais
 */
public class Conteudo {
    private String texto;
    private List<String> midias;
    private List<String> hashtags;
    private LocalDateTime dataAgendamento;
    private TipoConteudo tipo;

    public Conteudo(String texto, List<String> midias, List<String> hashtags, 
                    LocalDateTime dataAgendamento, TipoConteudo tipo) {
        this.texto = texto;
        this.midias = midias;
        this.hashtags = hashtags;
        this.dataAgendamento = dataAgendamento;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<String> getMidias() {
        return midias;
    }

    public void setMidias(List<String> midias) {
        this.midias = midias;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public TipoConteudo getTipo() {
        return tipo;
    }

    public void setTipo(TipoConteudo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Conteudo{" +
                "texto='" + texto + '\'' +
                ", midias=" + midias +
                ", hashtags=" + hashtags +
                ", dataAgendamento=" + dataAgendamento +
                ", tipo=" + tipo +
                '}';
    }
}
