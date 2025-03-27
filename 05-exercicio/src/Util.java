import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.text.DecimalFormat;

public class Util {

    private BilheteUnico[] bilhete = new BilheteUnico[3];
    private int index = 0;

    public void menuPrincipal() {
        int opcao = 0;
        String menu = "1. Administrador\n2. Usuário\n3. Finalizar";

        do {
            opcao = parseInt(showInputDialog(menu));
            if (opcao < 1 || opcao > 3) {
                showMessageDialog(null, "Opção inválida");
            } else {
                switch (opcao) {
                    case 1:
                        menuAdministrador();
                        break;

                    case 2:
                        menuUsuario();
                        break;
                }
            }
        } while (opcao != 3);
    }

    private void menuAdministrador() {
        int opcao;
        String menuAdmin = "1. Emitir bilhete\n";
        menuAdmin += "2. Listar bilhetes\n";
        menuAdmin += "3. Remover bilhetes\n";
        menuAdmin += "4. Sair\n";

        do {
            opcao = parseInt(showInputDialog(menuAdmin));
            if (opcao < 1 || opcao > 4) {
                showMessageDialog(null, "Opção inválido");
            } else {
                switch (opcao) {
                    case 1:
                        emitirBilhete();
                        break;
                    case 2:
                        listarBilhetes();
                        break;
                    case 3:
                        removerBilhete();
                        break;
                }
            }
        } while (opcao != 4);
    }

    private void removerBilhete() {
        int posicao = pesquisar();
        int resposta;
        if (posicao != -1) {
            resposta = showConfirmDialog(null, "Tem certeza que deseja remover: ");
            if (resposta == YES_OPTION) {
                bilhete[posicao] = bilhete[index];
                index--;
            }
        }
    }

    private void listarBilhetes() {
        String aux = "";
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < index; i++) {
            aux += "Numero do bilhete:" + bilhete[i].numero + "\n";
            aux += "Nome: " + bilhete[i].usuario.nome + "\n";
            aux += "Saldo: " + df.format(bilhete[i].consultarSaldo()) + "\n";
            aux += "Tipo de tarifa: " + bilhete[i].usuario.tipoTarifa + "\n";
            aux += "----------------------------\n";
        }
        showMessageDialog(null, aux);
    }

    private void emitirBilhete() {
        String nome;
        long cpf;
        String perfil;

        if (index < bilhete.length) {
            nome = showInputDialog("Nome:");
            cpf = parseLong(showInputDialog("CPF"));
            perfil = showInputDialog("Estudante ou Professor ou Comum");
            bilhete[index] = new BilheteUnico(new Usuario(nome, cpf, perfil));
            index++;
        }
    }

    private void menuUsuario() {
        int opcao;
        String menuUsuario = "1. Consultar Saldo\n";
        menuUsuario += "2. Carregar Bilhete\n";
        menuUsuario += "3. Passar na catraca\n";
        menuUsuario += "4. Sair\n";

        do {
            opcao = parseInt(showInputDialog(menuUsuario));
            if (opcao < 1 || opcao > 4) {
                showMessageDialog(null, "Opção inválido");
            } else {

                switch (opcao) {
                    case 1:
                        int usuarioAtivo = pesquisar();
                        if (confirmarExistencia(usuarioAtivo)) {
                            showMessageDialog(null, "Saldo: " + bilhete[usuarioAtivo].consultarSaldo());
                        }
                        break;
                    case 2:
                        usuarioAtivo = pesquisar();
                        if (confirmarExistencia(usuarioAtivo)) {
                            double valor = parseDouble(showInputDialog("Digite o valor que deseja carregar: "));
                            carregarBilhete(valor, usuarioAtivo);
                        }
                        break;
                    case 3:
                        usuarioAtivo = pesquisar();
                        if (confirmarExistencia(usuarioAtivo)) {
                            passarCatraca(usuarioAtivo);
                        }

                        break;
                }
            }
        } while (opcao != 4);

    }

    private int pesquisar() {
        long cpfP = parseLong(showInputDialog("Digite seu cpf: "));
        for (int i = 0; i < index; i++) {
            if (cpfP == bilhete[i].usuario.cpf) {
                return i;
            }
        }

        return -1;
    }

    private boolean confirmarExistencia(int usuarioAtivo) {
        if (usuarioAtivo < 0) {
            showMessageDialog(null, "Usuario Invalido");
            return false;
        } else {
            showMessageDialog(null, "Usuario: " + bilhete[usuarioAtivo].usuario.nome + " encontrado");
            return true;
        }
    }

    private void carregarBilhete(double valor, int usuarioAtivo) {
        bilhete[usuarioAtivo].saldo += valor;
        showMessageDialog(null, "Bilhete carregado com " + valor + "Saldo: " + bilhete[usuarioAtivo].saldo);
    }

    private void passarCatraca(int usuarioAtivo) {
        bilhete[usuarioAtivo].passarNaCatraca();
    }
}
