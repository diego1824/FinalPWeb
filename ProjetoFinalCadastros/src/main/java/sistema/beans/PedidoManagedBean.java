package sistema.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import org.primefaces.event.RowEditEvent;
import sistema.beans.datamodel.PedidoDataModel;
import sistema.modelos.Pedido;
import sistema.modelos.Produto;
import sistema.service.PedidoService;

@ManagedBean
@ViewScoped
public class PedidoManagedBean {

	private Pedido pedido = new Pedido();
	private Pedido pedidoSelecionado;
	private PedidoService servico = new PedidoService();
	private List<Pedido> pedidos;

	/**
	 * @return the pedidoSelecionado
	 */
	public Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	/**
	 * @param pedidoSelecionado
	 *            the pedidoSelecionado to set
	 */
	public void setPedidoSelecionado(Pedido pedidoSelecionado) {
		this.pedidoSelecionado = servico.pesquisar(pedidoSelecionado);
	}

	public void salvar() {
		pedido = servico.salvar(pedido);

		if (pedidos != null)
			pedidos.add(pedido);

		pedido = new Pedido();

	}

	public DataModel<Pedido> getPedidos() {
		if (pedidos == null)
			pedidos = servico.getPedidos();

		return new PedidoDataModel(pedidos);
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void remove(Pedido pedido) {
		if (servico.pesquisarProdutosPedido(pedido).size() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "N�o � poss�vel remover pedido",
					"Pedido possui produtos!"));
		} else {
			servico.remover(pedido);
			pedidos.remove(pedido);
		}
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Produto> getProdutosPedido() {
		if (pedidoSelecionado != null) {
			return servico.pesquisarProdutosPedido(pedidoSelecionado);
		} else
			return null;
	}

	public void onRowEdit(RowEditEvent event) {

		Pedido p = ((Pedido) event.getObject());
		servico.alterar(p);
	}

}
