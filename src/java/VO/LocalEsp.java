package VO;

public class LocalEsp {
    
   private int especialidadeCod;
   private String espNome;
   private String espInstituicao;

    public LocalEsp(int especialidadeCod, String espNome, String espInstituicao) {
        this.especialidadeCod = especialidadeCod;
        this.espNome = espNome;
        this.espInstituicao = espInstituicao;
    }

    public LocalEsp() {
    }

    public int getEspecialidadeCod() {
        return especialidadeCod;
    }

    public void setEspecialidadeCod(int especialidadeCod) {
        this.especialidadeCod = especialidadeCod;
    }

    public String getEspNome() {
        return espNome;
    }

    public void setEspNome(String espNome) {
        this.espNome = espNome;
    }

    public String getEspInstituicao() {
        return espInstituicao;
    }

    public void setEspInstituicao(String espInstituicao) {
        this.espInstituicao = espInstituicao;
    }

    @Override
    public String toString() {
        return "\n\nEspecialidade " + "\nEspecialidadeCod= " + especialidadeCod + "\nEspNome= " + espNome + "\nEspInstituicao= " + espInstituicao;
    }

   
   
}