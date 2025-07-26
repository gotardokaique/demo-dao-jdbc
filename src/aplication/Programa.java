package aplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.dao.DaoFabrica;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

    public static void main(String[] args) throws Exception {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        VendedorDao vendedorDao = DaoFabrica.createVendedorDao();

        System.out.println("=== TESTE 1: findById ===");
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);

        System.out.println("\n=== TESTE 2: insert ===");
        Departamento dep = new Departamento(1, null); 
        Vendedor novo = new Vendedor(null, "Teste Insert", "teste.insert@email.com", sdf.parse("15/05/1990"), 2500.0, dep);
        vendedorDao.insert(novo);
        System.out.println("Inserido! Novo id = " + novo.getId());

        System.out.println("\n=== TESTE 3: update ===");
        novo.setName("Teste Update");
        vendedorDao.update(novo);
        System.out.println("Atualizado: " + vendedorDao.findById(novo.getId()));

        System.out.println("\n=== TESTE 4: findAll ===");
        List<Vendedor> lista = vendedorDao.findAll();
        for (Vendedor ven : lista) {
            System.out.println(ven);
        }

        System.out.println("\n=== TESTE 5: delete ===");
        vendedorDao.deleteById(novo.getId());
        System.out.println("Deletado id = " + novo.getId());

    }

}
