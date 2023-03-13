package sd.ufpi.application.domain.form;

import java.util.List;

public class MensagemReencaminhadaForm {
    private List<Long> idsReceptores;

    public MensagemReencaminhadaForm(List<Long> idsReceptores){
        this.idsReceptores = idsReceptores;
    }

    public List<Long> getReceptores(){
        return this.idsReceptores;
    }

	public List<Long> getIdsReceptores() {
		return idsReceptores;
	}

	public void setIdsReceptores(List<Long> idsReceptores) {
		this.idsReceptores = idsReceptores;
	}
    
    
}
