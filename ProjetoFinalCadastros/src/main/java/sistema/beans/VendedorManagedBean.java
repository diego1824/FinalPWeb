package sistema.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

import sistema.modelos.Vendedor;
import sistema.service.VendedorService;

@ManagedBean
@ViewScoped
public class VendedorManagedBean {

	private Vendedor vendedor = new Vendedor();
	private List<Vendedor> vendedores;
	private VendedorService service = new VendedorService();

	// Edição de um vendedor na tabela
	public void onRowEdit(RowEditEvent event) {

		Vendedor a = ((Vendedor) event.getObject());
		service.alterar(a);
	}

	public void salvar() {
		vendedor = service.salvar(vendedor);

		if (vendedores != null)
			vendedores.add(vendedor);

		vendedor = new Vendedor();

	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	// Retorna a lista de vendedores para a tabela
	public List<Vendedor> getVendedores() {
		if (vendedores == null)
			vendedores = service.getVendedores();

		return vendedores;
	}

	public void remover(Vendedor vendedor) {
		service.remover(vendedor);
		vendedores.remove(vendedor);

	}

}
