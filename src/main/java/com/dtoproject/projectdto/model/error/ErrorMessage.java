package com.dtoproject.projectdto.model.error;

public class ErrorMessage {
  private String titulo;

  private Integer status;

  private String mensagem;

  public String getTitulo() {
    return titulo;
  }
  
  public ErrorMessage(String titulo, Integer status, String mensagem) {
    this.titulo = titulo;
    this.status = status;
    this.mensagem = mensagem;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

}
