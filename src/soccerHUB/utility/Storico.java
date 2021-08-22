package soccerHUB.utility;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Storico {

  int id;
  String nome, classe;
  String campo;
  LocalDate data;
  String ora;
  BigDecimal cellulare;
  String mail;

  public Storico(
      int id,
      String nome,
      String classe,
      String campo,
      LocalDate data,
      String ora,
      BigDecimal cellulare,
      String mail) {
    this.id = id;
    this.nome = nome;
    this.classe = classe;
    this.campo = campo;
    this.data = data;
    this.ora = ora;
    this.cellulare = cellulare;
    this.mail = mail;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getClasse() {
    return classe;
  }

  public void setClasse(String classe) {
    this.classe = classe;
  }

  public String getCampo() {
    return campo;
  }

  public void setCampo(String campo) {
    this.campo = campo;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getOra() {
    return ora;
  }

  public void setOra(String ora) {
    this.ora = ora;
  }

  public BigDecimal getCellulare() {
    return cellulare;
  }

  public void setCellulare(BigDecimal cellulare) {
    this.cellulare = cellulare;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }
}
