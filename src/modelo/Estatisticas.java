package modelo;

/**
 * Classe que representa as estatísticas de uma publicação
 */
public class Estatisticas {
    private String idPublicacao;
    private int visualizacoes;
    private int curtidas;
    private int compartilhamentos;
    private int comentarios;
    private double taxaEngajamento;

    public Estatisticas(String idPublicacao, int visualizacoes, int curtidas, 
                       int compartilhamentos, int comentarios) {
        this.idPublicacao = idPublicacao;
        this.visualizacoes = visualizacoes;
        this.curtidas = curtidas;
        this.compartilhamentos = compartilhamentos;
        this.comentarios = comentarios;
        this.taxaEngajamento = calcularTaxaEngajamento();
    }

    private double calcularTaxaEngajamento() {
        if (visualizacoes == 0) return 0.0;
        int totalInteracoes = curtidas + compartilhamentos + comentarios;
        return (totalInteracoes * 100.0) / visualizacoes;
    }

    // Getters e Setters
    public String getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(String idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
        this.taxaEngajamento = calcularTaxaEngajamento();
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
        this.taxaEngajamento = calcularTaxaEngajamento();
    }

    public int getCompartilhamentos() {
        return compartilhamentos;
    }

    public void setCompartilhamentos(int compartilhamentos) {
        this.compartilhamentos = compartilhamentos;
        this.taxaEngajamento = calcularTaxaEngajamento();
    }

    public int getComentarios() {
        return comentarios;
    }

    public void setComentarios(int comentarios) {
        this.comentarios = comentarios;
        this.taxaEngajamento = calcularTaxaEngajamento();
    }

    public double getTaxaEngajamento() {
        return taxaEngajamento;
    }

    @Override
    public String toString() {
        return String.format("Estatisticas{" +
                "idPublicacao='%s', " +
                "visualizacoes=%d, " +
                "curtidas=%d, " +
                "compartilhamentos=%d, " +
                "comentarios=%d, " +
                "taxaEngajamento=%.2f%%}",
                idPublicacao, visualizacoes, curtidas, compartilhamentos, 
                comentarios, taxaEngajamento);
    }
}
